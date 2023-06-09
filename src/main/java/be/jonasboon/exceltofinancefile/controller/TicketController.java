package be.jonasboon.exceltofinancefile.controller;


import be.jonasboon.exceltofinancefile.dto.TicketDTO;
import be.jonasboon.exceltofinancefile.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{id}")
    public TicketDTO getTicketById(@PathVariable String id){
        return ticketService.getTicketById(id);
    }

    @GetMapping("all")
    public List<TicketDTO> getAllTickets(){
        return ticketService.getAllTickets();
    }

    @PostMapping
    public ResponseEntity createTicket(@RequestBody TicketDTO ticket){
        return ticketService.createTicket(ticket);
    }

    @PutMapping()
    public ResponseEntity updateTicket(@RequestBody TicketDTO ticket){
        return ticketService.updateTicket(ticket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTicketById(@PathVariable String id){
        return ticketService.deleteTicketById(id);
    }
}
