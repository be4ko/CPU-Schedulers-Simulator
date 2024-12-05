import java.util.Arrays;
import FCAI.FCAIProcess;
import FCAI.FCAISchedule;
// import java.util.List;
// import java.util.ArrayList;
// import SJF.SJFProcess;
// import SJF.SJFSchedule;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        FCAIProcess p1 = new FCAIProcess("P1", "Red", 0, 17, 4, 4);
        FCAIProcess p2 = new FCAIProcess("P2", "Blue", 3, 6, 9, 3);
        FCAIProcess p3 = new FCAIProcess("P3", "Green", 4, 10, 3, 5);
        FCAIProcess p4 = new FCAIProcess("P4", "Green", 29, 4, 8, 2);

        new FCAISchedule(Arrays.asList(p1, p2, p3, p4));

        // List<SJFProcess> processes = new ArrayList<>();
        // processes.add(new SJFProcess("P1", "Red", 0, 5));
        // processes.add(new SJFProcess("P2", "Blue", 2, 3));
        // processes.add(new SJFProcess("P3", "Green", 4, 1));

        // SJFSchedule scheduler = new SJFSchedule();
        // String result = scheduler.simulateSJF(processes);
        // System.out.println(result);
    }
}
