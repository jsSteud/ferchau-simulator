import java.util.*;

public class Simulator {

    LeitsteuerungssystemImpl leitsteuerungssystem;
    private TimerCallback timerCallback;

    public Simulator() {

    }

    public void start(){
        setup();

        //      time a machine needs to fill one item (3x) -> just an example
        double machineFillingTime = 3.0;
        System.out.println("Simulator started with optimal conditions and "
                + machineFillingTime
                + " seconds per machine to fill one item");
        /**
         * Start with optimal condition:
         * - time difference = exact 1/3 * machineFillingTime
         * - fullPlace = free
         * - emptyPlace = occupied
         */
        leitsteuerungssystem.setEmptyPlaceSensor(true);
        leitsteuerungssystem.setFullPlaceSensor(false);

        leitsteuerungssystem.startTimer(machineFillingTime, timerCallback);
        leitsteuerungssystem.startTimer((2.0 / 3.0) * machineFillingTime, timerCallback);
        leitsteuerungssystem.startTimer((1.0 / 3.0) * machineFillingTime, timerCallback);

    }

    public void setup(){
        //        Fertigungsstation setup
        Fertigungsstation fertigungsstation = new Fertigungsstation();
        Machine machine_1 = new Machine();
        Machine machine_2 = new Machine();
        Machine machine_3 = new Machine();
        fertigungsstation.addMachine(machine_1, machine_2, machine_3);

        //        Leitsteuerungssystem setup
        LeitsteuerungssystemImpl leitsteuerungssystem = new LeitsteuerungssystemImpl(fertigungsstation);
        leitsteuerungssystem.addObserver(this);

        //         Timer Callback setup
        TimerCallback timerCallback = new TimerCallbackImpl();

        this.leitsteuerungssystem = leitsteuerungssystem;
        this.timerCallback = timerCallback;
    }

    public void onEmptyPlaceSensorChanged(boolean value){
        System.out.println("Empty place sensor changed to: "+ value);
        if(!value){
            int randomTimeHumanNeeds = humansAreRandom();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.print("Refiled item: ");
                    Simulator.this.leitsteuerungssystem.setEmptyPlaceSensor(true);
                }
            }, randomTimeHumanNeeds * 1000);
        }

    }

    public void onFullPlaceSensorChanged(boolean value){
        System.out.println("Full place sensor changed to: "+ value);
        if(value){
            int randomTimeHumanNeeds = humansAreRandom();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.print("Picked up item: ");
                    Simulator.this.leitsteuerungssystem.setFullPlaceSensor(false);
                    List<UUID> waitingQuery = leitsteuerungssystem.getWaitingQuery();
                    for (UUID timerId: waitingQuery) {
                        timerCallback.onTrigger(leitsteuerungssystem, timerId);
                        break;
                    }

                }
            }, randomTimeHumanNeeds * 1000);
        }

    }

    /**
     * TODO explain this function
     */
    public int humansAreRandom() {
        Random rand = new Random();
        int min = 1;
        int max = 2;
        int randomTimeHumanNeeds = rand.nextInt(max - min) + min;

        return randomTimeHumanNeeds;
    }

}
