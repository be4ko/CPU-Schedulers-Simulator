import FCAI.FCAIProcess;
import FCAI.FCAISchedule;
import java.util.List;
import java.util.ArrayList;
import SJF.SJFProcess;
import SJF.SJFSchedule;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        FCAIProcess[] processes = {
                new FCAIProcess("P1", "Red", 0, 17, 4, 4),
                new FCAIProcess("P2", "Blue", 3, 6, 9, 3),
                new FCAIProcess("P3", "Green", 4, 10, 3, 5),
                new FCAIProcess("P4", "Yellow", 29, 4, 8, 2)
        };
        new FCAISchedule(processes);

        List<SJFProcess> processes1 = new ArrayList<>();
        processes1.add(new SJFProcess("P1", "Red", 0, 5));
        processes1.add(new SJFProcess("P2", "Blue", 2, 3));
        processes1.add(new SJFProcess("P3", "Green", 4, 1));

        SJFSchedule scheduler = new SJFSchedule();
        String result = scheduler.simulateSJF(processes1);
        System.out.println(result);
    }
}
