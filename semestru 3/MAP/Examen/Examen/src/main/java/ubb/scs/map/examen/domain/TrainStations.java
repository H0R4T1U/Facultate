package ubb.scs.map.examen.domain;

public class TrainStations {
    private int id;
    private int departureId;
    private int arrivalId;

    public TrainStations(int departureId, int arrivalId) {
        this.departureId = departureId;
        this.arrivalId = arrivalId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepartureId() {
        return departureId;
    }

    public void setDepartureId(int departureId) {
        this.departureId = departureId;
    }

    public int getArrivalId() {
        return arrivalId;
    }

    public void setArrivalId(int arrivalId) {
        this.arrivalId = arrivalId;
    }
}
