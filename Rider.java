package elevator;

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
        elevator.RequestFloor(destinationFloor);
        elevator.Exit();
    }

    public synchronized AbstractElevator callElevator(){
        if(currentFloor < destinationFloor) {
            return building.CallUp(currentFloor);
        } else {
            return building.CallDown(currentFloor);
        }
    }
}
