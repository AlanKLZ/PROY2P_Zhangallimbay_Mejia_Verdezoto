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

public class ActualizarPuntajesActivity extends AppCompatActivity {
    private Button buttonAPuntajes;
    private Button btnRegresar;

    private Administrador administrador;

    private ArrayList<Participante> participantes = new ArrayList<>();
    private ArrayList<Partido> partidos = new ArrayList<>();
    private ArrayList<Resultado> resultados = new ArrayList<>();
    private ArrayList<Pronostico> pronosticos = new ArrayList<>();

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

        pronosticos = ManejoArchivos.leerPronosticos(this);
        buttonAPuntajes.setOnClickListener(view -> {
            administrador.actualizarPuntajes(participantes,pronosticos,partidos,resultados);
            ManejoArchivos.guardarParticipantes(participantes, this);
            Toast.makeText(this, "Puntajes actualizados correctamente", Toast.LENGTH_SHORT).show();
        });

        btnRegresar.setOnClickListener(v -> {
            finish();});
    }


}