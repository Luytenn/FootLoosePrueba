package com.prueba.footloose.ui.promDescuento;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prueba.footloose.R;
import com.prueba.footloose.adapter.AdapterCatalogo;
import com.prueba.footloose.adapter.AdapterPromDescuento;
import com.prueba.footloose.db.ConsultaCatalogo;
import com.prueba.footloose.model.Producto;
import com.prueba.footloose.ui.checkout.CheckoutFragment;

import java.util.ArrayList;

public class promoDescuentoFragment extends Fragment implements AdapterCatalogo.ClickedItem  {

    RecyclerView recyclerPromDes;
    AdapterPromDescuento adapterPromDes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_promo_descuento,container, false);

        recyclerPromDes = root.findViewById(R.id.recyclerViewPromDescuento);

        mostrarData();

        return root;

    }

    private void mostrarData() {



        ArrayList<Producto> listProdSQLITE = new ConsultaCatalogo(getContext()).mostrarProductos();

        recyclerPromDes.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterPromDes = new AdapterPromDescuento(getContext(), listProdSQLITE,this::ClickedUser);
        recyclerPromDes.setAdapter(adapterPromDes);

        adapterPromDes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ConsultaCatalogo producto = new ConsultaCatalogo(getContext());
                //producto.insertarProducto(adapterCart.,)

                //Intent intent = new Intent(getContext(), ReporteProdActivity.class);
                //startActivity(intent);

                Log.d("clickImagen", "click en el nombre works!" + producto.mostrarProductos().get(3).getCod_prod());

                /*
                double latitud = lisaPuntoVenta.get(recyclerpVenta.getChildAdapterPosition(v)).getLat();
                double longitud = lisaPuntoVenta.get(recyclerpVenta.getChildAdapterPosition(v)).getLng();
                Constantes.LATITUD = latitud;
                Constantes.LONGITUD = longitud;*/


                //Toast.makeText(getContext(), "Selecciono: " + Constantes.LATITUD, Toast.LENGTH_SHORT).show();


            }
        });


    }

    @Override
    public void ClickedUser(Producto producto) {

        //getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditPromDescFragment()).commit();

        EditPromDescFragment editPromDescFragment = new EditPromDescFragment();
        Bundle args =   new Bundle();
        args.putString("codProducto", String.valueOf(producto.getCod_prod()));
        args.putString("nomProducto", producto.getNombre_prod());
        args.putString("precioOriginal", String.valueOf(producto.getPrecio_prod()));
        args.putString("precioDescuento", String.valueOf(producto.getPrecio_descuento()));
        args.putString("stock", String.valueOf(producto.getStock()));
        args.putString("descPorcentaje", String.valueOf(producto.getDesPorcentaje()));
        editPromDescFragment.setArguments(args);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, editPromDescFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}