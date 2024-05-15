import java.util.UUID;

public interface TimerCallback {
    void onTrigger(LeitsteuerungssystemImpl leitsteuerungssystem, UUID timerId);
}
