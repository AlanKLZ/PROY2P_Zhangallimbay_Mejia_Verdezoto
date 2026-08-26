package com.pooespol.pronosticodepartidos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.pooespol.pronosticodepartidos.modelo.Participante;
import com.pooespol.pronosticodepartidos.modelo.Usuario;

import java.util.ArrayList;


/**
 * Activity para la tabla de posiciones
 */
public class TablaClasificacionActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TableLayout tableClasificacion;
    private Button btnVolver;
    private ArrayList<Participante> participantes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tabla_clasificacion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tableClasificacion = findViewById(R.id.tableLayout);
        btnVolver = findViewById(R.id.btnVolver);

        ArrayList<Usuario> usuarios = (ArrayList<Usuario>)getIntent().getSerializableExtra("usuarios");
        for (Usuario usuario : usuarios) {

            if (usuario instanceof Participante) {
                participantes.add((Participante) usuario);
            }
        }
        cargarTabla(participantes);
        //Abre el menu a la izquierda
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.abrir_menu,R.string.cerar_menu);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
        //Termina la activity actual y regresa al menu principal
        btnVolver.setOnClickListener(v->{
            finish();
        });
    }

    /**
     *Crea filas con los datos de todos los participantes, ordenados por mayor numero
     * de puntos y alfabeticamente
     * @param participantes Lista de participantes a mostrar
     */
    private void cargarTabla(ArrayList<Participante> participantes) {

        participantes.sort(null);

        int posicion = 1;

        for (Participante participante : participantes) {

            TableRow fila = new TableRow(this);

            TextView tvPosicion = new TextView(this);
            TextView tvParticipante = new TextView(this);
            TextView tvPuntaje = new TextView(this);

            tvPosicion.setText(String.valueOf(posicion));
            tvParticipante.setText(participante.getNombreCompleto());
            tvPuntaje.setText(
                    String.valueOf(participante.getPuntajeAcumulado())
            );

            fila.addView(tvPosicion);
            fila.addView(tvParticipante);
            fila.addView(tvPuntaje);

            tableClasificacion.addView(fila);

            posicion++;
        }
    }
}