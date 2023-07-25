package com.prueba.footloose.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.prueba.footloose.model.Producto;
import com.prueba.footloose.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ConsultaUsuario extends DbHelper{

    Context context;

    public ConsultaUsuario(@Nullable Context context) {
        super(context);
        this.context =  context;
    }
    public Boolean insertarUsuario(String num_documento, String password, String nombre, String apellido, String correo) {

        DbHelper dbHelper = new DbHelper(context);

        SQLiteDatabase miBD = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("num_documento", num_documento);
        contentValues.put("password", password);
        contentValues.put("nombre", nombre);
        contentValues.put("apellido", apellido);
        contentValues.put("correo", correo);

        long resultado = miBD.insert(TB_USUARIOS, null, contentValues);

        if (resultado == -1) {
            return false;
        } else {
            return true;
        }

    }

    public Boolean validarUsuario(String num_documento) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase miBD = dbHelper.getWritableDatabase();

        Cursor cursor = miBD.rawQuery("SELECT *FROM " + TB_USUARIOS + " WHERE  num_documento = ?", new String[]{num_documento});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean validarUsuarioPassword(String num_documento, String password) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase miBD = dbHelper.getWritableDatabase();

        Cursor cursor = miBD.rawQuery("SELECT *FROM " + TB_USUARIOS +  " WHERE  num_documento   = ?  AND password = ?", new String[]{num_documento, password});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public ArrayList<Usuario> mostrarUsuario() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase miBD = dbHelper.getWritableDatabase();

        ArrayList<Usuario> listaUsuario = new ArrayList<>();
        Usuario usu = null;
        Cursor cursorUsuarios = null;

        cursorUsuarios = miBD.rawQuery("SELECT * FROM " + TB_USUARIOS, null);

        if (cursorUsuarios.moveToFirst()) {
            do {
                usu = new Usuario();
                usu.setNumDocumento(cursorUsuarios.getString(1));
                usu.setPassword(cursorUsuarios.getString(2));
                usu.setNombre(cursorUsuarios.getString(3));
                usu.setApellido(cursorUsuarios.getString(4));
                usu.setEmail(cursorUsuarios.getString(5));
                listaUsuario.add(usu);

            } while (cursorUsuarios.moveToNext());
        }

        cursorUsuarios.close();

        return listaUsuario;
    }

    public List<Usuario> findUserByNumDoc(int num_documento, String password){

        List<Usuario> userSelected = new ArrayList<>();

        //get data from the database

        String queryString = "SELECT *FROM " + TB_USUARIOS + " WHERE  num_documento = " + num_documento + " AND password = " + password;



        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                String numDocumento = cursor.getString(1);
                String nombre = cursor.getString(3);
                String apellido = cursor.getString(4);
                String correo = cursor.getString(5);

                Usuario m = new Usuario(numDocumento,nombre, apellido,correo);
                userSelected.add(m);

            } while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return userSelected;

    }

}
