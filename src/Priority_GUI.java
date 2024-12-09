import PriorityScheduling.PrioSchedule;
import PriorityScheduling.PrioProcess;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Priority_GUI extends JFrame {
    private final PrioSchedule schedule;

    public Priority_GUI() {
        // Initialize the schedule object with processes and context switch duration
        PrioProcess[] processes = {
                new PrioProcess("P1", "Red", 0, 5, 2),
                new PrioProcess("P2", "Blue", 1, 3, 1),
                new PrioProcess("P3", "Green", 2, 2, 3),
                new PrioProcess("P4", "Yellow", 3, 6, 2)
        };

        schedule = new PrioSchedule(processes, 3);

        // Simulate the priority scheduling
        schedule.simulatePrio();

        // Set up JFrame
        setTitle("Priority (Non-Preemptive) Scheduling");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create and add the graph panel
        add(createGraphPanel(), BorderLayout.CENTER);
    }

    private JPanel createGraphPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int x = 50; // Starting x-coordinate
                int y = 50; // y-coordinate for the timeline
                int barHeight = 30; // Height of each process bar
                int contextSwitchWidth = 25; // Width to represent context switch

                // Retrieve the execution order from the schedule
                List<PrioProcess> executionOrder = schedule.getExecutionOrder();

                for (int i = 0; i < executionOrder.size(); i++) {
                    PrioProcess process = executionOrder.get(i);
                    int burstWidth = process.getBurstTime() * 20;

                    // Set color based on process
                    Color color = switch (process.getColor()) {
                        case "Red" -> Color.RED;
                        case "Blue" -> Color.BLUE;
                        case "Green" -> Color.GREEN;
                        case "Yellow" -> Color.YELLOW;
                        default -> Color.MAGENTA;
                    };
                    g.setColor(color);

                    // Draw the burst time bar
                    g.fillRect(x, y, burstWidth, barHeight);

                    // Add border and label
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, burstWidth, barHeight);
                    g.drawString(process.getName(), x + 5, y + barHeight / 2 + 5); // Adjusted Y for visibility

                    // Update x-coordinate for the next process
                    x += burstWidth;

                    // Draw context switch if not the last process
                    if (i < executionOrder.size() - 1) {
                        g.setColor(Color.lightGray);
                        g.fillRect(x, y, contextSwitchWidth, barHeight);

                        g.setColor(Color.BLACK);
                        g.drawRect(x, y, contextSwitchWidth, barHeight);

                        x += contextSwitchWidth; // Move x further for the next process
                    }
                }
            }
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Priority_GUI gui = new Priority_GUI();
            gui.setVisible(true);
        });
    }
}
