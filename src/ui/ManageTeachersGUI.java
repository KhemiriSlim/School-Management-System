package ui;

import model.User;
import service.TeacherService;
import model.Teacher;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
public class ManageTeachersGUI {
    private JFrame frame;
    private User user;

    private Color PRIMARY    = new Color(24, 95, 165);
    private Color WHITE      = Color.WHITE;
    private Color BG         = new Color(245, 245, 245);
    private Color BORDER     = new Color(200, 200, 200);
    private Color TEXT_MUTED = new Color(120, 120, 120);
    private Color DANGER     = new Color(192, 57, 43);
    private TeacherService teacherService = new TeacherService();

    public ManageTeachersGUI(User user) {
        this.user = user;
    }

    public void show() {
        frame = new JFrame("Manage Teachers");
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

        JLabel title = new JLabel("Manage Teachers");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(30, 30, 30));

        JButton addBtn = new JButton("+ Add Teacher");
        addBtn.setBackground(PRIMARY);
        addBtn.setForeground(WHITE);
        addBtn.setFont(new Font("Arial", Font.BOLD, 13));
        addBtn.setBorderPainted(false);
        addBtn.setFocusPainted(false);
        addBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addBtn.setPreferredSize(new Dimension(130, 36));

        addBtn.addActionListener(e -> {
            JTextField nameField     = new JTextField();
            JTextField emailField    = new JTextField();
            JPasswordField passField = new JPasswordField();
            JTextField subjectField  = new JTextField();
            JTextField phoneField    = new JTextField();

            JPanel form = new JPanel(new GridLayout(5, 2, 8, 8));
            form.add(new JLabel("Name:"));     form.add(nameField);
            form.add(new JLabel("Email:"));    form.add(emailField);
            form.add(new JLabel("Password:")); form.add(passField);
            form.add(new JLabel("Subject:"));  form.add(subjectField);
            form.add(new JLabel("Phone:"));    form.add(phoneField);

            int result = JOptionPane.showConfirmDialog(frame, form,
                    "Add Teacher", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                String name     = nameField.getText().trim();
                String email    = emailField.getText().trim();
                String password = new String(passField.getPassword()).trim();
                String subject  = subjectField.getText().trim();
                String phone    = phoneField.getText().trim();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()
                        || subject.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(frame,
                            "Please fill all fields!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                new Thread(() -> {
                    boolean success = teacherService.addTeacher(name, email, password, subject, phone);
                    SwingUtilities.invokeLater(() -> {
                        if (success) {
                            JOptionPane.showMessageDialog(frame,
                                    "Teacher added successfully!", "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                            // ← ADDED: refresh table after adding
                            frame.dispose();
                            new ManageTeachersGUI(user).show();
                        } else {
                            JOptionPane.showMessageDialog(frame,
                                    "Failed! Email may already exist.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    });
                }).start();
            }
        });

        topBar.add(title,  BorderLayout.WEST);
        topBar.add(addBtn, BorderLayout.EAST);

        // ── table ────────────────────────────────────────
        // ← ADDED: ID as first hidden column
        String[] columns = {"ID", "Name", "Email", "Subject", "Phone", "Action"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        List<Teacher> teachers = teacherService.getAllTeachers();
        for (Teacher t : teachers) {
            model.addRow(new Object[]{
                    t.getId(),
                    t.getName(),
                    t.getEmail(),
                    t.getSubject(),
                    t.getPhone(),
                    "Delete"
            });
        }



        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(36);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(240, 240, 240));
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER));
        table.setSelectionBackground(new Color(230, 241, 251));

        // ← ADDED: hide ID column
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        // ← ADDED: delete on click Action column
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                if (col == 5) { // ← Action column index
                    String userId = model.getValueAt(row, 0).toString();
                    String name   = model.getValueAt(row, 1).toString();
                    int confirm = JOptionPane.showConfirmDialog(frame,
                            "Delete teacher " + name + "?",
                            "Confirm", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        new Thread(() -> {
                            boolean success = teacherService.deleteTeacher(userId);
                            SwingUtilities.invokeLater(() -> {
                                if (success) {
                                    model.removeRow(row);
                                    JOptionPane.showMessageDialog(frame,
                                            "Teacher deleted!", "Success",
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
    }
}