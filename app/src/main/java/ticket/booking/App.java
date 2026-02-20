package ticket.booking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.services.TrainService;
import ticket.booking.services.UserBookingService;
import ticket.booking.Utils.UserServicesUtil;

public class App {
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;
    private static UserBookingService bookingService = null;
    private static TrainService trainService = null;

    public static void main(String[] args) {
        try {
            trainService = new TrainService();
            System.out.println("=================================");
            System.out.println("Welcome to Train Booking System");
            System.out.println("=================================");
            
            while (true) {
                if (currentUser == null) {
                    showLoginMenu();
                } else {
                    showMainMenu();
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void showLoginMenu() {
        System.out.println("\n1. Login");
        System.out.println("2. Sign Up");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                signUp();
                break;
            case 3:
                System.out.println("Thank you for using Train Booking System!");
                System.exit(0);
            default:
                System.out.println("Invalid option!");
        }
    }

    private static void login() {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            
            currentUser = new User(username, password, "", new ArrayList<>(), "");
            bookingService = new UserBookingService(currentUser);
            
            if (bookingService.loginUser()) {
                System.out.println("Login successful! Welcome " + username);
            } else {
                System.out.println("Invalid credentials!");
                currentUser = null;
                bookingService = null;
            }
        } catch (IOException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }

    private static void signUp() {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            
            String hashedPassword = UserServicesUtil.hashPassword(password);
            String userId = "user_" + UUID.randomUUID().toString().substring(0, 8);
            
            User newUser = new User(username, password, hashedPassword, new ArrayList<>(), userId);
            bookingService = new UserBookingService(newUser);
            
            if (bookingService.signUp(newUser)) {
                System.out.println("Sign up successful! You can now login.");
            } else {
                System.out.println("Sign up failed!");
            }
            bookingService = null;
        } catch (IOException e) {
            System.out.println("Error during sign up: " + e.getMessage());
        }
    }

    private static void showMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Search Trains");
        System.out.println("2. Book Ticket");
        System.out.println("3. View My Bookings");
        System.out.println("4. Cancel Ticket");
        System.out.println("5. Logout");
        System.out.print("Choose an option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                searchTrains();
                break;
            case 2:
                bookTicket();
                break;
            case 3:
                viewBookings();
                break;
            case 4:
                cancelTicket();
                break;
            case 5:
                logout();
                break;
            default:
                System.out.println("Invalid option!");
        }
    }

    private static void searchTrains() {
        System.out.print("Enter source city: ");
        String source = scanner.nextLine();
        System.out.print("Enter destination city: ");
        String destination = scanner.nextLine();
        
        List<Train> trains = trainService.searchTrains(source, destination);
        
        if (trains.isEmpty()) {
            System.out.println("No trains found for this route.");
        } else {
            System.out.println("\nAvailable Trains:");
            System.out.println("=================");
            for (Train train : trains) {
                System.out.println(train.toString());
                System.out.println("Stations: " + String.join(" -> ", train.getStations()));
                List<int[]> availableSeats = trainService.getAvailableSeats(train.getTrainId());
                System.out.println("Available seats: " + availableSeats.size());
                System.out.println("-----------------");
            }
        }
    }

    private static void bookTicket() {
        System.out.print("Enter train ID: ");
        String trainId = scanner.nextLine();
        
        Train train = trainService.getTrainById(trainId);
        if (train == null) {
            System.out.println("Train not found!");
            return;
        }
        
        List<int[]> availableSeats = trainService.getAvailableSeats(trainId);
        if (availableSeats.isEmpty()) {
            System.out.println("No seats available!");
            return;
        }
        
        System.out.println("Available seats:");
        for (int i = 0; i < Math.min(5, availableSeats.size()); i++) {
            System.out.println("Row: " + availableSeats.get(i)[0] + ", Col: " + availableSeats.get(i)[1]);
        }
        
        System.out.print("Enter row number: ");
        int row = scanner.nextInt();
        System.out.print("Enter column number: ");
        int col = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter source: ");
        String source = scanner.nextLine();
        System.out.print("Enter destination: ");
        String destination = scanner.nextLine();
        
        String ticketId = "ticket_" + UUID.randomUUID().toString().substring(0, 8);
        Ticket ticket = new Ticket(ticketId, currentUser.getUserId(), source, destination, new Date(), train);
        
        if (bookingService.bookTicket(ticket, trainId, row, col)) {
            System.out.println("Ticket booked successfully!");
            System.out.println("Ticket ID: " + ticketId);
        } else {
            System.out.println("Booking failed!");
        }
    }

    private static void viewBookings() {
        List<Ticket> tickets = bookingService.fetchBooking();
        if (tickets == null || tickets.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            System.out.println("\nYour Bookings:");
            System.out.println("==============");
            for (Ticket ticket : tickets) {
                ticket.getTicketInfo();
            }
        }
    }

    private static void cancelTicket() {
        System.out.print("Enter ticket ID to cancel: ");
        String ticketId = scanner.nextLine();
        System.out.print("Enter train ID: ");
        String trainId = scanner.nextLine();
        System.out.print("Enter row number: ");
        int row = scanner.nextInt();
        System.out.print("Enter column number: ");
        int col = scanner.nextInt();
        scanner.nextLine();
        
        if (bookingService.cancelTicket(ticketId, trainId, row, col)) {
            System.out.println("Ticket cancelled successfully!");
        } else {
            System.out.println("Cancellation failed!");
        }
    }

    private static void logout() {
        currentUser = null;
        bookingService = null;
        System.out.println("Logged out successfully!");
    }
}
