package ticket.booking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ticket.booking.dto.ApiResponse;
import ticket.booking.dto.BookingRequest;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.services.TrainService;
import ticket.booking.services.UserBookingService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @PostMapping("/book")
    public ResponseEntity<ApiResponse<String>> bookTicket(@RequestBody BookingRequest request) {
        try {
            TrainService trainService = new TrainService();
            Train train = trainService.getTrainById(request.getTrainId());
            
            if (train == null) {
                return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Train not found"));
            }
            
            User user = new User("", "", "", new ArrayList<>(), request.getUserId());
            UserBookingService bookingService = new UserBookingService(user);
            
            String ticketId = "ticket_" + UUID.randomUUID().toString().substring(0, 8);
            Ticket ticket = new Ticket(ticketId, request.getUserId(), 
                                      request.getSource(), request.getDestination(), 
                                      new Date(), train);
            
            if (bookingService.bookTicket(ticket, request.getTrainId(), 
                                         request.getRow(), request.getCol())) {
                return ResponseEntity.ok(ApiResponse.success("Ticket booked successfully", ticketId));
            } else {
                return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Booking failed. Seat may be unavailable"));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Ticket>>> getUserBookings(@PathVariable String userId) {
        try {
            User user = new User("", "", "", new ArrayList<>(), userId);
            UserBookingService bookingService = new UserBookingService(user);
            
            List<Ticket> tickets = bookingService.fetchBooking();
            
            if (tickets == null || tickets.isEmpty()) {
                return ResponseEntity.ok(ApiResponse.success("No bookings found", new ArrayList<>()));
            }
            
            return ResponseEntity.ok(ApiResponse.success("Bookings retrieved", tickets));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<ApiResponse<String>> cancelTicket(
            @RequestParam String ticketId,
            @RequestParam String userId,
            @RequestParam String trainId,
            @RequestParam int row,
            @RequestParam int col) {
        try {
            User user = new User("", "", "", new ArrayList<>(), userId);
            UserBookingService bookingService = new UserBookingService(user);
            
            if (bookingService.cancelTicket(ticketId, trainId, row, col)) {
                return ResponseEntity.ok(ApiResponse.success("Ticket cancelled successfully", ticketId));
            } else {
                return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Cancellation failed"));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(ApiResponse.error("Error: " + e.getMessage()));
        }
    }
}
