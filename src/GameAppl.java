import view.*;

import java.util.*;


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

        Integer nRacers = inputOutput.readInteger(
                String.format("Enter number of threads in the game [%d-%d]", minThreads, maxThreads),
                minThreads, maxThreads);

        Integer distance = inputOutput.readInteger(
                String.format("Enter distance in the game [%d-%d]", minDistance, maxDistance),
                minDistance, maxDistance);

        Racer[] racers = new Racer[nRacers];
        Race race = new Race(2,5,distance);

        startRacers(racers,race);
        waitRacers(racers);
        inputOutput.writeLn("Winner is " + race.photoFinish);
    }

    private static void waitRacers(Racer[] racers) {
        for(Racer racer : racers){
            try {
                racer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void startRacers(Racer[] racers, Race race) {
        for (int i = 0; i < racers.length; i++) {
            racers[i] = new Racer(i+1, race);
            racers[i].start();
        }
    }



}
