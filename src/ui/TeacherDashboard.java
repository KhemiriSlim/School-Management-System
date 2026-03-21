package ui;

import model.Teacher;

import javax.swing.*;
import java.awt.*;

public class TeacherDashboard {
    private JFrame frame;
    private Teacher teacher;

    private Color PRIMARY    = new Color(24, 95, 165);
    private Color WHITE      = Color.WHITE;
    private Color BG         = new Color(245, 245, 245);
    private Color BORDER     = new Color(200, 200, 200);
    private Color TEXT_MUTED = new Color(120, 120, 120);

    public TeacherDashboard(Teacher teacher) {
        this.teacher = teacher;
    }

    public void show() {
        frame = new JFrame("Teacher Dashboard");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BG);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(28, 28, 28, 28)
        ));
        card.setPreferredSize(new Dimension(340, 300));

        JLabel title = new JLabel("Teacher Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Welcome, " + teacher.getName(), SwingConstants.CENTER);
        sub.setFont(new Font("Arial", Font.PLAIN, 13));
        sub.setForeground(TEXT_MUTED);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel comingSoon = new JLabel("Coming soon...", SwingConstants.CENTER);
        comingSoon.setFont(new Font("Arial", Font.PLAIN, 13));
        comingSoon.setForeground(TEXT_MUTED);
        comingSoon.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(title);
        card.add(Box.createVerticalStrut(6));
        card.add(sub);
        card.add(Box.createVerticalStrut(20));
        card.add(comingSoon);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(BG);
        wrapper.add(card);
        frame.add(wrapper);
        frame.setVisible(true);
    }
}