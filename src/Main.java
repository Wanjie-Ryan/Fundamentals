// RACE CONDITION
class Counter {
    // simple class with one variable - count.
    // starts at 0
    // private means only Counter itself can access it directly
    private int count = 0;

    public void increment() {
        count++; // looks like one ops but its 3: 1. Read count 2. increment 3. write back
        // this is where the race condition happens. Btn step 1 and 3, another thread
        // can jump in.
    }

    public int getCount() {
        return count;
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // throws InterruptedException is required cause .join() can throw this
        // exception.
        // means "something interrupted this thread while it waiting"

        Counter counter = new Counter();
        // create object

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });
        // Creates thread 1 but does not start it yet. When started, t1 will call
        // counter.increment() 1000 times.
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });
        t1.start(); // tells java to beging executing this thread.
        t2.start(); // doesn't wait for t1 to finish, it will start executing t2, and both run
                    // together.
        // race condition happens here.
        t1.join();
        // join means "wait for this thread to finish before continuing"
        // without join, main thread would print the count before t1 and t2 finish their
        // 1000 iterations each. You'd always print 0 or some tiny number.
        t2.join(); // main thread pauses here until t2 is done.

        System.out.println("Count: " + counter.getCount());
        // continues to the println

    }
}

// NOTES
// Expected: 2000 (1000 from t1 + 1000 from t2)
// Actual: somewhere btn 1000-2000 - unprectictable
// Why? increments were lost - 2 threads read the same value simultaneously, both added 1, both wrote back - but one write overwrote the other. Once increment disappeared.
// when you run it multiple times, you will see different results each time. NOT 2000.@interface

