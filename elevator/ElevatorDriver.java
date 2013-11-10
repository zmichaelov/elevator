package elevator;

import java.io.*;


public class ElevatorDriver {
    public static void main(String[] args) {
        
        try {
            BufferedReader br = new BufferedReader(new FileReader("Elevator.input"));
            String line;
            int floors = 0, elevators = 0, riders = 0, max_capacity = 1000;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 4) {
                    floors = Integer.parseInt(parts[0]);
                    elevators = Integer.parseInt(parts[1]);
                    riders = Integer.parseInt(parts[2]);
                    max_capacity = Integer.parseInt(parts[3]);
                    break;
                }
            }
            br.close();

            AbstractBuilding building = new Building(floors, elevators, max_capacity);
            for (int i = 1; i <= riders; i++) {
                Thread r = new Thread(new Rider(building), Integer.toString(i));
                r.start();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
