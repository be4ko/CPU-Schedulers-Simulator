package interfaces;

public class Process {

    public String name;
    public String color;
    public int arrivalTime;
    public int burstTime;

    public Process(String name, String color, int arrivalTime, int burstTime) {
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }

}