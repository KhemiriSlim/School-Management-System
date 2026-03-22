package ui;

import model.Grade;
import model.User;
import model.Student;
import service.GradeService;
import service.StudentsService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageGradesGUI {
    private JFrame frame;
    private User user;

    private Color PRIMARY    = new Color(24, 95, 165);
    private Color WHITE      = Color.WHITE;
    private Color BG         = new Color(245, 245, 245);
    private Color BORDER     = new Color(200, 200, 200);
    private Color TEXT_MUTED = new Color(120, 120, 120);
    private GradeService gradeService     = new GradeService();
    private StudentsService studentsService = new StudentsService();

    public ManageGradesGUI(User user) {
        this.user = user;
    }

    public void show() {
        frame = new JFrame("Manage Grades");
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

        JLabel title = new JLabel("Manage Grades");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(30, 30, 30));

        JButton addBtn = new JButton("+ Add Grade");
        addBtn.setBackground(PRIMARY);
        addBtn.setForeground(WHITE);
        addBtn.setFont(new Font("Arial", Font.BOLD, 13));
        addBtn.setBorderPainted(false);
        addBtn.setFocusPainted(false);
        addBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addBtn.setPreferredSize(new Dimension(120, 36));

        topBar.add(title,  BorderLayout.WEST);
        topBar.add(addBtn, BorderLayout.EAST);

        // ── table ────────────────────────────────────────
        String[] columns = {"ID", "Student", "Subject", "Grade", "Date", "Action"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        // ← load real grades from database
        List<Grade> grades = gradeService.getAllGrades();
        for (Grade g : grades) {
            model.addRow(new Object[]{
                    g.getId(),
                    g.getStudentName(),
                    g.getSubject(),
                    g.getGrade(),
                    g.getDateTime(),
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

        // hide ID column
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        // ← delete on click Action column
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                if (col == 5) {
                    String gradeId = model.getValueAt(row, 0).toString();
                    String student = model.getValueAt(row, 1).toString();
                    int confirm = JOptionPane.showConfirmDialog(frame,
                            "Delete grade for " + student + "?",
                            "Confirm", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        new Thread(() -> {
                            boolean success = gradeService.deleteGrade(gradeId);
                            SwingUtilities.invokeLater(() -> {
                                if (success) {
                                    model.removeRow(row);
                                    JOptionPane.showMessageDialog(frame,
                                            "Grade deleted!", "Success",
                                            JOptionPane.INFORMATION_MESSAGE);
                                }
                            });
                        }).start();
                    }
                }
            }
        });

        // ← add grade dialog
        addBtn.addActionListener(e -> {
            // load students for dropdown
            List<Student> students = studentsService.getAllStudents();
            if (students.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "No students found!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // build dropdown with student names
            String[] studentNames = new String[students.size()];
            for (int i = 0; i < students.size(); i++) {
                studentNames[i] = students.get(i).getName();
            }
            JComboBox<String> studentBox = new JComboBox<>(studentNames);
            JTextField subjectField      = new JTextField();
            JTextField gradeField        = new JTextField();

            JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
            form.add(new JLabel("Student:"));  form.add(studentBox);
            form.add(new JLabel("Subject:"));  form.add(subjectField);
            form.add(new JLabel("Grade:"));    form.add(gradeField);

            int result = JOptionPane.showConfirmDialog(frame, form,
                    "Add Grade", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                int selectedIndex    = studentBox.getSelectedIndex();
                String studentId     = students.get(selectedIndex).getId();
                String subject       = subjectField.getText().trim();
                String gradeValue    = gradeField.getText().trim();

                if (subject.isEmpty() || gradeValue.isEmpty()) {
                    JOptionPane.showMessageDialog(frame,
                            "Please fill all fields!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                new Thread(() -> {
                    boolean success = gradeService.addGrade(studentId, subject, gradeValue);
                    SwingUtilities.invokeLater(() -> {
                        if (success) {
                            JOptionPane.showMessageDialog(frame,
                                    "Grade added!", "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                            frame.dispose();
                            new ManageGradesGUI(user).show(); // ← refresh
                        } else {
                            JOptionPane.showMessageDialog(frame,
                                    "Failed to add grade!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    });
                }).start();
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