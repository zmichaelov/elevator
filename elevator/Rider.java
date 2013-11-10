package elevator;
public class Rider implements Runnable {

	private AbstractBuilding myBuilding;
	private AbstractElevator myElevator;
	private String myName;
	private int myFloor;
	private int myDestination;

	public Rider(AbstractBuilding b, String name, int floor, int dest) {
		myBuilding = b;
		myName = name;
		myFloor = floor;
		myDestination = dest;
	}

	@Override
	public void run() {
		boolean direction = false;
		if (myDestination > myFloor){
			System.out.println(""+myName+": rider call up from floor "+myFloor);
			myElevator = myBuilding.CallUp(myFloor);
			direction = true;
		}
		else if (myDestination < myFloor){
			System.out.println(""+myName+": rider call down from floor "+myFloor);
			myElevator = myBuilding.CallDown(myFloor);
			direction = false;
		}
		else ; // TODO location = destination. exit thread

		System.out.println(""+myName+": rider want to enter "+myElevator.getName());
		if(!myElevator.enter(myFloor, direction)){
			//TODO could not board elevator
			System.out.println(""+myName+": rider could not enter "+myElevator.getName());
		}
		System.out.println(""+myName+": entered "+myElevator.getName());
		myElevator.requestFloor(myDestination);
		System.out.println(""+myName+": rider requested floor "+myDestination+" from "+myElevator.getName());
		myElevator.exit(myDestination);
		System.out.println(""+myName+": rider exited "+myElevator.getName());
	}
}
