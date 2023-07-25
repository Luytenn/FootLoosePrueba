package com.prueba.footloose.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.paypal.android.sdk.db;
import com.paypal.pyplcheckout.data.model.pojo.Cart;
import com.prueba.footloose.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class ConsultaCart extends DbHelper{

    Context context;

    public ConsultaCart(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public Boolean insertarProducto(String nombre, double precio, double precioDescuento, int stock, int consultarPorcentaje, int resourceId) {

        DbHelper dbHelper = new DbHelper(context);


        SQLiteDatabase miBD = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom_prod", nombre);
        contentValues.put("precio_prod", precio);
        contentValues.put("precio_descuento", precioDescuento);
        contentValues.put("stock", stock);
        contentValues.put("desc_porcentaje", consultarPorcentaje);
        contentValues.put("resourceId", resourceId);

        long resultado = miBD.insert(TB_CART, null, contentValues);

        if (resultado == -1) {
            return false;
        } else {
            return true;
        }

    }

    public ArrayList<Producto> mostrarProductos() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<Producto> listaProducto = new ArrayList<>();
        Producto pro ;
        Cursor cursorProducto = null;

        cursorProducto = db.rawQuery( "SELECT * FROM " + TB_CART, null);

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

    public boolean updateProducto(int cod_prod,String nombre, double precio, double precio_descuento, int stock) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        try{
            db.execSQL("UPDATE " + TB_CART + " SET nom_prod = '" + nombre + "', precio_prod = '" + precio+ "', precio_descuento = '"+precio_descuento +"', stock = '"+stock+"' WHERE cod_prod= '"+ cod_prod +"' ");
            correcto = true;
        } catch( Exception ex) {
            ex.toString();
        } finally {
            db.close();
        }

        return correcto;

    }

    public Boolean validarProducto(int id_producto) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase miBD = dbHelper.getWritableDatabase();

        Cursor cursor = miBD.rawQuery("SELECT *FROM " + TB_CART + " WHERE  cod_prod = ?", new String[]{String.valueOf(id_producto)});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<Producto> findItemCartByCod(int id_producto){

        List<Producto> itemSelected = new ArrayList<>();

        //get data from the database

        String queryString = "SELECT *FROM " + TB_CART + " WHERE  cod_prod = " + id_producto;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int itemsSelected = cursor.getInt(4);
                String descripcion = cursor.getString(1);
                Double precio = cursor.getDouble(2);
                Double precioDescuento = cursor.getDouble(3);

                Producto m = new Producto(descripcion, precio,precioDescuento,itemsSelected);
                itemSelected.add(m);

            } while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return itemSelected;

    }
    
    public double totalCartItems() {

        List<Producto> listCart =  mostrarProductos();
        double totalPrecio = 0.0;

        if (listCart.size()>0){

            for (Producto cart: listCart) {
                totalPrecio += cart.getStock()*cart.getPrecio_descuento();
            }


        }
        return totalPrecio;

    }


    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TB_CART);
        db.close();
    }

}