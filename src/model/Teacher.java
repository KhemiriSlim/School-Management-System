package model;

import ui.TeacherDashboard;

public class Teacher extends User {
    private String subject;
    private String phone;

    public Teacher(String id, String name, String email, String password, String subject, String phone) {
        super(id, name, email, password, "teacher");
        this.subject = subject;
        this.phone   = phone;
    }

    public String getSubject() { return subject; }
    public String getPhone()   { return phone; }


    public void showDashboard() {
        new TeacherDashboard(this).show();
    }
}