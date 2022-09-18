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
        BookingStatusDTO bookingStatusDTO;
        switch (state) {
            case "ALL":
                bookingStatusDTO = ALL;
                break;
            case "CURRENT":
                bookingStatusDTO = CURRENT;
                break;
            case "PAST":
                bookingStatusDTO = PAST;
                break;
            case "FUTURE":
                bookingStatusDTO = FUTURE;
                break;
            case "WAITING":
                bookingStatusDTO = WAITING;
                break;
            case "APPROVED":
                bookingStatusDTO = APPROVED;
                break;
            case "REJECTED":
                bookingStatusDTO = REJECTED;
                break;
            case "CANCELED":
                bookingStatusDTO = CANCELED;
            default:
                bookingStatusDTO = UNSUPPORTED_STATUS;
        }
        ;

        return bookingStatusDTO;
    }
}
