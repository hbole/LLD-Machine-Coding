package repositories;

import models.ParkingLot;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ParkingLotRepository {
    //DB Queries for Parking Lots
    private Map<Long, ParkingLot> parkingLots = new HashMap<>(); //Map between gate id & parking lot

    public Optional<ParkingLot> findParkingLotByGateId(Long gateId) {
        if(parkingLots.containsKey(gateId)) {
            return Optional.of(parkingLots.get(gateId));
        }
        return Optional.empty();
    }
}
