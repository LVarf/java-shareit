package ru.practicum.shareit.user.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


/**
 * // TODO .
 */
@Entity
@Data
@Builder(toBuilder = true)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    public long id;
    @NotBlank
    @Column
    private String name;
    @Email
    @NotEmpty
    @Column
    private String email;
}


