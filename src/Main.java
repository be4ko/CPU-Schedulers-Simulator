import java.util.ArrayList;
import java.util.List;

import SJF.SJFProcess;
import SJF.SJFSchedule;

public class Main {
    public static void main(String[] args) {
        List<SJFProcess> processes = new ArrayList<>();
        processes.add(new SJFProcess("P1", "Red", 0, 5));
        processes.add(new SJFProcess("P2", "Blue", 2, 3));
        processes.add(new SJFProcess("P3", "Green", 4, 1));

        SJFSchedule scheduler = new SJFSchedule();
        String result = scheduler.simulateSJF(processes);
        System.out.println(result);
    }
}
