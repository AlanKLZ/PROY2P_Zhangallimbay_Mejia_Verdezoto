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
    private Button btnTablaPosiciones;
    private Button btnPronosticos;
    private Button btnMisPronosticos;
    private Button btnSalirParticipante;
    private Participante actual;
    private ArrayList<Usuario> usuarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_principal_participante);

        TextView tVnombreParticipante = findViewById(R.id.tvnombreParticipante);
        btnTablaPosiciones = findViewById(R.id.btnTablaPosiciones);
        btnPronosticos = findViewById(R.id.btnPronosticos);
        btnMisPronosticos = findViewById(R.id.btnMisPronosticos);
        btnSalirParticipante = findViewById(R.id.btnSalirParticipante);
        actual = (Participante) getIntent().getSerializableExtra("actual");
        tVnombreParticipante.setText(actual.getNombreCompleto());

        usuarios = (ArrayList<Usuario>)getIntent().getSerializableExtra("usuarios");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void verTablaPosicion(View view){
        Intent tablaPosicion = new Intent(MenuPrincipalParticipante.this,TablaClasificacionActivity.class);
        //Pendiente el manejo de pasar el arraylist
        tablaPosicion.putExtra("usuarios",usuarios);
        tablaPosicion.putExtra("actual",actual);
        startActivity(tablaPosicion);
    }
    public void verPronosticos(View view){
        Intent pronosticos = new Intent(MenuPrincipalParticipante.this,PronosticosActivity.class);
        pronosticos.putExtra("actual",actual);
        pronosticos.putExtra("usuarios",usuarios);
        startActivity(pronosticos);
        
    }
    public void verMisPronosticos(View view){

    }
    public void salir(View view){
        finish();
    }
}
