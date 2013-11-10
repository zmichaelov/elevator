package elevator;

import java.util.ArrayList;
import java.util.List;

public class Building extends AbstractBuilding {
	/**
	 * Other variables/data structures as needed goes here
	 */
	private List<Elevator> elevators;

	// private List<AbstractElevator> elevators;
	public Building(int numFloors, int numElevators, int maxCapacity) {
		super(numFloors, numElevators, maxCapacity);
		elevators = new ArrayList<Elevator>();
		for (int i = 0; i < numElevators; i++) {
			Elevator e = new Elevator(numFloors, i,maxCapacity);
			elevators.add(e);
			synchronized (this) {
				Thread e_t = new Thread(e, "E" + i);
				e_t.start();
			}
		}
	}

	// by default will return the current elevator
	// need to extend to handle multiple elevators.
	@Override
	public synchronized AbstractElevator CallUp(int fromFloor) {
		// add a request for an elevator
		// and wait for it to arrive
		Elevator e = determineElevator(fromFloor);
		e.RequestFloor(fromFloor);
		return e;
	}

	@Override
	public synchronized AbstractElevator CallDown(int fromFloor) {
		Elevator e = determineElevator(fromFloor);
		e.RequestFloor(fromFloor);
		return e;
	}

	private synchronized Elevator determineElevator(int fromFloor) {
		Elevator result = null;
		for (Elevator elevator : elevators) {
			if(result != null){
				if (elevator.getRequests().contains(fromFloor) ||
						elevator.getFloor() == fromFloor && elevator.isOpen())
					return elevator;
				if (result.getRequests().size() >= elevator.getRequests().size()) {
					if(result.getRequests().size() == elevator.getRequests().size()){
						if(result.getRequests().size() != 0){
							if(Math.abs(result.getLastRequest() - fromFloor) >= Math.abs(elevator.getLastRequest() - fromFloor) && !elevator.isOpen()){
								result = elevator;
							}
						} else{
							if(Math.abs(result.getFloor() - fromFloor) >= Math.abs(elevator.getFloor() - fromFloor) && !elevator.isOpen()){
								result = elevator;
							}
						}
					} else result = elevator;
				}
			} else result = elevator;
		}
		return result;
	}
}
