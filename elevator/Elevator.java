package elevator;

import java.util.ArrayList;
import java.util.List;

public class Elevator extends AbstractElevator implements Runnable {

	private List<Integer> upCalls;
	private List<Integer> downCalls;
	private boolean doorsOpen;
	private int numRiders;
	private List<Integer> requestedFloors;

	public Elevator(int numFloors, int elevatorId, int maxOccupancyThreshold,
			AbstractBuilding building) {
		super(numFloors, elevatorId, maxOccupancyThreshold, building);
		upCalls = new ArrayList<Integer>(numFloors);
		downCalls = new ArrayList<Integer>(numFloors);
		requestedFloors = new ArrayList<Integer>(numFloors);
	}

	@Override
	public void run() {
		init();
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
	public void OpenDoors() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ClosedDoors() {
		// TODO Auto-generated method stub

	}

	@Override
	public void VisitFloor(int floor) {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized boolean Enter(int floor) {
		while (myFloor != floor || !doorsOpen) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		numRiders++;
		this.notifyAll();
		return true;
	}

	@Override
	public synchronized void Exit(int floor) {
		while (!doorsOpen || myFloor != floor){
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
	public synchronized void RequestFloor(int floor) {
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
