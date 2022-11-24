package Password;

import java.util.Random;

public class Password {
    private String nombre;
    private String valor;

    public Password(String nombre, String valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public Password(String nombre) {
        this.nombre = nombre;
        this.valor = generatePassword();
    }

    public String generatePassword(){
        String abc = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890!@#$%^&*()_+=-";
        char [] charset = {' ' * 8};
        Random random = new Random();
        for (int i = 0; i <= 10; i++ ){
            int pos = random.nextInt(abc.length());
            charset[i] = abc.charAt(pos);
        }
        return charset.toString();
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
}
