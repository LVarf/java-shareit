package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDTO;

public interface BookingService {
    BookingDTO createBooking(Long userId, BookingDTO bookingDTO);
}
