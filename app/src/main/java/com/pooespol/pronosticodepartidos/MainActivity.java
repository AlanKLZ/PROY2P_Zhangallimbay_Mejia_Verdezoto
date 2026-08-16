package com.pooespol.pronosticodepartidos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.pooespol.pronosticodepartidos.modelo.CredencialesInvalidasException;
import com.pooespol.pronosticodepartidos.modelo.ManejoArchivosUsuario;
import com.pooespol.pronosticodepartidos.modelo.TipoUsuario;
import com.pooespol.pronosticodepartidos.modelo.Usuario;

import java.util.ArrayList;

/**
 * Activity Principal encargada del inicio de sesión de la aplicación
 * Carga los usuarios registrados, valida credenciales
 * Dirige al usuario al menú correspondiente según su tipo
 */
public class MainActivity extends AppCompatActivity {
    private ArrayList<Usuario> usuarios;
    private TextInputEditText campoUsuario;
    private TextInputEditText campoContraseña;
    private Button btnIniciarSesion;

    /**
     * Inicializa la Activity, conecta los componentes de la interfaz,
     * carga los usuarios registrados y configura el evento de del botón
     * de inicio de sesión
     * @param savedInstanceState estado previamente guardado de la Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        campoUsuario = findViewById(R.id.editTextUsuario);
        campoContraseña = findViewById(R.id.EditTextContraseña);
        btnIniciarSesion = findViewById(R.id.btnInicioSesion);
        btnIniciarSesion.setOnClickListener(v-> iniciarSesion());

        usuarios = ManejoArchivosUsuario.leerUsuarios(this);
    }

    /**
     * Verifica si el nombre de Usuario y la contraseña ingresados coinciden
     * con las credenciales de algún usuario registrado
     * @param nombreUsuario nombre de Usuario ingresado para iniciar sesión
     * @param contraseña contraseña ingresada para iniciar sesión
     * @return usuario cuyas credenciales coinciden con las ingresadas
     * @throws CredencialesInvalidasException excepción si el usuario o la contraseña
     * son incorrectos
     */
    private Usuario autenticar(String nombreUsuario, String contraseña) throws CredencialesInvalidasException {
        for (Usuario usuario: usuarios){
            if (usuario.getNombreDeUsuario().equals(nombreUsuario) && usuario.getContraseña().equals(contraseña)) {
                return usuario;
            }
        }
        throw new CredencialesInvalidasException("El usuario o la contraseña son incorrectos");
    }

    /**
     * Recupera las credenciales ingresadas en la interfaz e
     * intenta autenticar al usuario.
     * Si las credenciales son correctas, abre el menú correspondiente
     * al tipo de usuario. Si son incorrectas, muestra el mensaje
     * de la excepción.
     */
    private void iniciarSesion(){
        String nombreUsuario = campoUsuario.getText().toString();
        String contraseña = campoContraseña.getText().toString();
        try{
            Usuario usuarioAutenticado = autenticar(nombreUsuario, contraseña);
            if (usuarioAutenticado.getTipoUsuario() == TipoUsuario.PARTICIPANTE){
                Intent intent = new Intent(MainActivity.this, MenuPrincipalParticipante.class);
                intent.putExtra("idUsuario", usuarioAutenticado.getIdUsuario());
                intent.putExtra("nombreCompleto", usuarioAutenticado.getNombreCompleto());
                intent.putExtra("usuarios",usuarios);
                startActivity(intent);
            }else if(usuarioAutenticado.getTipoUsuario() == TipoUsuario.ADMINISTRADOR){
                Intent intent = new Intent(MainActivity.this, MenuPrincipalAdministrador.class);
                intent.putExtra("idUsuario", usuarioAutenticado.getIdUsuario());
                intent.putExtra("nombreCompleto", usuarioAutenticado.getNombreCompleto());
                startActivity(intent);

            }
        }catch (CredencialesInvalidasException c){
            Toast.makeText(MainActivity.this, c.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

}