public class SingleThreadedTokenBucketFilter {

    private final int MAX_TOKENS;
    private long lastRequestTime = System.nanoTime();
    long currentBucketSize = 0;
    long refillRate;

    public SingleThreadedTokenBucketFilter(int maxTokens, long refillRate) {
        MAX_TOKENS = maxTokens;
        this.refillRate = refillRate;
    }

    /*
        Retrieve token if its available. If not sleep for the interval of refill rate.
     */
    synchronized void getToken() throws InterruptedException {
        refill();
        if (currentBucketSize == 0) {
            Thread.sleep(refillRate); // wait to get new tokens
        } else {
            currentBucketSize--;
        }

        System.out.println(
                "Granting " + Thread.currentThread().getName() + " token at " + lastRequestTime / 1_000_000_000);
    }

    void refill() {
        long now = System.nanoTime();
        long tokensToAdd = (now - lastRequestTime) * refillRate / 1_000_000_000;
        currentBucketSize = ( currentBucketSize + tokensToAdd ) % MAX_TOKENS;
        lastRequestTime = now;
    }
}
