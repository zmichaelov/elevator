
package elevator;

import java.util.List;

public class Building extends AbstractBuilding {
    /**
     * Other variables/data structures as needed goes here
     */
    private AbstractElevator theElevator;
//    private List<AbstractElevator> elevators;
    public Building(int numFloors, int numElevators) {
        super(numFloors, numElevators);
//        for (int i = 0; i < numElevators; i++) {
//           elevators.add(new Elevator(numFloors, i, 10));
//        }
        theElevator = new Elevator(numFloors, 0, 10);
    }

    @Override
    public synchronized AbstractElevator CallUp(int fromFloor) {
        theElevator.VisitFloor(fromFloor);
        return theElevator;
    }

    @Override
    public synchronized AbstractElevator CallDown(int fromFloor) {

        return theElevator;
    }
}
