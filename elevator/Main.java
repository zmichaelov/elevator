package elevator;

public class Main {
	
	public static void main(String[] args){
		Building b = new Building(10, 1);
		Elevator e1 = new Elevator(10, 1, 100, "e1");
		Rider r1 = new Rider(b, "r1", 3, 8);		
		
		Thread b_t = new Thread(b);
		b_t.start();
		
		Thread e1_t = new Thread(e1);
		e1_t.start();
		
		Thread r1_t = new Thread(r1);
		r1_t.start();
		
		
	}

}
