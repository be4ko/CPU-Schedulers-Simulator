package SRTF;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class SRTFSchedule {

    public void simulateSRTF(List<SRTFProcess> processes, int contextSwitching) {
        PriorityQueue<SRTFProcess> readyQueue = new PriorityQueue<>(
                (p1, p2) -> Integer.compare(p1.remainingTime, p2.remainingTime));

        processes.sort((p1, p2) -> Integer.compare(p1.process.arrivalTime, p2.process.arrivalTime));

        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        List<SRTFProcess> executionOrder = new ArrayList<>();
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

            // Check for context switching
            if (contextSwitchTime > 0) {
                contextSwitchTime--;
                currentTime++;
                continue; // Skip execution this cycle
            }

            // Check for preemption
            if (currentProcess != null && !readyQueue.isEmpty()) {
                SRTFProcess nextProcess = readyQueue.peek(); // Check the process with the shortest remaining time
                if (nextProcess.remainingTime < currentProcess.remainingTime) {
                    readyQueue.add(currentProcess); // Preempt the current process
                    currentProcess = readyQueue.poll(); // Switch to the new process
                    contextSwitchTime = contextSwitching; // Apply context switching delay
                }
            }

            if (currentProcess == null || currentProcess.remainingTime == 0) {
                if (currentProcess != null && currentProcess.remainingTime == 0) {
                    int processIndex = processes.indexOf(currentProcess);

                    // Record the finishing time
                    finishingTime[processIndex] = currentTime;

                    // Calculate turnaround time
                    turnaroundTime[processIndex] = finishingTime[processIndex] - currentProcess.process.arrivalTime;

                    // Calculate waiting time
                    waitingTime[processIndex] = turnaroundTime[processIndex] - currentProcess.process.burstTime;

                    // Update totals
                    totalWaitingTime += waitingTime[processIndex];
                    totalTurnaroundTime += turnaroundTime[processIndex];

                    executionOrder.add(currentProcess); // Add to execution order
                    currentProcess = null; // Process completed
                }

                if (!readyQueue.isEmpty()) {
                    currentProcess = readyQueue.poll(); // Pick the next process
                    contextSwitchTime = contextSwitching;
                }
            }

            if (currentProcess != null) {
                currentProcess.remainingTime--; // Decrement the remaining time
                currentTime++;
            } else if (index < processes.size()) {
                currentTime = processes.get(index).process.arrivalTime; // Advance to the next process's arrival
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
}
