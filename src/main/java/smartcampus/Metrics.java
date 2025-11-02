package smartcampus;

public class Metrics {
    public long dfsCalls = 0;
    public long edgeVisits = 0;
    public long queueOps = 0;
    public long relaxations = 0;
    private long startTime;
    private long endTime;

    public void startTimer() { startTime = System.nanoTime(); }
    public void stopTimer() { endTime = System.nanoTime(); }
    public double elapsedMillis() { return (endTime - startTime) / 1e6; }

    public void reset() {
        dfsCalls = edgeVisits = queueOps = relaxations = 0;
    }

    @Override
    public String toString() {
        return String.format("Time=%.3fms, dfsCalls=%d, edgeVisits=%d, queueOps=%d, relaxations=%d",
                elapsedMillis(), dfsCalls, edgeVisits, queueOps, relaxations);
    }
}
