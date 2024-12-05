package FCAI;

import interfaces.Process;

public class FCAIProcess {

    public Process process;
    public int priority;
    public int quantum;
    public double FCAIFactor;
    public double v1;
    public double v2;

    FCAIProcess(String name, String color, int arrivalTime, int burstTime, int priority, int quantum) {
        process = new Process(name, color, arrivalTime, burstTime);
        this.priority = priority;
        this.quantum = quantum;
    }

    public void setV(double v1, double v2) {
        this.v1 = v1;
        this.v2 = v2;

    }

    public void calculate() {
        FCAIFactor = (10 - priority) + (process.arrivalTime / v1) + (process.burstTime / v2);
    }

}