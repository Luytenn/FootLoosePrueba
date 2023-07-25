package com.prueba.footloose.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prueba.footloose.R;
import com.prueba.footloose.model.Producto;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterPromDescuento extends RecyclerView.Adapter<AdapterPromDescuento.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<Producto> model;
    ArrayList<Producto> listaOriginal;
    private AdapterCatalogo.ClickedItem clickedItem;


    //listener
    private View.OnClickListener listener;
    private Context context;

    public AdapterPromDescuento(Context context, ArrayList<Producto> model, AdapterCatalogo.ClickedItem clickedItem){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        this.clickedItem = clickedItem;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(model);
        this.context = context;

    }



    @Override
    public void onClick(View v) {

    }

    @NonNull
    @Override
    public AdapterPromDescuento.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_producto, parent, false);
        view.setOnClickListener(this);
        return new AdapterPromDescuento.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPromDescuento.ViewHolder holder, int position) {

        Producto producto = model.get(position);

        String cod_pro = String.valueOf(producto.getCod_prod());
        String nombre_pro = producto.getNombre_prod();
        Double precio_pro = producto.getPrecio_prod();
        Double precio_des = producto.getPrecio_descuento();
        String codigo_pro = String.valueOf(producto.getCod_prod());
        String cantidadItem = String.valueOf(producto.getStock());
        String desporcentaje = String.valueOf(producto.getDesPorcentaje());
        int idResource = producto.getResourceId();


        holder.txt_cod_pro.setText(cod_pro);
        holder.nom_producto.setText(nombre_pro);
        holder.precioOriginal.setText(precio_pro.toString());
        holder.precioDescuento.setText(precio_des.toString());
        holder.id_producto.setText(codigo_pro);
        holder.stock_producto.setText(cantidadItem);
        holder.desPorcentaje.setText(desporcentaje);
        holder.imageView.setImageResource(idResource);

        holder.cardview.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Log.d("Apdater", "Ingreso onClick");
                clickedItem.ClickedUser(producto);
            }
        });


    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_cod_pro;
        TextView nom_producto ;
        TextView precioOriginal;
        TextView precioDescuento;
        TextView id_producto;
        TextView stock_producto;
        TextView desPorcentaje;
        LinearLayout cardview;
        ImageView imageView;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);

            id_producto = itemView.findViewById(R.id.txt_cod_pro);
            nom_producto = itemView.findViewById(R.id.txt_nombreProducto);
            precioOriginal = itemView.findViewById(R.id.txt_precioOriginal);
            precioDescuento = itemView.findViewById(R.id.txt_descuentoPrecio);
            cardview = itemView.findViewById(R.id.producto_cardView);
            stock_producto = itemView.findViewById(R.id.txt_stockItem);
            txt_cod_pro = itemView.findViewById(R.id.txt_cod_pro);
            desPorcentaje = itemView.findViewById(R.id.txt_descuentoPorcentaje);
            imageView = itemView.findViewById(R.id.iv_productImage);


        }

    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    public interface ClickedItem{
        public void ClickedUser(Producto producto);
    }

}
