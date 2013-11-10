package elevator;


public class ElevatorDriver {
    public static void main(String[] args) {
        AbstractBuilding building = new Building(5, 1);
        Thread rider1 = new Thread(new Rider(building, 0, 3), "A");
        Thread rider2 = new Thread(new Rider(building, 1, 3), "B");
        Thread rider3 = new Thread(new Rider(building, 4, 0), "C");
        Thread rider4 = new Thread(new Rider(building, 2, 4), "D");
        Thread rider5 = new Thread(new Rider(building, 3, 4), "E");
        Thread rider6 = new Thread(new Rider(building, 3, 2), "F");

        rider1.start();
        rider2.start();
        rider3.start();
        rider4.start();
        rider5.start();
        rider6.start();
    }
}
