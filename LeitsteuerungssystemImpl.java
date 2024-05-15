import java.util.*;


public class LeitsteuerungssystemImpl {

    private final List<Simulator> observers = new ArrayList<>();
    private final Fertigungsstation fertigungsstation;
    private HashMap<UUID, Timer> timerHashMap = new HashMap<>();
    private List<UUID> waitingQuery = new ArrayList<>();


    public void addObserver(Simulator simulator) {
    observers.add(simulator);
    }

    public LeitsteuerungssystemImpl(Fertigungsstation fertigungsstation) {
        this.fertigungsstation = fertigungsstation;
    }

    public List<UUID> getWaitingQuery() {
        return waitingQuery;
    }

    public void addWaitingQuery(UUID uuid) {
        waitingQuery.add(uuid);
    }

    public void setEmptyPlaceSensor(boolean value){
        fertigungsstation.getEmptyPlaceSensor().setOccupied(value);
        for (Simulator simulator: observers){
            simulator.onEmptyPlaceSensorChanged(value);
        }
    }

    public void setFullPlaceSensor(boolean value){
        fertigungsstation.getFullPlaceSensor().setOccupied(value);
        for (Simulator simulator: observers){
            simulator.onFullPlaceSensorChanged(value);
        }
    }

    public boolean getEmptyPlaceSensor(){
        return fertigungsstation.getEmptyPlaceSensor().isOccupied;
    }

    public boolean getFullPlaceSensor(){

        return fertigungsstation.getFullPlaceSensor().isOccupied;
    }

    public HashMap<UUID, Timer> getTimerHashMap() {
        return timerHashMap;
    }

    public UUID startTimer(double seconds, TimerCallback timerCallback) {
        System.out.println(getTimerHashMap().size());
        System.out.println("Started new machine");
        Timer timer = new Timer();
        UUID timerID = UUID.randomUUID();
        timerHashMap.put(timerID, timer);
        long milliseconds = (long) (seconds * 1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timerCallback.onTrigger(LeitsteuerungssystemImpl.this, timerID);
            }
        }, milliseconds);
        return timerID;
    }

    public void killTimer(UUID timerID){
    timerHashMap.get(timerID).cancel();
    timerHashMap.remove(timerID);
    }

}
