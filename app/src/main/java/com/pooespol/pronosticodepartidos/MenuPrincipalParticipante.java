package com.pooespol.pronosticodepartidos;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuPrincipalParticipante extends AppCompatActivity {

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
        setContentView(R.layout.activity_menu_principal_participante);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inicializarComponentes();
        recibirDatosUsuario();
        configurarBotones();
    }

    // =====================================================
    // RELACIONAR IDS DEL XML CON JAVA
    // =====================================================

    private void inicializarComponentes() {
        //Borrar estos dos, ya que no se instancian, se crean desde otro metodo
        txtNombreUsuario = findViewById(R.id.txtNombreUsuario);
        txtTipoUsuario = findViewById(R.id.txtTipoUsuario);

        //borrar esto, solo es 1 layout
        layoutParticipante = findViewById(R.id.layoutParticipante);
        layoutAdministrador = findViewById(R.id.layoutAdministrador);
        //Se queda
        opcionTablaPosiciones = findViewById(R.id.opcionTablaPosiciones);
        opcionPronosticos = findViewById(R.id.opcionPronosticos);
        opcionMisPronosticos = findViewById(R.id.opcionMisPronosticos);
        //Se borra
        opcionAdministrarPartidos = findViewById(R.id.opcionAdministrarPartidos);
        opcionActualizarPuntajes = findViewById(R.id.opcionActualizarPuntajes);
        //Se mantiene
        opcionSalir = findViewById(R.id.opcionSalir);
    }

    // =====================================================
    // RECIBIR DATOS DEL USUARIO
    // =====================================================
    //Bien, pero es mejor que cree una view, mas no instanciarlo primero.
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
        //No sirve
        mostrarMenuSegunUsuario(tipoUsuario);
    }

    // =====================================================
    // MOSTRAR MENÚ SEGÚN TIPO DE USUARIO
    // =====================================================
    //Reduntante, para eso ya tenemos diferentes activities
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
    //Util separarlos, but ciertos botones no van
    private void configurarBotones() {

        opcionTablaPosiciones.setOnClickListener(v -> {
            Toast.makeText(
                    MenuPrincipalParticipante.this,
                    "Tabla de posiciones",
                    Toast.LENGTH_SHORT
            ).show();
        });

        opcionPronosticos.setOnClickListener(v -> {
            Toast.makeText(
                    MenuPrincipalParticipante.this,
                    "Pronósticos",
                    Toast.LENGTH_SHORT
            ).show();
        });

        opcionMisPronosticos.setOnClickListener(v -> {
            Toast.makeText(
                    MenuPrincipalParticipante.this,
                    "Mis pronósticos",
                    Toast.LENGTH_SHORT
            ).show();
        });

        opcionAdministrarPartidos.setOnClickListener(v -> {
            Toast.makeText(
                    MenuPrincipalParticipante.this,
                    "Administrar partidos",
                    Toast.LENGTH_SHORT
            ).show();
        });

        opcionActualizarPuntajes.setOnClickListener(v -> {
            Toast.makeText(
                    MenuPrincipalParticipante.this,
                    "Actualizar puntajes",
                    Toast.LENGTH_SHORT
            ).show();
        });

        opcionSalir.setOnClickListener(v -> {
            Toast.makeText(
                    MenuPrincipalParticipante.this,
                    "Sesión cerrada",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        });
    }
}