package PriorityScheduling;

import interfaces.Process;

public class PrioProcess {
    private Process process;
    private int turnaroundTime;
    private int waitingTime;
    private int priority;
    private boolean finished;

    public PrioProcess(String name, String color, int arrivalTime, int burstTime, int priority) {
        process = new Process(name, color, arrivalTime, burstTime);
        this.priority = priority;
        this.finished = false;
    }

    public void setTurnaroundTime(int completionTime) {
        turnaroundTime = completionTime - process.getArrivalTime();
    }

    public void setWaitingTime(int startTime) {
        this.waitingTime = startTime - process.getArrivalTime();
    }

    public void setArrivalTime(int arrivalTime) {
        process.setArrivalTime(arrivalTime);
    }

    public void setBurstTime(int burstTime) {
        process.setBurstTime(burstTime);
    }

    public void setName(String name) {
        process.setName(name);
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean getFinished() {
        return finished;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getArrivalTime() {
        return process.getArrivalTime();
    }

    public int getBurstTime() {
        return process.getBurstTime();
    }

    public String getName() {
        return process.getName();
    }
    public String getColor() { return process.color ; }
}
