package SJF;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class SJFSchedule {

    public String simulateSJF(List<SJFProcess> processes) {
        PriorityQueue<SJFProcess> readyQueue = new PriorityQueue<>(
                (p1, p2) -> Integer.compare(p1.process.burstTime, p2.process.burstTime));

        processes.sort((p1, p2) -> Integer.compare(p1.process.arrivalTime, p2.process.arrivalTime));

        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        List<SJFProcess> executionOrder = new ArrayList<>();
        int[] waitingTime = new int[processes.size()];
        int[] turnaroundTime = new int[processes.size()];
        int[] agingFactors = new int[processes.size()]; // Track aging for each process

        int index = 0;
        final int AGING_INCREMENT = 1; // Aging adjustment factor

        while (index < processes.size() || !readyQueue.isEmpty()) {
            // Add processes to the ready queue if they have arrived
            while (index < processes.size() && processes.get(index).process.arrivalTime <= currentTime) {
                readyQueue.add(processes.get(index));
                index++;
            }

            // Apply aging: reduce the effective burst time of waiting processes
            List<SJFProcess> tempQueue = new ArrayList<>(readyQueue);
            readyQueue.clear();
            for (SJFProcess process : tempQueue) {
                int processIndex = processes.indexOf(process);
                agingFactors[processIndex] += AGING_INCREMENT; // Increase aging factor
                int adjustedBurstTime = process.process.burstTime - agingFactors[processIndex];
                process.process.burstTime = Math.max(1, adjustedBurstTime); // Avoid negative or zero burst time
                readyQueue.add(process);
            }

            if (!readyQueue.isEmpty()) {
                // Process the job with the shortest (or aged) burst time
                SJFProcess currentProcess = readyQueue.poll();
                executionOrder.add(currentProcess);

                int processIndex = processes.indexOf(currentProcess);
                waitingTime[processIndex] = currentTime - currentProcess.process.arrivalTime;
                turnaroundTime[processIndex] = waitingTime[processIndex] + currentProcess.process.burstTime;

                totalWaitingTime += waitingTime[processIndex];
                totalTurnaroundTime += turnaroundTime[processIndex];

                // Execute the process
                currentTime += currentProcess.process.burstTime;
            } else {
                // No process is ready; increment time to the next process's arrival
                if (index < processes.size()) {
                    currentTime = processes.get(index).process.arrivalTime;
                }
            }
        }

        // Prepare the output
        StringBuilder output = new StringBuilder("Processes execution order:\n");
        for (SJFProcess process : executionOrder) {
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
