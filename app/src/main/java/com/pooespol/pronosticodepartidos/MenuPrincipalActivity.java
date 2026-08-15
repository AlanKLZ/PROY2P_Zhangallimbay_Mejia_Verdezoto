package com.pooespol.pronosticodepartidos;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MenuPrincipalActivity extends AppCompatActivity {

    // DATOS DEL USUARIO
    private TextView txtNombreUsuario;
    private TextView txtTipoUsuario;

    // CONTENEDORES
    private LinearLayout layoutParticipante;
    private LinearLayout layoutAdministrador;

    // OPCIONES PARTICIPANTE
    private LinearLayout opcionTablaPosiciones;
    private LinearLayout opcionPronosticos;
    private LinearLayout opcionMisPronosticos;

    // OPCIONES ADMINISTRADOR
    private LinearLayout opcionAdministrarPartidos;
    private LinearLayout opcionActualizarPuntajes;

    // SALIR
    private LinearLayout opcionSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        inicializarComponentes();
        recibirDatosUsuario();
        configurarBotones();
    }

    // =====================================================
    // RELACIONAR IDS DEL XML CON JAVA
    // =====================================================

    private void inicializarComponentes() {

        txtNombreUsuario = findViewById(R.id.txtNombreUsuario);
        txtTipoUsuario = findViewById(R.id.txtTipoUsuario);

        layoutParticipante = findViewById(R.id.layoutParticipante);
        layoutAdministrador = findViewById(R.id.layoutAdministrador);

        opcionTablaPosiciones = findViewById(R.id.opcionTablaPosiciones);
        opcionPronosticos = findViewById(R.id.opcionPronosticos);
        opcionMisPronosticos = findViewById(R.id.opcionMisPronosticos);

        opcionAdministrarPartidos = findViewById(R.id.opcionAdministrarPartidos);
        opcionActualizarPuntajes = findViewById(R.id.opcionActualizarPuntajes);

        opcionSalir = findViewById(R.id.opcionSalir);
    }

    // =====================================================
    // RECIBIR DATOS DEL USUARIO
    // =====================================================

    private void recibirDatosUsuario() {

        String nombreUsuario = getIntent().getStringExtra("nombreUsuario");
        String tipoUsuario = getIntent().getStringExtra("tipoUsuario");

        // Si todavía no llegan datos desde el login,
        // usamos valores de prueba.
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            nombreUsuario = "Juan Pérez";
        }

        if (tipoUsuario == null || tipoUsuario.trim().isEmpty()) {
            tipoUsuario = "Participante";
        }

        txtNombreUsuario.setText(nombreUsuario);
        txtTipoUsuario.setText(tipoUsuario);

        mostrarMenuSegunUsuario(tipoUsuario);
    }

    // =====================================================
    // MOSTRAR MENÚ SEGÚN TIPO DE USUARIO
    // =====================================================

    private void mostrarMenuSegunUsuario(String tipoUsuario) {

        if (tipoUsuario.equalsIgnoreCase("Administrador")
                || tipoUsuario.equalsIgnoreCase("Organizador")) {

            layoutParticipante.setVisibility(View.GONE);
            layoutAdministrador.setVisibility(View.VISIBLE);

        } else {

            layoutParticipante.setVisibility(View.VISIBLE);
            layoutAdministrador.setVisibility(View.GONE);
        }
    }

    // =====================================================
    // CONFIGURAR BOTONES
    // =====================================================

    private void configurarBotones() {

        opcionTablaPosiciones.setOnClickListener(v -> {
            Toast.makeText(
                    MenuPrincipalActivity.this,
                    "Tabla de posiciones",
                    Toast.LENGTH_SHORT
            ).show();
        });

        opcionPronosticos.setOnClickListener(v -> {
            Toast.makeText(
                    MenuPrincipalActivity.this,
                    "Pronósticos",
                    Toast.LENGTH_SHORT
            ).show();
        });

        opcionMisPronosticos.setOnClickListener(v -> {
            Toast.makeText(
                    MenuPrincipalActivity.this,
                    "Mis pronósticos",
                    Toast.LENGTH_SHORT
            ).show();
        });

        opcionAdministrarPartidos.setOnClickListener(v -> {
            Toast.makeText(
                    MenuPrincipalActivity.this,
                    "Administrar partidos",
                    Toast.LENGTH_SHORT
            ).show();
        });

        opcionActualizarPuntajes.setOnClickListener(v -> {
            Toast.makeText(
                    MenuPrincipalActivity.this,
                    "Actualizar puntajes",
                    Toast.LENGTH_SHORT
            ).show();
        });

        opcionSalir.setOnClickListener(v -> {
            Toast.makeText(
                    MenuPrincipalActivity.this,
                    "Sesión cerrada",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        });
    }
}