package com.example.herramania11;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias a los botones
        Button iniciarSesionButton = findViewById(R.id.button3); // INICIAR SESION
        Button registrarseButton = findViewById(R.id.button4); // REGISTRARSE

        // Asignar eventos de clic
        iniciarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar ActivityMain2 cuando se hace clic en INICIAR SESION
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        registrarseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar ActivityMain3 cuando se hace clic en REGISTRARSE
                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(intent);
            }
        });
    }
}
