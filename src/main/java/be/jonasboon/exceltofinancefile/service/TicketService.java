package be.jonasboon.exceltofinancefile.service;

import be.jonasboon.exceltofinancefile.dto.TicketDTO;
import be.jonasboon.exceltofinancefile.exception.ValidationException;
import be.jonasboon.exceltofinancefile.mapper.TicketMapper;
import be.jonasboon.exceltofinancefile.model.Ticket;
import be.jonasboon.exceltofinancefile.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static be.jonasboon.exceltofinancefile.mapper.TicketMapper.*;
import static java.lang.String.format;

@Service
@Slf4j
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public TicketDTO getTicketById(String id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        return ticket.map(TicketMapper::toDTO)
                .orElseThrow(() -> new RuntimeException(format("No ticket with id: %s", id)));
    }

    public List<TicketDTO> getAllTickets() {
        List<Ticket> allTickets = ticketRepository.findAll();
        return allTickets.stream().map(TicketMapper::toDTO).collect(Collectors.toList());
    }

    public ResponseEntity createTicket(TicketDTO ticketDTO) {
        Ticket savedTicket = ticketRepository.save(toEntity(ticketDTO));
        return ResponseEntity.ok(toDTO(savedTicket));
    }

    public ResponseEntity deleteTicketById(String id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if(ticket.isPresent()){
            ticketRepository.deleteById(id);
            return ResponseEntity.ok(format("Deleted ticket with id: %s", id));
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity updateTicket(TicketDTO ticket) {
        Ticket ticketToUpdate = toEntity(ticket);
        ticketRepository.save(ticketToUpdate);
        return ResponseEntity.ok().build();
    }
}
