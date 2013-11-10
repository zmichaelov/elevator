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
		if (myDestination > myFloor)
			myElevator = myBuilding.CallUp(myFloor);
		else if (myDestination < myFloor)
			myElevator = myBuilding.CallDown(myFloor);
		else
			; // TODO exit thread
	}
}
