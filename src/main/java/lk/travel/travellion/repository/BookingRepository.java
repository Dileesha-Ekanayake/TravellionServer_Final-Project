package lk.travel.travellion.repository;

import lk.travel.travellion.dto.bookingdto.CancellationDetailsDTO;
import lk.travel.travellion.entity.Booking;
import lk.travel.travellion.reports.bookingtrend.BookingTrendDTO;
import lk.travel.travellion.reports.dashboard.RecentBookingDTO;
import lk.travel.travellion.reports.profitabilityanalysisbytourtype.ProfitabilityAnalysisByTourCategoryDTO;
import lk.travel.travellion.reports.profitabilityanalysisbytourtype.ProfitabilityAnalysisByTourThemeDTO;
import lk.travel.travellion.reports.profitabilityanalysisbytourtype.ProfitabilityAnalysisByTourTypeDTO;
import lk.travel.travellion.reports.profitabilityanalysisbytourtype.TotalTourBookingsAndRevenueDTO;
import lk.travel.travellion.reports.touroccupancy.TourOccupancyDTO;
import lk.travel.travellion.service.booking.RoomCountDTO;
import lk.travel.travellion.uitl.numberService.projection.BookingCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer>, JpaSpecificationExecutor<Booking> {

    BookingCode findTopByOrderByIdDesc();

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Integer id);

    Optional<Booking> findByCode(String code);


//======================================Recent Bookings==============================================================//
    @Query("""
            SELECT NEW lk.travel.travellion.reports.dashboard.RecentBookingDTO(
                    b.code,
                    bps.name,
                    function('date_format', b.createdon, '%Y-%m-%d'),
                    b.bookingstatus.name
                )
                FROM Booking b
                    JOIN Bookingpassenger bps ON bps.booking.id = b.id
                WHERE bps.leadpassenger = true
                    AND DATEDIFF(CURRENT_DATE, b.createdon) <= 3
    """)
    List<RecentBookingDTO> getRecentBookings();






//=====================================Get Already Booking Room Count===================================================//


    @Query("""
        SELECT NEW lk.travel.travellion.service.booking.RoomCountDTO(
            accmRooms.roomtype.name,
            CAST(COALESCE(SUM(bAcccmRooms.count), 0) AS integer)
        )
        FROM Accommodation accmm
            JOIN accmm.accommodationrooms accmRooms
            LEFT JOIN accmm.bookingaccommodations bAccmm ON bAccmm.accommodation.id = accmm.id
            LEFT JOIN bAccmm.bookingaccommodationrooms bAcccmRooms ON bAcccmRooms.bookingaccommodation.id = bAccmm.id
            AND bAcccmRooms.roomtype = accmRooms.roomtype.name
            LEFT JOIN bAccmm.booking b
        WHERE accmRooms.roomtype.name IN :roomTypes
            AND accmm.id = :accommId
        GROUP BY accmRooms.roomtype.name
    """)
    List<RoomCountDTO> getAllBookingRoomsCountByAccommodationIdAndRoomTypes(
            @Param("accommId") Integer accommId,
            @Param("roomTypes") List<String> roomTypes
    );








//===========================================Find Cancellation Schemes====================================================//
//===========================================Accommodation====================================================//

    @Query("""
        SELECT NEW lk.travel.travellion.dto.bookingdto.CancellationDetailsDTO(
                accommCancel.cancellationscheme.name,
                accommCancel.amount
            )
            FROM Booking b
                JOIN b.bookingaccommodations baccomm
                JOIN baccomm.accommodation accomm
                JOIN accomm.accommodationcncelationcharges accommCancel
            WHERE b.code = :bookingCode
                AND accomm.id = :bookingAccommodationId
                AND accommCancel.cancellationscheme.name = (
                    CASE
                        WHEN DATEDIFF(b.departuredate, CURRENT_DATE) >= 30 THEN 'Before 30 days'
                        WHEN DATEDIFF(b.departuredate, CURRENT_DATE) >= 15 THEN 'Before 15 days'
                        WHEN DATEDIFF(b.departuredate, CURRENT_DATE) = 0 THEN 'Booking date'
                        ELSE 'After booking date'
                    END
                )
    """)
    CancellationDetailsDTO getCancellationSchemeByBookingCodeAndAccommodationId(
            @Param("bookingCode") String bookingCode,
            @Param("bookingAccommodationId") Integer bookingAccommodationId
    );

//========================================================================================================================//


//===========================================Transfer====================================================//

    @Query("""
        SELECT NEW lk.travel.travellion.dto.bookingdto.CancellationDetailsDTO(
                tCancel.cancellationscheme.name,
                tCancel.amount
            )
        FROM Booking b
            JOIN b.bookingtransfers bTransfer
            JOIN bTransfer.transfercontract tContract
            JOIN tContract.transfercancellationcharges tCancel
        WHERE b.code = :bookingCode
            AND tContract.id = :bookingTransferId
            AND tCancel.cancellationscheme.name = (
                CASE
                    WHEN DATEDIFF(b.departuredate, CURRENT_DATE) >= 30 THEN 'Before 30 days'
                    WHEN DATEDIFF(b.departuredate, CURRENT_DATE) >= 15 THEN 'Before 15 days'
                    WHEN DATEDIFF(b.departuredate, CURRENT_DATE) = 0 THEN 'Booking date'
                    ELSE 'After booking date'
                END
        )
    """)
    CancellationDetailsDTO getCancellationSchemeByBookingCodeAndTransferId(
            @Param("bookingCode") String bookingCode,
            @Param("bookingTransferId") Integer bookingTransferId
    );

//========================================================================================================================//



//===========================================Generic====================================================//
    @Query("""
        SELECT NEW lk.travel.travellion.dto.bookingdto.CancellationDetailsDTO(
                gCancel.cancellationscheme.name,
                gCancel.amount
            )
        FROM Booking b
            JOIN b.bookinggenerics bGenerics
            JOIN bGenerics.generic generic
            JOIN generic.genericcancellationcharges gCancel
        WHERE b.code = :bookingCode
            AND generic.id = :bookingGenericId
            AND gCancel.cancellationscheme.name = (
                CASE
                    WHEN DATEDIFF(b.departuredate, CURRENT_DATE) >= 30 THEN 'Before 30 days'
                    WHEN DATEDIFF(b.departuredate, CURRENT_DATE) >= 15 THEN 'Before 15 days'
                    WHEN DATEDIFF(b.departuredate, CURRENT_DATE) = 0 THEN 'Booking date'
                    ELSE 'After booking date'
                END
            )
    """)
    CancellationDetailsDTO getCancellationSchemeByBookingCodeAndGenericId(
            @Param("bookingCode") String bookingCode,
            @Param("bookingGenericId") Integer bookingGenericId
    );

//========================================================================================================================//








//===================================================================================================================================//

    @Query("SELECT COALESCE(SUM(ba.supplieramount), 0) FROM Bookingaccommodation ba JOIN ba.accommodation accmm WHERE accmm.supplier.brno =:supplierBrno")
    BigDecimal findTotalAccommodationSupplierAmount(@Param("supplierBrno") String supplierBrno);

    @Query("SELECT COALESCE(SUM(bt.suppliersamount), 0) FROM Bookingtour bt JOIN bt.tourcontract.touraccommodations ta JOIN ta.accommodation accmm WHERE accmm.supplier.brno =:supplierBrno")
    BigDecimal findTotalAccommodationSupplierAmountByTour(@Param("supplierBrno") String supplierBrno);


    @Query("SELECT COALESCE(SUM(bt.supplieramount), 0) FROM Bookingtransfer bt JOIN bt.transfercontract transfer WHERE transfer.supplier.brno =:supplierBrno")
    BigDecimal findTotalTransferSupplierAmount(@Param("supplierBrno") String supplierBrno);

    @Query("SELECT COALESCE(SUM(bt.suppliersamount), 0) FROM Bookingtour bt JOIN bt.tourcontract.tourtransfercontracts ta JOIN ta.transfercontract trc WHERE trc.supplier.brno =:supplierBrno")
    BigDecimal findTotalTransferSupplierAmountByTour(@Param("supplierBrno") String supplierBrno);


    @Query("SELECT COALESCE(SUM(bg.supplieramount), 0) FROM Bookinggeneric bg JOIN bg.generic generic WHERE generic.supplier.brno =:supplierBrno")
    BigDecimal findTotalGenericSupplierAmount(@Param("supplierBrno") String supplierBrno);

    @Query("SELECT COALESCE(SUM(bt.suppliersamount), 0) FROM Bookingtour bt JOIN bt.tourcontract.tourgenerics tg JOIN tg.generic gc WHERE gc.supplier.brno =:supplierBrnoó")
    BigDecimal findTotalGenericSupplierAmountByTour(@Param("supplierBrno") String supplierBrno);

    Long countByCreatedonBetween(Timestamp createdonAfter, Timestamp createdonBefore);








//================================================Get Pending and Overdue payments========================================//

    @Query("SELECT COALESCE(COUNT(b.id), 0) FROM Booking b WHERE b.balance <> 0")
    Long getAllPendingPaymentsBookings();

    @Query("SELECT COALESCE(COUNT(b.id), 0) FROM Booking b WHERE " +
            "DATEDIFF(b.departuredate, b.createdon) > 3 OR b.bookingstatus.name='Pending'")
    Long getAllOverduePaymentsBookings();

//========================================================================================================================//








//============================================Get All Booking Tour Count==========================================================//

    @Query("SELECT COALESCE(COUNT(bt.id), 0) FROM Bookingtour bt")
    Long getAllBookingsTourCount();

    @Query("SELECT COALESCE(COUNT(bt.id), 0) FROM Bookingtour bt WHERE bt.booking.bookingstatus.name = 'Confirmed'")
    Long getAllConfirmedBookingsTourCount();

    @Query("SELECT COALESCE(COUNT(bt.id), 0) FROM Bookingtour bt WHERE bt.booking.bookingstatus.name = 'Pending'")
    Long getAllPendingBookingsTourCount();

    @Query("SELECT COALESCE(COUNT(bt.id), 0) FROM Bookingtour bt " +
            "WHERE bt.booking.createdon BETWEEN :startDate AND :endDate")
    Long getAllBookingsTourCountByDateRange(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);


    @Query("SELECT COALESCE(COUNT(bt.id), 0) FROM Bookingtour bt " +
            "WHERE bt.booking.bookingstatus.name = 'Pending' AND bt.booking.createdon BETWEEN :startDate AND :endDate")
    Long getAllPendingBookingsTourCountByDateRange(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);







//==================================Get Sum amount for all Booking's Accommodations========================================//
    @Query("SELECT COALESCE(SUM(ba.totalamount - ba.supplieramount), 0) FROM Bookingaccommodation ba")
    BigDecimal getTotalProfitAllBookingAccommodations();

    @Query("SELECT COALESCE(SUM(ba.totalamount - ba.supplieramount), 0) FROM Bookingaccommodation ba " +
            "JOIN Booking b ON ba.booking.id = b.id " +
            "WHERE b.bookingstatus.id = :statusId")
    BigDecimal getTotalBookingAccommodationProfitByBookingStatus(@Param("statusId") Integer statusId);

    @Query("SELECT COALESCE(SUM(ba.totalamount - ba.supplieramount), 0) FROM Bookingaccommodation ba " +
            "WHERE ba.booking.createdon  BETWEEN :startDate AND :endDate")
    BigDecimal getTotalBookingAccommodationProfitByBookingDateRange(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    @Query("SELECT COALESCE(SUM(ba.totalamount - ba.supplieramount), 0) FROM Bookingaccommodation ba " +
            "WHERE ba.fromdatetime BETWEEN :startDate AND :endDate")
    BigDecimal getTotalBookingAccommodationProfitByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COALESCE(SUM(ba.totalamount - ba.supplieramount), 0) FROM Bookingaccommodation ba " +
            "JOIN Booking b ON ba.booking.id = b.id " +
            "WHERE b.bookingstatus.id = :statusId " +
            "AND ba.fromdatetime BETWEEN :startDate AND :endDate")
    BigDecimal getTotalBookingAccommodationProfitByStatusAndDateRange(@Param("statusId") Integer statusId,
                                                  @Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate);
//===================================================================================================================================//







//=================================Get Sum amount for all Booking's Transfers===========================================//
    @Query("SELECT COALESCE(SUM(tr.totalamount - tr.supplieramount), 0) FROM Bookingtransfer tr")
    BigDecimal getTotalProfitAllBookingTransferContracts();

    @Query("SELECT COALESCE(SUM(tr.totalamount - tr.supplieramount), 0) FROM Bookingtransfer tr " +
            "JOIN Booking b ON tr.booking.id = b.id " +
            "WHERE b.bookingstatus.id = :statusId")
    BigDecimal getTotalBookingTransferContractProfitByBookingStatus(@Param("statusId") Integer statusId);

    @Query("SELECT COALESCE(SUM(tr.totalamount - tr.supplieramount), 0) FROM Bookingtransfer tr " +
            "WHERE tr.booking.createdon BETWEEN :startDate AND :endDate")
    BigDecimal getTotalBookingTransferContractProfitByBookingDateRange(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    @Query("SELECT COALESCE(SUM(tr.totalamount - tr.supplieramount), 0) FROM Bookingtransfer tr " +
            "WHERE tr.fromdatetime BETWEEN :startDate AND :endDate")
    BigDecimal getTotalBookingTransferContractProfitByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COALESCE(SUM(tr.totalamount - tr.supplieramount), 0) FROM Bookingtransfer tr " +
            "JOIN Booking b ON tr.booking.id = b.id " +
            "WHERE b.bookingstatus.id = :statusId " +
            "AND tr.fromdatetime BETWEEN :startDate AND :endDate")
    BigDecimal getTotalBookingTransferContractProfitByStatusAndDateRange(@Param("statusId") Integer statusId,
                                                                      @Param("startDate") LocalDate startDate,
                                                                      @Param("endDate") LocalDate endDate);
//===================================================================================================================================//






//================================Get Sum amount for all Booking's Generics==============================================//
    @Query("SELECT COALESCE(SUM(bg.totalamount - bg.supplieramount), 0) FROM Bookinggeneric bg")
    BigDecimal getTotalProfitAllBookingGenerics();

    @Query("SELECT COALESCE(SUM(bg.totalamount - bg.supplieramount), 0) FROM Bookinggeneric bg " +
            "JOIN Booking b ON bg.booking.id = b.id " +
            "WHERE b.bookingstatus.id = :statusId")
    BigDecimal getTotalBookingGenericProfitByBookingStatus(@Param("statusId") Integer statusId);

    @Query("SELECT COALESCE(SUM(bg.totalamount - bg.supplieramount), 0) FROM Bookinggeneric bg " +
            "WHERE bg.booking.createdon BETWEEN :startDate AND :endDate")
    BigDecimal getTotalBookingGenericProfitByBookingDateRange(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    @Query("SELECT COALESCE(SUM(bg.totalamount - bg.supplieramount), 0) FROM Bookinggeneric bg " +
            "WHERE bg.fromdatetime BETWEEN :startDate AND :endDate")
    BigDecimal getTotalBookingGenericProfitByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COALESCE(SUM(bg.totalamount - bg.supplieramount), 0) FROM Bookinggeneric bg " +
            "JOIN Booking b ON bg.booking.id = b.id " +
            "WHERE b.bookingstatus.id = :statusId " +
            "AND bg.fromdatetime BETWEEN :startDate AND :endDate")
    BigDecimal getTotalBookingGenericProfitByStatusAndDateRange(@Param("statusId") Integer statusId,
                                                  @Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate);
//===================================================================================================================================//







//=====================================Get Sum amount for all Booking's Tours==============================================//
    @Query("SELECT COALESCE(SUM(to.totalamount - to.suppliersamount), 0) FROM Bookingtour to")
    BigDecimal getTotalProfitAllBookingTours();

    @Query("SELECT COALESCE(SUM(to.totalamount - to.suppliersamount), 0) FROM Bookingtour to " +
            "JOIN Booking b ON to.booking.id = b.id " +
            "WHERE b.bookingstatus.id = :statusId")
    BigDecimal getTotalBookingTourProfitByBookingStatus(@Param("statusId") Integer statusId);

    @Query("SELECT COALESCE(SUM(to.totalamount - to.suppliersamount), 0) FROM Bookingtour to " +
            "WHERE to.booking.createdon BETWEEN :startDate AND :endDate")
    BigDecimal getTotalBookingTourProfitByBookingDateRange(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    @Query("SELECT COALESCE(SUM(to.totalamount - to.suppliersamount), 0) FROM Bookingtour to " +
            "WHERE to.tourcontract.salesfrom BETWEEN :startDate AND :endDate")
    BigDecimal getTotalBookingTourProfitByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COALESCE(SUM(to.totalamount - to.suppliersamount), 0) FROM Bookingtour to " +
            "JOIN Booking b ON to.booking.id = b.id " +
            "WHERE b.bookingstatus.id = :statusId " +
            "AND to.tourcontract.salesfrom BETWEEN :startDate AND :endDate")
    BigDecimal getTotalBookingTourProfitByStatusAndDateRange(@Param("statusId") Integer statusId,
                                                                @Param("startDate") LocalDate startDate,
                                                                @Param("endDate") LocalDate endDate);
//===================================================================================================================================//







//========================================BOOKING TREND===============================================================//

    @Query("""
            SELECT NEW lk.travel.travellion.reports.bookingtrend.BookingTrendDTO(
                    EXTRACT(YEAR FROM b.departuredate),
                    EXTRACT(MONTH FROM b.departuredate),
                    MONTHNAME(b.departuredate),
                    CAST(COUNT(CASE WHEN b.bookingstatus.id = 3 THEN 1 END) AS BigDecimal),
                    CAST(COUNT(CASE WHEN b.bookingstatus.id = 4 THEN 1 END) AS BigDecimal),
                    CAST(COUNT(CASE WHEN b.bookingstatus.id = 2 THEN 1 END) AS BigDecimal),
                    CAST(COUNT(b) AS BigDecimal),
                    CAST(COUNT(CASE WHEN b.enddate < CURRENT_DATE THEN 1 END) AS BigDecimal)
                )
                FROM Booking b
            WHERE b.departuredate BETWEEN :startDate AND :endDate
            GROUP BY EXTRACT(YEAR FROM b.departuredate), EXTRACT(MONTH FROM b.departuredate)
            ORDER BY EXTRACT(YEAR FROM b.departuredate), EXTRACT(MONTH FROM b.departuredate)
        """)
    List<BookingTrendDTO> getMonthlyBookingCount(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


//===================================================================================================================================//







//================================================Tour Occupancy==============================================================//
    @Query("""
        SELECT NEW lk.travel.travellion.reports.touroccupancy.TourOccupancyDTO(
            EXTRACT(YEAR FROM b.departuredate),
            EXTRACT(MONTH FROM b.departuredate),
            MONTHNAME(b.departuredate),
            tt.name,
            (SELECT COUNT(tc2.id) FROM Tourcontract tc2 WHERE tc2.tourtype.id = tt.id),
            COALESCE(COUNT(bps.id), 0),
            tc.maxpaxcount
        )
        FROM Booking b
            JOIN b.bookingpassengers bps ON b.id = bps.booking.id
            JOIN b.bookingtours bt ON b.id = bt.booking.id
            JOIN bt.tourcontract tc ON bt.tourcontract.id = tc.id
            JOIN tc.tourtype tt ON tc.tourtype.id = tt.id
        WHERE b.departuredate BETWEEN :startDate AND :endDate
        GROUP BY EXTRACT(YEAR FROM b.departuredate),
                 EXTRACT(MONTH FROM b.departuredate),
                 MONTHNAME(b.departuredate),
                 tt.id,
                 tt.name
        ORDER BY EXTRACT(YEAR FROM b.departuredate), EXTRACT(MONTH FROM b.departuredate)
    """)
    List<TourOccupancyDTO> getTourOccupancyBuTourTypes(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);





//================================================Profitability Analysis By Tour Type==============================================================//

    @Query("""
        SELECT NEW lk.travel.travellion.reports.profitabilityanalysisbytourtype.ProfitabilityAnalysisByTourCategoryDTO(
            tc.name,
            CAST( COALESCE(SUM(b.totalpaid), 0) AS bigdecimal )
        )
        FROM Tourcategory tc
            LEFT JOIN tc.tourcontracts tourCon
            LEFT JOIN tourCon.bookingtours bookingTr
            LEFT JOIN bookingTr.booking b
        GROUP BY tc.name
    """)
    List<ProfitabilityAnalysisByTourCategoryDTO> getProfitabilityAnalysisByTourCategory();






//================================================Profitability Analysis By Tour Theme==============================================================//

    @Query("""
        SELECT NEW lk.travel.travellion.reports.profitabilityanalysisbytourtype.ProfitabilityAnalysisByTourThemeDTO(
        th.name,
        CAST( COALESCE(SUM(b.totalpaid), 0) as bigdecimal )
        )
        FROM Tourtheme th
            LEFT JOIN th.tourcontracts tourCon
            LEFT JOIN tourCon.bookingtours bookingTr
            LEFT JOIN bookingTr.booking b
        GROUP BY th.name
    """)
    List<ProfitabilityAnalysisByTourThemeDTO> getProfitabilityAnalysisByTourTheme();






//================================================Profitability Analysis By Tour Type==============================================================//


    @Query("""
        SELECT NEW lk.travel.travellion.reports.profitabilityanalysisbytourtype.ProfitabilityAnalysisByTourTypeDTO(
            tt.name,
            CAST( COALESCE(SUM(b.totalpaid), 0) AS bigdecimal)
        )
        FROM Tourtype tt
            LEFT JOIN tt.tourcontracts tourCon
            LEFT JOIN tourCon.bookingtours bookingTr
            LEFT JOIN bookingTr.booking b
        GROUP BY tt.name
    """)
    List<ProfitabilityAnalysisByTourTypeDTO> getProfitabilityAnalysisByTourType();







//================================================Total Tour Booking adn Revenue==============================================================//

    @Query("""
        SELECT NEW lk.travel.travellion.reports.profitabilityanalysisbytourtype.TotalTourBookingsAndRevenueDTO(
            CAST(COALESCE(COUNT(b.id), 0) AS integer),
            CAST(COALESCE(SUM(btr.totalamount), 0) AS bigdecimal),
            CAST(COALESCE(SUM(btr.suppliersamount), 0) AS bigdecimal)
        )
        FROM Bookingtour btr
        JOIN btr.booking b ON btr.booking.id = b.id
    """)
    TotalTourBookingsAndRevenueDTO getTotalTourBookingsAndRevenue();
}
