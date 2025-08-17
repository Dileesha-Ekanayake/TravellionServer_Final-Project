package lk.travel.travellion.service.employee;

import lk.dileesha.jpafilter.SpecificationBuilder;
import lk.dileesha.jpavalidator.DuplicateValidator;
import lk.travel.travellion.dto.employeedto.EmployeeListDTO;
import lk.travel.travellion.dto.employeedto.EmployeeRequestDTO;
import lk.travel.travellion.dto.employeedto.EmployeeResponseDTO;
import lk.travel.travellion.dto.employeedto.EmployeeforUserDetailsDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Employee;
import lk.travel.travellion.entity.User;
import lk.travel.travellion.entity.Userstatus;
import lk.travel.travellion.exceptions.ForeignKeyConstraintViolationException;
import lk.travel.travellion.exceptions.ResourceAlreadyExistException;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.repository.EmployeeRepository;
import lk.travel.travellion.repository.UserRepository;
import lk.travel.travellion.repository.UserStatusRepository;
import lk.travel.travellion.uitl.numberService.NumberService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    // User Related
    @Value("${user.status.locked}")
    private int locked;
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;

    // Employee Related
    private final EmployeeRepository employeeRepository;
    private final ObjectMapper objectMapper;
    private final NumberService numberService;

    private final SpecificationBuilder specificationBuilder;

    private final DuplicateValidator duplicateValidator;

    /**
     * Retrieves a list of all employees, optionally filtered by the specified filter criteria.
     *
     * @param filters a HashMap containing key-value pairs for filtering employee data.
     *                If null or empty, no filters are applied and all employees are returned.
     * @return a list of EmployeeResponseDTO objects representing the employees,
     *         filtered based on the specified criteria.
     * @throws ResourceNotFoundException if an invalid filter key is provided in the filters.
     */
    @Transactional(readOnly = true)
    @Override
    public List<EmployeeResponseDTO> getAllEmployees(HashMap<String, String> filters) {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponseDTO> employeeDTOs = objectMapper.toEmployeeResponseDTO(employees);

        if (filters == null || filters.isEmpty()) {
            return employeeDTOs;
        }

        try {
            Specification<Employee> employeeSpecification = specificationBuilder.createFilterSpecifications(filters);
            return objectMapper.toEmployeeResponseDTO(employeeRepository.findAll(employeeSpecification));
        }catch (PathElementException e){
            throw new ResourceNotFoundException("No such filter key found : " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of employees who have an employee status of "Assigned".
     *
     * @return a list of EmployeeListDTO objects representing employees with the "Assigned" status
     */
    @Transactional(readOnly = true)
    @Override
    public List<EmployeeListDTO> getEmployeeList() {
        List<Employee> employees = employeeRepository.findAll()
                .stream()
                .filter(employee -> "Assigned".equals(employee.getEmployeestatus().getName()))
                .toList();
        return objectMapper.toEmployeeListDTOs(employees);
    }

    /**
     * Retrieves the details of an employee for user-specific purposes based on the provided employee ID.
     *
     * @param employeeId the unique identifier of the employee whose details are to be retrieved
     * @return an EmployeeforUserDetailsDTO object containing the employee's details
     * @throws ResourceNotFoundException if no employee is found with the specified employeeId
     */
    @Transactional(readOnly = true)
    @Override
    public EmployeeforUserDetailsDTO getAllEmployeeForUserDetails(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id : " + employeeId + " not found"));

        return objectMapper.toEmployeeForUserDetailsDTO(employee);
    }

    /**
     * Retrieves the Employee details associated with the given username.
     *
     * @param username the username of the logged-in user
     * @return an EmployeeforUserDetailsDTO containing the employee details associated with the username
     * @throws ResourceNotFoundException if no employee is found for the given username
     */
    @Transactional(readOnly = true)
    @Override
    public EmployeeforUserDetailsDTO getEmployeeByLogInUser(String username) {
        return userRepository.findByUsername(username)
                .map(User::getEmployee)
                .map(objectMapper::toEmployeeForUserDetailsDTO)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found with user_name" + username));
    }

    /**
     * Retrieves the generated employee number.
     *
     * @return a string representing the generated employee number
     */
    @Transactional(readOnly = true)
    @Override
    public String getEmployeeNumber() {
        return numberService.generateEmployeeNumber();
    }

    /**
     * Saves a new employee to the database after validating the input and ensuring
     * there are no existing employees with the same details.
     *
     * @param employeeRequestDTO the data transfer object containing information about the employee to be saved
     * @return the saved Employee entity
     * @throws ResourceAlreadyExistException if an employee with the same details already exists
     */
    @Override
    public Employee saveEmployee(EmployeeRequestDTO employeeRequestDTO) {

        // Check for existing employee
        List<String> existingValues = checkExisting(employeeRequestDTO, null, true);
        if (!existingValues.isEmpty()) {
            throw new ResourceAlreadyExistException("Employee with " + String.join(" or ", existingValues) + " already exists");
        }
        return employeeRepository.save(objectMapper.toEmployeeEntity(employeeRequestDTO));
    }

    /**
     * Updates the details of an existing employee based on the provided employee request data.
     * Performs validation to ensure no conflicts with other existing employees.
     *
     * @param employeeRequestDTO The data transfer object containing the employee details to update.
     * @return The updated Employee entity after successful update and validation.
     * @throws ResourceNotFoundException If no employee with the given number exists.
     * @throws ResourceAlreadyExistException If another employee exists with conflicting fields.
     */
    @Override
    public Employee updateEmployee(EmployeeRequestDTO employeeRequestDTO) {

        // Check for existing employee (number)
        Employee existingEmployee = employeeRepository.findByNumber(employeeRequestDTO.getNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Employee with number " + employeeRequestDTO.getNumber() + " not found"));

        // Check for existing fields, but ignore this employee's own ID
        List<String> existingFields = checkExisting(employeeRequestDTO, existingEmployee.getId(), true);

        if (!existingFields.isEmpty()) {
            throw new ResourceAlreadyExistException("Employee with " + String.join(" or ", existingFields) + " already exists");
        }
        Employee updatedEmployee = employeeRepository.save(objectMapper.toEmployeeEntity(employeeRequestDTO));
        updateStatusOfUsersWithEmployee(updatedEmployee);
        return updatedEmployee;
    }

    /**
     * Deletes an employee with the specified id. If the employee is not found,
     * a ResourceNotFoundException is thrown. If the deletion fails due to a foreign
     * key constraint, a ForeignKeyConstraintViolationException is thrown.
     *
     * @param id the unique identifier of the employee to be deleted
     * @throws ResourceNotFoundException if the employee with the specified id does not exist
     * @throws ForeignKeyConstraintViolationException if the employee cannot be deleted due to
     *         a foreign key constraint
     */
    @Override
    public void deleteEmployee(Integer id) {
        try {
            employeeRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Employee with id " + id + " not found"));
            employeeRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("Cannot delete employee: Caused by having a user account");
        }
    }

    /**
     * Checks the existence of given employee details in the database.
     *
     * @param employeeRequestDTO the employee data transfer object containing the details to be checked
     * @param currentId the ID of the current employee, used to exclude it from the validation process
     * @param includeValue a flag indicating whether the values being checked should be included in the validation
     * @return a list of fields with values that already exist in the database
     */
    private List<String> checkExisting(EmployeeRequestDTO employeeRequestDTO, Integer currentId, boolean includeValue) {
        Map<String, String> fieldsToValidate = new HashMap<>();
        fieldsToValidate.put("number", employeeRequestDTO.getNumber());
        fieldsToValidate.put("nic", employeeRequestDTO.getNic());
        fieldsToValidate.put("mobile", employeeRequestDTO.getMobile());
        fieldsToValidate.put("email", employeeRequestDTO.getEmail());

        return duplicateValidator.checkDuplicates(Employee.class, fieldsToValidate, "id", currentId, includeValue);
    }

    /**
     * Updates the status of users associated with the provided employee if the employee's status
     * is not "Assigned". The method locks the users' accounts and updates their status in the system.
     *
     * @param employee The {@code Employee} object whose associated users' statuses need to be updated.
     *                 If the employee's status is not "Assigned", the users' accounts will be locked
     *                 and their status will be updated in the database.
     */
    private void updateStatusOfUsersWithEmployee(Employee employee) {

        if (!employee.getEmployeestatus().getName().equalsIgnoreCase("Assigned")) {
            try {
                List<User> userList = userRepository.findUserNameByEmployee(employee);
                if (userList != null && !userList.isEmpty()) {
                    for (User user : userList) {
                        if (user != null) {
                            boolean isLocked = Boolean.TRUE.equals(user.isAccountLocked());
                            if (!isLocked) {
                                int statusId = locked;
                                Userstatus status = userStatusRepository.findById(statusId)
                                        .orElseThrow(() -> new ResourceNotFoundException("User status not found"));

                                user.setAccountLocked(Boolean.TRUE);
                                user.setUserstatus(status);
                                userRepository.save(user);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new ResourceNotFoundException("Error updating user status: " + e.getMessage());
            }
        }
    }
}
