package com.example.moyaapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;

import DesUtil.DesUtil;
import Filemanager.FileManager;
import JSONmanager.JSONmanager;
import Password.Password;
import Password.PassManager;

public class PasswordManager extends AppCompatActivity {

    private PassManager universalPass;
    private static final String key = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    private  DesUtil desUtil = new DesUtil();
    private LinearLayout container;
    private LinearLayout dialog;
    private String correo;
    private String lastStringDialog = "";

    String TAG = "Estado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_manager);
        reciveIntent();
        container = findViewById(R.id.container);
        dialog = findViewById(R.id.dialog);
        desUtil.addStringKeyBase64(key);
        updateElements();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        boolean flag = false;
        flag = super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu ,  menu);
        return flag;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId() ) {
            case R.id.nuevoId:
                createValueField("nuevo");
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void reciveIntent(){
        Intent intent = getIntent();
        correo = intent.getStringExtra("userkey");
        if(correo == null){
            Toast.makeText(getApplicationContext(), "No tienes correo", Toast.LENGTH_SHORT).show();
            PasswordManager.this.finish();
        }
    }

    private void createValueField(String accion){// nuevo va a crear una contraseña, otro cambiara el nombre de esta
        EditText editText = generateEditText(EditText.generateViewId(), "Nombre");
        Button ok = generateButton(Button.generateViewId(), R.string.aceptar);
        Button cancel = generateButton(Button.generateViewId(), R.string.cancelar);
        LinearLayout top = generateLayout();
        LinearLayout bottom = generateLayout();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int totalWidth = displayMetrics.widthPixels;
        editText.setWidth(calPorCien(totalWidth, 70));
        ok.setWidth(calPorCien(totalWidth, 30));
        cancel.setWidth(calPorCien(totalWidth, 30));

        editText.setBackgroundResource(R.drawable.border_input);
        ok.setBackgroundResource(R.drawable.send_button);
        cancel.setBackgroundResource(R.drawable.register_login);

        editText.setTextColor(getColor(R.color.black));
        cancel.setTextColor(getColor(R.color.black));
        top.addView(editText);
        bottom.addView(ok);
        bottom.addView(cancel);

        dialog.addView(top);
        dialog.addView(bottom);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                top.removeAllViews();
                bottom.removeAllViews();
                dialog.removeAllViews();
                lastStringDialog = null;
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = editText.getText().toString();
                if(nombre != null && nombre.length() != 0){
                    top.removeAllViews();
                    bottom.removeAllViews();
                    dialog.removeAllViews();
                    if(accion.equals("nuevo")){ // agregar contraseña
                        agregarNuevaPass(nombre);
                    } else{ // cambiar nombre
                        Password lastPass = universalPass.getPassword(accion);
                        universalPass.deletePassword(accion);
                        lastPass.setNombre(nombre);
                        universalPass.addPassword(lastPass);
                        savePasswords(universalPass);
                        updateElements();
                    }
                    lastStringDialog = nombre;
                } else{
                    Toast.makeText(getApplicationContext(), "Asigna un nombre", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean agregarNuevaPass(String nombre){
        Password gen = new Password();
        Password password = new Password(nombre, gen.generatePassword() , correo);
        if(universalPass.addPassword(password)){
            Toast.makeText(getApplicationContext(), "Contraseña agregada", Toast.LENGTH_SHORT).show();
            savePasswords(universalPass);
            updateElements();
            return true;
        }
        else{
            Toast.makeText(getApplicationContext(), "Ya hay una contraseña con ese nombre", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void updateElements(){
        Log.d(TAG, "updateElements: ");
        universalPass = accessPasswords();
        container.removeAllViews();
        int passNumber = 0;

        for (int i = 0; i < universalPass.getLenght(); i++) {
            Password password = universalPass.getPassword(i);
            if(password.getOwner().equals(correo)){
                passNumber ++;
            }
        }

        if(passNumber <= 0){
            Log.d(TAG, "updateElements: No hay contraseñas");
            TextView textView = new TextView(this);
            textView.setText("No hay contraseñas");
            textView.setTextColor(getColor(R.color.black));
            textView.setGravity(Gravity.CENTER_VERTICAL);
            container.addView(textView);
        } else{
            for(int i = 0; i < universalPass.getLenght(); i++){
                if(universalPass.getPassword(i).getOwner().equals(correo)){
                    Password password = universalPass.getPassword(i);
                    Log.d(TAG, "updateElements: Agregando" + password.getNombre());
                    TextView textView = generateTextView(password.getNombre());
                    textView.setTextColor(getColor(R.color.black));
                    textView.setBackgroundResource(R.drawable.input_border);
                    textView.setPadding(10,5,5,10);
                    ImageView edit = generateImageView(R.drawable.edit);
                    ImageView remove = generateImageView(R.drawable.delete);
                    edit.setMaxHeight(30);
                    edit.setMaxWidth(30);
                    remove.setMaxHeight(30);
                    remove.setMaxWidth(30);
                    LinearLayout subContainer = generateLayout();
                    subContainer.setGravity(Gravity.CENTER_VERTICAL);
                    subContainer.addView(textView);
                    subContainer.addView(edit);
                    subContainer.addView(remove);
                    container.addView(subContainer);

                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            createValueField(password.getNombre());
                        }
                    });

                    remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolean succes = universalPass.deletePassword(password.getNombre());
                            savePasswords(universalPass);
                            updateElements();
                            Log.d(TAG, "onClick remove: " + succes);
                            Toast.makeText(getApplicationContext(), "Eliminado", Toast.LENGTH_SHORT).show();
                        }
                    });

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText(null, password.getValor());
                            if (clipboard == null) return;
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(getApplicationContext(), "Se copió la contraseña", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else{
                    Log.d(TAG, "updateElements: Skip" + universalPass.getPassword(i).getNombre());
                }
            }
        }
    }

    private PassManager accessPasswords(){
        FileManager fileManager = new FileManager();
        JSONmanager jsonMan = new JSONmanager();
        PassManager passManager;

        boolean existence = fileManager.accessFile(getDataDir(), "passAdministration.json");
        if(existence){
            Log.d(TAG, "accessPasswords: Archivo existe ");
            String encrypted_json = fileManager.readByteStream();
            Log.d(TAG, "accessPasswords: encryp: " + encrypted_json);
            String json = desUtil.desCifrar(encrypted_json);
            Log.d(TAG, "accessPasswords: deencryp: " + json);
            passManager = (PassManager) jsonMan.getObject(json, PassManager.class);
        } else{
            Log.d(TAG, "accessPasswords: Archivo NO existe ");
            passManager = new PassManager();
            String json = jsonMan.getJSON(passManager);
            String encrypted_json = desUtil.cifrar(json);
            fileManager.writeByteStream(json);
        }
        return passManager;
    }

    private void savePasswords(PassManager passManager){
        Log.d(TAG, "savePasswords: Guardando contras");
        FileManager fileManager = new FileManager();
        JSONmanager jsonMan = new JSONmanager();

        fileManager.accessFile(getDataDir(), "passAdministration.json");
        String json = jsonMan.getJSON(passManager);
        String encrypted_json = desUtil.cifrar(json);
        fileManager.writeByteStream(encrypted_json);
    }

    ///             UI ELEMENTS

    private int calPorCien(int ciento, int porciento){ //Caulcula el porcentaje
        return (porciento * ciento / 100);
    }

    private TextView generateTextView(String text){
        Log.d(TAG, "generateTextView: Creando texto " + text);
        TextView textView = new TextView(this);
        textView.setWidth(350);
        textView.setText(text);
        return  textView;
    }

    private ImageView generateImageView(int image){
        Log.d(TAG, "generateTextView: Creando Imagen ");
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(image);
        return imageView;
    }

    private EditText generateEditText(int id, String placeholder){
        Log.d(TAG, "generateTextView: Creando Input Text ");
        EditText editText = new EditText(this);
        editText.setId(id);
        editText.setHint(placeholder);
        return editText;
    }

    private Button generateButton(int id, int textValue){
        Log.d(TAG, "generateTextView: Creando Input Text ");
        Button button =  new Button(this);
        button.setText(textValue);
        button.setBackgroundColor(this.getColor(R.color.cyan));
        return button;
    }

    private LinearLayout generateLayout () {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return linearLayout;
    }
}