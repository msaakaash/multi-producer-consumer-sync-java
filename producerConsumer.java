
import java.util.LinkedList;
import java.util.Queue;

public class prod {
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
    }
}

class SharedBuffer {
    private final Queue<Integer> buffer = new LinkedList<>();
    private final int capacity;
    private int producedCount = 0;
    private final int totalItemsToProduce = 10; // 5 items per producer * 2 producers

    public SharedBuffer(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce(int item) throws InterruptedException {
        while (buffer.size() == capacity) {
            wait(); // Wait if buffer is full
        }
        buffer.add(item);
        producedCount++;
        System.out.println(Thread.currentThread().getName() + " Produced: " + item);
        displayBuffer();
        notifyAll();
    }

    public synchronized int consume() throws InterruptedException {
        while (buffer.isEmpty() && producedCount < totalItemsToProduce) {
            wait(); // Wait if buffer is empty
        }

        if (buffer.isEmpty()) return -1; // Stop consuming if production is complete

        int item = buffer.poll();
        System.out.println(Thread.currentThread().getName() + " Consumed: " + item);
        displayBuffer(); // Display buffer after consuming an item
        notifyAll(); // Notify producers
        return item;
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
                Thread.sleep((int) (Math.random() * 1000)); // Simulating production time
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
                Thread.sleep((int) (Math.random() * 1000)); // Simulating consumption time
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
