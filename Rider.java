
public class Rider implements Runnable {
    private AbstractBuilding building;
    private volatile int currentFloor, destinationFloor;

    public Rider(AbstractBuilding building, int currentFloor, int destinationFloor) {
        this.building = building;
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
    }

    @Override
    public void run() {

        AbstractElevator elevator = callElevator();
        while (!elevator.Enter()){// loop until we successfully call an elevator
            elevator = callElevator();
        }
        pushElevatorButton(elevator, destinationFloor);
        elevator.Exit();
    }

    public synchronized AbstractElevator callElevator(){
        if(currentFloor < destinationFloor) {
            ElevatorLogger.log("R"+Thread.currentThread().getName()+" pushes U"+currentFloor);
            return building.CallUp(currentFloor);
        } else {
            ElevatorLogger.log("R"+Thread.currentThread().getName()+" pushes D"+currentFloor);
            return building.CallDown(currentFloor);
        }
    }

    public void pushElevatorButton(AbstractElevator elevator, int destinationFloor) {
        ElevatorLogger.log("R"+Thread.currentThread().getName()+" pushes E?B"+destinationFloor);
        elevator.RequestFloor(destinationFloor);
    }
}
