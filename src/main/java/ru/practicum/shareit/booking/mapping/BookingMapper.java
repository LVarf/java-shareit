package ru.practicum.shareit.booking.mapping;

import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingDTO;
import ru.practicum.shareit.booking.dto.BookingStatusDTO;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserService;

public class BookingMapper {
    public static Booking mapperToBooking(BookingDTO bookingDTO, UserService userService, ItemService itemService) {
        Booking booking = new Booking();
        booking.setEndBooking(bookingDTO.getEndBooking());
        booking.setStartBooking(bookingDTO.getStartBooking());
        booking.setItem(ItemMapper.mapperToItem(itemService.getItemByItemId(bookingDTO.getItemId()), userService));
        booking.setBooker(
                UserMapper.mapperToUser(userService.getUserById(bookingDTO.getBookerId()))
        );
        booking.setStatus(statusToBooking(bookingDTO.getStatus()));
        return booking;
    }

    public static BookingDTO mapperToBookingDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setEndBooking(booking.getEndBooking());
        bookingDTO.setStartBooking(booking.getStartBooking());
        bookingDTO.setItemId(booking.getItem().getId());
        bookingDTO.setBookerId(booking.getBooker().getId());
        bookingDTO.setStatus(statusToBookingDTO(booking.getStatus()));
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
