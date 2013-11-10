
public class Building extends AbstractBuilding {
    /**
     * Other variables/data structures as needed goes here
     */
    private Elevator theElevator;
//    private List<AbstractElevator> elevators;
    public Building(int numFloors, int numElevators, int maxCapacity) {
        super(numFloors, numElevators, maxCapacity);
//        for (int i = 0; i < numElevators; i++) {
//           elevators.add(new Elevator(numFloors, i, MAX_CAPACITY));
//        }
        theElevator = new Elevator(numFloors, 0, maxCapacity);
        // start the elevators
        startElevators();
    }
    public synchronized void startElevators() {
        Thread elevator = new Thread(theElevator, "Elevator");
        elevator.start();
    }
    // by default will return the current elevator
    // need ot extend to handle multiple elevators.
    @Override
    public synchronized AbstractElevator CallUp(int fromFloor) {
        // add a request for an elevator
        // and wait for it to arrive
        theElevator.RequestFloor(fromFloor);
        return theElevator;
    }

    @Override
    public synchronized AbstractElevator CallDown(int fromFloor) {
        theElevator.RequestFloor(fromFloor);
        return theElevator;
    }
}
