package model;

public class Grade {
    private String id;
    private String studentName;
    private String subject;
    private String grade;
    private String dateTime;

    public Grade(String id, String studentName, String subject, String grade, String dateTime) {
        this.id          = id;
        this.studentName = studentName;
        this.subject     = subject;
        this.grade       = grade;
        this.dateTime    = dateTime;
    }

    public String getId()          { return id; }
    public String getStudentName() { return studentName; }
    public String getSubject()     { return subject; }
    public String getGrade()       { return grade; }
    public String getDateTime()    { return dateTime; }
}