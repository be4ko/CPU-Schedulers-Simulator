package FCAI;

import interfaces.Process;

public class FCAIProcess extends Thread {

    public Process process;
    public int priority;
    public int quantum;
    public int FCAIFactor;
    public int v1;
    public int v2;
    public FCAISchedule schedule;

    public FCAIProcess(String name, String color, int arrivalTime, int burstTime, int priority, int quantum) {
        process = new Process(name, color, arrivalTime, burstTime);
        this.priority = priority;
        this.quantum = quantum;
    }

    public void setV(int v1, int v2, FCAISchedule schedule) {
        this.v1 = v1;
        this.v2 = v2;
        this.schedule = schedule;
        calculate();
    }

    @Override
    public void run() {
        try {
            int i = process.arrivalTime;
            while (i != 0) {
                i--;
            }
            schedule.addToQueue(this);
        } catch (InterruptedException e) {
            System.out.println(process.name + "error " + e);
        }
    }

    public void updateBurstTime(int executedTime) {
        process.burstTime -= executedTime;
        calculate();
    }

    public void calculate() {
        FCAIFactor = (10 - priority) + (process.arrivalTime / v1) + (process.burstTime / v2);
    }
}
