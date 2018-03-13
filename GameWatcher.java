import java.util.ArrayList;

//uses subject interface to update all of our observers
public class GameWatcher implements Subject{
	
	private ArrayList<Observer> observers;
	
	//will hold piece eaten and piece used
	private Piece pieceUsed;
	private Piece pieceEaten;

	public GameWatcher(){
		observers = new ArrayList<Observer>();
		pieceUsed = null;
		pieceEaten = null;
	}

	@Override
	//adding a new observer to list
	public void register(Observer observerToAdd){
		observers.add(observerToAdd);
	}

	@Override
	//deleting an existing observer
	public void unregister(Observer observerToDelete){
		//get index to delete
		int obsIndex = observers.indexOf(observerToDelete);

		//delete observer
		observers.remove(obsIndex);
	}

	@Override
	//notifies all observer with the updated local variables(piece used/eaten)
	public void notifyObservers(){
		for(Observer o:observers){
			o.update(pieceUsed,pieceEaten);
		}
	}

	//sets local piece used and sends it to all observers
	public void setPieceUsed(Piece pieceUsed){
		this.pieceUsed = pieceUsed;
		notifyObservers();
		//cleanup is done so only one statistic is taken at a time, and no double data
		pieceUsed = null;
		pieceEaten = null;

	}

	//sets local piece eaten and sends it to all observers
	public void setPieceEaten(Piece pieceEaten){
		this.pieceEaten = pieceEaten;
		notifyObservers();
		//cleanup is done so only one statistic is taken at a time, and no double data
		pieceEaten = null;
		pieceUsed = null;

	}
}