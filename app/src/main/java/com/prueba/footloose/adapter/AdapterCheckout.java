package com.prueba.footloose.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prueba.footloose.R;
import com.prueba.footloose.model.Producto;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterCheckout  extends RecyclerView.Adapter<AdapterCheckout.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<Producto> model;
    ArrayList<Producto> listaOriginal;

    //listener
    private View.OnClickListener listener;
    private Context context;

    public AdapterCheckout(Context context, ArrayList<Producto> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(model);
        this.context = context;

    }


    @Override
    public void onClick(View v) {

    }

    @NonNull
    @Override
    public AdapterCheckout.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_checkout, parent, false);
        view.setOnClickListener(this);
        return new AdapterCheckout.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCheckout.ViewHolder holder, int position) {
        String nombre_pro = model.get(position).getNombre_prod();
        Double precio_pro = model.get(position).getPrecio_prod();
        Double precio_des = model.get(position).getPrecio_descuento();
        String cantidadItem = String.valueOf(model.get(position).getStock());
        int imgResource = model.get(position).getResourceId();

        holder.nom_producto.setText(nombre_pro);
        holder.precioOriginal.setText(precio_pro.toString());
        holder.precioDescuento.setText(precio_des.toString());
        holder.cantidadItem.setText(cantidadItem);
        holder.imageView.setImageResource(imgResource);

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nom_producto ;
        TextView precioOriginal;
        TextView precioDescuento;
        TextView cantidadItem;
        ImageView imageView;


        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            nom_producto = itemView.findViewById(R.id.txt_nombreProducto);
            precioOriginal = itemView.findViewById(R.id.txt_precioOriginal);
            precioDescuento = itemView.findViewById(R.id.txt_descuentoPrecio);
            cantidadItem = itemView.findViewById(R.id.txt_cantidadItem);
            imageView = itemView.findViewById(R.id.iv_productImage);
        }

    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

}
