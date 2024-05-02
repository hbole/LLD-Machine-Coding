package com.bookmyshow.services;

import com.bookmyshow.exceptions.ShowNotFoundException;
import com.bookmyshow.exceptions.ShowSeatNotAvailableException;
import com.bookmyshow.exceptions.UserNotFoundException;
import com.bookmyshow.models.*;
import com.bookmyshow.respositories.BookingRepository;
import com.bookmyshow.respositories.ShowRepository;
import com.bookmyshow.respositories.ShowSeatRepository;
import com.bookmyshow.respositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private BookingRepository bookingRepository;
    private PriceCalculatorService priceCalculatorService;

    public BookingService(
            UserRepository userRepository,
            ShowRepository showRepository,
            ShowSeatRepository showSeatRepository,
            BookingRepository bookingRepository,
            PriceCalculatorService priceCalculatorService
    ) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.bookingRepository = bookingRepository;
        this.priceCalculatorService = priceCalculatorService;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookTicket(Long userId, Long showId, List<Long> showSeatIds) throws UserNotFoundException, ShowNotFoundException, ShowSeatNotAvailableException {
        //1. Get the user with the given userId.
        //2. Get the show with the given showId.
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Show> optionalShow = showRepository.findById(showId);

        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("Invalid user id");
        }
        User user = optionalUser.get();

        if(optionalShow.isEmpty()) {
            throw new ShowNotFoundException("Invalid show id");
        }
        Show show = optionalShow.get();

        //3. Get the list of showSeats with the given showSeatIds.
        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        //4. Check the status of all the showSeat objects.
        for(ShowSeat showSeat : showSeats) {
            if(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)) {
                throw new ShowSeatNotAvailableException("Show seat is not available");
            }
        }

        //5. If Not available then throw an Exception.
        //6. If yes, then block the seats.
        //------------------ TAKE A LOCK -------------------
        //7. Check the status of the seats again
        //8. Save the status in the db.
        List<ShowSeat> savedShowSeats = new ArrayList<>();
        for(ShowSeat showSeat : showSeats) {
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            savedShowSeats.add(showSeatRepository.save(showSeat));
        }

        //---------------- RELEASE THE LOCK ----------------
        //9. Create the booking object.
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShowSeats(savedShowSeats);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setAmount(priceCalculatorService.calculatePrice(savedShowSeats, show));

        //10. Return the booking object.
        Booking savedBooking = bookingRepository.save(booking);
        return savedBooking;
    }
}
