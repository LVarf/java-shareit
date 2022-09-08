package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDTO;

import javax.validation.Valid;
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
    public BookingDTO updateStatus(Long itemOwner, @PathVariable Long bookingId) {
        return null;
    }

    @GetMapping("bookingId")
    public BookingDTO getBooking(Long requesterId, @PathVariable Long bookingId) {
        return null;
    }

    @GetMapping
    public List<BookingDTO> getBookingByUserId(@RequestParam("status") BookingStatus status, Long userId) {
        return null;
    }

    @GetMapping("/owner")
    public List<BookingDTO> getBookingItemsByOwnerId(@RequestParam("status") BookingStatus status, Long ownerId) {
        return null;
    }

}
