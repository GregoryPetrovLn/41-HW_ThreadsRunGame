import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Racer extends Thread {
    private int threadId;
    private Race race;
    static final Object mutex = new Object();
    private long finishTime;
    private int placeOnFinish;
    private CyclicBarrier gate;


    public Racer(int threadId, Race race, CyclicBarrier gate) {
        this.threadId = threadId;
        this.race = race;
        this.gate = gate;
    }

    public int getPlaceOnFinish() {
        return placeOnFinish;
    }

    public long getFinishTime() {
        return finishTime;
    }


    public int getThreadId() {
        return threadId;
    }

    @Override
    public void run() {
        Instant start = Instant.now();

        openGate();

        startRace();

        synchronized (mutex) {
            setFinisher(start);
        }
    }

    /**
     *
     */
    private void startRace() {
        int sleepDelta = race.getMax_sleep() - race.getMin_sleep() + 1;

        for (int i = 0; i < race.getDistance(); i++) {
            System.out.println(threadId);
            try {
                sleep((long) (race.getMin_sleep() + Math.random() * sleepDelta));
            } catch (InterruptedException e) {

            }
        }
    }

    /**
     *
     */
    private void openGate() {
        try {
            gate.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param start
     */
    private void setFinisher(Instant start) {
        Instant finish = Instant.now();

        //check if thread is a first one
        if (race.getPhotoFinish() == 0) {
            race.setPhotoFinish(threadId);
        }

        //set place for finisher
        int prevPlace = race.getPlace();
        placeOnFinish = prevPlace;
        race.setPlace(++prevPlace);

        //set time for finisher
        finishTime = ChronoUnit.MILLIS.between(start, finish);
    }


}
