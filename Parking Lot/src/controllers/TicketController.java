package controllers;

import dto.IssueTicketRequestDTO;
import dto.IssueTicketResponseDTO;
import dto.ResponseStatus;
import models.Ticket;
import services.TicketService;

public class TicketController {
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public IssueTicketResponseDTO issueTicket(IssueTicketRequestDTO issueTicketRequest) {
        //calls the ticket service
        IssueTicketResponseDTO issueTicketResponse = new IssueTicketResponseDTO();

        try {
            Ticket ticket = this.ticketService.issueTicket(
                    issueTicketRequest.getGateId(),
                    issueTicketRequest.getVehicleOwnerName(),
                    issueTicketRequest.getVehicleType(),
                    issueTicketRequest.getVehicleNumber()
            );

            issueTicketResponse.setResponseStatus(ResponseStatus.SUCCESS);
            issueTicketResponse.setTicket(ticket);
        } catch (Exception e) {
            issueTicketResponse.setResponseStatus(ResponseStatus.FAILURE);
        }

        return issueTicketResponse;
    }
}
