package com.pooespol.pronosticodepartidos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MenuPrincipalAdministrador extends AppCompatActivity {
    private TextView tVnombreAdministrador;
    private Button btnAdminstrarPartidos;
    private Button btnActualizarPartidos;
    private Button btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_principal_administrador);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnAdminstrarPartidos = findViewById(R.id.btnAdministrarPartidos);
        btnActualizarPartidos = findViewById(R.id.btnActualizarPuntajes);
        btnSalir = findViewById(R.id.btnSalirAdministrador);
        tVnombreAdministrador = findViewById(R.id.tVnombreAdministrador);
        String nombreCompleto = getIntent().getStringExtra("nombreCompleto");
        tVnombreAdministrador.setText(nombreCompleto);
    }

    public void actualizarPartidos(View view){

    }
    public void adminstrarPartidos(View view){

    }
    public void salir(View view){
        finish();
    }
}