import FCAI.FCAIProcess;
import FCAI.FCAISchedule;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FCAI_GUI extends JFrame {
    private List<FCAIProcess> processes;

    public FCAI_GUI() {
        // Initialize processes
        processes = new ArrayList<>();
        processes.add(new FCAIProcess("P1", "red", 0, 17, 4, 4));
        processes.add(new FCAIProcess("P2", "blue", 3, 6, 9, 3));
        processes.add(new FCAIProcess("P3", "green", 4, 10, 3, 5));
        processes.add(new FCAIProcess("P4", "yellow", 29, 4, 8, 2));

        new FCAISchedule(processes.toArray(new FCAIProcess[0]));

        // Set up JFrame
        setTitle("FCAI CPU Scheduling");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        // Add components
        add(createGraphPanel(), BorderLayout.CENTER);
    }

    private JPanel createGraphPanel() {
        JPanel graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int x = 50;
                int barHeight = 30;
                int gap = 50;

                for (FCAIProcess process : processes) {
                    int y = 50 + processes.indexOf(process) * (barHeight + gap);
                    int startX = x;

                    for (int quantumUsage : process.historyQuantum) {
                        switch (process.process.color) {
                            case "red" -> g.setColor(Color.RED);
                            case "blue" -> g.setColor(Color.BLUE);
                            case "green" -> g.setColor(Color.GREEN);
                            case "yellow" -> g.setColor(Color.yellow);
                            default -> g.setColor(Color.GRAY);
                        }

                        int segmentWidth = quantumUsage * 10;
                        g.fillRect(startX, y, segmentWidth, barHeight);

                        g.setColor(Color.BLACK);
                        g.drawRect(startX, y, segmentWidth, barHeight);

                        // Move startX for the next segment
                        startX += segmentWidth;
                    }

                    g.setColor(Color.BLACK);
                    g.drawString(process.process.name, x - 40, y + barHeight / 2);
                }
            }
        };

        graphPanel.setPreferredSize(new Dimension(800, 400));
        graphPanel.setBackground(Color.LIGHT_GRAY);
        return graphPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FCAI_GUI gui = new FCAI_GUI();
            gui.setVisible(true);
        });
    }
}
