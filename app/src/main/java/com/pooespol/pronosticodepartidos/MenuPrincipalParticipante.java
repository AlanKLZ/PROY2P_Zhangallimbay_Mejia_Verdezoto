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

import com.pooespol.pronosticodepartidos.modelo.Participante;
import com.pooespol.pronosticodepartidos.modelo.Usuario;

import java.util.ArrayList;

public class MenuPrincipalParticipante extends AppCompatActivity {
    private TextView tVnombreParticipante;
    private Button btnTablaPosiciones;
    private Button btnPronosticos;
    private Button btnMisPronosticos;
    private Button btnSalirParticipante;
    private ArrayList<Usuario> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_principal_participante);
        tVnombreParticipante = findViewById(R.id.tvnombreParticipante);
        btnTablaPosiciones = findViewById(R.id.btnTablaPosiciones);
        btnPronosticos = findViewById(R.id.btnPronosticos);
        btnMisPronosticos = findViewById(R.id.btnMisPronosticos);
        btnSalirParticipante = findViewById(R.id.btnSalirParticipante);
        String nombreCompleto = getIntent().getStringExtra("nombreCompleto");
        tVnombreParticipante.setText(nombreCompleto);
        btnPronosticos.setOnClickListener(v-> verPronosticos());
        usuarios = (ArrayList<Usuario>)getIntent().getSerializableExtra("usuarios");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void verTablaPosicion(View view){
        Intent tablaPosicion = new Intent(MenuPrincipalParticipante.this,TablaClasificacionActivity.class);
        tablaPosicion.putExtra("usuarios",usuarios);
        startActivity(tablaPosicion);
    }
    public void verPronosticos(){
        Intent intent = new Intent(MenuPrincipalParticipante.this,PronosticosActivity.class);
        startActivity(intent);
    }
    public void verMisPronosticos(View view){

    }
    public void salir(View view){
        finish();
    }
}