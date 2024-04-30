package services;

import dto.IssueTicketRequestDTO;
import exceptions.GateNotFoundException;
import exceptions.ParkingLotNotFoundException;
import factories.SpotAssignmentStrategyFactory;
import models.*;
import repositories.GateRepository;
import repositories.ParkingLotRepository;
import repositories.VehicleRepository;
import strategies.SpotAssignmentStrategy;

import java.util.Date;
import java.util.Optional;

public class TicketService {
    private GateRepository gateRepository;
    private VehicleRepository vehicleRepository;
    private ParkingLotRepository parkingLotRepository;

    public TicketService(GateRepository gateRepository, VehicleRepository vehicleRepository, ParkingLotRepository parkingLotRepository) {
        this.gateRepository = gateRepository;
        this.vehicleRepository = vehicleRepository;
        this.parkingLotRepository = parkingLotRepository;
    }

    public Ticket issueTicket(Long gateId, String ownerName, VehicleType vehicleType, String vehicleNumber) throws GateNotFoundException, ParkingLotNotFoundException {
        //generate ticket
        //1. Fetch the gate object from the database.
        Optional<Gate> optionalGate = this.gateRepository.findGateById(gateId);
        Gate gate;
        Operator operator;

        if(optionalGate.isPresent()) {
            gate = optionalGate.get();
            operator = gate.getCurrentOperator();
        } else {
            throw new GateNotFoundException("Invalid Gate Id");
        }

        //2. Fetch the vehicle details from the database.
        Optional<Vehicle> optionalVehicle = this.vehicleRepository.findVehicleByNumber(vehicleNumber);
        Vehicle vehicle;

        if(optionalVehicle.isPresent()) {
            vehicle = optionalVehicle.get();
        } else {
            //Save the vehicle
            vehicle = new Vehicle();
            vehicle.setOwnerName(ownerName);
            vehicle.setVehicleType(vehicleType);
            vehicle.setVehicleNumber(vehicleNumber);

            vehicle = this.vehicleRepository.saveVehicle(vehicle);
        }

        //3. Validate the user details.
        //4. Finally, issue the ticket.
        SpotAssignmentStrategy spotAssignmentStrategy= SpotAssignmentStrategyFactory.getSpotAssignmentStrategy(SpotAssignmentStrategyType.RANDOM, this.parkingLotRepository);

        ParkingSpot parkingSpot = spotAssignmentStrategy.assignParkingSpot(gate, vehicleType);
        return null;
    }
}
