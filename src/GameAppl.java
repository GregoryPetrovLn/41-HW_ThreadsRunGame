import view.*;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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

    private static void startGame(InputOutput inputOutput) {
        int minThreads = 3;
        int maxThreads = 10;

        int minDistance = 100;
        int maxDistance = 3500;

        Integer nThreads = inputOutput.readInteger(
                String.format("Enter number of threads in the game [%d-%d]", minThreads, maxThreads),
                minThreads, maxThreads);

        Integer distance = inputOutput.readInteger(
                String.format("Enter distance in the game [%d-%d]", minDistance, maxDistance),
                minDistance, maxDistance);



        race(nThreads, distance);


        inputOutput.writeLn("finish");
    }


    private static void race(Integer nThreads, Integer distance) {
        ArrayList<ThreadsRace> threads = new ArrayList<>(generateThreads(nThreads, distance));



        threads.stream().forEach(ThreadsRace::start);


        String threadChampion = "";
        for (ThreadsRace thread : threads) {
            try {
                thread.join();
                if(thread.isCompleted()){
                    threadChampion = thread.getName();
                    //break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        System.out.println("Champion is " + threadChampion);

    }

    private static ArrayList<ThreadsRace> generateThreads(Integer nThreads, Integer distance) {
        ArrayList<ThreadsRace> threads = new ArrayList<>();

        for (int i = 0; i < nThreads; i++) {
            String name = "" + i;
            threads.add(new ThreadsRace(name, distance));
        }
        return threads;
    }
}
