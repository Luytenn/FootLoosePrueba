package com.prueba.footloose.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.prueba.footloose.MainActivity;
import com.prueba.footloose.R;
import com.prueba.footloose.databinding.ActivityLoginBinding;
import com.prueba.footloose.db.ConsultaUsuario;
import com.prueba.footloose.model.Usuario;
import com.prueba.footloose.utils.LoadingDialog;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private TextView btnRegistro;
    private Button btnLogin;
    private EditText numDocumento;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        btnRegistro = findViewById(R.id.btnregistro);
        btnLogin = findViewById(R.id.btnLogin);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistroActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarIngreso();
            }
        });

    }

    private void validarIngreso(){

        numDocumento = findViewById(R.id.usuario);
        password = findViewById(R.id.pass);

        ConsultaUsuario consultaUsuario = new ConsultaUsuario(this);

        
        List<Usuario> listaUsuario = consultaUsuario.mostrarUsuario();


        for (Usuario usuario:
             listaUsuario) {
            Log.d("Login", "LoginUsuario: " + usuario.getPassword() + " " + usuario.getNum_documento() + " " + usuario.getEmail());
        }


        final LoadingDialog loadingDialog = new LoadingDialog(this);


        loadingDialog.show();

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                boolean result = consultaUsuario.validarUsuarioPassword(numDocumento.getText().toString(),password.getText().toString());

                if (result){

                    loadingDialog.cancel();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("numDocumento", numDocumento.getText().toString());
                    intent.putExtra("password", password.getText().toString());
                    startActivity(intent);

                } else{
                    loadingDialog.cancel();

                    Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();

                }



            }
        };
        handler.postDelayed(runnable, 2000);





    }

}