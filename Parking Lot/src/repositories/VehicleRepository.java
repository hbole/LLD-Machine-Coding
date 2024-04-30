package repositories;

import models.Gate;
import models.Vehicle;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VehicleRepository {
    //DB Queries for Vehicles
    private Map<Long, Vehicle> vehicles = new HashMap<>();
    private Long vehicleId;

    public Optional<Vehicle> findVehicleById(Long vehicleId) {
        if(vehicles.containsKey(vehicleId)) {
            return Optional.of(vehicles.get(vehicleId));
        }
        return Optional.empty();
    }

    public Optional<Vehicle> findVehicleByNumber(String vehicleNumber) {
        //Iterate over the complete map and check if there is any vehicle with given number or not.
        for(Vehicle vehicle : vehicles.values()) {
            if(vehicle.getVehicleNumber().equals(vehicleNumber)) {
                return Optional.of(vehicle);
            }
        }

        return Optional.empty();
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        vehicleId++;

        vehicle.setId(vehicleId);
        vehicles.put(vehicleId, vehicle);
        return vehicle;
    }
}
