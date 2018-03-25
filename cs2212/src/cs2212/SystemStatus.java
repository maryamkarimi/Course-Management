package cs2212;

public class SystemStatus {
	
	private boolean status;
	
	private static SystemStatus instance;
	
	private SystemStatus(){
		status = false;
	}
	
	public static SystemStatus getInstance(){
		if(instance == null)
			instance = new SystemStatus();
		return instance;
	}
	
	public void start() {
		status = true;
	}
	
	public void stop() {
		status = false;
	}
	
	public boolean isStarted() {
		return status;
	}
}
