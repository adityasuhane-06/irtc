package ticket.booking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ticket.booking.dto.ApiResponse;
import ticket.booking.dto.LoginRequest;
import ticket.booking.dto.SignUpRequest;
import ticket.booking.entities.User;
import ticket.booking.services.UserBookingService;
import ticket.booking.Utils.UserServicesUtil;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signUp(@RequestBody SignUpRequest request) {
        try {
            String hashedPassword = UserServicesUtil.hashPassword(request.getPassword());
            String userId = "user_" + UUID.randomUUID().toString().substring(0, 8);
            
            User newUser = new User(request.getUsername(), request.getPassword(), 
                                   hashedPassword, new ArrayList<>(), userId);
            
            UserBookingService bookingService = new UserBookingService(newUser);
            
            if (bookingService.signUp(newUser)) {
                return ResponseEntity.ok(ApiResponse.success("User registered successfully", userId));
            } else {
                return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Registration failed"));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest request) {
        try {
            User user = new User(request.getUsername(), request.getPassword(), 
                               "", new ArrayList<>(), "");
            
            UserBookingService bookingService = new UserBookingService(user);
            
            if (bookingService.loginUser()) {
                return ResponseEntity.ok(ApiResponse.success("Login successful", request.getUsername()));
            } else {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error("Invalid credentials"));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(ApiResponse.error("Error: " + e.getMessage()));
        }
    }
}
