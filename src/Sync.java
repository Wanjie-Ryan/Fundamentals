class SyncCounter {
    private int count = 0;

    public synchronized void increment() {
        count++; // only one thread at a time
    }

    public synchronized int getCount() {
        return count;
    }

}

public class Sync {

    public static void main(String[] args) throws InterruptedException {

        SyncCounter counters = new SyncCounter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counters.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counters.increment();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Count: " + counters.getCount());

    }

}

// synchronized forces threads to take turns. Thread 2 must wait for thread 1 to
// finish all 3 steps (read, add, write) before it can start. No more lost
// increments.

// Without synchronized:
// t1 reads count=5
// t2 reads count=5 ← same time, before t1 writes back
// t1 writes count=6
// t2 writes count=6 ← overwrites t1, should be 7
// One increment lost.

// With synchronized:
// t1 reads count=5
// t1 writes count=6 ← t2 must WAIT until t1 finishes
// t2 reads count=6
// t2 writes count=7 ← correct
// No lost increments.