package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.*;
import ru.practicum.shareit.booking.mapping.BookingMapper;
import ru.practicum.shareit.booking.model.*;
import ru.practicum.shareit.exceptions.*;
import ru.practicum.shareit.item.mapping.ItemMapper;
import ru.practicum.shareit.item.ItemRepository;
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
    private final ItemRepository itemRepository;


    @Override
    public BookingDTO createBooking(Long userId, BookingDTO bookingDTO) {
        if (bookingDTO.getStart().isAfter(bookingDTO.getEnd())
                || bookingDTO.getStart().isBefore(LocalDateTime.now().minusMinutes(1)))
            throw new ValidationException("Dates error");
        userService.getUserById(userId);
        Item item = itemRepository.findById(bookingDTO.getItemId())
                .orElseThrow(() -> new NotFoundObjectException("Has no item"));
        if (item.getOwner().getId() == userId) {
            throw new NotFoundRequestException("An user cannot booking an own item");
        }

        if (!item.getAvailable()) {
            throw new ValidationException("Response error: an item is not available");
        } else bookingDTO.setItem(ItemMapper.mapperToItemDTO(item));
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

        if (booking.getItem().getOwner().getId() != itemOwnerId) {
            throw new NotFoundObjectException("Bad request:wrong owner");
        }
        if (booking.getStatus() == BookingStatus.APPROVED) {
            throw new ValidationException("Error: status already set approved");
        }
        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else booking.setStatus(BookingStatus.REJECTED);
        bookingRepository.save(booking);
        return BookingMapper.mapperToBookingDTO(booking);
    }

    @Override
    public BookingDTO getBooking(Long userId, Long bookingId) {
        userService.getUserById(userId);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundObjectException("Error: item is not found"));
        if (booking.getItem().getOwner().getId() != userId
                && booking.getBooker().getId() != userId) {
            throw new NotFoundObjectException("Error: bookings for the user is not found");
        }

        return BookingMapper.mapperToBookingDTO(booking);
    }

    @Override
    public List<BookingDTO> getBookingByUserId(String state, Long userId) {
        userService.getUserById(userId);
        BookingStatusDTO bookingStatusDTO = BookingStatusDTO.convertState(state);

        List<Booking> list = null;
        switch (bookingStatusDTO) {
            case ALL:
                list = bookingRepository.findBookingByBookerIdOrderByStartDesc(userId)
                        .orElseThrow(NullPointerException::new);
                break;
            case CURRENT:
                list = bookingRepository
                        .findAllBookingsByBookerStateCurrent(userId, LocalDateTime.now())
                        .orElseThrow(NullPointerException::new);
                break;
            case PAST:
                list = bookingRepository.findAllBookingsByBookerPastState(userId, LocalDateTime.now())
                        .orElseThrow(NullPointerException::new);
                break;
            case FUTURE:
                list = bookingRepository.findAllBookingsByBookerFutureState(userId, LocalDateTime.now())
                        .orElseThrow(NullPointerException::new);
                break;
            case WAITING:
                list = bookingRepository.findByBookerIdAndStatus(userId,
                                BookingStatusDTO.WAITING.getStatusDTO())
                        .orElseThrow(NullPointerException::new);
                break;
            case REJECTED:
                list = bookingRepository.findByBookerIdAndStatus(userId,
                                BookingStatusDTO.REJECTED.getStatusDTO())
                        .orElseThrow(NullPointerException::new);
                break;
            default:
                throw new BadRequestException("Unknown state: UNSUPPORTED_STATUS");
        };
        return list.stream()
                .map(BookingMapper::mapperToBookingDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> getBookingByOwnerId(String state, Long userId) {
        userService.getUserById(userId);
        BookingStatusDTO bookingStatusDTO = BookingStatusDTO.convertState(state);

        List<Booking> list = null;
        switch (bookingStatusDTO) {
            case ALL:
                list = bookingRepository.findAllBookingsByOwnerItemAllState(userId)
                        .orElseThrow(NullPointerException::new);
                break;
            case CURRENT:
                list = bookingRepository
                        .findAllBookingsByOwnerItemCurrentState(userId, LocalDateTime.now())
                        .orElseThrow(NullPointerException::new);
                break;
            case PAST:
                list = bookingRepository.findAllBookingsByOwnerItemPastState(userId, LocalDateTime.now())
                        .orElseThrow(NullPointerException::new);
                break;
            case FUTURE:
                list = bookingRepository.findAllBookingsByOwnerItemFutureState(userId, LocalDateTime.now())
                        .orElseThrow(NullPointerException::new);
                break;
            case WAITING:
                list = bookingRepository.findAllBookingsByOwnerItemAndStatus(userId,
                                BookingStatusDTO.WAITING.getStatusDTO())
                        .orElseThrow(NullPointerException::new);
                break;
            case REJECTED:
                list = bookingRepository.findAllBookingsByOwnerItemAndStatus(userId,
                                BookingStatusDTO.REJECTED.getStatusDTO())
                        .orElseThrow(NullPointerException::new);
                break;
            default:
                throw new BadRequestException("Unknown state: UNSUPPORTED_STATUS");
        };
        return list.stream()
                .map(BookingMapper::mapperToBookingDTO)
                .collect(Collectors.toList());
    }
}
