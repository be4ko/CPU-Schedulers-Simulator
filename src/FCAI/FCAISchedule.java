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
    boolean flag = false;

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
        queue.offer(process);
        if (currentThread == null) {
            currentProcess = queue.poll();
            currentThread = new Thread(() -> executeProcess(currentProcess));
            currentThread.start();
        }
    }

    private FCAIProcess findHigherPriorityProcess(FCAIProcess currentProcess) {
        FCAIProcess highestPriorityProcess = null;

        for (FCAIProcess process : queue) {
            if (process.FCAIFactor < currentProcess.FCAIFactor) {
                if (highestPriorityProcess == null || process.FCAIFactor < highestPriorityProcess.FCAIFactor) {
                    highestPriorityProcess = process;
                }
            }
        }

        if (highestPriorityProcess != null) {
            queue.remove(highestPriorityProcess);
        }
        return highestPriorityProcess;
    }

    private void executeProcess(FCAIProcess process) {
        while (process != null && process.tempBurstTime > 0) {
            try {
                for (process.temQuantum = 0; process.temQuantum < process.quantum; process.temQuantum++) {
                    if (process.temQuantum > process.smallQuantum) {
                        FCAIProcess higherPriorityProcess = findHigherPriorityProcess(process);
                        if (higherPriorityProcess != null) {
                            System.out.println("Preempting " + process.process.name + " for "
                                    + higherPriorityProcess.process.name);
                            queue.offer(process);
                            updateQuantum(process);
                            process = higherPriorityProcess;
                            flag = true;
                            break;
                        }
                    }

                    time++;
                    Thread.sleep(1000);
                    process.updateBurstTime(1);

                    System.out.println("Executing " + process.process.name + ", remaining burst time: "
                            + process.tempBurstTime);

                    if (process.tempBurstTime <= 0) {
                        process.completionTime = time;
                        System.out.println("Process " + process.process.name + " completed.");
                        process = queue.poll();
                        break;
                    }
                }

                if (!flag && process != null && process.tempBurstTime > 0) {
                    queue.offer(process);
                    updateQuantum(process);
                    process = queue.poll();
                }
            } catch (InterruptedException e) {
                System.out.println("Process " + process.process.name + " was interrupted.");
                Thread.currentThread().interrupt();
            }
            flag = false;
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
        try {
            if (currentProcess != null) {
                currentThread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
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
