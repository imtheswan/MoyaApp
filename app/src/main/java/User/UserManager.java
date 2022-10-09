package User;

import java.util.List;

public class UserManager {
    private List<User> allUsers;

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
}
