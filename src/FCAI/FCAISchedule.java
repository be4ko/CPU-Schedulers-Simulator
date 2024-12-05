package FCAI;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FCAISchedule {
    Queue<FCAIProcess> queue = new LinkedList<>();
    List<FCAIProcess> processes;
    FCAIProcess workingProcess = null;
    int count = 0;
    int executionTime;
    int nonPreemptiveTime;

    public FCAISchedule(List<FCAIProcess> processes) throws InterruptedException {
        this.processes = processes;
        getV();
    }

    public void addToQueue(FCAIProcess process) throws InterruptedException {

        if (queue.isEmpty() && workingProcess == null) {
            workingProcess = process;
            execute();
        } else {
            if (workingProcess != null && process.FCAIFactor < workingProcess.FCAIFactor && count > nonPreemptiveTime) {
                workingProcess.interrupt();
                workingProcess.updateBurstTime(count);

                System.out.println(
                        process.process.name + " preempts" + workingProcess.process.name + ", runs for" + count
                                + " units\n");

                if (workingProcess.process.burstTime > 0) {
                    if (count < executionTime) {
                        workingProcess.quantum += executionTime - count;
                    } else {
                        System.out
                                .println(workingProcess.process.name + " starts execution, runs for " + executionTime +
                                        " units, " + "remaining " + workingProcess.process.burstTime);
                        workingProcess.quantum += 2;
                    }
                    queue.offer(workingProcess);

                } else {
                    System.out.println("Process " + workingProcess.process.name + " completed.");
                }
                workingProcess = process;
                execute();
            } else {
                queue.offer(process);

            }
        }
    }

    public void execute() {
        do {
            System.out.println("Executing process: " + workingProcess.process.name);
            count = 0;
            executionTime = Math.min(workingProcess.quantum, workingProcess.process.burstTime);
            nonPreemptiveTime = (int) Math.ceil(0.4 * executionTime);
            while (count < executionTime) {
                count++;
            }
            workingProcess.updateBurstTime(executionTime);
            System.out.println(workingProcess.process.name + " starts execution, runs for " + executionTime +
                    " units,\r\n" + "remaining " + workingProcess.process.burstTime);

            if (workingProcess.process.burstTime > 0) {
                workingProcess.quantum += 2;
                queue.offer(workingProcess);
            } else {
                System.out.println("Process " + workingProcess.process.name + " completed.");
            }
            workingProcess = queue.poll();
        } while (!queue.isEmpty());

    }

    public void getV() throws InterruptedException {
        int v1 = 0;
        int v2 = 0;
        for (FCAIProcess process : processes) {
            v1 = Math.max(v1, process.process.arrivalTime / 10);
            v2 = Math.max(v2, process.process.burstTime / 10);
        }
        for (FCAIProcess process : processes) {
            process.setV(v1, v2, this);
        }
        for (FCAIProcess process : processes) {
            process.start();
        }
        for (FCAIProcess process : processes) {
            process.join();
        }
    }

}
