/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg;

/**
 *
 * @author hp
 */
public class cuentaUsuario {

    private static int contadorCuentas = 1000;
    private String numeroCuenta;
    private double montoInicial;
    private int tipoCuenta;
    private String fechaApertura;
    private String codigoCliente;

    public cuentaUsuario(String numeroCuenta, double montoInicial, int tipoCuenta, String codigoCliente) {
        this.numeroCuenta = numeroCuenta;
        this.montoInicial = montoInicial;
        this.tipoCuenta = tipoCuenta;
        this.fechaApertura = obtenerFechaActual();
        this.codigoCliente = codigoCliente;

    }

    public cuentaUsuario(double montoInicial, int tipoCuenta, String codigoCliente) {
        this.montoInicial = montoInicial;
        this.tipoCuenta = tipoCuenta;
        this.codigoCliente = codigoCliente;
    }
    

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public double getMontoInicial() {
        return montoInicial;
    }

    public int getTipoCuenta() {
        return tipoCuenta;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    private String generarNumeroCuenta() {
        contadorCuentas++;
        return "A" + String.format("%06d", contadorCuentas);
    }

    public static void setContadorCuentas(int contadorCuentas) {
        cuentaUsuario.contadorCuentas = contadorCuentas;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public void setMontoInicial(double montoInicial) {
        this.montoInicial = montoInicial;
    }

    public void setTipoCuenta(int tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    private String obtenerFechaActual() {
        java.util.Date fecha = new java.util.Date();
        java.text.SimpleDateFormat formatoFecha = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return formatoFecha.format(fecha);
    }
}
