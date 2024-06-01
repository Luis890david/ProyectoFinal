/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg;

import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author LD$
 */
public class registro {

    private String DPI;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String correo;
    private String ocupacion;
    private double ingresoMensual;
    String data;

    public registro(String DPI, String nombre, String apellido, String direccion, String telefono, String correo, String ocupacion, double ingresoMensual) {
        this.DPI = DPI;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.ocupacion = ocupacion;
        this.ingresoMensual = ingresoMensual;

    }

    public registro() {
        
    }

    /*METODO PARA DEVOLVER DATOS*/
    public String getData() {
        
        data = "Gracias por sus Datos";
 
        // Imprimir cada componente en una línea separada
        System.out.println("DPI: " + getDPI());
        System.out.println("Nombre: " + getNombre());
        System.out.println("Apellido: " + getApellido());
        System.out.println("Dirección: " + getDireccion());
        System.out.println("Teléfono: " + getTelefono());
        System.out.println("Correo: " + getCorreo());
        System.out.println("Ocupación: " + getOcupacion());
        System.out.println("Ingreso Mensual: " + getIngresoMensual());

        return data;
        
    }

    public String getDPI() {
        return DPI;
    }

    public void setDPI(String DPI) {
        this.DPI = DPI;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public double getIngresoMensual() {
        return ingresoMensual;
    }

    public void setIngresoMensual(double ingresoMensual) {
        this.ingresoMensual = ingresoMensual;
    }

}
