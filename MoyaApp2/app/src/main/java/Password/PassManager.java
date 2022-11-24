package Password;

import java.util.ArrayList;
import java.util.List;

public class PassManager {
    private List<Password> passwordList = new ArrayList <Password>();

    private boolean alreadyExists(String nombre){
        for (Password password: passwordList) {
            if(nombre.equals(password.getNombre())) return true;
        }
        return false;
    }

    public Password getPassword(String nombre){
        for (Password password: passwordList) {
            if(nombre.equals(password.getNombre())) return password;
        }
        return null;
    }

    public Password getPassword(int position){
        if(position < passwordList.size()){
            return passwordList.get(position);
        }
        return null;
    }

    public boolean addPassword(Password password){
        if(alreadyExists(password.getNombre())){
            return false;
        }
        else{
            passwordList.add(password);
            return true;
        }
    }
}
