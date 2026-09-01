package com.pooespol.pronosticodepartidos;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.pooespol.pronosticodepartidos.modelo.EstadoPartido;
import com.pooespol.pronosticodepartidos.modelo.Fase;
import com.pooespol.pronosticodepartidos.modelo.ManejoArchivos;
import com.pooespol.pronosticodepartidos.modelo.Participante;
import com.pooespol.pronosticodepartidos.modelo.Partido;

import java.util.ArrayList;


public class PronosticosActivity extends AppCompatActivity {
    private Spinner spFase;
    private LinearLayout llPartidos;
    private ScrollView scrollViewPartidos;
    private Button btVolver;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ArrayList<Partido> partidos = new ArrayList<>();
    private Participante actual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("PRONOSTICOS", "ENTRO A ONCREATE");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pronosticos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        spFase = findViewById(R.id.spFase);
        llPartidos= findViewById(R.id.llPartidos);
        btVolver = findViewById(R.id.btVolver);
        partidos = ManejoArchivos.leerPartidos(this);

        Log.e("PRONOSTICOS", "Partidos leidos: " + partidos.size());
        scrollViewPartidos = findViewById(R.id.scrollViewPartidos);
        actual = (Participante)getIntent().getSerializableExtra("actual");

        //Listener para el spinner
        spFase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(
                    AdapterView<?> parent,
                    View view,
                    int position,
                    long id) {

                // Limpiar los partidos anteriores
                llPartidos.removeAllViews();

                Fase faseSeleccionada;

                switch (position) {
                    case 0:
                        faseSeleccionada = Fase.FASE_DE_GRUPOS;
                        break;

                    case 1:
                        faseSeleccionada = Fase.DIECISEISAVOS_DE_FINAL;
                        break;

                    case 2:
                        faseSeleccionada = Fase.OCTAVOS_DE_FINAL;
                        break;

                    case 3:
                        faseSeleccionada = Fase.CUARTOS_DE_FINAL;
                        break;

                    case 4:
                        faseSeleccionada = Fase.SEMIFINALES;
                        break;

                    case 5:
                        faseSeleccionada = Fase.TERCER_LUGAR;
                        break;

                    case 6:
                        faseSeleccionada = Fase.FINAL;
                        break;

                    default:
                        return;
                }

                ArrayList<Partido> partidosFase = new ArrayList<>();

                for (Partido partido : partidos) {
                    if (partido.getFaseTorneo() == faseSeleccionada) {
                        partidosFase.add(partido);
                    }
                }

                mostrarPartidos(partidosFase);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //llPartidos.removeAllViews();
            }
        });
        // Configuracion del menu lateral
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Abre el menu lateral con el boton de la barra superior
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.abrir_menu,
            R.string.cerar_menu
        );
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        //Instanciando el header
        View headerView = navigationView.getHeaderView(0);
        TextView nombreMenu = headerView.findViewById(R.id.nombreMenu);
        nombreMenu.setText(actual.getNombreCompleto());


        // Configuracion de los items del menu
        navigationView.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.navCerrarSesion) {

                Intent intent = new Intent(
                        PronosticosActivity.this,
                        MainActivity.class
                );

                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                );

                startActivity(intent);
                return true;
            }
            return false;
        });

        btVolver.setOnClickListener(v -> { finish();
        });
    }
    public void mostrarPartidos(ArrayList<Partido>partidos){
        for (Partido partido : partidos) {

            View vistaPartido = getLayoutInflater()
                    .inflate(R.layout.item_partido_pronostico, llPartidos, false);

            TextView tvFecha = vistaPartido.findViewById(R.id.tVFecha);
            TextView tvHora = vistaPartido.findViewById(R.id.tVHora);
            TextView tvEstadio = vistaPartido.findViewById(R.id.tVEstadio);
            TextView tvEstado = vistaPartido.findViewById(R.id.tVEstado);
            tvEstado.setText(partido.getEstadoPartido().toString());

            switch (partido.getEstadoPartido()) {

                case ABIERTO:
                    tvEstado.setTextColor(Color.GREEN);
                    break;

                case CERRADO:
                    tvEstado.setTextColor(Color.RED);
                    break;

                case FINALIZADO:
                    tvEstado.setTextColor(Color.GRAY);
                    break;
            }

            TextView tvSeleccion1 = vistaPartido.findViewById(R.id.tVSeleccion1);
            TextView tvSeleccion2 = vistaPartido.findViewById(R.id.tVSeleccion2);

            EditText gol1 = vistaPartido.findViewById(R.id.editTextGol1);
            EditText gol2 = vistaPartido.findViewById(R.id.editTextGol2);

            Button buttonGuardar = vistaPartido.findViewById(R.id.buttonGuardar);
            //Pendiente el listener de este boton

            // Asignar información del objeto
            tvFecha.setText(partido.getFecha());
            tvHora.setText(partido.getHora());
            tvEstadio.setText(partido.getEstadio());

            tvSeleccion1.setText(partido.getSeleccion1());
            tvSeleccion2.setText(partido.getSeleccion2());


            // Agregar la vista al ScrollView
            llPartidos.addView(vistaPartido);
        }
    }
}
