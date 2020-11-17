import view.*;

import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class GameAppl {
    private static InputOutput io = new ConsoleInputOutput();

    static Item menuItems[] = {
            Item.of("Game", GameAppl::startGame),
            Item.exit()
    };

    static Menu mainMenu = new Menu("Main menu", new ArrayList<>(Arrays.asList(menuItems)));

    public static void main(String[] args) {
        mainMenu.perform(io);
    }

    /**
     *
     * @param inputOutput
     */
    private static void startGame(InputOutput inputOutput) {
        //get data from view and define a new race
        //0)nRacers
        //1)distance
        int[] raceParams = getRaceParams(inputOutput);
        Race race = new Race(2, 5, raceParams[1]);
        Racer[] racers = new Racer[raceParams[0]];

        //make a gate for threads to start at the same time
        CyclicBarrier gate = new CyclicBarrier(raceParams[0] + 1);

        //starting threads and tell them wait for a command open gate
        startRacers(racers, race, gate);

        //open gates in all threads
        openGateForAllRacers(gate);

        //join() all
        waitRacers(racers);

        //print results
        printResults(racers, inputOutput, race);
    }

    /**
     *
     * @param inputOutput
     * @return
     */
    private static int[] getRaceParams(InputOutput inputOutput) {
        int minThreads = 3;
        int maxThreads = 10;

        int minDistance = 100;
        int maxDistance = 3500;

        Integer nRacers = inputOutput.readInteger(
                String.format("Enter number of threads in the game [%d-%d]", minThreads, maxThreads),
                minThreads, maxThreads);

        Integer distance = inputOutput.readInteger(
                String.format("Enter distance in the game [%d-%d]", minDistance, maxDistance),
                minDistance, maxDistance);

        return new int[]{nRacers, distance};
    }

    /**
     *
     * @param gate
     */
    private static void openGateForAllRacers(CyclicBarrier gate) {
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
     * @param racers
     * @param inputOutput
     * @param race
     */
    private static void printResults(Racer[] racers, InputOutput inputOutput, Race race) {
        inputOutput.writeLn("Winner is " + race.getPhotoFinish());

        for (int i = 0; i < racers.length; i++) {
            long time = racers[i].getFinishTime();
            String result = String.format("Thread #%d finished with place-%d after %d", racers[i].getThreadId(), racers[i].getPlaceOnFinish(), time);
            inputOutput.writeLn(result);
        }
    }

    /**
     *
     * @param racers
     */
    private static void waitRacers(Racer[] racers) {
        for (Racer racer : racers) {
            try {
                racer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param racers
     * @param race
     * @param gate
     */
    private static void startRacers(Racer[] racers, Race race, CyclicBarrier gate) {
        for (int i = 0; i < racers.length; i++) {
            racers[i] = new Racer(i + 1, race, gate);
            racers[i].start();
        }
    }


}
