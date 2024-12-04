import java.util.Iterator;

public class sjfInput {
    public String name;
    public String color;
    public int arrivalTime;
    public int burstTime;

    public sjfInput(String name, String color, int arrivalTime, int burstTime) {
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }

    public static Iterator<Process> iterator() {
        return null;
    }
}