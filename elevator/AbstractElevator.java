package elevator;

public abstract class AbstractElevator {

	protected int numFloors; 
	protected int elevatorId;
	protected int maxOccupancyThreshold;
	protected int myFloor;
	protected String myName;

	/**
	 * Other variables/data structures as needed goes here 
	 */

	public AbstractElevator(int numFloors, int elevatorId, int maxOccupancyThreshold) {
		this.numFloors = numFloors;
		this.elevatorId = elevatorId;
		this.maxOccupancyThreshold = maxOccupancyThreshold;
		this.myFloor = 1;
	}

	/**
	 * Elevator control inferface: invoked by Elevator thread.
 	 */

	/* Signal incoming and outgoing riders */
	public abstract void openDoors(int floor); 	

	/**
	 * When capacity is reached or the outgoing riders are exited and
	 * incoming riders are in. 
 	 */
	public abstract void closeDoors();

	/* Go to a requested floor */
	public abstract void visitFloor(int floor);


	/**
	 * Elevator rider interface (part 1): invoked by rider threads. 
  	 */

	/* Enter the elevator */
	public abstract boolean enter(int floor, boolean up);
	
	/* Exit the elevator */
	public abstract void exit(int floor);

	/* Request a destination floor once you enter */
 	public abstract void requestFloor(int floor);

 	/* Return Current Floor */
	public abstract int getFloor();

	public abstract void callUp(int fromFloor);

	public abstract void callDown(int fromFloor);

	public String getName() {
		return myName;
	}
	
	/* Other methods as needed goes here */
}

