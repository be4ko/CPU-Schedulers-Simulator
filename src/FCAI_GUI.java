import FCAI.FCAIProcess;
import FCAI.FCAISchedule;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FCAI_GUI extends JFrame {
    private FCAISchedule schedule;
    private List<FCAIProcess> processes;

    public FCAI_GUI() {
        // Initialize processes
        processes = new ArrayList<>();
        processes.add(new FCAIProcess("P1", "red", 0, 17, 4, 4));
        processes.add(new FCAIProcess("P2", "blue", 3, 6, 9, 3));
        processes.add(new FCAIProcess("P3", "green", 4, 10, 3, 5));
        processes.add(new FCAIProcess("P4", "yellow", 29, 4, 8, 2));

        schedule = new FCAISchedule(processes.toArray(new FCAIProcess[0]));

        // Set up JFrame
        setTitle("FCAI CPU Scheduling");
        setSize(700, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createGraphPanel(schedule), BorderLayout.CENTER);
    }

    private JPanel createGraphPanel(FCAISchedule schedule) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int x = 50;
                int barHeight = 30;
                int y = 50;

                // Iterate through the output queue to draw the scheduling
                for (Map.Entry<FCAIProcess, Integer> entry : schedule.output) {
                    FCAIProcess process = entry.getKey();
                    int segmentWidth = entry.getValue() * 10;

                    // Set color based on process
                    switch (process.process.color) {
                        case "red" -> g.setColor(Color.RED);
                        case "blue" -> g.setColor(Color.BLUE);
                        case "green" -> g.setColor(Color.GREEN);
                        case "yellow" -> g.setColor(Color.YELLOW);
                        default -> g.setColor(Color.GRAY);
                    }

                    // Draw the segment
                    g.fillRect(x, y, segmentWidth, barHeight);

                    // Add border to the segment
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, segmentWidth, barHeight);

                    // Draw process label
                    g.drawString(process.process.name, x + 5, y + barHeight / 2);

                    // Update X position for the next process segment
                    x += segmentWidth + 5;
                }
            }
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FCAI_GUI gui = new FCAI_GUI();
            gui.setVisible(true);
        });
    }
}
