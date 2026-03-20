package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginGUI {
    private JFrame frame;
    private Color PRIMARY    = new Color(24, 95, 165);
    private Color WHITE      = Color.WHITE;
    private Color BG         = new Color(245, 245, 245);
    private Color BORDER     = new Color(200, 200, 200);
    private Color TEXT_MUTED = new Color(120, 120, 120);
    private Color ADMIN_BG   = new Color(238, 237, 254);
    private Color ADMIN_FG   = new Color(60, 52, 137);
    private Color TEACHER_BG = new Color(225, 245, 238);
    private Color TEACHER_FG = new Color(8, 80, 65);
    private Color STUDENT_BG = new Color(250, 238, 218);
    private Color STUDENT_FG = new Color(99, 56, 6);

    public void show() {
        frame = new JFrame("School Management System");
        frame.setSize(400, 520);
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

        // title
        JLabel title = new JLabel("School Management", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Sign in to your account", SwingConstants.CENTER);
        sub.setFont(new Font("Arial", Font.PLAIN, 13));
        sub.setForeground(TEXT_MUTED);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // role tabs
        JToggleButton adminBtn   = new JToggleButton("Admin");
        JToggleButton teacherBtn = new JToggleButton("Teacher");
        JToggleButton studentBtn = new JToggleButton("Student");
        styleRoleBtn(adminBtn,   ADMIN_BG,   ADMIN_FG,   true);
        styleRoleBtn(teacherBtn, WHITE,      TEXT_MUTED, false);
        styleRoleBtn(studentBtn, WHITE,      TEXT_MUTED, false);
        ButtonGroup group = new ButtonGroup();
        group.add(adminBtn);
        group.add(teacherBtn);
        group.add(studentBtn);
        adminBtn.setSelected(true);

        JPanel rolePanel = new JPanel(new GridLayout(1, 3, 6, 0));
        rolePanel.setBackground(WHITE);
        rolePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        rolePanel.add(adminBtn);
        rolePanel.add(teacherBtn);
        rolePanel.add(studentBtn);

        // fields
        JTextField emailField = new JTextField("Email");
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(0, 12, 0, 12)
        ));

        JPasswordField passField = new JPasswordField();
        passField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passField.setFont(new Font("Arial", Font.PLAIN, 14));
        passField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(0, 12, 0, 12)
        ));

        // login button
        JButton loginBtn = new JButton("Sign in");
        loginBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        loginBtn.setBackground(PRIMARY);
        loginBtn.setForeground(WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setBorderPainted(false);
        loginBtn.setFocusPainted(false);
        loginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // register link — hidden by default
        JLabel registerLink = new JLabel("New student? Register here", SwingConstants.CENTER);
        registerLink.setFont(new Font("Arial", Font.PLAIN, 13));
        registerLink.setForeground(PRIMARY);
        registerLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerLink.setVisible(false); // ← hidden by default

        // tab switching — show/hide register link
        adminBtn.addActionListener(e -> {
            styleRoleBtn(adminBtn,   ADMIN_BG,   ADMIN_FG,   true);
            styleRoleBtn(teacherBtn, WHITE,      TEXT_MUTED, false);
            styleRoleBtn(studentBtn, WHITE,      TEXT_MUTED, false);
            registerLink.setVisible(false); // ← hide
        });
        teacherBtn.addActionListener(e -> {
            styleRoleBtn(adminBtn,   WHITE,      TEXT_MUTED, false);
            styleRoleBtn(teacherBtn, TEACHER_BG, TEACHER_FG, true);
            styleRoleBtn(studentBtn, WHITE,      TEXT_MUTED, false);
            registerLink.setVisible(false); // ← hide
        });
        studentBtn.addActionListener(e -> {
            styleRoleBtn(adminBtn,   WHITE,      TEXT_MUTED, false);
            styleRoleBtn(teacherBtn, WHITE,      TEXT_MUTED, false);
            styleRoleBtn(studentBtn, STUDENT_BG, STUDENT_FG, true);
            registerLink.setVisible(true); // ← show
        });

        // click register link → go to RegisterGUI
        registerLink.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new RegisterGUI().show(); // ← navigate to register screen
            }
        });

        // assemble
        card.add(title);
        card.add(Box.createVerticalStrut(6));
        card.add(sub);
        card.add(Box.createVerticalStrut(20));
        card.add(rolePanel);
        card.add(Box.createVerticalStrut(20));
        card.add(emailField);
        card.add(Box.createVerticalStrut(12));
        card.add(passField);
        card.add(Box.createVerticalStrut(20));
        card.add(loginBtn);
        card.add(Box.createVerticalStrut(8));
        card.add(registerLink); // ← add at bottom

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(BG);
        wrapper.add(card);
        frame.add(wrapper);
        frame.setVisible(true);
    }

    private void styleRoleBtn(JToggleButton btn, Color bg, Color fg, boolean selected) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Arial", selected ? Font.BOLD : Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}