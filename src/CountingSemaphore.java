class CountingSemaphore {

    /*
            Allows multiple threads to access a fixed set of resources in parallel
     */
    int usedPermits = 0;
    int maxCount;

    public CountingSemaphore(int count) {
        this.maxCount = count;

    }

    public CountingSemaphore(int count, int initialAvailablePermits) {
        this.maxCount = count;
        this.usedPermits = this.maxCount - initialAvailablePermits;
    }

    public synchronized void acquire() throws InterruptedException {

        while (usedPermits == maxCount)
            wait();

        notify();
        usedPermits++;

    }

    public synchronized void release() throws InterruptedException {

        while (usedPermits == 0)
            wait();

        usedPermits--;
        notify();
    }
}
