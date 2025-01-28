package ubb.scs.map.avioane.Observers;


public interface Observable {
    void addObserver(Observer e);
    void removeObserver(Observer e);
    void notifyObservers();

}
