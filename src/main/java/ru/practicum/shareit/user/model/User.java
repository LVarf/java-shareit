package ru.practicum.shareit.user.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * // TODO .
 */
@Data
@Builder(toBuilder = true)
public class User {
    public long id;
    @NotBlank
    private String name;
    @Email
    @NotEmpty
    private String email;
}
