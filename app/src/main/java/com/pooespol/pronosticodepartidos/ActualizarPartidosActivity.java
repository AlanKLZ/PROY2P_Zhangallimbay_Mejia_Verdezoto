package com.pooespol.pronosticodepartidos;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.pooespol.pronosticodepartidos.modelo.Administrador;
import com.pooespol.pronosticodepartidos.modelo.DatosIncompletosException;
import com.pooespol.pronosticodepartidos.modelo.EstadoPartido;
import com.pooespol.pronosticodepartidos.modelo.Fase;
import com.pooespol.pronosticodepartidos.modelo.ManejoArchivos;
import com.pooespol.pronosticodepartidos.modelo.Participante;
import com.pooespol.pronosticodepartidos.modelo.Partido;
import com.pooespol.pronosticodepartidos.modelo.PronosticoFueraDeTiempoException;
import com.pooespol.pronosticodepartidos.modelo.Resultado;
import com.pooespol.pronosticodepartidos.modelo.Usuario;

import java.util.ArrayList;

/**
 * Clase del actividad para actualizar los partidos
 * @author Alan
 */
public class ActualizarPartidosActivity extends AppCompatActivity {
    private Spinner spFase;
    private Administrador actual;
    private LinearLayout llPartidos;
    private ArrayList<Partido> partidos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actualizar_partidos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Asigando views
        actual = (Administrador) getIntent().getSerializableExtra("actual");
        spFase = findViewById(R.id.spFase);
        llPartidos = findViewById(R.id.llPartidos);
        Button btnVolver = findViewById(R.id.btVolver);
        partidos = ManejoArchivos.leerPartidos(this);

        //Configuracion del menu
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Instanciando el header
        View headerView = navigationView.getHeaderView(0);
        TextView nombreMenu = headerView.findViewById(R.id.nombreMenu);
        nombreMenu.setText(actual.getNombreCompleto());

        //Abre el menu a la izquierda
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.abrir_menu,R.string.cerar_menu);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        //Aqui se configura los items del menú
        navigationView.setNavigationItemSelectedListener(item -> {
            //Listener para cerrar sesion
            if (item.getItemId() == R.id.navCerrarSesion) {
                Intent intent = new Intent(
                        ActualizarPartidosActivity.this,
                        MainActivity.class
                );

                // Elimina las Activities anteriores
                // para que no pueda regresar con el botón atrás.
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                );

                startActivity(intent);

                return true;
            }
            return false;
        });

        //Listener para el spinner
        /**
         * Dependiendo del item seleccionado se muestran los partidos
         */
        spFase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(
                    AdapterView<?> parent,
                    View view,
                    int position,
                    long id
            ) {

                Fase faseSeleccionada;

                switch (position) {

                    case 0:
                        faseSeleccionada =
                            Fase.FASE_DE_GRUPOS;
                        break;

                    case 1:
                        faseSeleccionada =
                            Fase.DIECISEISAVOS_DE_FINAL;
                        break;
                    case 2:
                        faseSeleccionada =
                            Fase.OCTAVOS_DE_FINAL;
                        break;

                    case 3:
                        faseSeleccionada =
                            Fase.CUARTOS_DE_FINAL;
                        break;

                    case 4:
                        faseSeleccionada =
                            Fase.SEMIFINALES;
                        break;

                    case 5:
                        faseSeleccionada =
                            Fase.TERCER_LUGAR;
                        break;

                    case 6:
                        faseSeleccionada =
                            Fase.FINAL;
                        break;

                    default:
                        return;
                }
                ArrayList<Partido> partidosFiltrados = new ArrayList<>();

                for (Partido partido : partidos) {
                    if (partido.getFaseTorneo() == faseSeleccionada) {
                        partidosFiltrados.add(partido);
                    }
                }
                mostrarPartidos(partidosFiltrados);
            }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            }
        );
        //Termina la activity actual y regresa al menu principal
        btnVolver.setOnClickListener(v->{
            finish();
        });
    }

    /**
     * Se crean los views con los atributos de los partidos, además, dependiendo del item del spinner se
     * invoca el metodo de mostrarPartidos(...). Además que edita los views creados dependiendo del estado
     * que se encuentre el partido
     * @param vistaPartido View que se está creando
     * @param partido Objeto de un partido del que se mostrarán sus atributos
     */
    private void configurarEstadoPartido(View vistaPartido, Partido partido) {

        TextView tvEstado = vistaPartido.findViewById(R.id.tvEstado);
        TextView tvMensaje = vistaPartido.findViewById(R.id.tvMensaje);
        LinearLayout llMarcadorEditable = vistaPartido.findViewById(R.id.llMarcadorEditable);
        LinearLayout llMarcadorFinal = vistaPartido.findViewById(R.id.llMarcadorFinal);
        EditText etGol1 = vistaPartido.findViewById(R.id.etGol1);
        EditText etGol2 = vistaPartido.findViewById(R.id.etGol2);
        TextView tvGol1 = vistaPartido.findViewById(R.id.tvGol1);
        TextView tvGol2 = vistaPartido.findViewById(R.id.tvGol2);
        Button btnAccion = vistaPartido.findViewById(R.id.btnAccion);
        /**
         * Dependiendo del estado, se editan las views que se muestran en el scroll.
         */
        switch (partido.getEstadoPartido()) {

            case ABIERTO:
                tvEstado.setText("ABIERTO");
                tvEstado.setTextColor(Color.parseColor("#388E3C"));
                tvMensaje.setText(("Los participantes pueden registrar o modificar sus pronósticos"));
                tvMensaje.setTextColor(Color.parseColor("#212121"));
                tvMensaje.setBackgroundColor(Color.parseColor("#67f58d"));


                llMarcadorEditable.setVisibility(View.VISIBLE);
                llMarcadorFinal.setVisibility(View.GONE);

                etGol1.setFocusable(false);
                etGol1.setClickable(false);

                etGol2.setFocusable(false);
                etGol2.setClickable(false);

                btnAccion.setVisibility(View.VISIBLE);
                btnAccion.setText("Cerrar pronósticos");

                btnAccion.setOnClickListener(v -> {
                    partido.setEstadoPartido(EstadoPartido.CERRADO);
                    ManejoArchivos.guardarPartidos(partidos, this);
                    configurarEstadoPartido(vistaPartido, partido);
                });

                break;

            case CERRADO:
                tvEstado.setText("CERRADO");
                tvEstado.setTextColor(Color.parseColor("#F57C00"));
                tvMensaje.setText(("Los pronósticos están cerrados.\nRegistra el resultado oficial cuando el partido haya finalizado"));
                tvMensaje.setTextColor(Color.parseColor("#9c6000"));
                tvMensaje.setBackgroundColor(Color.parseColor("#f5d6a4"));

                llMarcadorEditable.setVisibility(View.VISIBLE);
                llMarcadorFinal.setVisibility(View.GONE);

                etGol1.setFocusableInTouchMode(true);
                etGol1.setClickable(true);

                etGol2.setFocusableInTouchMode(true);
                etGol2.setClickable(true);

                btnAccion.setVisibility(View.VISIBLE);
                btnAccion.setText("Registrar resultado");

                btnAccion.setOnClickListener(v -> {
                    try {
                        /*
                         * Verificación adicional por si el estado
                         * cambió mientras la vista estaba abierta.
                         */
                        if (partido.getEstadoPartido() != EstadoPartido.CERRADO) {
                            throw new PronosticoFueraDeTiempoException(
                                    "El partido ya no permite registrar resultados."
                            );
                        }

                        String goles1 = etGol1.getText().toString().trim();
                        String goles2 = etGol2.getText().toString().trim();

                        if (goles1.isEmpty() || goles2.isEmpty()) {
                            throw new DatosIncompletosException(
                                    "Debe ingresar el resultado de ambos equipos."
                            );
                        }

                        int gol1 = Integer.parseInt(goles1);
                        int gol2 = Integer.parseInt(goles2);

                        Resultado resultado = new Resultado(
                                partido.getIdPartido(),
                                gol1,
                                gol2
                        );

                        ManejoArchivos.registrarResultado(resultado, this);
                        partido.setEstadoPartido(EstadoPartido.FINALIZADO);
                        ManejoArchivos.guardarPartidos(partidos, this);
                        configurarEstadoPartido(vistaPartido, partido);

                    } catch (DatosIncompletosException | PronosticoFueraDeTiempoException e) {
                        Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case FINALIZADO:
                tvEstado.setText("FINALIZADO");
                tvEstado.setTextColor(Color.parseColor("#D32F2F"));
                tvMensaje.setText(("Resultado registrado. El partido ha finalizado"));
                tvMensaje.setTextColor(Color.parseColor("#020036"));
                tvMensaje.setBackgroundColor(Color.parseColor("#aeabf5"));

                llMarcadorEditable.setVisibility(View.GONE);
                llMarcadorFinal.setVisibility(View.VISIBLE);

                Resultado resultado = ManejoArchivos.obtenerResultado(partido.getIdPartido(), this);

                if (resultado != null) {
                    tvGol1.setText(String.valueOf(resultado.getGolesSeleccion1()));
                    tvGol2.setText(String.valueOf(resultado.getGolesSeleccion2()));
                }

                btnAccion.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * Este método cargas las views de los partidos seleccionados
     * @param partidosFiltrados Lista de partidos del cual se van a mostrar en el scroll
     */
    public void mostrarPartidos(ArrayList<Partido> partidosFiltrados) {

        // Eliminar los partidos mostrados anteriormente
        llPartidos.removeAllViews();

        for (Partido partido : partidosFiltrados) {
            View vistaPartido = getLayoutInflater()
                    .inflate(
                            R.layout.item_administrar_partido,
                            llPartidos,
                            false
                    );
            TextView tvIdPartido = vistaPartido.findViewById(R.id.tvIdPartido);
            TextView tvFecha = vistaPartido.findViewById(R.id.tvFecha);

            TextView tvHora = vistaPartido.findViewById(R.id.tvHora);

            TextView tvEstadio = vistaPartido.findViewById(R.id.tvEstadio);

            TextView tvSeleccion1 = vistaPartido.findViewById(R.id.tvSeleccion1);

            TextView tvSeleccion2 = vistaPartido.findViewById(R.id.tvSeleccion2);

            ImageView imgSeleccion1 = vistaPartido.findViewById(R.id.imgSeleccion1);

            ImageView imgSeleccion2 = vistaPartido.findViewById(R.id.imgSeleccion2);


            // Cargar información común
            tvFecha.setText(partido.getFecha());
            tvHora.setText(partido.getHora());
            tvEstadio.setText(partido.getEstadio());
            tvIdPartido.setText(partido.getIdPartido());

            tvSeleccion1.setText(partido.getSeleccion1());
            tvSeleccion2.setText(partido.getSeleccion2());


            configurarEstadoPartido(vistaPartido, partido);
            llPartidos.addView(vistaPartido);
        }
    }
}