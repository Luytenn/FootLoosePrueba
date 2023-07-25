package com.prueba.footloose;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.prueba.footloose.db.ConsultaUsuario;
import com.prueba.footloose.model.Usuario;
import com.prueba.footloose.ui.cart.CartFragment;
import com.prueba.footloose.ui.catalogo.CatalogoFragment;
import com.prueba.footloose.ui.checkout.CheckoutFragment;
import com.prueba.footloose.ui.login.LoginActivity;
import com.prueba.footloose.ui.promDescuento.promoDescuentoFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ListView navigationListView;
    ImageView burgerIcon;
    ImageView cartIcon;
    private TextView nombre;
    private TextView apellido;
    private TextView correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        burgerIcon = findViewById(R.id.iv_burgerIcon);
        cartIcon = findViewById(R.id.iv_cartIcon);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CatalogoFragment()).commit();
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open,
        //R.string.close);
        //drawerLayout.addDrawerListener(toggle);
        //toggle.syncState();

        Bundle bundle = getIntent().getExtras();

        int numDocumentobundle = Integer.parseInt(bundle.getString("numDocumento"));
        String numPass =    bundle.getString("password");

        setUserData(numDocumentobundle, numPass);

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CartFragment()).commit();
            }
        });

        burgerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.barra:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_catalogo:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CatalogoFragment()).commit();
                break;

            //case R.id.nav_cart:
             //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CartFragment()).commit();
              //  break;
            //case R.id.nav_checkout:
               // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CheckoutFragment()).commit();
                //break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new promoDescuentoFragment()).commit();
                break;
            case R.id.nav_salir:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setUserData(int numDoc, String password) {

        ConsultaUsuario consultaUsuario = new ConsultaUsuario(this);

        NavigationView navView  = (NavigationView)findViewById(R.id.nav_view);
        View headerView = navView.getHeaderView(0);

        nombre =  headerView.findViewById(R.id.txtNombre);
        apellido = headerView.findViewById(R.id.txtApellido);
        correo = headerView.findViewById(R.id.txtEmail);

        List<Usuario> usuarioFirst =  consultaUsuario.findUserByNumDoc(numDoc,password);

        nombre.setText(usuarioFirst.get(0).getNombre());
        apellido.setText(usuarioFirst.get(0).getApellido());
        correo.setText(usuarioFirst.get(0).getEmail());



    }

}