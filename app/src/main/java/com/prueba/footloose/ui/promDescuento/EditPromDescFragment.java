package com.prueba.footloose.ui.promDescuento;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.prueba.footloose.R;
import com.prueba.footloose.adapter.AdapterCatalogo;
import com.prueba.footloose.db.ConsultaCatalogo;
import com.prueba.footloose.model.Producto;
import com.prueba.footloose.utils.LoadingDialog;

public class EditPromDescFragment extends Fragment  {

    EditText descuentoPorc;
    EditText precioDescuento;
    EditText precioOriginal;
    EditText nombreProducto;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_edit_prom_desc,container, false);

        descuentoPorc = root.findViewById(R.id.txtDescuentoPorcentaje);
        precioDescuento = root.findViewById(R.id.txtPrecioDescuento);
        precioOriginal = root.findViewById(R.id.txtPrecioOriginal);
        button = root.findViewById(R.id.btnActualizarDescuento);
        nombreProducto = root.findViewById(R.id.txtNombreProducto);

        String codProducto = getArguments().getString("codProducto");
        String nomProducto = getArguments().getString("nomProducto");
        String preOriginal = getArguments().getString("precioOriginal");
        String preDescuento = getArguments().getString("precioDescuento");
        String stock = getArguments().getString("stock");
        String desPorcentaje = getArguments().getString("descPorcentaje");


        nombreProducto.setText(nomProducto);
        precioOriginal.setText(preOriginal);
        precioDescuento.setText(preDescuento);
        descuentoPorc.setText(desPorcentaje);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LoadingDialog loadingDialog = new LoadingDialog(getActivity());


                loadingDialog.show();

                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                        //Double.parseDouble(precioDescuento.getText().toString()) >=0 && Double.parseDouble(precioDescuento.getText().toString()) <=100

                        if (precioDescuento.getText().toString().isEmpty()){

                            Toast.makeText(getContext(), "Ingrese un porcentaje correcto", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Actualizo correctamente", Toast.LENGTH_LONG).show();

                            new ConsultaCatalogo(getContext()).updateDescuentoProducto(Integer.parseInt(codProducto), nomProducto,
                                    Double.parseDouble(preOriginal), Double.parseDouble(precioDescuento.getText().toString()), Integer.parseInt(stock),Integer.parseInt(descuentoPorc.getText().toString()));

                        }

                        loadingDialog.cancel();

                    }
                };
                handler.postDelayed(runnable, 3000);



            }
        });

        descuentoPorc.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {   //Convert the Text to String

                try {
                    if (descuentoPorc.getText()!=null){



                        double resultado = descuentoProducto(Integer.parseInt(String.valueOf(descuentoPorc.getText())), Double.parseDouble(precioOriginal.getText().toString()));

                        precioDescuento.setText(String.valueOf(resultado));

                    }
                }catch (Exception e){
                }




            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Does not do any thing in this case
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Does not do any thing in this case
            }
        });


        return root;

    }

    private double descuentoProducto(int porcentaje, double precioOriginal) {


        double porc = (double) porcentaje/100;

        double precioDescuento = precioOriginal - (porc*precioOriginal);

        return precioDescuento;

    }



}