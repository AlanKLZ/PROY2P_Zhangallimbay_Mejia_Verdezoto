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

    // CONTENEDORES DE MENÚ
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
        cargarUsuario();
        configurarBotones();
    }


    // =====================================================
    // RELACIONAR LOS IDS DEL XML CON JAVA
    // =====================================================

    private void inicializarComponentes() {

        txtNombreUsuario = findViewById(R.id.txtNombreUsuario);
        txtTipoUsuario = findViewById(R.id.txtTipoUsuario);

        layoutParticipante = findViewById(R.id.layoutParticipante);
        layoutAdministrador = findViewById(R.id.layoutAdministrador);

        opcionTablaPosiciones =
                findViewById(R.id.opcionTablaPosiciones);

        opcionPronosticos =
                findViewById(R.id.opcionPronosticos);

        opcionMisPronosticos =
                findViewById(R.id.opcionMisPronosticos);

        opcionAdministrarPartidos =
                findViewById(R.id.opcionAdministrarPartidos);

        opcionActualizarPuntajes =
                findViewById(R.id.opcionActualizarPuntajes);

        opcionSalir =
                findViewById(R.id.opcionSalir);
    }


    // =====================================================
    // CARGAR DATOS DEL USUARIO
    // =====================================================

    private void cargarUsuario() {

        /*
         * Por ahora dejamos estos datos de prueba.
         * Después los podemos recibir desde LoginActivity.
         */

        String nombre = "Juan Pérez";
        String tipo = "Participante";

        txtNombreUsuario.setText(nombre);
        txtTipoUsuario.setText(tipo);

        mostrarMenu(tipo);
    }


    // =====================================================
    // MOSTRAR MENÚ SEGÚN EL TIPO DE USUARIO
    // =====================================================

    private void mostrarMenu(String tipo) {

        if (tipo.equalsIgnoreCase("Administrador")) {

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


        // TABLA DE POSICIONES
        opcionTablaPosiciones.setOnClickListener(v -> {

            Toast.makeText(
                    MenuPrincipalActivity.this,
                    "Tabla de posiciones",
                    Toast.LENGTH_SHORT
            ).show();

            /*
             * Cuando ya exista la Activity de tabla:
             *
             * Intent intent = new Intent(
             *         MenuPrincipalActivity.this,
             *         TablaPosicionesActivity.class
             * );
             *
             * startActivity(intent);
             */
        });


        // PRONÓSTICOS
        opcionPronosticos.setOnClickListener(v -> {

            Toast.makeText(
                    MenuPrincipalActivity.this,
                    "Pronósticos",
                    Toast.LENGTH_SHORT
            ).show();

        });


        // MIS PRONÓSTICOS
        opcionMisPronosticos.setOnClickListener(v -> {

            Toast.makeText(
                    MenuPrincipalActivity.this,
                    "Mis pronósticos",
                    Toast.LENGTH_SHORT
            ).show();

        });


        // ADMINISTRAR PARTIDOS
        opcionAdministrarPartidos.setOnClickListener(v -> {

            Toast.makeText(
                    MenuPrincipalActivity.this,
                    "Administrar partidos",
                    Toast.LENGTH_SHORT
            ).show();

        });


        // ACTUALIZAR PUNTAJES
        opcionActualizarPuntajes.setOnClickListener(v -> {

            Toast.makeText(
                    MenuPrincipalActivity.this,
                    "Actualizar puntajes",
                    Toast.LENGTH_SHORT
            ).show();

        });


        // SALIR
        opcionSalir.setOnClickListener(v -> {

            Toast.makeText(
                    MenuPrincipalActivity.this,
                    "Saliendo...",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        });
    }
}