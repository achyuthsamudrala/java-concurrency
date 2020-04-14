public class BlockingQueueWithSemaphore {
    Integer[] array;
    int size = 0;
    int capacity;
    int head = 0;
    CountingSemaphore semLock = new CountingSemaphore(1, 1);
    CountingSemaphore semProducer;
    CountingSemaphore semConsumer;

    public BlockingQueueWithSemaphore(int capacity) {
        array = new Integer[capacity];
        this.capacity = capacity;
        this.semProducer = new CountingSemaphore(capacity, capacity);
        this.semConsumer = new CountingSemaphore(capacity, 0);
    }

    public void enqueue(Integer item) throws InterruptedException {

        semProducer.acquire();
        semLock.acquire();

        int tailIndex = ( head + size ) % capacity;
        array[tailIndex] = item;
        size++;

        semLock.release();
        semConsumer.release();
    }

    public Integer dequeue() throws InterruptedException {
        semConsumer.acquire();
        semLock.acquire();

        Integer item = array[head];
        head = ( head + 1 ) % capacity;
        size--;

        semLock.release();
        semProducer.release();

        return item;
    }
}
