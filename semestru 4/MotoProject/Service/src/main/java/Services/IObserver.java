package Services;


import model.Player;


public interface IObserver {

     void playerAdded(Player player) throws MotoException;
}
