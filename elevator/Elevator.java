package elevator;

import barrier.AbstractEventBarrier;
import barrier.EventBarrier;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Elevator extends AbstractElevator implements Runnable{

    /**
     * Other variables/data structures as needed goes here
     */
    private volatile int currentFloor;
    private List<Integer> requestedFloors;
    private AtomicInteger numRiders;
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
        numRiders = new AtomicInteger(0);
    }

    @Override
    public synchronized void OpenDoors() {
    	doorsOpen = true;
        ElevatorLogger.log(Thread.currentThread().getName() +" on F"+currentFloor+" opens");
        doorBarriers.get(currentFloor).raise();// signal waiting riders to Enter() and Exit()
        // this will block until all riders have gotten on
        this.ClosedDoors(); // close doors when they are done
    }

    @Override
    public synchronized void ClosedDoors() {
            // block until rider enters
    	doorsOpen = false;
        ElevatorLogger.log(Thread.currentThread().getName()+" on F"+currentFloor+" closes");
        System.out.println("Number of riders after closing on floor " + currentFloor + ": " + numRiders.get());
    }

    @Override
    public synchronized void VisitFloor(int floor) {
        if (currentFloor < floor) {
            ElevatorLogger.log(Thread.currentThread().getName() +" moves up to F"+floor);
        } else if (currentFloor > floor) {
            ElevatorLogger.log(Thread.currentThread().getName() +" moves down to F"+floor);
        }
        currentFloor = floor;
        this.OpenDoors();
    }
    // one event barrier should work for both leaving and
    // entering riders
    @Override
    public boolean Enter() {
        if (numRiders.get() == maxOccupancyThreshold) {// failure if we are at capacity
            System.out.println(Thread.currentThread().getName() + " could not enter on " + currentFloor);
            doorBarriers.get(currentFloor).complete(); // even if we have failed, we mark as complete or
            // this generation of the event barrier will hang indefinitely
            return false;
        }
        ElevatorLogger.log("R"+Thread.currentThread().getName()+" enters E? on F"+currentFloor);
        numRiders.incrementAndGet();// increment the number of riders
        doorBarriers.get(currentFloor).complete(); // signal that a rider has gotten on
        return true;
    }

    @Override
    public void Exit() {
        ElevatorLogger.log("R"+Thread.currentThread().getName()+" exits E? on F"+currentFloor);
        numRiders.decrementAndGet();        
        doorBarriers.get(currentFloor).complete();
    }

    @Override
    public void RequestFloor(int floor) {
        synchronized (doorBarriers.get(floor)) {// need to use the event barrier lock or we will deadlock
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
