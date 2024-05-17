import java.util.UUID;
import java.util.function.Supplier;

public class TimerCallbackImpl implements TimerCallback{
    @Override
    public void onTrigger(Leitsteuerungssystem leitsteuerungssystem, UUID timerId) {

        waitForCondition(() -> !leitsteuerungssystem.getFullPlaceSensor());
        System.out.println("Full place(true): PICK UP!");
        leitsteuerungssystem.setFullPlaceSensor(true);


        waitForCondition(leitsteuerungssystem::getEmptyPlaceSensor);
        System.out.println("Empty place(false): REFILL!");
        leitsteuerungssystem.setEmptyPlaceSensor(false);
//       TODO add this timer to running machines
        leitsteuerungssystem.startTimer(6.0, this);


    }

    private void waitForCondition(Supplier<Boolean> sensor) {
        while (!sensor.get()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }
}
