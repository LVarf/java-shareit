package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDTO;

import java.util.List;

public interface BookingService {
    BookingDTO createBooking(Long userId, BookingDTO bookingDTO);
    BookingDTO patchApproved(Long itemId, Long bookingId, boolean approved);
    BookingDTO getBooking(Long userId, Long bookingId);
    List<BookingDTO> getBookingByUserId(String state, Long userId);
    List<BookingDTO> getBookingByOwnerId(String state, Long userId);
}
