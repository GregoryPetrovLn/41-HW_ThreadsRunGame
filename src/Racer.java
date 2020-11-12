import java.util.Random;


public class Racer extends Thread {
    private int threadId;
    private Race race;

    public Racer(int threadId, Race race) {
        this.threadId = threadId;
        this.race = race;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public int getThreadId() {
        return threadId;
    }

    @Override
    public void run() {
        int sleepDelta = race.max_sleep - race.min_sleep + 1;

        for (int i = 0; i < race.distance; i++) {
            System.out.println(threadId);
            try {
                sleep((long) (race.min_sleep + Math.random() * sleepDelta));
            } catch (InterruptedException e) {

            }
        }

        if (race.photoFinish == 0) {
            race.photoFinish = threadId;
        }

    }


}
