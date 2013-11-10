
public abstract class AbstractElevator {

	protected int numFloors; 
	protected int elevatorId;
	protected int maxOccupancyThreshold;
	protected int myFloor;
	protected AbstractBuilding myBuilding;

	/**
	 * Other variables/data structures as needed goes here 
	 */

	public AbstractElevator(int numFloors, int elevatorId, int maxOccupancyThreshold, AbstractBuilding building) {
		this.numFloors = numFloors;
		this.elevatorId = elevatorId;
		this.maxOccupancyThreshold = maxOccupancyThreshold;
		this.myFloor = 1;
		this.myBuilding = building;
	}

	/**
	 * Elevator control inferface: invoked by Elevator thread.
 	 */

	/* Signal incoming and outgoing riders */
	public abstract void OpenDoors(); 	

	/**
	 * When capacity is reached or the outgoing riders are exited and
	 * incoming riders are in. 
 	 */
	public abstract void ClosedDoors();

	/* Go to a requested floor */
	public abstract void VisitFloor(int floor);


	/**
	 * Elevator rider interface (part 1): invoked by rider threads. 
  	 */

	/* Enter the elevator */
	public abstract boolean Enter();
	
	/* Exit the elevator */
	public abstract void Exit();

	/* Request a destination floor once you enter */
 	public abstract void RequestFloor(int floor);

 	/* Return Current Floor */
	public abstract int getFloor();

	public abstract void setDirection(int dir);

	public abstract void callUp(int fromFloor);
	
	/* Other methods as needed goes here */
}

