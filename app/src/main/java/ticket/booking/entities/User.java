package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class User {
    private String name;
    private String password;
    @JsonProperty("hashed_password")
    private String hashpassword;
    @JsonProperty("tickets_booked")
    private List<Ticket> ticketbooked;
    @JsonProperty("user_id")
    private String userId;
    
    public User() {}
    
    public User(String name, String password, String hashpassword, List<Ticket> ticketbooked,
            String userId) {
        this.name = name;
        this.password = password;
        this.hashpassword = hashpassword;
        this.ticketbooked = ticketbooked;
        this.userId = userId;
    }



    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getHashpassword() {
        return hashpassword;
    }
    public void setHashpassword(String hashpassword) {
        this.hashpassword = hashpassword;
    }
    public List<Ticket> getTicketbooked() {
        return ticketbooked;
    }
    public void setTicketbooked(List<Ticket> ticketbooked) {
        this.ticketbooked = ticketbooked;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void addTicket(Ticket ticket){
        this.ticketbooked.add(ticket);
    }
    public void removeTicket(Ticket ticket){
        this.ticketbooked.remove(ticket);
    }
    public void clearTickets(){
        this.ticketbooked.clear();
    }

    public void printTicket(){
        for(int i=0;i<ticketbooked.size();i++){
            System.out.println(ticketbooked.get(i).getTicketInfo());
        }
    }

    public User() {}


}