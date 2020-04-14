class BlockingQueueWithWaitNotify {

    Integer[] array;
    Object lock = new Object();
    int size = 0;
    int capacity;
    int head = 0;

    public BlockingQueueWithWaitNotify(int capacity) {
        array = new Integer[capacity];
        this.capacity = capacity;
    }

    public void enqueue(Integer item) throws InterruptedException {
        synchronized (lock) {
            while (size == capacity) {
                lock.wait();
            }
            int tailIndex = ( head + size ) % capacity;
            array[tailIndex] = item;
            size++;
            lock.notifyAll();
        }
    }

    public Integer dequeue() throws InterruptedException {

        Integer item = null;
        synchronized (lock) {
            while (size == 0) {
                lock.wait();
            }
            item = array[head];
            head = ( head + 1 ) % capacity;
            size--;
            lock.notifyAll();
        }

        return item;
    }
}



