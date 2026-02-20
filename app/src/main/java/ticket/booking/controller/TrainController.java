package ticket.booking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ticket.booking.dto.ApiResponse;
import ticket.booking.entities.Train;
import ticket.booking.services.TrainService;

import java.util.List;

@RestController
@RequestMapping("/api/trains")
@CrossOrigin(origins = "*")
public class TrainController {

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Train>>> searchTrains(
            @RequestParam String source,
            @RequestParam String destination) {
        try {
            TrainService trainService = new TrainService();
            List<Train> trains = trainService.searchTrains(source, destination);
            
            if (trains.isEmpty()) {
                return ResponseEntity.ok(ApiResponse.success("No trains found", trains));
            }
            
            return ResponseEntity.ok(ApiResponse.success("Trains found", trains));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    @GetMapping("/{trainId}")
    public ResponseEntity<ApiResponse<Train>> getTrainById(@PathVariable String trainId) {
        try {
            TrainService trainService = new TrainService();
            Train train = trainService.getTrainById(trainId);
            
            if (train == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(ApiResponse.success("Train found", train));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    @GetMapping("/{trainId}/seats")
    public ResponseEntity<ApiResponse<List<int[]>>> getAvailableSeats(@PathVariable String trainId) {
        try {
            TrainService trainService = new TrainService();
            List<int[]> seats = trainService.getAvailableSeats(trainId);
            
            if (seats == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(ApiResponse.success("Available seats", seats));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(ApiResponse.error("Error: " + e.getMessage()));
        }
    }
}
