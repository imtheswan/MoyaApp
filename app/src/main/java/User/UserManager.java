package User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserManager {
    //User[] usersArray = {};
    //public List<User> allUsers = Arrays.asList(usersArray);
    public List<User> allUsers = new ArrayList<User>();

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

    public boolean authenticateUser(String correo, String pass){
        for (User user: allUsers) {
            if(user.getEmail().equals(correo)
                    && user.getPass().equals(pass))
                return true;
        }
        return false;
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

    public  String showUsers(){
        String userString = "";
        for (User user : allUsers) {
            userString = userString + user.getFirstName();
        }
        return userString;
    }
}
