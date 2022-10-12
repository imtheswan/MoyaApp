package User;

import java.util.List;

public class User {



    private String email;
    private String firstName;
    private String lastName;
    private String pass;

    private int age;
    private int gender;

    public Exception error;
    //constructores
    public User(String name){
        this.email = "correo Prueba";
        this.firstName = name;
        this.lastName = "nombre Prueba";
        this.pass = "qwerty";
        this.age = 10;
        this.gender = 3;
    }

    public User (){

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
