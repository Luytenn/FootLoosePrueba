package com.prueba.footloose.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.prueba.footloose.MainActivity;
import com.prueba.footloose.R;
import com.prueba.footloose.databinding.ActivityLoginBinding;
import com.prueba.footloose.db.ConsultaUsuario;
import com.prueba.footloose.utils.LoadingDialog;

public class RegistroActivity extends AppCompatActivity {

    Button btnVolverLogin;
    Button btnRegistro;
    EditText num_documento;
    EditText nombre;
    EditText apellido;
    EditText correo;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activitiy_registro);
        btnVolverLogin = findViewById(R.id.btnVolverIngreso);
        btnRegistro = findViewById(R.id.btnregistro);

        btnVolverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registroUser();

            }
        });

    }

    private void registroUser(){

        ConsultaUsuario consultaUsuario = new ConsultaUsuario(this);


        num_documento = findViewById(R.id.txtDNI);
        correo = findViewById(R.id.txtEmail);
        nombre = findViewById(R.id.txtNombre);
        apellido = findViewById(R.id.txtApellido);
        password = findViewById(R.id.txtPassword);



        if (num_documento.getText().toString().isEmpty() || correo.getText().toString().isEmpty() || nombre.getText().toString().isEmpty() ||
                apellido.getText().toString().isEmpty() || password.getText().toString().isEmpty()){

            Toast.makeText(this, "Debe ingresar todos los campos", Toast.LENGTH_LONG).show();

        }else{

            final LoadingDialog loadingDialog = new LoadingDialog(this);


            loadingDialog.show();

            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {

                    consultaUsuario.insertarUsuario(num_documento.getText().toString(), password.getText().toString(),
                            nombre.getText().toString(), apellido.getText().toString(), correo.getText().toString());

                    loadingDialog.cancel();

                    Toast.makeText(RegistroActivity.this, "Usuario creado!", Toast.LENGTH_LONG).show();

                    num_documento.setText("");
                    nombre.setText("");
                    apellido.setText("");
                    correo.setText("");
                    password.setText("");

                }
            };
            handler.postDelayed(runnable, 2000);



        }






    }

}