package elevator;
public class Rider implements Runnable {

	private AbstractBuilding myBuilding;
	private AbstractElevator myElevator;
	private String myName;
	private int myFloor;
	private int myDestination;

	public Rider(Building b, String name, int floor, int dest) {
		myBuilding = b;
		myName = name;
		myFloor = floor;
		myDestination = dest;
	}

	@Override
	public void run() {
		boolean direction = false;
		if (myDestination > myFloor){
			myElevator = myBuilding.CallUp(myFloor);
			direction = true;
		}
		else if (myDestination < myFloor){
			myElevator = myBuilding.CallDown(myFloor);
			direction = false;
		}
		else ; // TODO location = destination. exit thread
		if(!myElevator.enter(myFloor, direction)){
			//TODO could not board elevator
		}
		myElevator.requestFloor(myDestination);
		myElevator.exit(myDestination);
	}
}
