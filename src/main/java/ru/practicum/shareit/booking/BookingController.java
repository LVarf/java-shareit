package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDTO;
import ru.practicum.shareit.booking.dto.BookingStatusDTO;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * // TODO .
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingDTO createBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                    @RequestBody @Valid BookingDTO bookingDTO) {
        return bookingService.createBooking(userId, bookingDTO);
    }

    @PatchMapping("/{bookingId}")
    public BookingDTO updateStatus(@RequestHeader("X-Sharer-User-Id") Long itemOwner,
                                   @PathVariable Long bookingId,
                                   @RequestParam(value = "approved") boolean approved) {
        return bookingService.patchApproved(itemOwner, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDTO getBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                 @PathVariable("bookingId") Long bookingId) {
        return bookingService.getBooking(userId, bookingId);
    }

    @GetMapping
    public List<BookingDTO> getBookingByUserId(
            @RequestParam(value = "state", required = false,
                    defaultValue = "ALL") String state,
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.getBookingByUserId(state, userId);
    }

    @GetMapping("/owner")
    public List<BookingDTO> getBookingItemsByOwnerId(
            @RequestParam(value = "state", required = false, defaultValue = "ALL") String state,
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.getBookingByOwnerId(state, userId);
    }

}
