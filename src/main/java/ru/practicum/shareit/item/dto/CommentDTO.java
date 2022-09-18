package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.user.dto.UserDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    @NotNull
    @NotBlank
    private String text;
    private String authorName;
    private ItemDTOForGetByItemId item;
    private UserDTO author;
    private LocalDateTime created = LocalDateTime.now();
}
