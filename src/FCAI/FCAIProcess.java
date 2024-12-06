package FCAI;

import interfaces.Process;
import java.util.ArrayList;
import java.util.List;

public class FCAIProcess {
    public Process process;
    public int priority;
    public int quantum;
    public int FCAIFactor;
    public double v1;
    public double v2;
    public int tempBurstTime;
    public int startTime = -1;
    public int completionTime;
    public int FixedFCAIFactor;
    public int temQuantum = 0;
    public int smallQuantum;
    List<Integer> historyQuantum = new ArrayList<>();

    public FCAIProcess(String name, String color, int arrivalTime, int burstTime, int priority, int quantum) {
        process = new Process(name, color, arrivalTime, burstTime);
        this.priority = priority;
        this.quantum = quantum;
        historyQuantum.add(quantum);
        smallQuantum = (int) (.4 * quantum);
        this.tempBurstTime = burstTime;
    }

    public void setV(double v1, double v2) {
        this.v1 = v1;
        this.v2 = v2;
        FixedFCAIFactor = (int) ((10 - priority) + (process.arrivalTime / v1));
        calculate();
    }

    public void updateQuantum(int x) {
        quantum += x;
        historyQuantum.add(quantum);
        smallQuantum = (int) (.4 * quantum);
    }

    public void updateBurstTime(int executedTime) {
        tempBurstTime -= executedTime;
        calculate();
    }

    public void calculate() {
        FCAIFactor = (int) (FixedFCAIFactor + (tempBurstTime / v2));

    }

    public int getTurnaroundTime() {
        return completionTime - process.arrivalTime;
    }

    public int getWaitingTime() {
        return getTurnaroundTime() - process.burstTime;
    }
}
