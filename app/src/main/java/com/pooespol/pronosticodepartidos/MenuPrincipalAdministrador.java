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
/**
 * Activity que representa el menú principal del administrador.
 *
 * Permite acceder a las opciones para administrar los partidos,
 * actualizar los puntajes de los participantes y salir de la aplicación.
 * También muestra el nombre del administrador que inició sesión.
 *
 * @author andreaverdezotolung
 * @author Alan
 */

public class MenuPrincipalAdministrador extends AppCompatActivity {
    private TextView tVnombreAdministrador;
    private Button btnAdminstrarPartidos;
    private Button btnActualizarPartidos;
    private Button btnSalir;
    private Administrador administrador;

    /**
     * Inicializa el menú principal del administrador,
     * recupera el administrador autenticado y muestra su nombre
     * en la interfaz.
     *
     * @param savedInstanceState estado previamente guardado de la Activity
     */
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
    /**
     * Abre la pantalla para administrar los partidos del torneo.
     * Envía el administrador autenticado a la siguiente Activity
     * para conservar la información de la sesión.
     *
     * @param view vista que ejecuta la acción
     */
    public void actualizarPartidos(View view){
        Intent actualizarPartidos = new Intent(MenuPrincipalAdministrador.this,ActualizarPartidosActivity.class);
        actualizarPartidos.putExtra("actual",administrador);
        startActivity(actualizarPartidos);
    }
    /**
     * Abre la pantalla destinada a actualizar los puntajes
     * de los participantes.
     * Envía el administrador autenticado a la siguiente Activity
     * para realizar el proceso de actualización.
     * @param v vista que ejecuta la acción
     */
    public void actualizarPuntajes(View v){
        Intent actualizarPuntajes = new Intent (MenuPrincipalAdministrador.this, ActualizarPuntajesActivity.class);
        actualizarPuntajes.putExtra("actual", administrador);
        startActivity(actualizarPuntajes);
    }
    /**
     * Finaliza la Activity actual al seleccionar la opción de salir.
     * @param view vista que ejecuta la acción
     */
    public void salir(View view){
        finish();
    }
}