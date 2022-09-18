package ru.practicum.shareit.booking.mapping;

import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingDTO;
import ru.practicum.shareit.booking.dto.BookingStatusDTO;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.mapping.ItemMapper;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserService;

public class BookingMapper {
    public static Booking mapperToBooking(BookingDTO bookingDTO, UserService userService) {
        Booking booking = new Booking();
        booking.setEnd(bookingDTO.getEnd());
        booking.setStart(bookingDTO.getStart());
        booking.setItem(ItemMapper.mapperToItem(bookingDTO.getItem(), userService));
        booking.setBooker(
                UserMapper.mapperToUser(bookingDTO.getBooker())
        );
        booking.setStatus(statusToBooking(bookingDTO.getStatus()));
        return booking;
    }

    public static BookingDTO mapperToBookingDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setEnd(booking.getEnd());
        bookingDTO.setStart(booking.getStart());
        bookingDTO.setItem(ItemMapper.mapperToItemDTO(booking.getItem()));
        bookingDTO.setBooker(UserMapper.mapperToUserDTO(booking.getBooker()));
        bookingDTO.setStatus(statusToBookingDTO(booking.getStatus()));
        bookingDTO.setBookerId(booking.getBooker().getId());
        return bookingDTO;
    }

    private static BookingStatus statusToBooking(BookingStatusDTO statusDTO) {
        switch (statusDTO) {
            case WAITING:
                return BookingStatus.WAITING;
            case APPROVED:
                return BookingStatus.APPROVED;
            case CANCELED:
                return BookingStatus.CANCELED;
            case REJECTED:
                return BookingStatus.REJECTED;
            default:
                return null;
        }
    }

    private static BookingStatusDTO statusToBookingDTO(BookingStatus status) {
        switch (status) {
            case WAITING:
                return BookingStatusDTO.WAITING;
            case APPROVED:
                return BookingStatusDTO.APPROVED;
            case CANCELED:
                return BookingStatusDTO.CANCELED;
            case REJECTED:
                return BookingStatusDTO.REJECTED;
            default:
                return null;
        }
    }
}
