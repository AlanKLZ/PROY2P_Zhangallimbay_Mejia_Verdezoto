package com.pooespol.pronosticodepartidos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class TablaClasificacionActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ImageButton btnMenu;
    private TableLayout tableLayout;
    private Button btnVolver;
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
        btnMenu = findViewById(R.id.btnMenu);
        tableLayout = findViewById(R.id.tableLayout);
        btnVolver = findViewById(R.id.btnVolver);

        //Abre el menu a la izquierda
        btnMenu.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });
        //Termina la activity actual y regresa al menu principal
        btnVolver.setOnClickListener(v->{
            finish();
        });
    }
}