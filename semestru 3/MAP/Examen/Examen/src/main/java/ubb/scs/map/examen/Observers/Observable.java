package ubb.scs.map.examen.Observers;


public interface Observable {
    void addObserver(Observer e);
    void removeObserver(Observer e);
    void notifyObservers();

}
