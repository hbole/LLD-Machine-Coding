package com.bookmyshow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMovieResponseDTO  {
    private Long bookingId;
    private ResponseStatus responseStatus;
}
