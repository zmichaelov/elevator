package elevator;

import java.io.*;

public class Rider implements Runnable {
    private AbstractBuilding building;

    private volatile int currentFloor, destinationFloor;

    public Rider(AbstractBuilding building) {
        this.building = building;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Elevator.input"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    // Check if line is for this rider
                    if (parts[0].equals(Thread.currentThread().getName())) {
                        this.currentFloor = Integer.parseInt(parts[1]);
                        this.destinationFloor = Integer.parseInt(parts[2]);
                        // Call and wait for Elevator
                        AbstractElevator elevator = callElevator();
                        while (!elevator.Enter()){// loop until we successfully call an elevator
                            elevator = callElevator();
                        }
                        pushElevatorButton(elevator, destinationFloor);
                        elevator.Exit();
                    }
                }
            }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
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
