import controllers.TicketController;
import dto.IssueTicketRequestDTO;
import dto.IssueTicketResponseDTO;
import dto.ResponseStatus;
import models.Ticket;
import models.VehicleType;
import repositories.GateRepository;
import repositories.ParkingLotRepository;
import repositories.TicketRepository;
import repositories.VehicleRepository;
import services.TicketService;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Client {
    public static void main(String[] args) {
        GateRepository gateRepository = new GateRepository();
        VehicleRepository vehicleRepository = new VehicleRepository();
        ParkingLotRepository parkingLotRepository = new ParkingLotRepository();
        TicketRepository ticketRepository = new TicketRepository();

        TicketService ticketService = new TicketService(
                gateRepository,
                vehicleRepository,
                parkingLotRepository,
                ticketRepository
        );

        TicketController ticketController = new TicketController(ticketService);

        IssueTicketRequestDTO issueTicketRequest = new IssueTicketRequestDTO();
        issueTicketRequest.setGateId(1234L);
        issueTicketRequest.setVehicleNumber("KA53HP2722");
        issueTicketRequest.setVehicleType(VehicleType.TWO_WHEELER);
        issueTicketRequest.setVehicleOwnerName("Harshit");

        //TODO: Enter some random data in the map and check if we are able to create a ticket object or not

        Ticket ticket;
        IssueTicketResponseDTO issueTicketResponse = ticketController.issueTicket(issueTicketRequest);
        if (issueTicketResponse.getResponseStatus().equals(ResponseStatus.SUCCESS)) {
            ticket = issueTicketResponse.getTicket();
        } else {
            System.out.println("Issue with generating a ticket");
        }
    }
}