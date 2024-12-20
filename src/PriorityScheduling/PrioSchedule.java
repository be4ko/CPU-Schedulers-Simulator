package PriorityScheduling;

import java.util.*;

public class PrioSchedule {
    private PrioProcess[] processes;
    public PriorityQueue<PrioProcess> priorityQueue;
    private int contextSwitch;
    private List<PrioProcess> executionOrder; //تبع ابوبكر "ممنوع الإقتراب أو التعديل"


    public PrioSchedule(PrioProcess[] processes) {
        this.processes = processes;
        this.contextSwitch = 0;
        this.executionOrder = new ArrayList<>();
    }

    public PrioSchedule(PrioProcess[] processes, int contextSwitch) {
        this.processes = processes;
        this.contextSwitch = contextSwitch;
        this.executionOrder = new ArrayList<>();
    }

    public void simulatePrio() {
        Comparator<PrioProcess> priorityComparator = Comparator.comparingInt(PrioProcess::getPriority);
        priorityQueue = new PriorityQueue<>(priorityComparator);

        int time = 0; // a simulation for current time in the program
        double averageWaitingTime = 0;
        double averageTurnaroundTime = 0;
        int remainingProcesses = processes.length;

        while (remainingProcesses > 0) {
            for (int j = 0; j < processes.length; j++) {
                if (processes[j].getArrivalTime() <= time && processes[j].getFinished() == false) {
                    priorityQueue.add(processes[j]);
                    processes[j].setFinished(true);
                }
            }

            if (!priorityQueue.isEmpty()) {

                System.out.println("Ready processes: ");
                Iterator<PrioProcess> through = priorityQueue.iterator();



                while (through.hasNext()) {
                    PrioProcess readyProcess = through.next();
                    System.out.println("Process Name: " + readyProcess.getName() +
                            ", Priority: " + readyProcess.getPriority());
                }

                PrioProcess currentProcess = priorityQueue.poll();
                executionOrder.add(currentProcess);
                currentProcess.setWaitingTime(time);

                int burstTime = currentProcess.getBurstTime();

                System.out.println("Process " + currentProcess.getName() +
                        " starts ");

                while (burstTime > 0) {
                    System.out.println("Process " + currentProcess.getName() +
                            " working, remaining time: " + burstTime);
                    try {
                        Thread.sleep(1000); // 1 second
                    } catch (InterruptedException e) {
                        System.err.println("Simulation interrupted");
                        Thread.currentThread().interrupt();
                        return;
                    }
                    burstTime--;
                    time++;
                }

                time += contextSwitch;

                currentProcess.setTurnaroundTime(time);
                System.out.println("Process " + currentProcess.getName() +
                        " completed at time: " + time);

                System.out.println("With waiting time: " + currentProcess.getWaitingTime() +
                        " and turnaround time: " + currentProcess.getTurnaroundTime());

                averageWaitingTime += currentProcess.getWaitingTime();
                averageTurnaroundTime += currentProcess.getTurnaroundTime();

                remainingProcesses--;
            } else
                time++;
        }

        averageWaitingTime = Math.ceil(averageWaitingTime / processes.length);
        averageTurnaroundTime = Math.ceil(averageTurnaroundTime / processes.length);

        System.out.println("Average Waiting Time for all processes: " + averageWaitingTime +
                " and Average Turnaround Time for all processes: " + averageTurnaroundTime);
    }

    public List<PrioProcess> getExecutionOrder() { //تبع ابوبكر "ممنوع الإقتراب أو التعديل"
        return executionOrder;
    }
}
