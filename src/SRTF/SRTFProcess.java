package SRTF;

import interfaces.Process;

public class SRTFProcess {
    public Process process;
    public int remainingTime;

    public SRTFProcess(String name, String color, int arrivalTime, int burstTime) {
        process = new Process(name, color, arrivalTime, burstTime);
        this.remainingTime = burstTime; // Initialize with the burst time
    }
}
