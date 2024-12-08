import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import SJF.SJFProcess;
import java.util.Comparator;

public class SFJ_GUI extends JFrame {

    private List<SJFProcess> processes = new ArrayList<>();
    private List<SJFProcess> executionOrder = new ArrayList<>();
    private JTextArea resultsArea;

    public SFJ_GUI() {
        // Initialize processes
        processes.add(new SJFProcess("P1", "Red", 0, 7));
        processes.add(new SJFProcess("P2", "Blue", 2, 4));
        processes.add(new SJFProcess("P3", "Green", 4, 1));
        processes.add(new SJFProcess("P4", "black", 5, 4));

        calculateExecutionOrder();

        String result = generateResults();

        // Set up JFrame
        setTitle("SJF CPU Scheduling");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Add components
        add(createGraphPanel(), BorderLayout.CENTER);
        add(createStatsPanel(result), BorderLayout.SOUTH);
    }

    private void calculateExecutionOrder() {
        List<SJFProcess> readyQueue = new ArrayList<>(processes);
        int currentTime = 0;

        while (!readyQueue.isEmpty()) {
            // Select the process with the shortest burst time that has arrived
            int finalCurrentTime = currentTime;
            SJFProcess nextProcess = readyQueue.stream()
                    .filter(p -> p.process.arrivalTime <= finalCurrentTime)
                    .min(Comparator.comparingInt(p -> p.process.burstTime))
                    .orElse(null);

            if (nextProcess == null) {
                // If no process has arrived, increment time
                currentTime++;
                continue;
            }
            executionOrder.add(nextProcess);
            currentTime += nextProcess.process.burstTime;
            readyQueue.remove(nextProcess);
        }
    }

    private String generateResults() {
        int currentTime = 0;
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("Waiting Time for each process:\n");

        // Calculate waiting and turnaround times
        for (SJFProcess process : executionOrder) {
            int waitingTime = Math.max(0, currentTime - process.process.arrivalTime);
            int turnaroundTime = waitingTime + process.process.burstTime;

            totalWaitingTime += waitingTime;
            totalTurnaroundTime += turnaroundTime;

            currentTime += process.process.burstTime;

            resultBuilder.append(process.process.name).append(": ").append(waitingTime).append("\n");
        }

        currentTime = 0;
        for (SJFProcess process : executionOrder) {
            currentTime += process.process.burstTime;
        }

        double avgWaitingTime = totalWaitingTime / processes.size();
        double avgTurnaroundTime = totalTurnaroundTime / processes.size();

        resultBuilder.append("\nAverage Waiting Time: ").append(String.format("%.2f", avgWaitingTime)).append("\n");
        resultBuilder.append("Average Turnaround Time: ").append(String.format("%.2f", avgTurnaroundTime)).append("\n");

        return resultBuilder.toString();
    }

    private JPanel createGraphPanel() {
        JPanel graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int x = 50;
                int y = 50;
                int barHeight = 30;
                int gap = 5;

                for (SJFProcess process : executionOrder) {
                    switch (process.process.color) {
                        case "Red" -> g.setColor(Color.RED);
                        case "Blue" -> g.setColor(Color.BLUE);
                        case "Green" -> g.setColor(Color.GREEN);
                        default -> g.setColor(Color.GRAY);
                    }

                    int barWidth = process.process.burstTime * 50;
                    g.fillRect(x, y, barWidth, barHeight);

                    g.setColor(Color.BLACK);
                    g.drawString(process.process.name, x + 5, y + 20);

                    // Move to the next column
                    x += barWidth + gap;
                }
            }
        };

        graphPanel.setPreferredSize(new Dimension(800, 400));
        graphPanel.setBackground(Color.lightGray);
        return graphPanel;
    }

    private JPanel createStatsPanel(String result) {
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BorderLayout());

        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        resultsArea.setText(result);

        JScrollPane scrollPane = new JScrollPane(resultsArea);
        statsPanel.add(scrollPane, BorderLayout.CENTER);
        statsPanel.setPreferredSize(new Dimension(800, 70));
        return statsPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SFJ_GUI gui = new SFJ_GUI();
            gui.setVisible(true);
        });
    }
}