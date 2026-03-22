package service;

import model.Grade;
import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GradeService {
    private Connection conn = DatabaseConnection.getInstance().getConnection();
    public boolean addGrade(String studentId,String subject,String grade){
        String dateTime = java.time.LocalDateTime.now().toString();
        String sql = "INSERT INTO grades (id,student_id,subject,grade,date_time) VALUES (?,?,?,?,?)";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2,studentId);
            stmt.setString(3,subject);
            stmt.setString(4,grade);
            stmt.setString(5,dateTime);
            stmt.executeUpdate();
            return true;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteGrade(String id){
        String sql = "DELETE FROM grades WHERE id =?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,id);
            stmt.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public List<Grade> getAllGrades() {
        String sql = "SELECT g.id, u.name, g.subject, g.grade, g.date_time " +
                "FROM grades g, students s, users u " +
                "WHERE g.student_id = s.id " +
                "AND s.user_id = u.id";
        List<Grade> list = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Grade grade = new Grade(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("subject"),
                        rs.getString("grade"),
                        rs.getString("date_time")
                );
                list.add(grade);
            }
        } catch (SQLException e) {
            System.out.println("Failed: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}
