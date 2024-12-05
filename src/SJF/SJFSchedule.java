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

        int index = 0;

        while (index < processes.size() || !readyQueue.isEmpty()) {
            while (index < processes.size() && processes.get(index).process.arrivalTime <= currentTime) {
                readyQueue.add(processes.get(index));
                index++;
            }

            if (!readyQueue.isEmpty()) {
                SJFProcess currentProcess = readyQueue.poll();
                executionOrder.add(currentProcess);

                int processIndex = processes.indexOf(currentProcess);
                waitingTime[processIndex] = currentTime - currentProcess.process.arrivalTime;
                turnaroundTime[processIndex] = waitingTime[processIndex] + currentProcess.process.burstTime;

                totalWaitingTime += waitingTime[processIndex];
                totalTurnaroundTime += turnaroundTime[processIndex];

                currentTime += currentProcess.process.burstTime;
            } else {
                // No process is ready; increment time to the next process's arrival
                if (index < processes.size()) {
                    currentTime = processes.get(index).process.arrivalTime;
                }
            }
        }

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
