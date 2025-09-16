import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * GUI Clock dengan jam-menit-detik, tanggal kiri atas, dan suhu kanan atas.
 */
public class Clock {
    private JFrame frame;
    private JLabel timeLabel;
    private JLabel dateLabel;
    private JLabel tempLabel;
    private ClockDisplay clock;
    private volatile boolean clockRunning = false;
    private TimerThread timerThread;

    public Clock() {
        makeFrame();
        clock = new ClockDisplay();
        updateLabels();
    }

    private void updateLabels() {
        timeLabel.setText(clock.getTime());
        dateLabel.setText(clock.getDate());
        tempLabel.setText("30°C");
    }

    private void start() {
        if (!clockRunning) {
            clockRunning = true;
            timerThread = new TimerThread();
            timerThread.start();
        }
    }

    private void stop() {
        clockRunning = false;
    }

    private void step() {
        clock.timeTick();
        updateLabels();
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(frame,
            "Clock Version 2.0\nMenampilkan jam, tanggal, dan suhu.",
            "About Clock", JOptionPane.INFORMATION_MESSAGE);
    }

    private void quit() {
        System.exit(0);
    }

    private void makeFrame() {
        frame = new JFrame("Clock");
        JPanel contentPane = (JPanel) frame.getContentPane();
        contentPane.setBorder(new EmptyBorder(10, 20, 10, 20));
        contentPane.setLayout(new BorderLayout(12, 12));

        JPanel topPanel = new JPanel(new BorderLayout());
        dateLabel = new JLabel("01-01-2025", SwingConstants.LEFT);
        tempLabel = new JLabel("30°C", SwingConstants.RIGHT);
        topPanel.add(dateLabel, BorderLayout.WEST);
        topPanel.add(tempLabel, BorderLayout.EAST);
        contentPane.add(topPanel, BorderLayout.NORTH);

        timeLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        Font displayFont = timeLabel.getFont().deriveFont(72.0f);
        timeLabel.setFont(displayFont);
        contentPane.add(timeLabel, BorderLayout.CENTER);

        JPanel toolbar = new JPanel();
        toolbar.setLayout(new GridLayout(1, 0, 6, 0));

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> start());
        toolbar.add(startButton);

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> stop());
        toolbar.add(stopButton);

        JButton stepButton = new JButton("Step");
        stepButton.addActionListener(e -> step());
        toolbar.add(stepButton);

        contentPane.add(toolbar, BorderLayout.SOUTH);

        makeMenuBar(frame);

        frame.pack();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void makeMenuBar(JFrame frame) {
        final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);

        JMenu menu = new JMenu("File");
        menubar.add(menu);

        JMenuItem item = new JMenuItem("About Clock...");
        item.addActionListener(e -> showAbout());
        menu.add(item);

        menu.addSeparator();

        item = new JMenuItem("Quit");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
        item.addActionListener(e -> quit());
        menu.add(item);
    }

    class TimerThread extends Thread {
        public void run() {
            while (clockRunning) {
                step();
                pause();
            }
        }

        private void pause() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException exc) {
                // ignore
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Clock());
    }
}
