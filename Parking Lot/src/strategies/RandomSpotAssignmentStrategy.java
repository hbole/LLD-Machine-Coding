package strategies;

import exceptions.ParkingLotNotFoundException;
import models.*;
import repositories.ParkingLotRepository;

import java.util.List;
import java.util.Optional;

public class RandomSpotAssignmentStrategy implements SpotAssignmentStrategy {
    private ParkingLotRepository parkingLotRepository;

    public RandomSpotAssignmentStrategy(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    @Override
    public ParkingSpot assignParkingSpot(Gate gate, VehicleType vehicleType) throws ParkingLotNotFoundException {
        //Get parking lot by gate id
        Optional<ParkingLot> optionalParkingLot = parkingLotRepository.findParkingLotByGateId(gate.getId());
        ParkingLot parkingLot;

        if(optionalParkingLot.isPresent()) {
            parkingLot = optionalParkingLot.get();
        } else {
            throw new ParkingLotNotFoundException("No ParkingLot Associated With This Gate");
        }

        List<ParkingFloor> parkingFloors = parkingLot.getParkingFloors();
        for(ParkingFloor parkingFloor : parkingFloors) { //5 to 10 Floors.
            List<ParkingSpot> parkingSpots = parkingFloor.getParkingSpots();

            for(ParkingSpot parkingSpot : parkingSpots) {
                List<VehicleType> supportedVehicleType = parkingSpot.getVehicleTypes();
                ParkingSpotStatus parkingSpotStatus = parkingSpot.getParkingSpotStatus();

                if(supportedVehicleType.contains(vehicleType) && parkingSpotStatus.equals(ParkingSpotStatus.EMPTY)) {
                    return parkingSpot;
                }
            }
        }
        return null;
    }
}
