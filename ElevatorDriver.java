
public class ElevatorDriver {
    public static void main(String[] args) {
        AbstractBuilding building = new Building(5, 1);
        Thread rider1 = new Thread(new Rider(building, 0, 3), "1");
        Thread rider2 = new Thread(new Rider(building, 1, 3), "2");
        Thread rider3 = new Thread(new Rider(building, 4, 0), "3");
        Thread rider4 = new Thread(new Rider(building, 2, 4), "4");
        Thread rider5 = new Thread(new Rider(building, 3, 4), "5");
        Thread rider6 = new Thread(new Rider(building, 3, 2), "6");

        rider1.start();
        rider2.start();
        rider3.start();
        rider4.start();
        rider5.start();
        rider6.start();
    }
}
