package com.pooespol.pronosticodepartidos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;



public class PronosticosActivity extends AppCompatActivity {
    private Spinner spFase;
    private LinearLayout llPartidos;
    private Button btVolver;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

btVolver.setOnClickListener(v -> {
    finish();
});
    }
}
