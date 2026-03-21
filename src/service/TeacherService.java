package service;

import model.Teacher;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeacherService {
    private Connection conn = DatabaseConnection.getInstance().getConnection();
    public boolean addTeacher(String name, String email, String password, String subject, String phone){
        String sql = "INSERT INTO users (id,name,email,password,role) VALUES(?,?,?,?,?)";
        String sql1 = "INSERT INTO teachers (id,user_id,subject,phone) VALUES(?,?,?,?)";
        try{
            String UserId = UUID.randomUUID().toString();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,UserId);
            stmt.setString(2,name);
            stmt.setString(3,email);
            stmt.setString(4,password);
            stmt.setString(5,"teacher");
            stmt.executeUpdate();
            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            stmt1.setString(1,UUID.randomUUID().toString());
            stmt1.setString(2,UserId);
            stmt1.setString(3,subject);
            stmt1.setString(4,phone);
            stmt1.executeUpdate();
            System.out.println("Account Teacher created successfully!");
            return true;
        }catch (SQLException e){
            System.out.println("Register Teacher failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteTeacher(String userId){
        String sql = "Delete FROM teachers Where user_Id = ?";
        String sql1 = "DELETE FROM users WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,userId);
            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            stmt1.setString(1,userId);
            stmt.executeUpdate();
            stmt1.executeUpdate();
            System.out.println("Account Teacher Deleted successfully!");
            return true;
        }catch (SQLException e){
            System.out.println("Delete failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public List<Teacher> getAllTeachers() {
        String sql = "SELECT u.id, u.name, u.email, t.subject, t.phone " +
                "FROM users u, teachers t " +
                "WHERE u.id = t.user_id";
        List<Teacher> list = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Teacher teacher = new Teacher(rs.getString("id"), rs.getString("name"), rs.getString("email"), "", rs.getString("subject"), rs.getString("phone"));;

                list.add(teacher);
            }
        } catch (SQLException e) {
            System.out.println("Failed: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}
