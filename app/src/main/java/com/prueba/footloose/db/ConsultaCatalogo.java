package com.prueba.footloose.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.prueba.footloose.model.Producto;

import java.util.ArrayList;

public class ConsultaCatalogo extends DbHelper {

    Context context;

    public ConsultaCatalogo(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public Boolean insertarProducto(String nombre, double precio, double precioDescuento, int stock, int desc_porcentaje, int resourceId) {

        DbHelper dbHelper = new DbHelper(context);


        SQLiteDatabase miBD = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom_prod", nombre);
        contentValues.put("precio_prod", precio);
        contentValues.put("precio_descuento", precioDescuento);
        contentValues.put("stock", stock);
        contentValues.put("desc_porcentaje", desc_porcentaje);
        contentValues.put("resourceId", resourceId);

        long resultado = miBD.insert(TB_PRODUCTOS, null, contentValues);

        if (resultado == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean updateProducto(int cod_prod,String nombre, double precio, double precio_descuento, int stock) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        try{
            db.execSQL("UPDATE " + TB_PRODUCTOS + " SET nom_prod = '" + nombre + "', precio_prod = '" + precio+ "', precio_descuento = '"+precio_descuento +"', stock = '"+stock+"' WHERE cod_prod= '"+ cod_prod +"' ");
            correcto = true;
        } catch( Exception ex) {
            ex.toString();
        } finally {
            db.close();
        }

        return correcto;

    }

    public boolean updateDescuentoProducto(int cod_prod, String nombre, Double precio, Double precio_descuento, int stock, int desPorcentaje ) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        try{
            db.execSQL("UPDATE " + TB_PRODUCTOS + " SET nom_prod = '" + nombre + "', precio_prod = '" + precio+ "', precio_descuento = '"+precio_descuento +"', stock = '"+stock+"', desc_porcentaje = '"+desPorcentaje+"'  WHERE cod_prod= '"+ cod_prod +"' ");
            correcto = true;
        } catch( Exception ex) {
            ex.toString();
        } finally {
            db.close();
        }

        return correcto;

    }

    public ArrayList<Producto> mostrarProductos() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<Producto> listaProducto = new ArrayList<>();
        Producto pro ;
        Cursor cursorProducto = null;

        cursorProducto = db.rawQuery( "SELECT * FROM " + TB_PRODUCTOS, null);

        if(cursorProducto.moveToFirst()) {
            do{
                pro = new Producto();
                pro.setCod_prod(cursorProducto.getInt(0));
                pro.setNombre_prod(cursorProducto.getString(1));
                pro.setPrecio_prod(cursorProducto.getDouble(2));
                pro.setPrecio_descuento(cursorProducto.getDouble(3));
                pro.setStock(cursorProducto.getInt(4));
                pro.setDesPorcentaje(cursorProducto.getInt(5));
                pro.setResourceId(cursorProducto.getInt(6));
                listaProducto.add(pro);

            }while(cursorProducto.moveToNext());
        }

        cursorProducto.close();

        return listaProducto;
    }



}
