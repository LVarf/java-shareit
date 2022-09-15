package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDTO;
import ru.practicum.shareit.booking.dto.BookingStatusDTO;
import ru.practicum.shareit.booking.mapping.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.exceptions.NotFoundObjectException;
import ru.practicum.shareit.exceptions.UnsupportedStatusException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ItemService itemService;
    private final ItemRepository itemRepository;
    @Override
    public BookingDTO createBooking(Long userId, BookingDTO bookingDTO) {
        if(bookingDTO.getStart().isAfter(bookingDTO.getEnd())
                || bookingDTO.getStart().isBefore(LocalDateTime.now().minusMinutes(1)))
            throw new ValidationException("Dates error");
        userService.getUserById(userId);
        Item item = itemRepository.findById(bookingDTO.getItemId())
                .orElseThrow(() -> new NotFoundObjectException("Has no item"));
        if (item.getOwner().getId() == userId)
            throw new BadRequestException("An user cannot booking an own item");
        if (!item.getAvailable())
            throw new ValidationException("Response error: an item is not available");
        else bookingDTO.setItem(ItemMapper.mapperToItemDTO(item));
        bookingDTO.setBooker(userService.getUserById(userId));
        Booking booking = BookingMapper.mapperToBooking(bookingDTO, userService);
        bookingDTO = BookingMapper.mapperToBookingDTO(bookingRepository.save(booking));
        return bookingDTO;
    }

    @Override
    public BookingDTO patchApproved(Long itemOwnerId, Long bookingId, boolean approved) {
        userService.getUserById(itemOwnerId);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(NullPointerException::new);

        if(booking.getItem().getOwner().getId() != itemOwnerId)
            throw new NotFoundObjectException("Bad request:wrong owner");
        if(booking.getStatus() == BookingStatus.APPROVED)
            throw new ValidationException("Error: status already set approved");

        if(approved)
            booking.setStatus(BookingStatus.APPROVED);
        else booking.setStatus(BookingStatus.REJECTED);
        bookingRepository.save(booking);
        return BookingMapper.mapperToBookingDTO(booking);
    }

    @Override
    public BookingDTO getBooking(Long userId, Long bookingId) {
        userService.getUserById(userId);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundObjectException("Error: item is not found"));
        if (booking.getItem().getOwner().getId() != userId
                && booking.getBooker().getId() != userId)
            throw new NotFoundObjectException("Error: bookings for the user is not found");

        return BookingMapper.mapperToBookingDTO(booking);
    }

    @Override
    public List<BookingDTO> getBookingByUserId(String state, Long userId) {
        userService.getUserById(userId);

        List<Booking> list = switch (state) {
            case "ALL" -> bookingRepository.findAllBookingsByBookerAllState(userId)
                    .orElseThrow(NullPointerException::new);
            case "CURRENT" -> bookingRepository.findAllBookingsByBookerStateCurrent(userId, LocalDateTime.now())
                    .orElseThrow(NullPointerException::new);
            case "PAST" -> bookingRepository.findAllBookingsByBookerPastState(userId, LocalDateTime.now())
                    .orElseThrow(NullPointerException::new);
            case "FUTURE" -> bookingRepository.findAllBookingsByBookerFutureState(userId, LocalDateTime.now())
                    .orElseThrow(NullPointerException::new);
            case "WAITING" -> bookingRepository.findByBookerIdAndStatus(userId,
                            BookingStatusDTO.WAITING.getName())
                    .orElseThrow(NullPointerException::new);
            case "REJECTED" -> bookingRepository.findByBookerIdAndStatus(userId,
                            BookingStatusDTO.REJECTED.getName())
                    .orElseThrow(NullPointerException::new);
            default -> throw new UnsupportedStatusException("UNSUPPORTED_STATUS");
        };
        return list.stream()
                .map(BookingMapper::mapperToBookingDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> getBookingByOwnerId(String state, Long userId) {
        userService.getUserById(userId);

        List<Booking> list = switch (state) {
            case "ALL" -> bookingRepository.findAllBookingsByOwnerItemAllState(userId)
                    .orElseThrow(NullPointerException::new);
            case "CURRENT" -> bookingRepository.findAllBookingsByOwnerItemCurrentState(userId, LocalDateTime.now())
                    .orElseThrow(NullPointerException::new);
            case "PAST" -> bookingRepository.findAllBookingsByOwnerItemPastState(userId, LocalDateTime.now())
                    .orElseThrow(NullPointerException::new);
            case "FUTURE" -> bookingRepository.findAllBookingsByOwnerItemFutureState(userId, LocalDateTime.now())
                    .orElseThrow(NullPointerException::new);
            case "WAITING" -> bookingRepository.findAllBookingsByOwnerItemAndStatus(userId,
                            BookingStatusDTO.WAITING.getName())
                    .orElseThrow(NullPointerException::new);
            case "REJECTED" -> bookingRepository.findAllBookingsByOwnerItemAndStatus(userId,
                            BookingStatusDTO.REJECTED.getName())
                    .orElseThrow(NullPointerException::new);
            default -> throw new UnsupportedStatusException("UNSUPPORTED_STATUS");
        };
        return list.stream()
                .map(BookingMapper::mapperToBookingDTO)
                .collect(Collectors.toList());
    }
}
