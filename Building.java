import java.util.ArrayList;
import java.util.List;

public class Building extends AbstractBuilding implements Runnable{

	private static final int MAX_OCCUPANTS = 0;
	private List<Integer> upCalls;
	private List<Integer> downCalls;
	private AbstractElevator myCurrentElevator;

	public Building(int numFloors, int numElevators) {
		super(numFloors, numElevators);
		initElevators(numElevators);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		init();
		myCurrentElevator = signalElevator();
	}

	private synchronized AbstractElevator signalElevator() {
		AbstractElevator e = determineElevator();
		
		
		
	}

	private synchronized void init() {
		while(upCalls.isEmpty() && downCalls.isEmpty()){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
		this.wait();
		
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
