package ticket.booking.entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;

public class Train {
    @JsonProperty("train_id")
    private String trainId;
    @JsonProperty("train_number")
    private String trainNumber;
    @JsonProperty("train_name")
    private String trainName;
    @JsonProperty("arrival_time")
    private Date arrivalTime;
    @JsonProperty("departure_time")
    private Date departureTime;
    private List<List<Integer>>seats;
    @JsonProperty("station_times")
    private Map<String,String>staionTimes;
    private List<String>stations;
    public Train(){}

    public Train(String trainId, String trainNumber, String trainName, Date arrivalTime, Date departureTime,
            List<List<Integer>> seats, Map<String, String> staionTimes, List<String> stations) {
        this.trainId = trainId;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.seats = seats;
        this.staionTimes = staionTimes;
        this.stations = stations;
    }
    public String getTrainId() {
        return trainId;
    }
    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }
    public String getTrainNumber() {
        return trainNumber;
    }
    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }
    public String getTrainName() {
        return trainName;
    }
    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }
    public Date getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }
    public List<List<Integer>> getSeats() {
        return seats;
    }
    public void setSeats(List<List<Integer>> seats) {
        this.seats = seats;
    }
    public Map<String, String> getStaionTimes() {
        return staionTimes;
    }
    public void setStaionTimes(Map<String, String> staionTimes) {
        this.staionTimes = staionTimes;
    }
    public List<String> getStations() {
        return stations;
    }
    public void setStations(List<String> stations) {
        this.stations = stations;
    }
    public String toString(){
        return "Train ID: "+this.trainId+", Train Number: "+this.trainNumber+", Train Name: "+this.trainName;
    }
}
