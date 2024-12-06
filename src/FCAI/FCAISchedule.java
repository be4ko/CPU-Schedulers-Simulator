package FCAI;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class FCAISchedule {
    private final Queue<FCAIProcess> queue = new LinkedList<>();
    private FCAIProcess[] processes;
    private FCAIProcess currentProcess = null;
    private Thread currentThread = null;
    int time = 0;

    public FCAISchedule(FCAIProcess[] processes) {
        this.processes = processes;
        getV();
        FCAIManagement();
    }

    public void getV() {
        double v1 = 0.0, v2 = 0.0;
        for (FCAIProcess process : processes) {
            v1 = Math.max(v1, (double) (process.process.arrivalTime / 10.0));
            v2 = Math.max(v2, (double) (process.process.burstTime / 10.0));

        }
        for (FCAIProcess process : processes) {
            process.setV(v1, v2);
        }
    }

    private synchronized void scheduleThread(FCAIProcess process) {

        if (currentThread != null && currentThread.isAlive()) {
            if (process.FCAIFactor < currentProcess.FCAIFactor
                    && currentProcess.temQuantum > currentProcess.smallQuantum) {
                currentThread.interrupt();
                System.out.println("Preempting " + currentProcess.process.name + " for " + process.process.name);
                queue.offer(currentProcess);
                updateQuantum(currentProcess);
            } else {
                queue.offer(process);
                return;
            }
        }

        currentProcess = process;
        currentThread = new Thread(() -> executeProcess(process));
        currentThread.start();
    }

    private synchronized void schedulerThread(FCAIProcess process) {

        currentProcess = process;
        currentThread = new Thread(() -> executeProcess(process));
        currentThread.start();
    }

    private void executeProcess(FCAIProcess process) {
        try {
            int quantum = process.quantum;
            for (process.temQuantum = 0; process.temQuantum < quantum; process.temQuantum++) {
                time++;
                Thread.sleep(940);
                process.updateBurstTime(1);
                System.out.println(
                        "Executing " + process.process.name + ", remaining burst time: " + process.tempBurstTime);

                if (process.tempBurstTime <= 0) {
                    process.completionTime = time;
                    System.out.println("Process " + process.process.name + " completed.");
                    return;
                }
            }
            queue.offer(process);
            updateQuantum(process);
            while (!queue.isEmpty()) {
                FCAIProcess nextProcess = queue.poll();
                schedulerThread(nextProcess);
                try {
                    currentThread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Process " + process.process.name + " was preempted.");
        }
    }

    private void updateQuantum(FCAIProcess process) {
        if (process.tempBurstTime > 0) {
            if (currentProcess.temQuantum == currentProcess.quantum) {
                process.updateQuantum(2);
            } else {
                process.updateQuantum(currentProcess.quantum - currentProcess.temQuantum);
            }
        }
    }

    private void FCAIManagement() {
        Arrays.sort(processes, Comparator.comparingInt(p -> p.process.arrivalTime));

        for (int i = 0; i < processes.length - 1; i++) {
            scheduleThread(processes[i]);
            try {
                Thread.sleep((processes[i + 1].process.arrivalTime - processes[i].process.arrivalTime) * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        scheduleThread(processes[processes.length - 1]);

        while (!queue.isEmpty()) {
            FCAIProcess nextProcess = queue.poll();
            schedulerThread(nextProcess);
            try {
                currentThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        printResults();
    }

    private void printResults() {
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        for (FCAIProcess process : processes) {
            int turnaroundTime = process.getTurnaroundTime();
            int waitingTime = process.getWaitingTime();
            totalWaitingTime += waitingTime;
            totalTurnaroundTime += turnaroundTime;

            System.out.println("Process " + process.process.name + " Turnaround Time: " + turnaroundTime
                    + ", Waiting Time: " + waitingTime + " , " + process.historyQuantum);
        }

        System.out.println("Average Turnaround Time: " + ((float) totalTurnaroundTime / processes.length));
        System.out.println("Average Waiting Time: " + ((float) totalWaitingTime / processes.length));
    }
}
