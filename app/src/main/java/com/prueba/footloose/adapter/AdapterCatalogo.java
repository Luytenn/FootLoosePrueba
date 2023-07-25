package com.prueba.footloose.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;
import com.prueba.footloose.R;
import com.prueba.footloose.model.Producto;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdapterCatalogo extends RecyclerView.Adapter<AdapterCatalogo.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<Producto> model;
    ArrayList<Producto> listaOriginal;
    private ClickedItem clickedItem;


    //listener
    private View.OnClickListener listener;
    private Context context;

    public AdapterCatalogo(Context context, ArrayList<Producto> model, ClickedItem clickedItem){
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_producto, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Producto producto = model.get(position);

        String nombre_pro = producto.getNombre_prod();
        Double precio_pro = producto.getPrecio_prod();
        Double precio_des = producto.getPrecio_descuento();
        String codigo_pro = String.valueOf(producto.getCod_prod());
        String cantidadItem = String.valueOf(producto.getStock());
        String descuentoPorcentaje = String.valueOf(producto.getDesPorcentaje());
        int resourceId =producto.getResourceId();


        holder.nom_producto.setText(nombre_pro);
        holder.precioOriginal.setText(precio_pro.toString());
        holder.precioDescuento.setText(precio_des.toString());
        holder.id_producto.setText(codigo_pro);
        holder.stock_producto.setText(cantidadItem);
        holder.desc_porcentaje.setText(descuentoPorcentaje);
        holder.imageview.setImageResource(resourceId);

       /* Glide.with(context)
                .asBitmap()
                .load(producto.getUrlImg())
                .placeholder(R.drawable.zapatilla)
                .into(holder.imageview);*/

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
        TextView nom_producto ;
        TextView precioOriginal;
        TextView precioDescuento;
        TextView id_producto;
        TextView stock_producto;
        TextView desc_porcentaje;
        LinearLayout cardview;
        ImageView imageview;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);

            id_producto = itemView.findViewById(R.id.txt_cod_pro);
            nom_producto = itemView.findViewById(R.id.txt_nombreProducto);
            precioOriginal = itemView.findViewById(R.id.txt_precioOriginal);
            precioDescuento = itemView.findViewById(R.id.txt_descuentoPrecio);
            cardview = itemView.findViewById(R.id.producto_cardView);
            stock_producto = itemView.findViewById(R.id.txt_stockItem);
            desc_porcentaje = itemView.findViewById(R.id.txt_descuentoPorcentaje);
            imageview = itemView.findViewById(R.id.iv_productImage);


        }

    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    public interface ClickedItem{
        public void ClickedUser(Producto producto);
    }


}
