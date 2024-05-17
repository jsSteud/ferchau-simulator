import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class main {

    public static void main(String[] args) {

        Leitsteuerungssystem leitsteuerungssystem = new Leitsteuerungssystem();
        Simulator simulator = new Simulator(leitsteuerungssystem);
        leitsteuerungssystem.setSimulator(simulator);

        simulator.start();


    }

}
