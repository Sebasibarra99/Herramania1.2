package com.example.herramania11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity3 extends AppCompatActivity {


    EditText nombre;
    EditText correoRegister;
    EditText contrasenaRegister;
    Button btn_register;

    FirebaseAuth mAuth;
    FirebaseFirestore nFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);



        nombre = findViewById(R.id.nombre);
        correoRegister = findViewById(R.id.correo);
        contrasenaRegister = findViewById(R.id.contrasena);
        btn_register = findViewById(R.id.btn_register);
        mAuth = FirebaseAuth.getInstance();
        nFirestore = FirebaseFirestore.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        String name = nombre.getText().toString();
        String email = correoRegister.getText().toString();
        String password = contrasenaRegister.getText().toString();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            if (isEmailValid(email)){
                if (password.length() >= 6){
                    createUser(name,email,password);
                }else{
                    Toast.makeText(this, "LA CONTRASENA DEBE TENER AL MENOS 6 CARACTERES", Toast.LENGTH_SHORT).show();                }

            }else{
                Toast.makeText(this, "EL CORREO NO ES VALIDO", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "INGRESE TODOS LOS DATOS", Toast.LENGTH_SHORT).show();
        }
    }

    private void createUser(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("name",name);
                    map.put("email",email);
                    map.put("password",password);
                    nFirestore.collection("Users").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent i = new Intent(MainActivity3.this, MainActivity2.class );
                                startActivity(i);
                                Toast.makeText(MainActivity3.this, "El usuario ha sido registrado con exito", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(MainActivity3.this, "No se ha logrado registrar el usuario. Intente nuevamente.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isEmailValid(String email){
         String expression =  "^[\\w\\-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
         Matcher matcher = pattern.matcher(email);
         return matcher.matches();
    }

}