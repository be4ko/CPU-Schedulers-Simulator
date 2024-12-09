package SRTF;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class SRTFSchedule {

    public String simulateSRTF(List<SRTFProcess> processes, int contextSwitching) {
        PriorityQueue<SRTFProcess> readyQueue = new PriorityQueue<>(
                (p1, p2) -> Integer.compare(p1.remainingTime, p2.remainingTime));

        processes.sort((p1, p2) -> Integer.compare(p1.process.arrivalTime, p2.process.arrivalTime));

        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        List<SRTFProcess> executionOrder = new ArrayList<>();
        int[] waitingTime = new int[processes.size()];
        int[] turnaroundTime = new int[processes.size()];

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
                continue;
            }

            if (currentProcess == null || currentProcess.remainingTime == 0) {
                if (currentProcess != null && currentProcess.remainingTime == 0) {
                    // Process has finished execution
                    int processIndex = processes.indexOf(currentProcess);
                    turnaroundTime[processIndex] = currentTime - currentProcess.process.arrivalTime;
                    waitingTime[processIndex] = turnaroundTime[processIndex] - currentProcess.process.burstTime;

                    totalWaitingTime += waitingTime[processIndex];
                    totalTurnaroundTime += turnaroundTime[processIndex];

                    executionOrder.add(currentProcess);
                    currentProcess = null; // Process completed
                }

                if (!readyQueue.isEmpty()) {
                    currentProcess = readyQueue.poll();
                    contextSwitchTime = contextSwitching;
                }
            }

            if (currentProcess != null) {
                currentProcess.remainingTime--;
                currentTime++;
            } else if (index < processes.size()) {
                currentTime = processes.get(index).process.arrivalTime;
            }
        }

        // Prepare the output
        StringBuilder output = new StringBuilder("Processes execution order:\n");
        for (SRTFProcess process : executionOrder) {
            output.append(process.process.name).append(" ");
        }

        output.append("\n\nWaiting Time for each process:\n");
        for (int i = 0; i < processes.size(); i++) {
            output.append(processes.get(i).process.name)
                    .append(": ")
                    .append(waitingTime[i])
                    .append("\n");
        }

        output.append("\nTurnaround Time for each process:\n");
        for (int i = 0; i < processes.size(); i++) {
            output.append(processes.get(i).process.name)
                    .append(": ")
                    .append(turnaroundTime[i])
                    .append("\n");
        }

        double avgWaitingTime = (double) totalWaitingTime / processes.size();
        double avgTurnaroundTime = (double) totalTurnaroundTime / processes.size();

        output.append("\nAverage Waiting Time: ").append(String.format("%.2f", avgWaitingTime));
        output.append("\nAverage Turnaround Time: ").append(String.format("%.2f", avgTurnaroundTime));

        return output.toString();
    }
}
