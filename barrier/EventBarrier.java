package barrier;
import java.util.*;

public class EventBarrier extends AbstractEventBarrier {

	Boolean isRaised = false;
	int waitingCount = 0;

	public EventBarrier () {}

	public synchronized void arrive() {
		this.waitingCount++;
		while (!isRaised) {
			try {
				this.wait();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	public synchronized void raise() {
		this.isRaised = true;
		this.notifyAll();
		while (this.waiters() > 0) {
			try {
				this.wait();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		isRaised = false;
	}

	public synchronized void complete() {
		this.waitingCount--;
		while (this.waiters() > 0) {
			try {
				this.wait();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		this.notifyAll();
	}

	public synchronized int waiters() {
		return this.waitingCount;
	}

	public static class Decorator extends AbstractEventBarrier {

		EventBarrier eb;
		String name;

		public Decorator (EventBarrier eb, String name) {
			this.eb = eb;
			this.name = name;
		}

		public void arrive() { 
			System.out.println(this.name + ": Arrived");
			this.eb.arrive(); 
			System.out.println(this.name + ": Running");
		}

		public void raise() { 
			System.out.println(this.name + ": Raising!");
			this.eb.raise();
			System.out.println(this.name + ": ALL DONE");
		}

		public void complete() {
			System.out.println(this.name + ": Done");
			this.eb.complete();
		}

		public int waiters() {
			int w = this.eb.waiters();
			System.out.println(this.name + " sees " + w + " waiters");
			return w;
		}

	}

	public static class ControllerDecorator extends Decorator implements Runnable {

		public ControllerDecorator (EventBarrier eb) {
			super(eb, "Controller");
		}

		public void run() {
			System.out.println(this.name + " init!");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			super.raise();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			super.raise();
		}

	}

	public static class MinstrelDecorator extends Decorator implements Runnable {

		public int timeout;

		public MinstrelDecorator (EventBarrier eb, String name, int timeout) {
			super(eb, name);
			this.timeout = timeout;
		}

		public void run() {
			System.out.println(this.name + " init!");
			try {
				Thread.sleep(this.timeout);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			super.arrive();
			try {
				Thread.sleep(this.timeout);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			super.complete();
		}
	}

	public static void main(String [] args) {
		EventBarrier eb = new EventBarrier();

		// Controller
		ControllerDecorator controller = new ControllerDecorator(eb);
		Thread controller_t = new Thread(controller);
		controller_t.start();

		MinstrelDecorator abe = new MinstrelDecorator(eb, "Abe", 1);
		Thread abe_t = new Thread(abe);
		abe_t.start();

		MinstrelDecorator bob = new MinstrelDecorator(eb, "Bob", 2000);
		Thread bob_t = new Thread(bob);
		bob_t.start();

		MinstrelDecorator chris = new MinstrelDecorator(eb, "Chris", 3000);
		Thread chris_t = new Thread(chris);
		chris_t.start();

	}

}