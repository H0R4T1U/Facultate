package ubb.scs.map.examen.Service;

import ubb.scs.map.examen.Observers.Observable;
import ubb.scs.map.examen.Observers.Observer;
import ubb.scs.map.examen.Repository.CityRepository;
import ubb.scs.map.examen.Repository.TrainStationsRepository;
import ubb.scs.map.examen.domain.City;
import ubb.scs.map.examen.domain.TrainStations;

import java.util.*;
import java.util.stream.StreamSupport;

public class Service implements Observable {
    List<Observer> observers = new ArrayList<>();

    CityRepository cityRepository;
    TrainStationsRepository trainStationsRepository;

    Map<Map<Integer,Integer>,Integer> searching = new HashMap<>();

    public Service(CityRepository cityRepository, TrainStationsRepository trainStationsRepository) {
        this.cityRepository = cityRepository;
        this.trainStationsRepository = trainStationsRepository;
    }

    public List<City> getCities(){
        return StreamSupport.stream(cityRepository.findAll().spliterator(),false)
                .toList();
    }
    public List<TrainStations> getTrains(){
        return StreamSupport.stream(trainStationsRepository.findAll().spliterator(),false)
                .toList();
    }

    public List<List<TrainStations>> getTrainStations(int departure, int arrival,Boolean direct){
        if(direct){
            return Collections.singletonList(StreamSupport.stream(trainStationsRepository.findAll().spliterator(), false)
                    .filter(trainStations -> trainStations.getArrivalId() == arrival && trainStations.getDepartureId() == departure)
                    .toList());
        }else {
            return findPaths(getTrains(), departure, arrival);
        }
    }
    public void decrementSearching(Map<Integer,Integer> old){
        searching.put(old,searching.get(old)-1); // decrementam valoarea veche
    }
    public void setSearching(Map<Integer,Integer> key){
        if(searching.containsKey(key)){
            searching.put(key,searching.get(key)+1);
        }else {
            searching.put(key, 1);
        }
        notifyObservers();
    }
    public Integer getSearches(Map<Integer,Integer> key){
        return searching.get(key);
    }
    private List<List<TrainStations>> findPaths(List<TrainStations> connections, int start, int end) {
        List<List<TrainStations>> paths = new ArrayList<>();
        dfs(connections, start, end, new ArrayList<>(), new HashSet<>(), paths);
        return paths;
    }

    private void dfs(List<TrainStations> connections, int current, int end,
                            List<TrainStations> path, Set<Integer> visited,
                            List<List<TrainStations>> paths) {
        if (current == end) {
            paths.add(new ArrayList<>(path)); // Add a copy of the current path
            return;
        }

        visited.add(current);
        for (TrainStations connection : connections) {
            if (connection.getDepartureId() == current && !visited.contains(connection.getArrivalId())) {
                path.add(connection);
                dfs(connections, connection.getArrivalId(), end, path, visited, paths);
                path.remove(path.size() - 1); // Backtrack
            }
        }
        visited.remove(current);
    }

    @Override
    public void addObserver(Observer e) {
        this.observers.add(e);
    }

    @Override
    public void removeObserver(Observer e) {
        this.observers.remove(e);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public String getCityById(int id) {
        return cityRepository.findOne(id).get().getName();
    }
}
