package SJF;

import interfaces.Process;

public class SJFProcess {
    public Process process;

    public SJFProcess(String name, String color, int arrivalTime, int burstTime) {
        process = new Process(name, color, arrivalTime, burstTime);
    }

}