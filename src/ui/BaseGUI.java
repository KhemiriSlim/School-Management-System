package ui;

import javax.swing.*;
import java.awt.*;

public abstract class BaseGUI {
    protected JFrame frame;
    protected Color PRIMARY    = new Color(24, 95, 165);
    protected Color BG         = new Color(248, 248, 246);
    protected Color WHITE      = Color.WHITE;
    protected Color BORDER     = new Color(200, 200, 195);
    protected Color TEXT_MUTED = new Color(100, 100, 95);

    public BaseGUI(String title) {
        frame = new JFrame(title);
        frame.setSize(460, 580);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BG);
        frame.setLayout(new GridBagLayout());
    }

    public abstract void show();

    protected JButton createPrimaryButton(String text) {
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

    protected JTextField createField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(0, 12, 0, 12)
        ));
        return field;
    }

    protected JLabel createTitle(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    protected JButton createBackButton() {
        JButton btn = new JButton("Back");
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btn.setBackground(WHITE);
        btn.setForeground(TEXT_MUTED);
        btn.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    protected JLabel createSubtitle(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setForeground(TEXT_MUTED);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}