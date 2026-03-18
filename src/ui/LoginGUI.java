package ui;

import javax.swing.*;
import java.awt.*;

public class LoginGUI extends BaseGUI {

    public LoginGUI() {
        super("School Management System");
    }

    @Override
    public void show() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(32, 32, 32, 32)
        ));
        card.setPreferredSize(new Dimension(360, 480));

        // header
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(PRIMARY);
        header.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        JLabel headerTitle = new JLabel("School Management", SwingConstants.CENTER);
        headerTitle.setFont(new Font("Arial", Font.BOLD, 18));
        headerTitle.setForeground(WHITE);
        headerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel headerSub = new JLabel("Sign in to your account", SwingConstants.CENTER);
        headerSub.setFont(new Font("Arial", Font.PLAIN, 12));
        headerSub.setForeground(new Color(180, 210, 240));
        headerSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.add(headerTitle);
        header.add(Box.createVerticalStrut(4));
        header.add(headerSub);

        // role buttons
        JPanel rolePanel = new JPanel(new GridLayout(1, 3, 6, 0));
        rolePanel.setBackground(WHITE);
        rolePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        JToggleButton adminBtn   = createRoleButton("Admin");
        JToggleButton teacherBtn = createRoleButton("Teacher");
        JToggleButton studentBtn = createRoleButton("Student");
        ButtonGroup group = new ButtonGroup();
        group.add(adminBtn);
        group.add(teacherBtn);
        group.add(studentBtn);
        adminBtn.setSelected(true);
        adminBtn.setBackground(new Color(230, 241, 251));
        adminBtn.setForeground(PRIMARY);
        rolePanel.add(adminBtn);
        rolePanel.add(teacherBtn);
        rolePanel.add(studentBtn);

        // fields
        JTextField emailField = createField("Email");
        JPasswordField passField = new JPasswordField();
        passField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passField.setFont(new Font("Arial", Font.PLAIN, 14));
        passField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(0, 12, 0, 12)
        ));

        // login button
        JButton loginBtn = createPrimaryButton("Sign in");

        // assemble
        card.add(header);
        card.add(Box.createVerticalStrut(20));
        card.add(rolePanel);
        card.add(Box.createVerticalStrut(20));
        card.add(emailField);
        card.add(Box.createVerticalStrut(12));
        card.add(passField);
        card.add(Box.createVerticalStrut(20));
        card.add(loginBtn);

        frame.add(card);
        frame.setVisible(true);
    }

    private JToggleButton createRoleButton(String text) {
        JToggleButton btn = new JToggleButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBackground(WHITE);
        btn.setForeground(TEXT_MUTED);
        btn.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        return btn;
    }
}