package com.prueba.footloose.ui.catalogo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.prueba.footloose.R;
import com.prueba.footloose.adapter.AdapterCatalogo;
import com.prueba.footloose.db.ConsultaCart;
import com.prueba.footloose.db.ConsultaCatalogo;
import com.prueba.footloose.model.Producto;
import com.prueba.footloose.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class CatalogoFragment extends Fragment implements AdapterCatalogo.ClickedItem  {

    RecyclerView recyclerCatalogo;
    AdapterCatalogo adapterCatalogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_catalogo,container, false);

        recyclerCatalogo = root.findViewById(R.id.recyclerViewCatalogo);

        mostrarData();

        return root;

    }

    private void mostrarData() {



        ArrayList<Producto> producto = new ArrayList<Producto>();

        /*producto.add(new Producto("zapatilla Nike 1", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike 2", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike 3", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike 4", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));
        producto.add(new Producto("zapatilla Nike", 100, 150, 50));
        */

        if (new ConsultaCatalogo(getContext()).mostrarProductos().size() <= 0){

            new ConsultaCatalogo(getContext()).insertarProducto("Dunk SB LOW 1", 100, 10, 1,90, R.drawable.nike2);
            new ConsultaCatalogo(getContext()).insertarProducto("Air yeezy low 2", 100, 50, 1,50, R.drawable.nike3);
            new ConsultaCatalogo(getContext()).insertarProducto("Air force 1 low 3", 100, 90, 2,10, R.drawable.nike4);
            new ConsultaCatalogo(getContext()).insertarProducto("jordan 13 retro 4", 321, 282.48, 5,12, R.drawable.nike5);
            new ConsultaCatalogo(getContext()).insertarProducto("Mag bttf 5", 800, 760, 10,5, R.drawable.nike6);
            new ConsultaCatalogo(getContext()).insertarProducto("Foamposite one 6", 1500, 750, 5,50, R.drawable.nike7);

        }

        ArrayList<Producto> listProdSQLITE = new ConsultaCatalogo(getContext()).mostrarProductos();

        recyclerCatalogo.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterCatalogo = new AdapterCatalogo(getContext(), listProdSQLITE,this::ClickedUser);
        recyclerCatalogo.setAdapter(adapterCatalogo);


        adapterCatalogo.setOnClickListener(new View.OnClickListener() {

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

        final LoadingDialog loadingDialog = new LoadingDialog(getActivity());

        loadingDialog.show();

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                ConsultaCart consultaCart = new ConsultaCart(getContext());


                if (consultaCart.validarProducto(producto.getCod_prod())){

                    boolean result = reduceItemStock(producto);

                    if (result){
                        List<Producto> firstProduct = consultaCart.findItemCartByCod(producto.getCod_prod());

                        int canItems = firstProduct.get(0).getStock();
                        canItems++;

                        consultaCart.updateProducto(producto.getCod_prod(),producto.getNombre_prod(), producto.getPrecio_prod(), producto.getPrecio_descuento(),canItems);

                        Toast.makeText(getContext(),"Item agregado al carrito", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(),"No hay Stock", Toast.LENGTH_LONG).show();
                    }


                    adapterCatalogo.notifyDataSetChanged();

                }else {

                    String nombreProducto = producto.getNombre_prod();


                    ConsultaCart proCart = new ConsultaCart(getContext());
                    proCart.insertarProducto(producto.getNombre_prod(), producto.getPrecio_prod(), producto.getPrecio_descuento(), 1, producto.getDesPorcentaje(), producto.getResourceId());

                    ArrayList<Producto> proList=  proCart.mostrarProductos();

                    Toast.makeText(getContext(),"Item agregado al carrito", Toast.LENGTH_LONG).show();

           /* if ( proList != null){
                Toast.makeText(getContext(), "CatalogoFragment: " + proList.get(0).getCod_prod(), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "Lista nulo", Toast.LENGTH_SHORT).show();
            }*/

                }


                loadingDialog.cancel();
            }
        };
        handler.postDelayed(runnable, 3000);


    }

    private boolean reduceItemStock(Producto producto) {

        ConsultaCatalogo consultaCatalogo = new ConsultaCatalogo(getContext());

        if (producto.getStock() > 0) {
            int canItems = producto.getStock();
            canItems--;
            consultaCatalogo.updateProducto(producto.getCod_prod(), producto.getNombre_prod(), producto.getPrecio_prod(), producto.getPrecio_descuento(), canItems);
            return true;
        } else {
            return false;
        }


    }

}