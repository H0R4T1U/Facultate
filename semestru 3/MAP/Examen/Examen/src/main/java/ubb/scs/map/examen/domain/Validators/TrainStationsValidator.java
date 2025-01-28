package ubb.scs.map.examen.domain.Validators;

import ubb.scs.map.examen.domain.TrainStations;

public class TrainStationsValidator implements Validator<TrainStations> {
    @Override
    public void validate(TrainStations entity) throws ValidationException {
        if(entity == null || entity.getDepartureId() >= 0 || entity.getArrivalId() >= 0){
            throw new ValidationException("Train station is invalid!");
        }
    }
}
