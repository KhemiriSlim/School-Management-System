package ui;

import model.Student;
import model.User;
import service.StudentsService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageStudentsGUI {
    private JFrame frame;
    private User user;

    private Color PRIMARY    = new Color(24, 95, 165);
    private Color WHITE      = Color.WHITE;
    private Color BG         = new Color(245, 245, 245);
    private Color BORDER     = new Color(200, 200, 200);
    private Color TEXT_MUTED = new Color(120, 120, 120);
    private StudentsService studentsService = new StudentsService();

    public ManageStudentsGUI(User user) {
        this.user = user;
    }

    public void show() {
        frame = new JFrame("Manage Students");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BG);

        JPanel main = new JPanel(new BorderLayout(16, 16));
        main.setBackground(BG);
        main.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        // ── top bar ──────────────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(BG);

        JLabel title = new JLabel("Manage Students");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(30, 30, 30));
        topBar.add(title, BorderLayout.WEST);

        // ── table ────────────────────────────────────────
        String[] columns = {"ID", "Name", "Email", "Class", "Birthdate", "Action"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        // ← load students from database
        List<Student> students = studentsService.getAllStudents();
        for (Student s : students) {
            model.addRow(new Object[]{
                    s.getId(),
                    s.getName(),
                    s.getEmail(),
                    s.getclasse_name(),
                    s.getbirthdate(),
                    "Delete"
            });
        } // ← closing for loop

        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(36);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(240, 240, 240));
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER));
        table.setSelectionBackground(new Color(230, 241, 251));

        // hide ID column
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        // delete on click Action column
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                if (col == 5) {
                    String userId = model.getValueAt(row, 0).toString();
                    String name   = model.getValueAt(row, 1).toString();
                    int confirm = JOptionPane.showConfirmDialog(frame,
                            "Delete student " + name + "?",
                            "Confirm", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        new Thread(() -> {
                            boolean success = studentsService.deleteStudent(userId);
                            SwingUtilities.invokeLater(() -> {
                                if (success) {
                                    model.removeRow(row);
                                    JOptionPane.showMessageDialog(frame,
                                            "Student deleted!", "Success",
                                            JOptionPane.INFORMATION_MESSAGE);
                                }
                            });
                        }).start();
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        scrollPane.getViewport().setBackground(WHITE);

        // ── back button ──────────────────────────────────
        JButton backBtn = new JButton("Back to Dashboard");
        backBtn.setBackground(WHITE);
        backBtn.setForeground(TEXT_MUTED);
        backBtn.setFont(new Font("Arial", Font.PLAIN, 13));
        backBtn.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        backBtn.setFocusPainted(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.setPreferredSize(new Dimension(160, 36));
        backBtn.addActionListener(e -> {
            frame.dispose();
            new AdminDashboard(user).show();
        });

        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomBar.setBackground(BG);
        bottomBar.add(backBtn);

        // ── assemble ─────────────────────────────────────
        main.add(topBar,     BorderLayout.NORTH);
        main.add(scrollPane, BorderLayout.CENTER);
        main.add(bottomBar,  BorderLayout.SOUTH);

        frame.add(main);
        frame.setVisible(true);
    } // ← closing show()
} // ← closing class