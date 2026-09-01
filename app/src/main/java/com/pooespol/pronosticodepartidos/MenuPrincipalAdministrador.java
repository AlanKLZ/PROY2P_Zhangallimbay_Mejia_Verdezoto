package com.pooespol.pronosticodepartidos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pooespol.pronosticodepartidos.modelo.Administrador;

public class MenuPrincipalAdministrador extends AppCompatActivity {
    private TextView tVnombreAdministrador;
    private Button btnAdminstrarPartidos;
    private Button btnActualizarPartidos;
    private Button btnSalir;
    private Administrador administrador;

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
        administrador = (Administrador) getIntent().getSerializableExtra("actual");
        tVnombreAdministrador.setText(administrador.getNombreCompleto());

    }

    public void actualizarPartidos(View view){
        Intent actualizarPartidos = new Intent(MenuPrincipalAdministrador.this,ActualizarPartidosActivity.class);
        actualizarPartidos.putExtra("actual",administrador);
        startActivity(actualizarPartidos);
    }
    public void adminstrarPartidos(View view){

    }
    public void actualizarPuntajes(View v){
        Intent actualizarPuntajes = new Intent (MenuPrincipalAdministrador.this, ActualizarPuntajesActivity.class);
        actualizarPuntajes.putExtra("actual", administrador);
        startActivity(actualizarPuntajes);
    }
    public void salir(View view){
        finish();
    }
}