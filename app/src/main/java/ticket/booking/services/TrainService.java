package ticket.booking.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Train;

public class TrainService {
    private static final String TRAIN_PATH = "app/src/main/java/ticket/booking/localDB/train.json";
    private List<Train> trainList;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public TrainService() throws IOException {
        File trains = new File(TRAIN_PATH);
        trainList = objectMapper.readValue(trains, new TypeReference<List<Train>>() {});
    }

    public List<Train> searchTrains(String source, String destination) {
        return trainList.stream()
            .filter(train -> train.getStations().contains(source) && 
                           train.getStations().contains(destination) &&
                           train.getStations().indexOf(source) < train.getStations().indexOf(destination))
            .collect(Collectors.toList());
    }

    public Train getTrainById(String trainId) {
        return trainList.stream()
            .filter(train -> train.getTrainId().equals(trainId))
            .findFirst()
            .orElse(null);
    }

    public List<int[]> getAvailableSeats(String trainId) {
        Train train = getTrainById(trainId);
        if (train == null) return null;
        
        List<List<Integer>> seats = train.getSeats();
        return java.util.stream.IntStream.range(0, seats.size())
            .boxed()
            .flatMap(row -> java.util.stream.IntStream.range(0, seats.get(row).size())
                .filter(col -> seats.get(row).get(col) == 0)
                .mapToObj(col -> new int[]{row, col}))
            .collect(Collectors.toList());
    }

    public boolean bookSeat(String trainId, int row, int col) throws IOException {
        Train train = getTrainById(trainId);
        if (train == null) return false;
        
        List<List<Integer>> seats = train.getSeats();
        if (row >= seats.size() || col >= seats.get(row).size()) return false;
        if (seats.get(row).get(col) == 1) return false;
        
        seats.get(row).set(col, 1);
        saveTrainsToFile();
        return true;
    }

    public boolean cancelSeat(String trainId, int row, int col) throws IOException {
        Train train = getTrainById(trainId);
        if (train == null) return false;
        
        List<List<Integer>> seats = train.getSeats();
        if (row >= seats.size() || col >= seats.get(row).size()) return false;
        
        seats.get(row).set(col, 0);
        saveTrainsToFile();
        return true;
    }

    private void saveTrainsToFile() throws IOException {
        File trains = new File(TRAIN_PATH);
        objectMapper.writeValue(trains, trainList);
    }
}
