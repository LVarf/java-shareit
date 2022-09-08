package ru.practicum.shareit.booking.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * // TODO .
 */
@Data
public class BookingDTO {
    /*@NotNull
    @NotBlank*/
    private Long itemId;
    /*@NotNull
    @NotBlank*/
    private LocalDateTime startBooking;
    /*@NotNull
    @NotBlank*/
    private LocalDateTime endBooking;

   /* @NotNull
    @NotBlank*/
    private Long bookerId;
    private BookingStatusDTO status = BookingStatusDTO.WAITING;
}
