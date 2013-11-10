package elevator;
import java.util.ArrayList;
import java.util.List;

public class Building extends AbstractBuilding implements Runnable{

	private static final int MAX_OCCUPANTS = 0;
	private List<Integer> upCalls;
	private List<Integer> downCalls;
	private List<Elevator> myElevators;
	private AbstractElevator myCurrentElevator;

	public Building(int numFloors, int numElevators) {
		super(numFloors, numElevators);
		initElevators(numElevators);
	}

	@Override
	public void run() {
		waitForCall();
		processCalls();
	}

	private synchronized void processCalls() {
		myCurrentElevator = determineElevator();
		for(Integer i:upCalls){
			myCurrentElevator.callUp(i);
		}
		for(Integer j: downCalls){
			myCurrentElevator.callDown(j);
		}
	}
	private synchronized void waitForCall() {
		while(upCalls.isEmpty() && downCalls.isEmpty()){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

	private void initElevators(int numElevators) {
		myElevators = new ArrayList<Elevator>();
		for (int i = 1; i <= numElevators; i++) {
			Elevator e = new Elevator(numFloors, i, MAX_OCCUPANTS, this);
			Thread t = new Thread(e);
			myElevators.add(e);
			t.start();
		}
	}

	@Override
	public synchronized AbstractElevator CallUp(int fromFloor) {
		upCalls.add(fromFloor);
		this.notify();
		try {
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null; //TODO
		
	}

	private AbstractElevator determineElevator() {
		// for part one with only one elevator.
		return myElevators.get(0);
	}

	@Override
	public AbstractElevator CallDown(int fromFloor) {
		// TODO Auto-generated method stub
		return null;
	}

}
