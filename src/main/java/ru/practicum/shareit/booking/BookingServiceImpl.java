package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDTO;
import ru.practicum.shareit.booking.mapping.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.UserService;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ItemService itemService;
    @Override
    public BookingDTO createBooking(Long userId, BookingDTO bookingDTO) {
        Booking booking = BookingMapper.mapperToBooking(bookingDTO, userService, itemService);
        return BookingMapper.mapperToBookingDTO(bookingRepository.save(booking));
    }
}
