package elevator;

import java.util.ArrayList;
import java.util.List;

public class Elevator extends AbstractElevator implements Runnable {

	private List<Integer> upCalls;
	private List<Integer> downCalls;
	private boolean doorsOpen;
	private int numRiders;
	private List<Integer> requestedFloors;
	private boolean ridersCanExit;
	private boolean ridersCanEnter;

	public Elevator(int numFloors, int elevatorId, int maxOccupancyThreshold, String name) {
		super(numFloors, elevatorId, maxOccupancyThreshold);
		upCalls = new ArrayList<Integer>(numFloors);
		downCalls = new ArrayList<Integer>(numFloors);
		requestedFloors = new ArrayList<Integer>(numFloors);
		this.myName = name;
	}

	@Override
	public void run() {
		while (true) {
			init();
			handleUpCalls();
			handleDownCalls();
		}
	}

	private synchronized void handleUpCalls() {
		for (int i = myFloor; i <= numFloors; i++) {
			if (upCalls.get(i) > 0 || requestedFloors.get(i) > 0)
				doFloorTasks(i);
		}
	}

	private void handleDownCalls() {
		for (int i = myFloor; i >= 0; i--) {
			if (downCalls.get(i) > 0 || requestedFloors.get(i) > 0)
				doFloorTasks(i);
		}
	}

	private void doFloorTasks(int floor) {
		visitFloor(floor);
		openDoors(floor);
		closeDoors();
	}

	private synchronized void init() {
		while (upCalls.isEmpty() && downCalls.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void openDoors(int floor) {
		doorsOpen = true;
		System.out.println(""+myName+": doors open");
		ridersCanEnter = false;
		System.out.println(""+myName+": riders can enter");
		ridersCanExit = true;
		int expectedRidersExit = numRiders - requestedFloors.get(floor);
		this.notifyAll();
		while (numRiders != expectedRidersExit) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ridersCanExit = false;
		ridersCanEnter = true;
		System.out.println(""+myName+": riders can exit");
		this.notifyAll();
		int expectedRidersEnter = numRiders + upCalls.get(floor)
				+ downCalls.get(floor);
		while (numRiders != expectedRidersEnter) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			expectedRidersEnter = numRiders + upCalls.get(floor)
					+ downCalls.get(floor);
		}
	}

	@Override
	public void closeDoors() {
		doorsOpen = false;
		System.out.println(""+myName+": doors closed");

	}

	@Override
	public void visitFloor(int floor) {
		myFloor = floor;
		System.out.println(""+myName+": visit floor "+floor);

	}

	@Override
	public synchronized boolean enter(int floor, boolean up) {
		while (myFloor != floor || !doorsOpen || !ridersCanEnter) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		numRiders++;
		if (up)
			upCalls.set(floor, upCalls.get(floor) - 1);
		else
			downCalls.set(floor, downCalls.get(floor) - 1);
		this.notifyAll();
		return true;
	}

	@Override
	public synchronized void exit(int floor) {
		while (!doorsOpen || myFloor != floor || !ridersCanExit) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		numRiders--;
		this.notifyAll();

	}

	@Override
	public synchronized void requestFloor(int floor) {
		requestedFloors.set(floor, requestedFloors.get(floor) + 1);

	}

	@Override
	public int getFloor() {
		return myFloor;
	}

	@Override
	public synchronized void callUp(int fromFloor) {
		upCalls.set(fromFloor, upCalls.get(fromFloor) + 1);
	}

	@Override
	public synchronized void callDown(int fromFloor) {
		downCalls.set(fromFloor, downCalls.get(fromFloor) + 1);
	}

}
