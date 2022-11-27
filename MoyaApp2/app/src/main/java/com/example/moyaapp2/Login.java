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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import DesUtil.DesUtil;
import Filemanager.FileManager;
import JSONmanager.JSONmanager;
import Password.*;
import User.User;
import User.UserManager;
import Validator.Validator;

public class Login extends AppCompatActivity {

    private EditText inputUsuario;
    private EditText inputPass;
    private Button linkRegister;
    private Button ingresar;
    private Button passwordReset;

    private User user = new User();

    FileManager fileManager = new FileManager();
    JSONmanager jsonManager = new JSONmanager();
    UserManager userManager = new UserManager();
    private DesUtil desUtil = new DesUtil();
    private static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        desUtil.addStringKeyBase64(KEY);

        inputUsuario = findViewById(R.id.inputUsuario);
        inputPass = findViewById(R.id.editTextPass);
        linkRegister = findViewById(R.id.buttonRegister);
        ingresar = findViewById(R.id.buttonIngresar);
        passwordReset = findViewById(R.id.passReset);

        //verificar que haya un archivo de registro

        Boolean fileExists = fileManager.accessFile(getDataDir(),"RegistroMoyaApp.json");

        if(fileExists && fileManager.existence){
            Log.d("Estado", "Ya hay archivo registro");
        } else if(fileExists == null){
            Toast.makeText(getApplicationContext(), "No es posible crear registro", Toast.LENGTH_SHORT);
        }
        else{ //
            Log.d("Estado", "No hay archivo registro");
            User testUser = new User("Fulano Prueba");
            userManager.addUser(testUser);
            String json = jsonManager.getJSON(userManager);
            Log.d("Estado", "JSON: " + json);
            FileManager newFile = new FileManager();
            Boolean newFileExists = newFile.accessFile(getDataDir(), "RegistroMoyaApp.json");
            Log.d("Estado", "newfile" + newFileExists.toString());
            newFile.writeByteStream(desUtil.cifrar(json));
            Toast.makeText(getApplicationContext(), "No hay ningun usuario registrado, regístrese", Toast.LENGTH_SHORT).show();
            hitAndRun(Register.class);
        }

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getData()){
                    DigestManager digestManager = new DigestManager();
                    fileManager.accessFile(getDataDir(),"RegistroMoyaApp.json");
                    String registroJSON = fileManager.readByteStream();
                    registroJSON = desUtil.desCifrar(registroJSON);
                    Log.d("Estado", "JSON LOGIN " + registroJSON);
                    userManager = (UserManager) jsonManager.getObject(registroJSON, UserManager.class);
                    boolean auth = userManager.authenticateUser(user.getEmail(), user.getPassHash());
                    Log.d("Estado", "AUTH " + user.getEmail() + user.getPass());
                    if(auth) {
                        Toast.makeText(getApplicationContext(), "Usuario valido", Toast.LENGTH_SHORT).show();
                        try{
                            Handler handler = new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Login.this, Principal.class);
                                    intent.putExtra("userkey",user.getEmail());
                                    Login.this.startActivity(intent);
                                    Login.this.finish();
                                }
                            });
                        } catch(Exception e){
                            Log.d("Estado", e.getStackTrace().toString());
                        }
                    } else{
                        Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        linkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitAndRun(Register.class);
            }
        });

        passwordReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = inputUsuario.getText().toString();
                if(correo == null || correo.length() <= 0){
                    Toast.makeText(getApplicationContext(), "Escribe tu email", Toast.LENGTH_SHORT).show();
                } else{
                    String json = fileManager.readByteStream();
                    json = desUtil.desCifrar(json);
                    userManager = (UserManager) jsonManager.getObject(json, UserManager.class);
                    User user = userManager.getUser(correo);
                    userManager.removeUser(user);
                    if(user != null){
                        DigestManager digestManager = new DigestManager();
                        Password password = new Password();
                        String newPass = password.generatePassword();

                        user.setPass(newPass);
                        user.setPassHash();
                        userManager.addUser(user);
                        json = jsonManager.getJSON(userManager);
                        fileManager.writeByteStream(desUtil.cifrar(json));

                        String correoCifrado = desUtil.cifrar(correo);
                        String htmlClear = "<html><h2>Buenos días, " + user.getFirstName() + " " + user.getLastName() + "</h2><h2>Tu nueva constraseña es: "+ newPass +"</h2></html>";
                        String htmlCifrado = desUtil.cifrar(htmlClear);
                        sendInfo(correoCifrado, htmlCifrado, getApplicationContext());
                        Toast.makeText(getApplicationContext(), "Correo enviado", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(getApplicationContext(), "Ese correo no está registrado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void hitAndRun(Class activity){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Login.this, activity);
                Login.this.startActivity(intent);
                Login.this.finish();
            }
        });
    }

    public boolean getData(){
        String email = inputUsuario.getText().toString().toLowerCase();
        String pass = inputPass.getText().toString();
        if(email.length() == 0){
            inputUsuario.setError("Campo Requerido");
            return false;
        }
        if(pass.length() == 0){
            inputPass.setError("Campo Requerido");
            return false;
        }
        Validator validator = new Validator();
        if(validator.verifyEmail(email)){
            user.setEmail(email);
            user.setPass(pass);
            user.setPassHash();
            return true;
        } else{
            inputUsuario.setError("Escribe un correo válido");
            return false;
        }
    }

    public boolean sendInfo(String correoCifrado, String mensajeHTMLCifrado , Context context){
        JsonObjectRequest jsonObjectRequest = null;
        JSONObject jsonObject = null;
        String url = "https://us-central1-nemidesarrollo.cloudfunctions.net/envio_correo";
        RequestQueue requestQueue = null;
        if( correoCifrado == null || correoCifrado.length() == 0 || mensajeHTMLCifrado == null || mensajeHTMLCifrado.length() == 0 )
        {
            return false;
        }
        jsonObject = new JSONObject( );
        try
        {
            jsonObject.put("correo" , correoCifrado);
            jsonObject.put("mensaje" , mensajeHTMLCifrado);
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