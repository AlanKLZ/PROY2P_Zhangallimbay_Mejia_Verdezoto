package com.pooespol.pronosticodepartidos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pooespol.pronosticodepartidos.modelo.Administrador;
import com.pooespol.pronosticodepartidos.modelo.ManejoArchivos;
import com.pooespol.pronosticodepartidos.modelo.Participante;
import com.pooespol.pronosticodepartidos.modelo.Partido;
import com.pooespol.pronosticodepartidos.modelo.Pronostico;
import com.pooespol.pronosticodepartidos.modelo.Resultado;
import com.pooespol.pronosticodepartidos.modelo.Usuario;

import java.util.ArrayList;

/**
 * Activity encargada de actualizar puntajes acumulados
 * de los participantes.
 * Carga los participantes, partidos, resultados y
 * pronósticos registrados. Al seleccionar la opción actualizar,
 * solicita al administrador el recálculo de los puntajes y
 * almacena los valores obtenidos en participantes.txt
 *
 * También permite regresar al menú principal del administrador
 *
 * @author andreaverdezotolung
 */

public class ActualizarPuntajesActivity extends AppCompatActivity {
    private Button buttonAPuntajes;
    private Button btnRegresar;

    private Administrador administrador;

    private ArrayList<Participante> participantes = new ArrayList<>();
    private ArrayList<Partido> partidos = new ArrayList<>();
    private ArrayList<Resultado> resultados = new ArrayList<>();
    private ArrayList<Pronostico> pronosticos = new ArrayList<>();

    /**
     * Inicializa la pantalla de actualización de puntajes,
     * carga la información necesaria y configura las acciones
     * de los botones de actualizar y regresar.
     *
     * @param savedInstanceState estado previamente guardado de la Activity
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actualizar_puntajes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        buttonAPuntajes = findViewById(R.id.buttonAPuntajes);
        btnRegresar = findViewById(R.id.btnRegresar);
        administrador = (Administrador) getIntent().getSerializableExtra("actual");
        // Cargar usuarios
        ArrayList<Usuario> usuarios = ManejoArchivos.leerUsuarios(this);

        // Obtener únicamente los participantes
        for (Usuario usuario : usuarios) {
            if (usuario instanceof Participante) {
                participantes.add((Participante) usuario);
            }
        }

        // Cargar los demás datos necesarios
        partidos = ManejoArchivos.leerPartidos(this);

        resultados = ManejoArchivos.leerResultados(this);

        pronosticos = ManejoArchivos.leerTodosPronosticos(this);
        buttonAPuntajes.setOnClickListener(view -> {
            administrador.actualizarPuntajes(participantes,pronosticos,partidos,resultados);
            for (Pronostico pronostico : pronosticos) {
                for (Partido partido : partidos) {
                    if (partido.getIdPartido().equals(pronostico.getIdPartido())) {
                        ManejoArchivos.registrarPronostico(pronostico, partido.getFaseTorneo(), this);
                    }
                }
            }
            ManejoArchivos.guardarParticipantes(participantes, this);
            Toast.makeText(this, "Puntajes actualizados correctamente", Toast.LENGTH_SHORT).show();
        });

        btnRegresar.setOnClickListener(v -> {
            finish();});
    }


}