package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.dto.BookingStatusDTO;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // region methods for getBookingByUserId
    @Query(value = "select * " +
            "from bookings b " +
            "where b.booker_id = ?1 " +
            "ORDER BY b.start_date DESC ", nativeQuery = true)
    Optional<List<Booking>> findAllBookingsByBookerAllState(Long userId);

    @Query(value = "select * " +
            "from bookings b " +
            "where b.booker_id = ?1 " +
            "and b.end_date > ?2 " +
            "and b.start_date < ?2" +
            "ORDER BY b.start_date DESC", nativeQuery = true)
    Optional<List<Booking>> findAllBookingsByBookerStateCurrent(Long userId, LocalDateTime now);

    @Query(value = "select * " +
            "from bookings b " +
            "where b.booker_id = ?1 " +
            "and b.end_date < ?2 " +
            "ORDER BY b.start_date DESC ", nativeQuery = true)
    Optional<List<Booking>> findAllBookingsByBookerPastState(Long userId, LocalDateTime now);

    @Query(value = "select * " +
            "from bookings b " +
            "where b.booker_id = ?1 " +
            "and b.start_date > ?2 " +
            "ORDER BY b.start_date DESC ", nativeQuery = true)
    Optional<List<Booking>> findAllBookingsByBookerFutureState(Long userId, LocalDateTime now);

    @Query(value = "select * " +
            "from bookings b " +
            "where b.booker_id = ?1 " +
            "and b.status = ?2 " +
            "ORDER BY b.start_date DESC ", nativeQuery = true)
    Optional<List<Booking>> findByBookerIdAndStatus(Long userId, String status);

    //endregion

    @Query(value = "select * " +
            "from bookings b " +
            "left join items i " +
            "on b.item_id = i.id " +
            "where i.owner_id = ?1 " +
            "ORDER BY start_date desc;", nativeQuery = true)
    Optional<List<Booking>> findAllBookingsByOwnerItemAllState(Long userId);

    @Query(value = "select * " +
            "from bookings b " +
            "left join items i " +
            "on b.item_id = i.id " +
            "where i.owner_id = ?1 " +
            "and b.end_date > ?2 " +
            "and b.start_date < ?2" +
            "ORDER BY start_date desc;", nativeQuery = true)
    Optional<List<Booking>> findAllBookingsByOwnerItemCurrentState(Long userId, LocalDateTime now);

    @Query(value = "select * " +
            "from bookings b " +
            "left join items i " +
            "on b.item_id = i.id " +
            "where i.owner_id = ?1 " +
            "and b.end_date < ?2 " +
            "ORDER BY start_date desc;", nativeQuery = true)
    Optional<List<Booking>> findAllBookingsByOwnerItemPastState(Long userId, LocalDateTime now);

    @Query(value = "select * " +
            "from bookings b " +
            "left join items i " +
            "on b.item_id = i.id " +
            "where i.owner_id = ?1 " +
            "and b.start_date > ?2 " +
            "ORDER BY b.start_date desc;", nativeQuery = true)
    Optional<List<Booking>> findAllBookingsByOwnerItemFutureState(Long userId, LocalDateTime now);

    @Query(value = "select * " +
            "from bookings b " +
            "left join items i " +
            "on b.item_id = i.id " +
            "where i.owner_id = ?1 " +
            "and b.status = ?2 " +
            "ORDER BY b.start_date desc;", nativeQuery = true)
    Optional<List<Booking>> findAllBookingsByOwnerItemAndStatus(Long userId, String status);

    @Query(value = "select * " +
            "from bookings b " +
            "where b.item_id = ?1 " +
            "and b.end_date < ?2 " +
            "ORDER BY b.start_date desc " +
            "limit 1;", nativeQuery = true)
    Optional<List<Booking>> findLastBooking(Long itemId, LocalDateTime now);

    @Query(value = "select * " +
            "from bookings b " +
            "where b.item_id = ?1 " +
            "and b.start_date > ?2 " +
            "ORDER BY b.start_date desc " +
            "limit 1;", nativeQuery = true)
    Optional<List<Booking>> findNextBooking(Long userId, LocalDateTime now);


}
