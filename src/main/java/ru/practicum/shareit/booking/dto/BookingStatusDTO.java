package ru.practicum.shareit.booking.dto;

import lombok.Getter;

@Getter
public enum BookingStatusDTO {
    WAITING("WAITING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    CANCELED("CANCELED"),
    ALL("ALL"),
    CURRENT("CURRENT"),
    PAST("PAST"),
    FUTURE("FUTURE"),
    UNSUPPORTED_STATUS("UNSUPPORTED_STATUS");



    private final String statusDTO;

    BookingStatusDTO(String statusDTO) {
        this.statusDTO = statusDTO;
    }

    public static BookingStatusDTO convertState(String state) {
        return switch (state) {
            case "ALL" -> ALL;
            case "CURRENT" -> CURRENT;
            case "PAST" -> PAST;
            case "FUTURE" -> FUTURE;
            case "WAITING" -> WAITING;
            case "APPROVED" -> APPROVED;
            case "REJECTED" -> REJECTED;
            case "CANCELED" -> CANCELED;
            default -> UNSUPPORTED_STATUS;
        };
    }
}
