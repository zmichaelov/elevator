package elevator;

import java.util.List;

public class Elevator extends AbstractElevator implements Runnable {
	
	private List<Integer> upCalls;
	private List<Integer> downCalls;

	public Elevator(int numFloors, int elevatorId, int maxOccupancyThreshold, AbstractBuilding building) {
		super(numFloors, elevatorId, maxOccupancyThreshold, building);
	}

	@Override
	public void run() {
		init();
	}

	private synchronized void init() {
		while(upCalls.isEmpty() && downCalls.isEmpty()){
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
	public boolean Enter() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void Exit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RequestFloor(int floor) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getFloor(){
		return myFloor;
	}

	@Override
	public void callUp(int fromFloor) {
		//TODO
	}

	@Override
	public void callDown(int fromFloor) {
		// TODO Auto-generated method stub
		
	}

}
