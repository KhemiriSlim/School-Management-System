package ui;

import model.User;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard {
    private JFrame frame;
    private User user;

    private Color PRIMARY    = new Color(24, 95, 165);
    private Color WHITE      = Color.WHITE;
    private Color BG         = new Color(245, 245, 245);
    private Color BORDER     = new Color(200, 200, 200);
    private Color TEXT_MUTED = new Color(120, 120, 120);
    private Color DANGER     = new Color(192, 57, 43);

    public AdminDashboard(User user) {
        this.user = user;
    }

    public void show() {
        frame = new JFrame("Admin Dashboard");
        frame.setSize(400, 500);
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
        card.setPreferredSize(new Dimension(340, 420));

        // welcome
        JLabel title = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Welcome, " + user.getName(), SwingConstants.CENTER);
        sub.setFont(new Font("Arial", Font.PLAIN, 13));
        sub.setForeground(TEXT_MUTED);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // buttons
        JButton teachersBtn = createPrimaryButton("Manage Teachers");
        JButton studentsBtn = createPrimaryButton("Manage Students");
        JButton gradesBtn   = createPrimaryButton("Manage Grades");
        JButton logoutBtn   = createDangerButton("Logout");
        teachersBtn.addActionListener(e->{
            frame.dispose();
            new ManageTeachersGUI(user).show();
        });
        // assemble
        card.add(title);
        card.add(Box.createVerticalStrut(6));
        card.add(sub);
        card.add(Box.createVerticalStrut(30));
        card.add(teachersBtn);
        card.add(Box.createVerticalStrut(10));
        card.add(studentsBtn);
        card.add(Box.createVerticalStrut(10));
        card.add(gradesBtn);
        card.add(Box.createVerticalStrut(20));
        card.add(logoutBtn);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(BG);
        wrapper.add(card);
        frame.add(wrapper);
        frame.setVisible(true);
    }

    private JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btn.setBackground(PRIMARY);
        btn.setForeground(WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton createDangerButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btn.setBackground(DANGER);
        btn.setForeground(WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}