package com.prueba.footloose.ui.cart;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.prueba.footloose.MainActivity;
import com.prueba.footloose.R;
import com.prueba.footloose.adapter.AdapterCart;
import com.prueba.footloose.adapter.AdapterCatalogo;
import com.prueba.footloose.db.ConsultaCart;
import com.prueba.footloose.model.Producto;
import com.prueba.footloose.ui.catalogo.CatalogoFragment;
import com.prueba.footloose.ui.checkout.CheckoutFragment;

import java.util.ArrayList;


public class CartFragment extends Fragment {

    RecyclerView recyclerCart;
    AdapterCart adapterCart;
    Button btnCheckout;
    ImageView btnDelete;
    TextView totalAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cart,container, false);

        recyclerCart = root.findViewById(R.id.recyclerViewCart);
        btnCheckout = root.findViewById(R.id.btnCheckout);
        btnDelete = root.findViewById(R.id.btnDelete);

        mostrarData(root);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConsultaCart(getContext()).deleteAll();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new CatalogoFragment()).commit();
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new CheckoutFragment()).commit();
            }
        });

        return root;
    }

    private void mostrarData(View root) {

        ConsultaCart proCart = new ConsultaCart(getContext());
        ArrayList<Producto> productoLista =  proCart.mostrarProductos();


        totalAmount = root.findViewById(R.id.txt_totalAmount);

        totalAmount.setText(String.valueOf(proCart.totalCartItems()));

        /* DUMMY DATA*/
        /*ArrayList<Producto> producto = new ArrayList<Producto>();
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));*/

        recyclerCart.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterCart = new AdapterCart(getContext(), productoLista);
        recyclerCart.setAdapter(adapterCart);


        adapterCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ConsultaCart producto = new ConsultaCart(getContext());
                //producto.insertarProducto(adapterCart.,)

                //Intent intent = new Intent(getContext(), ReporteProdActivity.class);
                //startActivity(intent);

                Log.d("clickImagen", "click en el nombre works!");

                /*
                double latitud = lisaPuntoVenta.get(recyclerpVenta.getChildAdapterPosition(v)).getLat();
                double longitud = lisaPuntoVenta.get(recyclerpVenta.getChildAdapterPosition(v)).getLng();
                Constantes.LATITUD = latitud;
                Constantes.LONGITUD = longitud;*/


                //Toast.makeText(getContext(), "Selecciono: " + Constantes.LATITUD, Toast.LENGTH_SHORT).show();


            }
        });


    }

}