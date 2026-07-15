// A race condition is triggered when a thread jumps in between the process, cause the summation is a 3 step process: read, increment, write back

// AtomicInteger solves this by making all 3 steps happen as one unbreakable ops. No thread jumps in the middle.

import java.util.concurrent.atomic.AtomicInteger;

class AtomicCounter {

    private AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet(); // atomic: read + increment + write happen as one step
    }

    public int getCount() {
        return count.get();
    }
}

public class Atomic {

    public static void main(String[] args) throws InterruptedException {

        AtomicCounter counter = new AtomicCounter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Count: " + counter.getCount()); // reliably 2000, every run

    }

}

// ATOMIC VS SYNCHRONIZED
// 1. Synchronized
// locks entire method.
// other threads must wait outside.
// like a toilet with a lock - one person at a time.

// 2. AtomicInteger
// no lock needed
// uses low level CPU instructions to do read+add+write atomically.
// faster than synchronized
// handles one transaction completely before accepting the next.
