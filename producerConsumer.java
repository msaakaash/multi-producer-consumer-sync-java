import java.util.LinkedList;
import java.util.Queue;

public class Prod {
    public static void main(String[] args) {
        SharedBuffer buffer = new SharedBuffer(5);

        Thread producer1 = new Thread(new Producer(buffer, 1), "Producer-1");
        Thread producer2 = new Thread(new Producer(buffer, 2), "Producer-2");
        Thread consumer1 = new Thread(new Consumer(buffer, 1), "Consumer-1");
        Thread consumer2 = new Thread(new Consumer(buffer, 2), "Consumer-2");

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();

        try {
            producer1.join();
            producer2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        buffer.setProductionComplete(); // Notify consumers that production is complete
    }
}

class SharedBuffer {
    private final Queue<Integer> buffer = new LinkedList<>();
    private final int capacity;
    private int producedCount = 0;
    private final int totalItemsToProduce = 10; // 5 items per producer * 2 producers
    private boolean productionComplete = false; // Flag to indicate when production is complete

    public SharedBuffer(int capacity) {
        this.capacity = capacity;
    }

    // Producer method: Produces an item and adds it to the buffer
    public synchronized void produce(int item) {
        try {
            while (buffer.size() == capacity) {
                wait(); // Wait if the buffer is full
            }
            buffer.add(item);
            producedCount++;
            System.out.println(Thread.currentThread().getName() + " Produced: " + item);
            displayBuffer();
            notify(); // Notify one consumer that an item is available
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(Thread.currentThread().getName() + " was interrupted while producing.");
        }
    }

    // Consumer method: Consumes an item from the buffer
    public synchronized int consume() {
        try {
            while (buffer.isEmpty()) {
                if (productionComplete && producedCount >= totalItemsToProduce) {
                    return -1; // Stop consuming if production is complete
                }
                wait(); // Wait if the buffer is empty
            }

            int item = buffer.poll();
            System.out.println(Thread.currentThread().getName() + " Consumed: " + item);
            displayBuffer();
            notify(); // Notify one producer that space is available
            return item;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(Thread.currentThread().getName() + " was interrupted while consuming.");
            return -1;
        }
    }

    // Mark production as complete so consumers can stop waiting
    public synchronized void setProductionComplete() {
        productionComplete = true;
        notifyAll(); // Notify all consumers waiting on an empty buffer
    }

    private synchronized void displayBuffer() {
        System.out.print("Current Buffer: [ ");
        for (int item : buffer) {
            System.out.print(item + " ");
        }
        System.out.println("]");
    }
}

class Producer implements Runnable {
    private final SharedBuffer buffer;
    private final int id;

    public Producer(SharedBuffer buffer, int id) {
        this.buffer = buffer;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                int item = id * 100 + i;
                buffer.produce(item);
                Thread.sleep((int) (Math.random() * 500)); // Simulating random production time
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer implements Runnable {
    private final SharedBuffer buffer;
    private final int id;

    public Consumer(SharedBuffer buffer, int id) {
        this.buffer = buffer;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int item = buffer.consume();
                if (item == -1) break; // Stop consuming if all items are produced
                Thread.sleep((int) (Math.random() * 500)); // Simulating random consumption time
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
