package systemStatus;

public class SystemStatus {
	
	private boolean status; // a boolean representing the current status of system, false when system is in a stopped state and true when it is in a running state.
	
	private static SystemStatus instance; // an instance of this class
	
	// constructor of the class, system status is off initially
	private SystemStatus(){
		status = false;
	}
	
	// returns the instance 
	public static SystemStatus getInstance(){
		if(instance == null)
			instance = new SystemStatus();
		return instance;
	}
	
	// changes the boolean status to true
	public void start() {
		status = true;
	}
	
	// changes the boolean status to false
	public void stop() {
		status = false;
	}
	
	// returns the status, if this method returns true it means system is in running state.
	public boolean isStarted() {
		return status;
	}
}
