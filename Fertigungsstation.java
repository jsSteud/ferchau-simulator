import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fertigungsstation {
    private List<Machine> machineList = new ArrayList<>();
    private Sensor emptyPlaceSensor = new Sensor();
    private Sensor fullPlaceSensor = new Sensor();

    public void addMachine(Machine... machines) {
        machineList.addAll(Arrays.asList(machines));
    }


    public void removeMachine(Machine machine){
        machineList.remove(machine);
    }

    public Sensor getEmptyPlaceSensor() {
        return emptyPlaceSensor;
    }

    public Sensor getFullPlaceSensor() {
        return fullPlaceSensor;
    }

    public List<Machine> getMachines() {
        return machineList;
    }
}
