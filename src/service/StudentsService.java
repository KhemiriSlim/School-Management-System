package service;

import model.Student;
import model.Teacher;
import model.User;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentsService {
    private Connection conn = DatabaseConnection.getInstance().getConnection();
    public boolean deleteStudent(String id){
        String sql = "Delete from students where user_id =?";
        String sql1 = "Delete from users where id =?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,id);
            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            stmt1.setString(1,id);
            stmt.executeUpdate();
            stmt1.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public List<Student> getAllStudents() {
        String sql = "SELECT u.id, u.name,u.email, s.class_name, s.birthdate " +
                "FROM users u, students s " +
                "WHERE u.id = s.user_id";
        List<Student> list = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Student student = new Student(rs.getString("id"), rs.getString("name"), rs.getString("email"), "", rs.getString("class_name"), rs.getString("birthdate"));;

                list.add(student);
            }
        } catch (SQLException e) {
            System.out.println("Failed: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

}
