package com.prueba.footloose.model;

public class Producto {

    private int cod_prod;
    private String nombre_prod;
    private double precio_prod;
    private double precio_descuento;
    private int stock;
    private int descuentoPorcentaje;
    private int resourceId;





    public Producto(){

    }

    public Producto( String nombre_prod, double precio_prod, double precio_prod_mayor, int stock) {

        this.nombre_prod = nombre_prod;
        this.precio_prod = precio_prod;
        this.precio_descuento = precio_prod_mayor;
        this.stock = stock;
    }

    public Producto( String nombre_prod, double precio_prod, double precio_prod_mayor, int stock, int descuentoPorcentaje) {

        this.nombre_prod = nombre_prod;
        this.precio_prod = precio_prod;
        this.precio_descuento = precio_prod_mayor;
        this.stock = stock;
        this.descuentoPorcentaje = descuentoPorcentaje;
    }

    public Producto( String nombre_prod, double precio_prod, double precio_prod_mayor, int stock, int descuentoPorcentaje, int resourceId) {

        this.nombre_prod = nombre_prod;
        this.precio_prod = precio_prod;
        this.precio_descuento = precio_prod_mayor;
        this.stock = stock;
        this.descuentoPorcentaje = descuentoPorcentaje;
        this.resourceId = resourceId;
    }

    public Producto( String nombre_prod, double precio_prod, double precio_prod_mayor) {

        this.nombre_prod = nombre_prod;
        this.precio_prod = precio_prod;
        this.precio_descuento = precio_prod_mayor;

    }


    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
    public int getDesPorcentaje() {
        return descuentoPorcentaje;
    }

    public void setDesPorcentaje(int descuentoPorcentaje) {
        this.descuentoPorcentaje = descuentoPorcentaje;
    }

    public int getCod_prod() {
        return cod_prod;
    }

    public void setCod_prod(int cod_prod) {
        this.cod_prod = cod_prod;
    }

    public String getNombre_prod() {
        return nombre_prod;
    }

    public void setNombre_prod(String nombre_prod) {
        this.nombre_prod = nombre_prod;
    }

    public double getPrecio_prod() {
        return precio_prod;
    }

    public void setPrecio_prod(double precio_prod) {
        this.precio_prod = precio_prod;
    }

    public double getPrecio_descuento() {
        return precio_descuento;
    }

    public void setPrecio_descuento(double precio_descuento) {
        this.precio_descuento = precio_descuento;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}
