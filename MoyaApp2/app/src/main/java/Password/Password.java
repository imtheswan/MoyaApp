package Password;

import java.util.Random;

public class Password {
    private String nombre;
    private String valor;
    private String owner;

    public Password(String nombre, String valor, String owner) {
        this.nombre = nombre;
        this.valor = valor;
        this.owner = owner;
    }

    public Password(){
        this.nombre = "default";
        this.valor = "default";
        this.owner = "default";
    }

    public Password(String nombre) {
        this.nombre = nombre;
        this.valor = generatePassword();
    }

    public String generatePassword(){
        StringBuilder pass = new StringBuilder();
        String abc = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890!@#$%^&*()_+=-";
        Random random = new Random();
        for (int i = 0; i < 10; i++ ){
            int pos = random.nextInt(abc.length());
            pass.append(abc.charAt(pos));
        }
        return pass.toString();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
