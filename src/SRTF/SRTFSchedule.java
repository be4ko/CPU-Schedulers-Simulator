package SRTF;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class SRTFSchedule {
    private List<SRTFProcess> executionOrder = new ArrayList<>();

    public void simulateSRTF(List<SRTFProcess> processes, int contextSwitching) {
        PriorityQueue<SRTFProcess> readyQueue = new PriorityQueue<>(
                (p1, p2) -> Integer.compare(p1.remainingTime, p2.remainingTime));

        processes.sort((p1, p2) -> Integer.compare(p1.process.arrivalTime, p2.process.arrivalTime));

        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int[] waitingTime = new int[processes.size()];
        int[] turnaroundTime = new int[processes.size()];
        int[] finishingTime = new int[processes.size()]; // Store the finishing time for each process

        int index = 0;
        SRTFProcess currentProcess = null;
        int contextSwitchTime = 0;

        while (index < processes.size() || !readyQueue.isEmpty() || currentProcess != null) {
            // Add processes to the ready queue if they have arrived
            while (index < processes.size() && processes.get(index).process.arrivalTime <= currentTime) {
                readyQueue.add(processes.get(index));
                index++;
            }

            // Select a process if none is running
            if (currentProcess == null && !readyQueue.isEmpty()) {
                currentProcess = readyQueue.poll();
                contextSwitchTime = contextSwitching; // Apply context switching
            }

            // Check for preemption
            if (currentProcess != null && !readyQueue.isEmpty()) {
                SRTFProcess nextProcess = readyQueue.peek();
                if (nextProcess.remainingTime < currentProcess.remainingTime) {
                    readyQueue.add(currentProcess); // Preempt the current process
                    currentProcess = readyQueue.poll(); // Switch to the new process
                    contextSwitchTime = contextSwitching;
                }
            }

            // Execute the current process
            if (contextSwitchTime == 0 && currentProcess != null) {
                currentProcess.remainingTime--;
                executionOrder.add(currentProcess); // Add to execution order for visualization

                // Finish the process if completed
                if (currentProcess.remainingTime == 0) {
                    int processIndex = processes.indexOf(currentProcess);

                    finishingTime[processIndex] = currentTime + 1;
                    turnaroundTime[processIndex] = finishingTime[processIndex] - currentProcess.process.arrivalTime;
                    waitingTime[processIndex] = turnaroundTime[processIndex] - currentProcess.process.burstTime;

                    totalWaitingTime += waitingTime[processIndex];
                    totalTurnaroundTime += turnaroundTime[processIndex];

                    currentProcess = null; // Mark as completed
                }
            }

            // Increment time
            if (contextSwitchTime > 0) {
                contextSwitchTime--;
            } else if (currentProcess == null) {
                currentTime++;
            } else {
                currentTime++;
            }
        }

        System.out.println("Waiting Time for each process:");
        for (int i = 0; i < processes.size(); i++) {
            System.out.println(processes.get(i).process.name + ": " + waitingTime[i]);
        }

        System.out.println("Turnaround Time for each process:");
        for (int i = 0; i < processes.size(); i++) {
            System.out.println(processes.get(i).process.name + ": " + turnaroundTime[i]);
        }

        double avgWaitingTime = (double) totalWaitingTime / processes.size();
        double avgTurnaroundTime = (double) totalTurnaroundTime / processes.size();

        System.out.println("Average Waiting Time: " + String.format("%.2f", avgWaitingTime));
        System.out.println("Average Turnaround Time: " + String.format("%.2f", avgTurnaroundTime));
    }

    // Getter methods for execution order and timing data
    public List<SRTFProcess> getExecutionOrder() {
        return executionOrder;
    }

}