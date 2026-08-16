package com.pooespol.pronosticodepartidos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuPrincipalParticipante extends AppCompatActivity {
    private TextView tVnombreParticipante;
    private Button btnTablaPosiciones;
    private Button btnPronosticos;
    private Button btnMisPronosticos;
    private Button btnSalirParticipante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_principal_participante);
        tVnombreParticipante = findViewById(R.id.tvnombreParticipante);
        btnTablaPosiciones = findViewById(R.id.btnTablaPosiciones);
        btnPronosticos = findViewById(R.id.btnPronósticos);
        btnMisPronosticos = findViewById(R.id.btnMisPronosticos);
        btnSalirParticipante = findViewById(R.id.btnSalirParticipante);
        String nombreParticipante = getIntent().getStringExtra("nombreCompleto");
        tVnombreParticipante.setText(nombreCompleto);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}