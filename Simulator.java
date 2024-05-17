import java.util.*;

public class Simulator {

    Leitsteuerungssystem leitsteuerungssystem; // = new Leitsteuerungssystem();
    private final List<UUID> runningMachines = new ArrayList<>();
    private final List<UUID> waitingMachines = new ArrayList<>();
    private final TimerCallback timerCallback = new TimerCallbackImpl();

    public Simulator(Leitsteuerungssystem leitsteuerungssystem) {
        this.leitsteuerungssystem = leitsteuerungssystem;
    }

    public void start(){
        //      time a machine needs to fill one item (3x) -> just an example
        double timePerFillUp = 6.0;
        /**
         * Start with optimal condition:
         * - time difference = exact 1/3 * machineFillingTime
         * - fullPlace = free
         * - emptyPlace = occupied
         */
        leitsteuerungssystem.setEmptyPlaceSensor(true);
        leitsteuerungssystem.setFullPlaceSensor(false);

        runningMachines.add(leitsteuerungssystem.startTimer((1.0 / 3.0) * timePerFillUp, timerCallback));
        runningMachines.add(leitsteuerungssystem.startTimer((2.0 / 3.0) * timePerFillUp, timerCallback));
        runningMachines.add(leitsteuerungssystem.startTimer(timePerFillUp, timerCallback));

    }

    public void onEmptyPlaceSensorChanged(){
        if (!leitsteuerungssystem.getEmptyPlaceSensor()){
            int randomTimeHumanNeeds = humansAreRandom();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Simulator.this.leitsteuerungssystem.setEmptyPlaceSensor(true);
                    System.out.println("Empty place(true): refilled");
                }
            }, randomTimeHumanNeeds * 1000);
        }

    }

    public void onFullPlaceSensorChanged(){
        if (leitsteuerungssystem.getFullPlaceSensor()){
//            leitsteuerungssystem.killTimer(runningMachines.get(0));
//            runningMachines.remove(0);
            int randomTimeHumanNeeds = humansAreRandom();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Simulator.this.leitsteuerungssystem.setFullPlaceSensor(false);
                    System.out.println("Full place(false): picked up");
                }
            }, randomTimeHumanNeeds * 1000);
        }


    }

    private int humansAreRandom(){
        Random rand = new Random();
        int min = 0;
        int max = 2;
        int randomTimeHumanNeeds = rand.nextInt(max - min) + min;

        return randomTimeHumanNeeds;
    }


}
