package ticket.booking.entities;
import java.util.*;

public class Train {
    private String trainId;
    private String trainNo;
    private Date arrivalTime;
    private Date departureTime;
    private List<List<Integer>>seats;
    private Map<String,Date>staionTimes;
    private List<String>stations;
    
}
