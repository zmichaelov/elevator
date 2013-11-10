package elevator;


public class ElevatorDriver {
    public static void main(String[] args) {
        AbstractBuilding building = new Building(20, 3);
        int j = 19;
        for(int i = 0; i < 10; i++ ){
        	Thread rider = new Thread(new Rider(building, i, j), "--r"+i);
        	rider.start();
        	j--;
        }
        
    }
}
