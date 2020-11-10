import java.util.Random;


public class ThreadsRace extends Thread {
    private int nRuns;
    private String name;
    private boolean completed = false;

    public ThreadsRace(String name, int nRuns) {
        super();
        this.nRuns = nRuns;
        this.name = name;
    }

    @Override
    public void run() {
        int minDelay = 2;
        int maxDelay = 5;

        for (int i = 0; i < nRuns; i++) {
            System.out.println(name);
            try {
                sleep(new Random().nextInt((maxDelay - minDelay) + 1) + minDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        completed = true;

    }

    public boolean isCompleted() {
        return completed;
    }



//    private String threadMsg(String msg){
//        String threadName = Thread.currentThread().getName();
//        return null;
//    }

}
