package com.bookmyshow.controllers;

import com.bookmyshow.dto.BookMovieRequestDTO;
import com.bookmyshow.dto.BookMovieResponseDTO;
import com.bookmyshow.dto.ResponseStatus;
import com.bookmyshow.exceptions.ShowNotFoundException;
import com.bookmyshow.exceptions.ShowSeatNotAvailableException;
import com.bookmyshow.exceptions.UserNotFoundException;
import com.bookmyshow.models.Booking;
import com.bookmyshow.services.BookingService;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public BookMovieResponseDTO bookTicket(BookMovieRequestDTO bookMovieRequest) throws UserNotFoundException, ShowNotFoundException, ShowSeatNotAvailableException {
        BookMovieResponseDTO bookMovieResponse = new BookMovieResponseDTO();

        try {
            Booking booking = bookingService.bookTicket(
                    bookMovieRequest.getUserId(),
                    bookMovieRequest.getShowId(),
                    bookMovieRequest.getShowSeatIds()
            );
            bookMovieResponse.setBookingId(booking.getId());
            bookMovieResponse.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            bookMovieResponse.setResponseStatus(ResponseStatus.FAILURE);
        }

        return bookMovieResponse;
    }
}
