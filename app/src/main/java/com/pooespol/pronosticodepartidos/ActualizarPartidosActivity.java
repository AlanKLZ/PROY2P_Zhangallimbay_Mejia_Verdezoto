package com.pooespol.pronosticodepartidos;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.pooespol.pronosticodepartidos.modelo.Participante;
import com.pooespol.pronosticodepartidos.modelo.Partido;
import com.pooespol.pronosticodepartidos.modelo.PronosticoFueraDeTiempoException;
import com.pooespol.pronosticodepartidos.modelo.Resultado;
import com.pooespol.pronosticodepartidos.modelo.Usuario;

import java.util.ArrayList;

public class ActualizarPartidosActivity extends AppCompatActivity {
    private Spinner spFase;
    private Administrador actual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actualizar_partidos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Asigando views
        actual = (Administrador) getIntent().getSerializableExtra("actual");
        spFase = findViewById(R.id.spFase);
        LinearLayout linearLayout = findViewById(R.id.llPartidos);
        Button btnVolver = findViewById(R.id.btnVolver);

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
        //Termina la activity actual y regresa al menu principal
        btnVolver.setOnClickListener(v->{
            finish();
        });
    }
    private void configurarEstadoPartido(View vistaPartido, Partido partido) {

        TextView tvEstado = vistaPartido.findViewById(R.id.tvEstado);
        LinearLayout llMarcadorEditable = vistaPartido.findViewById(R.id.llMarcadorEditable);
        LinearLayout llMarcadorFinal = vistaPartido.findViewById(R.id.llMarcadorFinal);
        EditText etGol1 = vistaPartido.findViewById(R.id.etGol1);
        EditText etGol2 = vistaPartido.findViewById(R.id.etGol2);
        TextView tvGol1 = vistaPartido.findViewById(R.id.tvGol1);
        TextView tvGol2 = vistaPartido.findViewById(R.id.tvGol2);
        Button btnAccion = vistaPartido.findViewById(R.id.btnAccion);

        String idResultado = "000"; //Pendiente

        switch (partido.getEstadoPartido()) {

            case ABIERTO:
                tvEstado.setText("ABIERTO");
                tvEstado.setTextColor(Color.parseColor("#388E3C"));

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
                    configurarEstadoPartido(vistaPartido, partido);
                });

                break;

            case CERRADO:
                tvEstado.setText("CERRADO");
                tvEstado.setTextColor(Color.parseColor("#F57C00"));

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
                                idResultado,
                                partido.getIdPartido(),
                                gol1,
                                gol2
                        );

                        //registrarResultado(resultado); //Con ManejoArchivos(pendiente)

                        partido.setEstadoPartido(EstadoPartido.FINALIZADO);
                        configurarEstadoPartido(vistaPartido, partido);

                    } catch (DatosIncompletosException | PronosticoFueraDeTiempoException e) {
                        Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case FINALIZADO:
                tvEstado.setText("FINALIZADO");
                tvEstado.setTextColor(Color.parseColor("#D32F2F"));

                llMarcadorEditable.setVisibility(View.GONE);
                llMarcadorFinal.setVisibility(View.VISIBLE);

                Resultado resultado = obtenerResultado(
                        partido.getIdPartido()
                );

                if (resultado != null) {
                    tvGol1.setText(String.valueOf(resultado.getGolesSeleccion1()));
                    tvGol2.setText(String.valueOf(resultado.getGolesSeleccion2()));
                }

                btnAccion.setVisibility(View.GONE);
                break;
        }
    }
    public Resultado obtenerResultado(String idPartido){
        ArrayList<Resultado> resultados = new ArrayList<>();
        //resultados = ManejoArchivos metodo pendiente
        for (Resultado r:resultados){
            if(idPartido.equals(r.getIdPartido())){
                return r;
            }
        }
        return null;
    }
}