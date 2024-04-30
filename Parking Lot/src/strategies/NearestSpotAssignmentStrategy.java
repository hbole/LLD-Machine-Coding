package strategies;

import exceptions.ParkingLotNotFoundException;
import models.Gate;
import models.ParkingSpot;
import models.VehicleType;

public class NearestSpotAssignmentStrategy implements SpotAssignmentStrategy {
    @Override
    public ParkingSpot assignParkingSpot(Gate gate, VehicleType vehicleType) throws ParkingLotNotFoundException {
        return null;
    }
}
