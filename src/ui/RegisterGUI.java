package ui;

import service.AuthService;

import javax.swing.*;
import java.awt.*;

public class RegisterGUI {
    private JFrame frame;
    private Color PRIMARY    = new Color(24, 95, 165);
    private Color WHITE      = Color.WHITE;
    private Color BG         = new Color(245, 245, 245);
    private Color BORDER     = new Color(200, 200, 200);
    private Color TEXT_MUTED = new Color(120, 120, 120);
    private AuthService authService = new AuthService();
    public void show() {
        frame = new JFrame("Student Register");
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
        JLabel title = new JLabel("Create Account", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Register as a new student", SwingConstants.CENTER);
        sub.setFont(new Font("Arial", Font.PLAIN, 13));
        sub.setForeground(TEXT_MUTED);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // fields
        JTextField nameField  = createField("Full name");
        JTextField emailField = createField("Email");

        JPasswordField passField = new JPasswordField();
        passField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passField.setFont(new Font("Arial", Font.PLAIN, 14));
        passField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(0, 12, 0, 12)
        ));

        JTextField birthdateField = createField("Birthdate (dd/mm/yyyy)");
        JTextField classField     = createField("Class (ex: 3A)");

        // register button
        JButton registerBtn = new JButton("Create Account");
        registerBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        registerBtn.setBackground(PRIMARY);
        registerBtn.setForeground(WHITE);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 14));
        registerBtn.setBorderPainted(false);
        registerBtn.setFocusPainted(false);
        registerBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        registerBtn.addActionListener(e -> {
            String name      = nameField.getText().trim();
            String email     = emailField.getText().trim();
            String password  = new String(passField.getPassword()).trim();
            String birthdate = birthdateField.getText().trim();
            String className = classField.getText().trim();

            if (name.isEmpty() || name.equals("Full name") ||
                    email.isEmpty() || email.equals("Email") ||
                    password.isEmpty() ||
                    birthdate.isEmpty() || birthdate.equals("Birthdate (dd/mm/yyyy)") ||
                    className.isEmpty() || className.equals("Class (ex: 3A)")) {
                JOptionPane.showMessageDialog(frame,
                        "Please fill all fields!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            new Thread(() -> {
                boolean success = authService.studendRegister(name, email, password, className, birthdate);


                SwingUtilities.invokeLater(() -> {
                    if (success) {
                        JOptionPane.showMessageDialog(frame,
                                "Account created successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                        new LoginGUI().show();
                    } else {
                        JOptionPane.showMessageDialog(frame,
                                "Registration failed! Email may already exist.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });
            }).start();
        });

        // back to login link
        JLabel backLink = new JLabel("Already have an account? Sign in", SwingConstants.CENTER);
        backLink.setFont(new Font("Arial", Font.PLAIN, 13));
        backLink.setForeground(PRIMARY);
        backLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        backLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                frame.dispose();
                new LoginGUI().show(); // ← back to login
            }
        });

        // assemble
        card.add(title);
        card.add(Box.createVerticalStrut(6));
        card.add(sub);
        card.add(Box.createVerticalStrut(20));
        card.add(nameField);
        card.add(Box.createVerticalStrut(10));
        card.add(emailField);
        card.add(Box.createVerticalStrut(10));
        card.add(passField);
        card.add(Box.createVerticalStrut(10));
        card.add(birthdateField);
        card.add(Box.createVerticalStrut(10));
        card.add(classField);
        card.add(Box.createVerticalStrut(20));
        card.add(registerBtn);
        card.add(Box.createVerticalStrut(8));
        card.add(backLink);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(BG);
        wrapper.add(card);
        frame.add(wrapper);
        frame.setVisible(true);
    }

    private JTextField createField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setForeground(TEXT_MUTED); // ← gray placeholder color
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(0, 12, 0, 12)
        ));

        // clear on focus, restore on blur
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(TEXT_MUTED);
                }
            }
        });
        return field;
    }

}