import java.util.*;

public class Leitsteuerungssystem {

    private boolean emptyPlaceSensor;
    private boolean fullPlaceSensor;
    Simulator simulator;

    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
    }

    public void setEmptyPlaceSensor(boolean value){
        this.emptyPlaceSensor = value;
        simulator.onEmptyPlaceSensorChanged();
    }

    public void setFullPlaceSensor(boolean value){
        this.fullPlaceSensor = value;
        simulator.onFullPlaceSensorChanged();
    }

    public boolean getEmptyPlaceSensor(){
        return this.emptyPlaceSensor;
    }

    public boolean getFullPlaceSensor(){
        return this.fullPlaceSensor;
    }

    public UUID startTimer(double seconds, TimerCallback timerCallback) {
        Timer timer = new Timer();
        UUID timerID = UUID.randomUUID();
        long milliseconds = (long) (seconds * 1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timerCallback.onTrigger(Leitsteuerungssystem.this, timerID);
            }
        }, milliseconds);
        return timerID;
    }

    public void killTimer(UUID timerID){

    }

}
