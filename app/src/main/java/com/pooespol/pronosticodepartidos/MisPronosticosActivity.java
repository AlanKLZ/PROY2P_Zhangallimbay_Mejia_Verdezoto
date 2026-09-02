package com.pooespol.pronosticodepartidos;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pooespol.pronosticodepartidos.modelo.Fase;
import com.pooespol.pronosticodepartidos.modelo.ManejoArchivos;
import com.pooespol.pronosticodepartidos.modelo.Participante;
import com.pooespol.pronosticodepartidos.modelo.Partido;
import com.pooespol.pronosticodepartidos.modelo.Pronostico;
import com.pooespol.pronosticodepartidos.modelo.Resultado;

import java.util.ArrayList;
/**
 * Activity que permite al participante visualizar los pronósticos que ha registrado.
 * Muestra la fase del torneo, el partido, el pronóstico realizado, el resultado
 * oficial y los puntos obtenidos.
 * @author andreaverdezotolung
 */

public class MisPronosticosActivity extends AppCompatActivity {

    private LinearLayout llMisPronosticos;
    private Button buttonVolver;
    private Participante actual;

    private ArrayList<Pronostico> pronosticos = new ArrayList<>();
    private ArrayList<Partido> partidos = new ArrayList<>();

    /**
     * Inicializa la Activity, obtiene al participante que inició sesión,
     * carga sus pronósticos y los partidos registrados, y muestra la
     * información de los pronósticos en pantalla.
     *
     * @param savedInstanceState estado previamente guardado de la Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mis_pronosticos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        llMisPronosticos = findViewById(R.id.llMisPronosticos);
        buttonVolver = findViewById(R.id.buttonVolver);

        actual = (Participante)getIntent().getSerializableExtra("actual");
        pronosticos = ManejoArchivos.leerPronosticosParticipante(actual.getIdUsuario(), this);
        partidos = ManejoArchivos.leerPartidos(this);

        mostrarPronosticos(pronosticos);
        buttonVolver.setOnClickListener(view -> {finish();});
    }
    /**
     * Muestra en pantalla los pronósticos realizados por el participante.
     * Para cada pronóstico busca el partido correspondiente y presenta
     * su fase, selecciones, marcador pronosticado, resultado oficial
     * y puntos obtenidos.
     *
     * @param pronosticos lista de pronósticos que se mostrarán en pantalla
     */
    private void mostrarPronosticos(ArrayList<Pronostico>pronosticos){
        llMisPronosticos.removeAllViews();
        for(Pronostico pronostico: pronosticos){
            Partido partidoEncontrado = null;
            for(Partido partido: partidos){
                if(partido.getIdPartido().equals(pronostico.getIdPartido())){
                    partidoEncontrado = partido;
                }
            }
            if(partidoEncontrado != null){
                View vistaPronostico = getLayoutInflater().inflate(R.layout.item_mi_pronostico, llMisPronosticos, false);
                TextView tvFase = vistaPronostico.findViewById(R.id.tvFase);
                TextView tvPartido = vistaPronostico.findViewById(R.id.tvPartido);
                TextView tvPronostico = vistaPronostico.findViewById(R.id.tvPronostico);
                TextView tvResultado = vistaPronostico.findViewById(R.id.tvResultado);
                TextView tvPuntos = vistaPronostico.findViewById(R.id.tvPuntos);
                tvFase.setText(obtenerNombreFase(partidoEncontrado.getFaseTorneo()));
                tvPartido.setText(partidoEncontrado.getSeleccion1()+" vs "+partidoEncontrado.getSeleccion2());
                tvPronostico.setText("Tu pronóstico: "+ pronostico.getGolesSeleccion1() + " - "+ pronostico.getGolesSeleccion2());
                Resultado resultado= ManejoArchivos.obtenerResultado(partidoEncontrado.getIdPartido(), this);
                if(resultado != null){
                    tvResultado.setText("Resultado oficial: "+ resultado.getGolesSeleccion1() + " - "+ resultado.getGolesSeleccion2());
                }else{
                    tvResultado.setText("Resultado oficial: Pendiente");
                }
                if(pronostico.getPuntosObtenidos() == -1){
                    tvPuntos.setText("Puntos obtenidos: Pendiente");
                }else{
                    tvPuntos.setText("Puntos obtenidos: "+ pronostico.getPuntosObtenidos());
                }
                llMisPronosticos.addView(vistaPronostico);
            }
        }
    }
    /**
     * Obtiene el nombre de una fase del torneo en un formato adecuado
     * para mostrarlo al usuario.
     *
     * @param fase fase del torneo que se desea representar
     * @return nombre de la fase en formato legible
     */

    private String obtenerNombreFase(Fase fase) {

        if (fase == Fase.FASE_DE_GRUPOS) {
            return "Fase de grupos";

        } else if (fase == Fase.DIECISEISAVOS_DE_FINAL) {
            return "Dieciseisavos de final";

        } else if (fase == Fase.OCTAVOS_DE_FINAL) {
            return "Octavos de final";

        } else if (fase == Fase.CUARTOS_DE_FINAL) {
            return "Cuartos de final";

        } else if (fase == Fase.SEMIFINALES) {
            return "Semifinales";

        } else if (fase == Fase.TERCER_LUGAR) {
            return "Tercer lugar";

        } else {
            return "Final";
        }
    }
}