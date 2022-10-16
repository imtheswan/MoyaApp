package com.example.moyaparcial2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import Filemanager.FileManager;
import JSONmanager.JSONmanager;
import User.User;
import User.UserManager;
import Validator.Validator;

public class Register extends AppCompatActivity {
    // UI objects
    private EditText inputName;
    private EditText inputLastName;
    private EditText inputNickName;
    private EditText inputEmail;
    private EditText inputPhone;
    private EditText inputPass;
    private EditText inputAge;
    private EditText inputBirth;
    private RadioButton inputMan;
    private RadioButton inputWoman;
    private EditText inputDescription;
    private Switch inputARView;
    private ToggleButton inputPublicProfile;

    private Button submit;
    private Button linkLogin;
    // Managment object
    private FileManager userRegistry = new FileManager();
    private User user = new User();
    private JSONmanager jsonManager = new JSONmanager();

    private boolean verifiedData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        inputName = findViewById(R.id.inputName);
        inputLastName = findViewById(R.id.inputLastName);
        inputNickName = findViewById(R.id.inputNickName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPhone = findViewById(R.id.inputPhone);
        inputPass = findViewById(R.id.inputPassR);
        inputAge = findViewById(R.id.inputAge);
        inputBirth = findViewById(R.id.inputBirth);
        inputMan = findViewById(R.id.radioMan);
        inputWoman = findViewById(R.id.radioWoman);
        inputDescription = findViewById(R.id.inputDescription);
        inputARView = findViewById(R.id.switchAR);
        inputPublicProfile = findViewById(R.id.inputPublicProfile);
        submit = findViewById(R.id.submitButton);
        linkLogin = findViewById(R.id.loginLinkButton);

        Toast.makeText(getApplicationContext(), getDataDir().toString(), Toast.LENGTH_SHORT).show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = getData();
                if(valid){
                    if (writeData()) {
                        t("Buen registro @_@");
                        hitAndRun();
                    } else{
                        t("Error al registrar usuario");
                    }
                }
                else{
                    t("Por favor, corriga los datos");
                }
            }
        });

        linkLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hitAndRun();
                    }
                }
        );
    }

    public void hitAndRun() { //cambiar a login activity
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void test() {
        String text = userRegistry.readPlainText();
        Boolean able = userRegistry.writePlainText("Hola, mundo");
        Toast.makeText(getApplicationContext(), "TextInit: " + text, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "TextInit: " + able.toString(), Toast.LENGTH_LONG).show();
    }

    public void t(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public boolean getData() { //Obtiene los valores de las entradas

        Validator ovalidator = new Validator();
        Log.d("Estado", "EMAIL " + Boolean.toString(ovalidator.verifyEmail("juanpamora31@gmail.com")));
        String bir = "31/05/2005";
        Log.d("Estado", "BIRTH " + Integer.toString(bir.length()));
        Log.d("Estado", "BIRTH " + bir.charAt(2));
        Log.d("Estado", "BIRTH " + bir.charAt(5));
        Log.d("Estado", "BIRTH " + Boolean.toString((bir.length() == 10) && bir.charAt(2) == '/' && bir.charAt(5) == '/'));

        String name = inputName.getText().toString();
        String lastName = inputLastName.getText().toString();
        String nickName = inputNickName.getText().toString();
        String email = inputEmail.getText().toString().toLowerCase();
        String phone = inputPhone.getText().toString();
        String pass = inputPass.getText().toString();
        String edad = inputAge.getText().toString();
        String nacimiento = inputBirth.getText().toString();
        boolean hombre = inputMan.isChecked();
        boolean mujer = inputWoman.isChecked();
        String descripcion = inputDescription.getText().toString();
        boolean arView = inputARView.isChecked();
        boolean perfilPublico = inputPublicProfile.isChecked();
        Validator validator = new Validator();
        boolean error = false;
        if(!validator.verifyText(name)){
            inputName.setError("Nombre inválido");
            error = true;
        }
        if (!validator.verifyText(lastName)){
            inputLastName.setError("Apellido inválido");
            error = true;
        }
        if (!validator.verifyEmail(email)){
            inputEmail.setError("Correo inválido");
            error = true;
        }
        if (!validator.verifyPhone(phone)){
            inputPhone.setError("Teléfono inválido");
            error = true;
        }
        if (!validator.verifyNumber(edad)){
            inputAge.setError("Edad inválida");
            error = true;
        }
        if(!validator.verifyBirth(nacimiento)){
            inputBirth.setError("Edad inválida");
            error = true;
        }
        if(pass.length() == 0){
            inputPass.setError("Necesita contraseña");
            error = true;
        }
        if (!error){
            DigestManager digestManager = new DigestManager();
            user.setFirstName(name);
            user.setLastName(lastName);
            user.setNickName(nickName);
            user.setEmail(email);
            user.setPhone(Integer.parseInt(phone));
            //// GET PASS DIGEST
            user.setPass(pass);
            user.setPassHash();
            user.setAge(Integer.parseInt(edad));
            user.setBirth(nacimiento);
            if(hombre)
                user.setGender(1);
            else
                user.setGender(2);
            user.setDescription(descripcion);
            user.setArView(arView);
            user.setPublicProfile(perfilPublico);
            return true;
        }
        return false;

    }

    public boolean writeData() { //Escribe y registra al usuario
            Boolean creationState = userRegistry.accessFile(getDataDir(), "RegistroMoyaApp.json");
            Log.d("Estado", "AccesFile " + creationState.toString());
            String registro = userRegistry.readPlainText();
            Log.d("Estado", "JSON recuperado: " + registro);
            UserManager usMG;
            usMG = (UserManager) jsonManager.getObject(registro, UserManager.class);
            Log.d("Estado", "Recuperados: " + usMG.showUsers());
            usMG.addUser(user);
            registro = jsonManager.getJSON(usMG);
            creationState = userRegistry.writePlainText(registro);
            return creationState;
    }
}