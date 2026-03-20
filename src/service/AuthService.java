package service;
import model.Admin;
import model.User;
import model.Teacher;
import model.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
public class AuthService {
    private Connection conn = DatabaseConnection.getInstance().getConnection();
    public boolean studendRegister(String name,String email,String password,String className, String birthdate){
        String sql = "INSERT INTO users (id,name,email,password,role) VALUES (?,?,?,?,?)";
        String sql1 = "INSERT INTO students (id,user_id,class_name,birthdate) VALUES (?,?,?,?)";
        try{
            String userId = UUID.randomUUID().toString();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);
            stmt.setString(2,name);
            stmt.setString(3,email);
            stmt.setString(4,password);
            stmt.setString(5,"student");
            stmt.executeUpdate();
            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            stmt1.setString(1,UUID.randomUUID().toString());
            stmt1.setString(2,userId);
            stmt1.setString(3,className);
            stmt1.setString(4,birthdate);
            stmt1.executeUpdate();
            System.out.println("Account created successfully!");
            return true;
        }catch (SQLException e){
            System.out.println("Register failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public User login(String email,String password,String role){
        String sql ="SELECT * From users WHERE email = ? and password = ? and role =?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,email);
            stmt.setString(2,password);
            stmt.setString(3,role);
            ResultSet res = stmt.executeQuery();
            if(res.next()){
                String id   = res.getString("id");
                String name = res.getString("name");
                System.out.println("Login successful!");
                switch (role) {
                    case "admin":   return new Admin(id, name, email, password);
                    case "teacher": return new Teacher(id, name, email, password);
                    case "student": return new Student(id, name, email, password);
                }
            }else{
                System.out.println("Wrong email or password!");
            }
        }catch (SQLException e){
            System.out.println("Wrong email or password!");
            e.printStackTrace();
        }
        return null;
    }

}
