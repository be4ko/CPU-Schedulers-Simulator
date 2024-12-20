import FCAI.FCAIProcess;
import FCAI.FCAISchedule;
import PriorityScheduling.PrioProcess;
import PriorityScheduling.PrioSchedule;
import SJF.SJFProcess;
import SJF.SJFSchedule;
import SRTF.SRTFProcess;
import SRTF.SRTFSchedule;

import java.util.List;
import java.util.ArrayList;

public class Main {
        public static void main(String[] args) throws InterruptedException {
                // FCAIProcess[] processes = {
                // new FCAIProcess("P1", "Red", 0, 17, 4, 4),
                // new FCAIProcess("P2", "Blue", 3, 6, 9, 3),
                // new FCAIProcess("P3", "Green", 4, 10, 3, 5),
                // new FCAIProcess("P4", "Yellow", 29, 4, 8, 2)
                // };
                // new FCAISchedule(processes);

                // List<SJFProcess> processes1 = new ArrayList<>();
                // processes1.add(new SJFProcess("P1", "Red", 0, 10));
                // processes1.add(new SJFProcess("P2", "Blue", 2, 4));
                // processes1.add(new SJFProcess("P3", "Green", 3, 1));

                // SJFSchedule scheduler = new SJFSchedule();
                // String result = scheduler.simulateSJF(processes1);
                // System.out.println(result);

                // PrioProcess[] processes = {
                // new PrioProcess("P1", "Red", 0, 5, 2),
                // new PrioProcess("P2", "Blue", 1, 3, 1),
                // new PrioProcess("P3", "Green", 2, 8, 3),
                // new PrioProcess("P4", "Yellow", 3, 6, 2)
                // };

                // PrioSchedule scheduler = new PrioSchedule(processes, 1);

                // System.out.println("Starting Priority Scheduling Simulation...");
                // scheduler.simulatePrio();
                // System.out.println("Simulation Complete!");

                // List<SJFProcess> processes1 = new ArrayList<>();
                // processes1.add(new SJFProcess("P1", "Red", 0, 5));
                // processes1.add(new SJFProcess("P2", "Blue", 2, 3));
                // processes1.add(new SJFProcess("P3", "Green", 4, 1));

                // SJFSchedule scheduler = new SJFSchedule();
                // String result = scheduler.simulateSJF(processes1);
                // System.out.println(result);

                List<SRTFProcess> processList = new ArrayList<>();
                processList.add(new SRTFProcess("P1", "Red", 0, 7));
                processList.add(new SRTFProcess("P2", "Blue", 2, 4));
                processList.add(new SRTFProcess("P3", "Green", 4, 1));
                processList.add(new SRTFProcess("P4", "Yellow", 5, 4));
                
                int contextSwitchingTime = 2; // Adjust as needed
                int agingFactor = 1; // Adjust the aging factor to mitigate starvation
                SRTFSchedule scheduler = new SRTFSchedule();

                scheduler.simulateSRTFWithAging(processList, contextSwitchingTime, agingFactor);
        }

}
