package elevator;

import barrier.AbstractEventBarrier;
import barrier.EventBarrier;

public class Elevator extends AbstractElevator {

    /**
     * Other variables/data structures as needed goes here
     */
    private AbstractEventBarrier incoming, outgoing;
    public Elevator(int numFloors, int elevatorId, int maxOccupancyThreshold) {
        super(numFloors, elevatorId, maxOccupancyThreshold);
        incoming = new EventBarrier();
        outgoing = new EventBarrier();
    }

    @Override
    public void OpenDoors() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void ClosedDoors() {

        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void VisitFloor(int floor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean Enter() {
        incoming.raise(); // allow waiting riders to enter
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void Exit() {
        outgoing.raise();
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void RequestFloor(int floor) {

    }
}
