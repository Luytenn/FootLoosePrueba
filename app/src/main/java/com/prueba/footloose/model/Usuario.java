package com.prueba.footloose.model;

public class Usuario {

    private int codigo;
    private String num_documento;
    private String nombre;
    private String apellido;
    private String correo;
    private String password;


    public Usuario(){

    }

    public Usuario(String num_documento,String nombre, String apellido, String correo){
        this.num_documento = num_documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
    }


    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNum_documento() {
        return num_documento;
    }

    public void setNumDocumento(String num_documento) {
        this.num_documento = num_documento;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return correo;
    }

    public void setEmail(String correo) {
        this.correo = correo;
    }

}
