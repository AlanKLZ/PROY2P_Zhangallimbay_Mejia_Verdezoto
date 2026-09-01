package com.pooespol.pronosticodepartidos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.pooespol.pronosticodepartidos.modelo.ManejoArchivos;
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
    private NavigationView navigationView;
    private ArrayList<Participante> participantes = new ArrayList<>();
    private Participante actual;
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
        actual = (Participante) getIntent().getSerializableExtra("actual");
        //Configuracion del menu
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        //Instanciando el header
        View headerView = navigationView.getHeaderView(0);
        TextView nombreMenu = headerView.findViewById(R.id.nombreMenu);
        nombreMenu.setText(actual.getNombreCompleto());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tableClasificacion = findViewById(R.id.tableLayout);
        btnVolver = findViewById(R.id.btnVolver);

        actualizarParticipantes();
        cargarTabla(participantes);

        //Abre el menu a la izquierda
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.abrir_menu,R.string.cerar_menu);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        //Aqui se configura los items del menú
        navigationView.setNavigationItemSelectedListener(item -> {
       // Ya estamos en TablaClasificacionActivity
        if (item.getItemId() == R.id.navClasificacion) {
            drawerLayout.closeDrawers();
            return true;
        }
        // Ir a PronosticosActivity
        if (item.getItemId() == R.id.navPronostico) {
            Intent intent = new Intent(TablaClasificacionActivity.this,PronosticosActivity.class
        );

        intent.putExtra("actual", actual);

        startActivity(intent);
        return true;
    }

        // Ir a Mis Pronósticos
        // Pendiente: agregar navegación cuando se cree la Activity de Mis Pronósticos
        /*
        if (item.getItemId() == R.id.navPerfil) {
        // Aquí se abrirá la Activity de Mis Pronósticos :)
        return true;
}
*/    
      // Cerrar sesión
        if (item.getItemId() == R.id.navCerrarSesion) {
            Intent intent = new Intent(TablaClasificacionActivity.this, MainActivity.class
        );

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK
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
    @Override
    protected void onResume() {
        super.onResume();
        actualizarParticipantes();
        View headerView = navigationView.getHeaderView(0);

        TextView puntosMenu =
                headerView.findViewById(R.id.puntosMenu);

        puntosMenu.setText(
                "Puntos: " + actual.getPuntajeAcumulado()
        );
        cargarTabla(participantes);
    }

    //falta el javadoc
    private void actualizarParticipantes(){
        participantes.clear();
        ArrayList<Usuario> usuarios = ManejoArchivos.leerUsuarios(this);
        for(Usuario usuario: usuarios){
            if(usuario instanceof Participante){
                Participante participante = (Participante) usuario;
                participantes.add(participante);
                if(participante.getIdUsuario().equals(actual.getIdUsuario())){
                    actual=participante;
                }
            }
        }
    }
    /**
     *Crea filas con los datos de todos los participantes, ordenados por mayor numero
     * de puntos y alfabeticamente
     * @param participantes Lista de participantes a mostrar
     */
    private void cargarTabla(ArrayList<Participante> participantes) {
        // Elimina las filas anteriores, conservando el encabezado para actualizarlo en el onResume
        int cantidadFilas = tableClasificacion.getChildCount();

        if (cantidadFilas > 1) {
            tableClasificacion.removeViews(1, cantidadFilas - 1);
        }

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
