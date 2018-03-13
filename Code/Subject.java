public interface Subject{
	
	//registers a new observer
	public void register(Observer o);

	//unregisters an observer
	public void unregister(Observer o);

	//notfies observer of event
	public void notifyObservers();
}