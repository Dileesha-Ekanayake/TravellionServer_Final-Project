package lk.travel.travellion.service.transfer;

import lk.travel.travellion.entity.Transfer;
import lk.travel.travellion.entity.Transfercontract;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransferContractRelationshipMappingService {

    private final TransferRepository transferRepository;

    /**
     * Saves the given transfer after setting its pickup and drop-off locations.
     *
     * @param transfer the transfer object that needs to be saved
     */
    //Helper methods
    private void saveTransfer(Transfer transfer) {
        setTransferPickupLocations(transfer);
        setTransferDropLocations(transfer);
        transferRepository.save(transfer);
    }

    /**
     * Sets the transfer reference for each pickup location associated with the given transfer.
     *
     * @param transfer the Transfer object containing pickup locations to be updated
     */
    private void setTransferPickupLocations(Transfer transfer) {
        Optional.ofNullable(transfer.getPickuplocations())
                .ifPresent(pickupLocations ->
                        pickupLocations.forEach(pickupLocation ->
                                pickupLocation.setTransfer(transfer))
                );
    }

    /**
     * Associates the given transfer object with its drop locations by setting
     * the transfer reference in each drop location.
     *
     * @param transfer the transfer object containing the drop locations to be updated
     */
    private void setTransferDropLocations(Transfer transfer) {
        Optional.ofNullable(transfer.getDroplocations())
                .ifPresent(dropLocations ->
                        dropLocations.forEach(dropLocation ->
                                dropLocation.setTransfer(transfer))
                );
    }

    /**
     * Updates an existing {@code Transfer} with new details from another {@code Transfer} object.
     * This method finds the existing transfer in the repository by its ID and updates its pick-up
     * and drop locations based on the provided new transfer.
     * and update the isReturn too
     *
     * @param extTransfer the existing transfer to be updated, identified by its ID
     * @param newTransfer the new transfer containing updated pick-up and drop location details
     * @throws ResourceNotFoundException if the existing transfer with specified ID is not found
     */
    private void updateTransfer(Transfer extTransfer, Transfer newTransfer) {
        Transfer existingTransfer = transferRepository.findById(extTransfer.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Transfer with id " + extTransfer.getId() + " not found"));

        existingTransfer.getPickuplocations().clear();
        existingTransfer.getDroplocations().clear();

        Optional.ofNullable(newTransfer.getDroplocations()).ifPresent(droplocations -> {
            droplocations.forEach(droplocation -> {
                droplocation.setTransfer(existingTransfer);
                existingTransfer.getDroplocations().add(droplocation);
            });
        });
        Optional.ofNullable(newTransfer.getPickuplocations()).ifPresent(pickuplocations -> {
            pickuplocations.forEach(pickuplocation -> {
                pickuplocation.setTransfer(existingTransfer);
                existingTransfer.getPickuplocations().add(pickuplocation);
            });
        });
        existingTransfer.setIsreturn(newTransfer.getIsreturn());
        transferRepository.save(existingTransfer);
    }

    /**
     * Sets the relations between the given Transfercontract entity and its associated entities
     * such as transfer, transfer discounts, transfer cancellation charges, and transfer rates.
     *
     * @param transferContractEntity the Transfercontract entity containing its associated entities
     */
    protected void setTransferContractEntityRelations(Transfercontract transferContractEntity) {
        saveTransfer(transferContractEntity.getTransfer());

        Optional.ofNullable(transferContractEntity.getTransferdiscounts())
                .ifPresent(transferdiscounts ->
                        transferdiscounts.forEach(transferdiscount ->
                                transferdiscount.setTransfercontract(transferContractEntity)
                        ));
        Optional.ofNullable(transferContractEntity.getTransfercancellationcharges())
                .ifPresent(transfercancellationcharges ->
                        transfercancellationcharges.forEach(transfercancellationcharge ->
                                transfercancellationcharge.setTransfercontract(transferContractEntity)
                        ));
        Optional.ofNullable(transferContractEntity.getTransferrates())
                .ifPresent(transferrates ->
                        transferrates.forEach(transferrate ->
                                transferrate.setTransfercontract(transferContractEntity)
                        ));
    }

    /**
     * Updates the relations of a Transfercontract entity by clearing and rebuilding
     * associated collections such as transfer cancellation charges, transfer discounts,
     * and transfer rates, while also updating the transfer details.
     *
     * @param existingTransferContract the current Transfercontract entity to be updated
     * @param transferContractEntity   the new Transfercontract entity containing updated information
     */
    protected void updateTransferContractEntityRelations(Transfercontract existingTransferContract, Transfercontract transferContractEntity) {

        updateTransfer(existingTransferContract.getTransfer(), transferContractEntity.getTransfer());

        existingTransferContract.getTransfercancellationcharges().clear();
        Optional.ofNullable(transferContractEntity.getTransfercancellationcharges())
                .ifPresent(newCancellationCharge ->
                        newCancellationCharge.forEach(transfercancellationcharge -> {
                                    transfercancellationcharge.setTransfercontract(existingTransferContract);
                                    existingTransferContract.getTransfercancellationcharges().add(transfercancellationcharge);
                                }
                        ));

        existingTransferContract.getTransferdiscounts().clear();
        Optional.ofNullable(transferContractEntity.getTransferdiscounts())
                .ifPresent(newDiscounts ->
                        newDiscounts.forEach(transferdiscount -> {
                                    transferdiscount.setTransfercontract(existingTransferContract);
                                    existingTransferContract.getTransferdiscounts().add(transferdiscount);
                                }
                        ));

        existingTransferContract.getTransferrates().clear();
        Optional.ofNullable(transferContractEntity.getTransferrates())
                .ifPresent(newRates ->
                        newRates.forEach(transferrates -> {
                                    transferrates.setTransfercontract(existingTransferContract);
                                    existingTransferContract.getTransferrates().add(transferrates);
                                }
                        ));
    }
}
