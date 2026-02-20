package ticket.booking.services;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.User;
import ticket.booking.entities.Ticket;
import ticket.booking.Utils.UserServicesUtil;
public class UserBookingService {
    private User user;
    private static final String USER_PATH = "app/src/main/java/ticket/booking/localDB/user.json";
    private List<User>userList;




    /**
     * Initializes the booking service with user details.
     * Stores the user instance to avoid repeated parameter passing across methods.
     * 
     * @param user1 the user object containing user details
     */
    



    private static final ObjectMapper objectMapper = new ObjectMapper();
    // Method to convert json to java object and vice-versa
    // static -> reuse single instance





    public UserBookingService(User user1) throws StreamReadException, DatabindException, IOException{
        this.user = user1;
        // store the user passed from caller
        // user variable of class represent the user that operate on system
        File users=new File(USER_PATH);
        // create the file instance to read the user data from json file
        userList=objectMapper.readValue(users,new TypeReference<List<User>>(){});
        /*
        -> Read the JSON
        -> Convert JSON array to List of Users
        -> Why TypeReference?
        Ans. Because Java loses generic type information at runtime due to type erasure.
        */
    }
    /*
    1. This is constructor that take the  user data that operate on system as parameter
    2. Intializes the user instance variable of class 
    3. load the users data from json file to userList variable
     */


    private void saveUsersToFile() throws IOException{
        File users=new File(USER_PATH);
        objectMapper.writeValue(users, userList);
    }





    public Boolean loginUser(){
        Optional<User> foundUser=userList.stream().filter(user1->{
            return user1.getName().equals(user.getName()) && UserServicesUtil.checkPassword(user.getPassword(), user1.getHashpassword());
        }).findFirst();
        return foundUser.isPresent();
    }


    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUsersToFile();
            return Boolean.TRUE;
        }catch(IOException ex){
            return Boolean.FALSE;
        }
    }


    public Boolean bookTicket(Ticket ticket, String trainId, int row, int col) {
        try {
            TrainService trainService = new TrainService();
            if (trainService.bookSeat(trainId, row, col)) {
                user.addTicket(ticket);
                Optional<User> userInList = userList.stream()
                    .filter(u -> u.getUserId().equals(user.getUserId()))
                    .findFirst();
                if (userInList.isPresent()) {
                    userInList.get().addTicket(ticket);
                    saveUsersToFile();
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        } catch (IOException ex) {
            return Boolean.FALSE;
        }
    }

    public Boolean cancelTicket(String ticketId, String trainId, int row, int col) {
        try {
            TrainService trainService = new TrainService();
            Optional<Ticket> ticketToRemove = user.getTicketbooked().stream()
                .filter(t -> t.getTicketId().equals(ticketId))
                .findFirst();

            if (ticketToRemove.isPresent() && trainService.cancelSeat(trainId, row, col)) {
                user.removeTicket(ticketToRemove.get());
                Optional<User> userInList = userList.stream()
                    .filter(u -> u.getUserId().equals(user.getUserId()))
                    .findFirst();
                if (userInList.isPresent()) {
                    userInList.get().removeTicket(ticketToRemove.get());
                    saveUsersToFile();
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        } catch (IOException ex) {
            return Boolean.FALSE;
        }
    }

    public List<Ticket> fetchBooking() {
        Optional<User> userInList = userList.stream()
            .filter(u -> u.getUserId().equals(user.getUserId()))
            .findFirst();
        return userInList.map(User::getTicketbooked).orElse(null);
    }

}
