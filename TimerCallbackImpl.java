import java.util.UUID;

public class TimerCallbackImpl implements TimerCallback{
    @Override
    public void onTrigger(LeitsteuerungssystemImpl leitsteuerungssystem, UUID timerId) {
        boolean emptyPlaceSensorIsOccupied = leitsteuerungssystem.getEmptyPlaceSensor();
        boolean fullPlaceSensorIsOccupied = leitsteuerungssystem.getFullPlaceSensor();
        if (!fullPlaceSensorIsOccupied){
            System.out.print("Item dropped in full place: ");
            leitsteuerungssystem.setFullPlaceSensor(true);
            leitsteuerungssystem.killTimer(timerId);
//            TODO optimize while loop
            while (!emptyPlaceSensorIsOccupied);
            if (emptyPlaceSensorIsOccupied) {
                leitsteuerungssystem.startTimer(6.0, this);
                System.out.print("Took item from empty place: ");
                leitsteuerungssystem.setEmptyPlaceSensor(false);
            }
        } else {
            leitsteuerungssystem.addWaitingQuery(timerId);
        }

    }
}
