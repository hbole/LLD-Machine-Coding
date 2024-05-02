package com.bookmyshow.services;

import com.bookmyshow.models.*;
import com.bookmyshow.respositories.ShowSeatTypeRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PriceCalculatorService {
    private ShowSeatTypeRepository showSeatTypeRepository;

    public PriceCalculatorService(ShowSeatTypeRepository showSeatTypeRepository) {
        this.showSeatTypeRepository = showSeatTypeRepository;
    }

    public int calculatePrice(List<ShowSeat> showSeats, Show show) {
        int totalAmount = 0;
        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show);
        Map<SeatType, Integer> seatTypePriceMapping = new HashMap<>();

        for(ShowSeatType showSeatType : showSeatTypes) {
            SeatType seatType = showSeatType.getSeatType();
            int seatPrice = showSeatType.getPrice();
            seatTypePriceMapping.put(seatType, seatPrice);
        }

        for(ShowSeat showSeat : showSeats) {
            Seat currentSeat = showSeat.getSeat();
            int currentPrice = seatTypePriceMapping.get(currentSeat.getSeatType());

            totalAmount += currentPrice;
        }

        return totalAmount;
    }
}
