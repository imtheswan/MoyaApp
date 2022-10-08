package User;

import java.util.List;

public class User {

    private List<User> allUsers;

    private String email;
    private String firstName;
    private String lastName;
    private String pass;

    private int age;
    private int gender;

    public Exception error;

    public Boolean addUser(User user){
        try {
            allUsers.add(user);
            return Boolean.TRUE;
        } catch (Exception e){
            error = e;
            return Boolean.FALSE;
        }
    }

    public User getUser(int index){
        return allUsers.get(index);
    }

    public User getUser(String correo){
        for (User user: allUsers) {
            if(correo.equals(user.getEmail())){
                return user;
            }
        }
        return null;
    }

    public Boolean updateUser(int index, User newUser){
        try{
            allUsers.set(index, newUser);
            return Boolean.TRUE;
        } catch (Exception e){
            error = e;
            return null;
        }
    }

    public Boolean removeUser(User user){
        try{
            allUsers.remove(user);
            return  Boolean.TRUE;
        } catch (Exception e){
            error = e;
            return Boolean.FALSE;
        }
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
