import java.util.UUID;

public interface TimerCallback {
    void onTrigger(Leitsteuerungssystem leitsteuerungssystem, UUID timerId);
}
