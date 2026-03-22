package model;

public class Student extends User{
    private String class_name;
    private String birthdate;
    public Student(String id,String name,String email,String password,String class_name,String birthdate){
        super(id,name,email,password,"Student");
        this.birthdate = birthdate;
        this.class_name = class_name;
    }
    public String getclasse_name() { return class_name; }
    public String getbirthdate()   { return birthdate; }

}
