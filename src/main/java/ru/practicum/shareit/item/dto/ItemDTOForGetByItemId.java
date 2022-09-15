package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.dto.BookingDTO;
import ru.practicum.shareit.requests.ItemRequest;
import ru.practicum.shareit.user.dto.UserDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * // TODO .
 */
@Data
@Builder(toBuilder = true)
public class ItemDTOForGetByItemId {
    private long id;
    @NotBlank
    @NotEmpty
    private String name;
    @NotBlank
    @NotEmpty
    private String description;
    @NotNull
    private Boolean available;
    private UserDTO owner;
    private ItemRequest request;
    private BookingDTO lastBooking;
    private BookingDTO nextBooking;
    private List<CommentDTO> comments;
}
