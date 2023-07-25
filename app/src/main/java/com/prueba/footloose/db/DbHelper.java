package com.prueba.footloose.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "footloose.db";
    public static final String TB_USUARIOS = "tb_usuarios";
    public static final String TB_PRODUCTOS = "tb_producto";
    public static final String TB_CART = "tb_cart";


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase mysqlLite) {

        //SIN LLAVES FORANEAS, NO HAY REPORTE QUE SOLICITE COMBINACIONES DE TABLAS

        mysqlLite.execSQL("CREATE TABLE " + TB_USUARIOS + "(cod_usu INTEGER PRIMARY KEY, num_documento Text, password Text," +
                "nombre Text not null, apellido Text not null, correo Text) ");

        mysqlLite.execSQL("CREATE TABLE " + TB_PRODUCTOS + " (cod_prod INTEGER PRIMARY KEY, nom_prod Text, precio_prod" +
                " double, precio_descuento double ,stock INTEGER, desc_porcentaje INTEGER, resourceId INTEGER) ");

        mysqlLite.execSQL("CREATE TABLE " + TB_CART + " (cod_prod INTEGER PRIMARY KEY, nom_prod Text, precio_prod" +
                " double, precio_descuento double ,stock INTEGER , desc_porcentaje INTEGER,resourceId INTEGER) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase mysqlLite, int oldVersion, int newVersion) {
        mysqlLite.execSQL("DROP TABLE " + TB_USUARIOS);
        mysqlLite.execSQL("DROP TABLE " + TB_PRODUCTOS);
        mysqlLite.execSQL("DROP TABLE " + TB_CART);
        onCreate(mysqlLite);
    }


}