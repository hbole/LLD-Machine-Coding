package strategies;

import exceptions.ParkingLotNotFoundException;
import models.Gate;
import models.ParkingSpot;
import models.VehicleType;

public interface SpotAssignmentStrategy {
    ParkingSpot assignParkingSpot(Gate gate, VehicleType vehicleType) throws ParkingLotNotFoundException;
}
