import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Sjf {

    public String simulateSJF(List<sjfInput> processes) {
        PriorityQueue<sjfInput> readyQueue = new PriorityQueue<>((p1, p2) -> Integer.compare(p1.burstTime, p2.burstTime));

        processes.sort((p1, p2) -> Integer.compare(p1.arrivalTime, p2.arrivalTime));

        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        List<sjfInput> executionOrder = new ArrayList<>();
        int[] waitingTime = new int[processes.size()];
        int[] turnaroundTime = new int[processes.size()];

        int index = 0;

        while (index < processes.size() || !readyQueue.isEmpty()) {
            while (index < processes.size() && processes.get(index).arrivalTime <= currentTime) {
                readyQueue.add(processes.get(index));
                index++;
            }

            if (!readyQueue.isEmpty()) {
                sjfInput currentProcess = readyQueue.poll();
                executionOrder.add(currentProcess);

                int processIndex = processes.indexOf(currentProcess);
                waitingTime[processIndex] = currentTime - currentProcess.arrivalTime;
                turnaroundTime[processIndex] = waitingTime[processIndex] + currentProcess.burstTime;

                totalWaitingTime += waitingTime[processIndex];
                totalTurnaroundTime += turnaroundTime[processIndex];

                currentTime += currentProcess.burstTime;
            } else {
                // No process is ready; increment time to the next process's arrival
                if (index < processes.size()) {
                    currentTime = processes.get(index).arrivalTime;
                }
            }
        }

        StringBuilder output = new StringBuilder("Processes execution order:\n");
        for (sjfInput process : executionOrder) {
            output.append(process.name).append(" ");
        }

        output.append("\n\nWaiting Time for each process:\n");
        for (int i = 0; i < processes.size(); i++) {
            output.append(processes.get(i).name)
                    .append(": ")
                    .append(waitingTime[i])
                    .append("\n");
        }

        output.append("\nTurnaround Time for each process:\n");
        for (int i = 0; i < processes.size(); i++) {
            output.append(processes.get(i).name)
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
