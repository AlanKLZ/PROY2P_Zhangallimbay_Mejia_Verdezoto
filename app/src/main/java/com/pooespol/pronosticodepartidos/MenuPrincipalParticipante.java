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
/**
 * Activity que representa el menú principal del participante.
 * Permite acceder a la tabla de posiciones, registrar pronósticos,
 * consultar los pronósticos realizados y cerrar la sesión.
 * @author andreaverdezotolung
 */

public class MenuPrincipalParticipante extends AppCompatActivity {
    private Button btnTablaPosiciones;
    private Button btnPronosticos;
    private Button btnMisPronosticos;
    private Button btnSalirParticipante;
    private Participante actual;
    private ArrayList<Usuario> usuarios = new ArrayList<>();

    /**
     * Inicializa la Activity y obtiene la información del participante
     * que inició sesión y la lista de usuarios recibida desde la Activity anterior.
     *
     * @param savedInstanceState estado previamente guardado de la Activity
     */

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
    /**
     * Abre la pantalla de tabla de posiciones y envía la información
     * del participante actual.
     * @param view vista que ejecuta la acción
     */
    public void verTablaPosicion(View view){
        Intent tablaPosicion = new Intent(MenuPrincipalParticipante.this,TablaClasificacionActivity.class);
        //Pendiente el manejo de pasar el arraylist
        tablaPosicion.putExtra("usuarios",usuarios);
        tablaPosicion.putExtra("actual",actual);
        startActivity(tablaPosicion);
    }
    /**
     * Abre la pantalla en la que el participante puede registrar pronósticos.
     * @param view vista que ejecuta la acción
     */
    public void verPronosticos(View view){
        Intent pronosticos = new Intent(MenuPrincipalParticipante.this,PronosticosActivity.class);
        pronosticos.putExtra("actual",actual);
        pronosticos.putExtra("usuarios",usuarios);
        startActivity(pronosticos);
        
    }
    /**
     * Abre la pantalla que muestra los pronósticos registrados
     * por el participante actual.
     * @param view vista que ejecuta la acción
     */
    public void verMisPronosticos(View view){
        Intent misPronosticos = new Intent(MenuPrincipalParticipante.this, MisPronosticosActivity.class);
        misPronosticos.putExtra("actual", actual);
        startActivity(misPronosticos);
    }
    /**
     * Cierra el menú principal del participante y regresa
     * a la pantalla anterior.
     * @param view vista que ejecuta la acción
     */
    public void salir(View view){
        finish();
    }
}
