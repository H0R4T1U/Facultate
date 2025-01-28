package ubb.scs.map.clinica.Observers;


public interface Observable {
    void addObserver(Observer e);
    void removeObserver(Observer e);
    void notifyObservers();

}
