package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class Ticket {
    @JsonProperty("ticket_id")
    private String ticketId;
    @JsonProperty("user_id")
    private String userId;
    private String source;
    private String destination;
    @JsonProperty("date_of_journey")
    private Date dateOfJourney;
    private Train train;

    public Ticket(){}
    public Ticket(String ticketId, String userId, String source, String destination, Date dateOfJourney,
            Train train) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.source = source;
        this.destination = destination;
        this.dateOfJourney = dateOfJourney;
        this.train = train;
    }
    public String getTicketId() {
        return ticketId;
    }
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public Date getDateOfJourney() {
        return dateOfJourney;
    }
    public void setDateOfJourney(Date dateOfJourney) {
        this.dateOfJourney = dateOfJourney;
    }
    public Train getTrain() {
        return train;
    }
    public void setTrain(Train train) {
        this.train = train;
    }
    public String getTicketInfo(){
            System.out.println("Ticket Information");
            System.out.println("******************");
            System.out.println("Ticket ID: " + this.ticketId);
            System.out.println("User ID: " + this.userId);
            System.out.println("Source: " + this.source);
            System.out.println("Destination: " + this.destination);
            System.out.println("Date of Journey: " + this.dateOfJourney);
            System.out.println("Train Details: " + this.train.toString());
            System.out.println("******************");

        return "";
    }
}
