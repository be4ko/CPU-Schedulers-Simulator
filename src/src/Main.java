import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<sjfInput> processes = new ArrayList<>();
        processes.add(new sjfInput("P1", "Red", 0, 5));
        processes.add(new sjfInput("P2", "Blue", 2, 3));
        processes.add(new sjfInput("P3", "Green", 4, 1));

        Sjf scheduler = new Sjf();
        String result = scheduler.simulateSJF(processes);
        System.out.println(result);
    }
}
