package model;

public class Teacher extends User{
    public Teacher(String id,String name,String email,String password){
        super(id,name,email,password,"Teacher");
    }
}
