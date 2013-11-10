
package elevator;

import barrier.AbstractEventBarrier;
import barrier.EventBarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Elevator extends AbstractElevator implements Runnable{

    /**
     * Other variables/data structures as needed goes here
     */
    private volatile int currentFloor;
    private List<Integer> requestedFloors;
    private List<AbstractEventBarrier> doorBarriers;
	private boolean doorsOpen;
    // need to count which riders get off at which point
    public Elevator(int numFloors, int elevatorId, int maxOccupancyThreshold) {
        super(numFloors, elevatorId, maxOccupancyThreshold);
        requestedFloors = new ArrayList<Integer>();
        doorBarriers = new ArrayList<AbstractEventBarrier>();
        for (int i = 0; i < numFloors; i++) {
            doorBarriers.add(new EventBarrier());
        }
        currentFloor = 0; // start at bottom
        doorsOpen = false;
    }

    @Override
    public synchronized void OpenDoors() {
        System.out.println(Thread.currentThread().getName() +": Doors Opening on Floor " + currentFloor);
        doorsOpen = true;
        doorBarriers.get(currentFloor).raise();// signal waiting riders to Enter() and Exit()
        // this will block until all riders have gotten on
        this.ClosedDoors(); // close doors when they are done
    }

    @Override
    public synchronized void ClosedDoors() {
            // block until rider enters
    	doorsOpen = false;
        System.out.println(Thread.currentThread().getName() +": Doors Closing on Floor " + currentFloor);
    }

    @Override
    public synchronized void VisitFloor(int floor) {
        System.out.println(Thread.currentThread().getName() +": Visiting Floor " + floor);
        currentFloor = floor;
        this.OpenDoors();
    }
    // one event barrier should work for both leaving and
    // entering riders
    @Override
    public boolean Enter() {
        System.out.println(Thread.currentThread().getName() + " entering on Floor " + currentFloor);
        doorBarriers.get(currentFloor).complete(); // signal that a rider has gotten on
        return true;
    }

    @Override
    public void Exit() {
        System.out.println(Thread.currentThread().getName() + " exiting on Floor " + currentFloor);
        doorBarriers.get(currentFloor).complete();
    }

    @Override
    public void RequestFloor(int floor) {
        synchronized (doorBarriers.get(floor)) {// need to use the event barrier lock or we will deadlock
            System.out.println(Thread.currentThread().getName() + " requesting Floor " + floor);
            requestedFloors.add(floor);
            doorBarriers.get(floor).arrive();
        }
    }

    @Override
    public void run() {// the elevator is a thread
        while(true) {

            while(!requestedFloors.isEmpty()) {
                Integer nextFloor = requestedFloors.remove(0);
                assert(nextFloor != null);
                VisitFloor(nextFloor);
            }

        }
    }
    
    public synchronized int getFloor(){
    	return currentFloor;
    }
    
    public synchronized int getLastRequest() {
    	return requestedFloors.get(requestedFloors.size()-1);
    }

	public synchronized List<Integer> getRequests() {
		return requestedFloors;
	}

	public synchronized boolean isOpen() {
		return doorsOpen;
	}
}
