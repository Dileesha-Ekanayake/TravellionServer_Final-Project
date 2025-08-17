package lk.travel.travellion.repository;

import lk.travel.travellion.entity.Customerpayment;
import lk.travel.travellion.uitl.numberService.projection.CustomerpaymentCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public interface CustomerpaymentRepository extends JpaRepository<Customerpayment, Integer>, JpaSpecificationExecutor<Customerpayment> {
    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Integer id);

    CustomerpaymentCode findTopByCustomerIdOrderByIdDesc(Integer customerId);

    int countByBookingCode(String bookingCode);

    @Query("SELECT COALESCE(SUM(cp.paidamount), 0) FROM Customerpayment cp WHERE cp.booking.code = :bookingCode")
    BigDecimal sumAmountByBookingCode(@Param("bookingCode") String bookingCode);








    @Query("""
        SELECT
            EXTRACT(YEAR FROM cp.createdon),
            EXTRACT(MONTH FROM cp.createdon),
            MONTHNAME(cp.createdon),
            COALESCE(SUM(CASE WHEN cp.balance = 0 THEN cp.paidamount ELSE 0 END), 0),
            COALESCE(SUM(CASE WHEN cp.balance <> 0 THEN cp.paidamount ELSE 0 END), 0),
            COALESCE(SUM(CASE WHEN DATEDIFF(CURRENT_DATE, cp.booking.departuredate)
                BETWEEN -3 AND 0 AND cp.balance <> 0 THEN cp.balance ELSE 0 END), 0)
        FROM Customerpayment cp
        WHERE cp.createdon BETWEEN :startDate AND :endDate
        GROUP BY EXTRACT(YEAR FROM cp.createdon), EXTRACT(MONTH FROM cp.createdon), MONTHNAME(cp.createdon)
        ORDER BY EXTRACT(YEAR FROM cp.createdon), EXTRACT(MONTH FROM cp.createdon)
    """)
    List<Object[]> getCustomerPaymentCollection(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);


}
