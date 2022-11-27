package com.example.moyaapp2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import DesUtil.DesUtil;
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

    //Email verification: DES
    private static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    private DesUtil desUtil = new DesUtil();
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

        //Toast.makeText(getApplicationContext(), getDataDir().toString(), Toast.LENGTH_SHORT).show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = getData();
                if(valid){
                    if (writeData()) {
                        try{
                            desUtil.addStringKeyBase64(KEY);
                            String emailCifrado = desUtil.cifrar(user.getEmail());
                            String htmlCifrado = desUtil.cifrar("<html><h1>Registro para una app????</h1></html>");
                            boolean result = sendInfo(emailCifrado, htmlCifrado, getBaseContext());
                            Log.d("Estado", "Correo Cifrado para enviar: " + emailCifrado);
                            Log.d("Estado", "HTML Cifrado para enviar: " + htmlCifrado);
                            Log.d("Estado", "Respuesta Volley: " + result);
                        } catch (Exception e){
                            Log.d("Estado", "Error");
                            e.printStackTrace();
                        }
                        t("Buen registro");
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
        String registro = userRegistry.readByteStream();
        Log.d("Estado", "JSON recuperado: " + registro);
        UserManager usMG;
        usMG = (UserManager) jsonManager.getObject(registro, UserManager.class);
        Log.d("Estado", "Recuperados: " + usMG.showUsers());
        usMG.addUser(user);
        registro = jsonManager.getJSON(usMG);
        creationState = userRegistry.writeByteStream(registro);
        return creationState;
    }

    public boolean sendInfo(String correoCifrado, String mensajeHTMLCifrado , Context context){
            JsonObjectRequest jsonObjectRequest = null;
            JSONObject jsonObject = null;
            String url = "https://us-central1-nemidesarrollo.cloudfunctions.net/function-test";
            RequestQueue requestQueue = null;
            if( correoCifrado == null || correoCifrado.length() == 0 || mensajeHTMLCifrado == null || mensajeHTMLCifrado.length() == 0 )
            {
                return false;
            }
            jsonObject = new JSONObject( );
            try
            {
                jsonObject.put("correo" , correoCifrado.substring(0, correoCifrado.length() -1) );
                jsonObject.put("mensaje" , mensajeHTMLCifrado.substring(0, mensajeHTMLCifrado.length() - 1));
                Log.i("Estado", jsonObject.toString());
                jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.i("Estado", response.toString());
                        // responseStr = response.toString();
                    }
                } , new  Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Estado", error.toString());
                    }
                } );
                requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(jsonObjectRequest);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            return true;}
}