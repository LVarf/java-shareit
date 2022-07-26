package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder(toBuilder = true)
public class UserDTO {
    private long id;
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
}
