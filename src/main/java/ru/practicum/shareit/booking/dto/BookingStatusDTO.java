package ru.practicum.shareit.booking.dto;

import lombok.Getter;

@Getter
public enum BookingStatusDTO {
    WAITING("WAITING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    CANCELED("CANCELED");

    private String name;

    BookingStatusDTO(String name) {
        this.name = name;
    }
}
