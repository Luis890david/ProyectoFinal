/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

/**
 *
 * @author hp
 */
public class Apertura {

    String rutaArchivo = System.getProperty("user.dir") + "/Registro/usuario.txt";
    String rutaCuentas = System.getProperty("user.dir") + "/Registro/cuentas.txt";

    private List<String> numerosDeCuenta = new ArrayList<>();

    public void apertura() {

        Scanner aper = new Scanner(System.in);

        System.out.print("Ingrese el DPI del cliente: ");
        String DPI = aper.nextLine();

        registro cliente = buscarUsuarioPorDPI(DPI);

        if (cliente == null) {
            System.out.println("Cliente no encontrado");
            return;
        }

        // Solicitar datos al usuario
        System.out.print("Ingrese el monto inicial: ");
        double montoInicial = aper.nextDouble();

        System.out.print("Ingrese el tipo de cuenta (1 para monetario, 2 para ahorro): ");
        int tipoCuenta = aper.nextInt();

        // Generar un número de cuenta único automáticamente utilizando UUID
        String numeroCuenta = generarNumeroCuenta();

        // Crear la cuenta
        cuentaUsuario cuenta = new cuentaUsuario(numeroCuenta, montoInicial, tipoCuenta, cliente.getDPI());
        guardarCuenta(cuenta);

        // Mostrar información de la cuenta creada
        System.out.println("\nCuenta creada exitosamente:");
        System.out.println("Nombre del cliente: " + cliente.getNombre() + " " + cliente.getApellido());
        System.out.println("Número de cuenta: " + cuenta.getNumeroCuenta());
        System.out.println("Fecha de apertura: " + cuenta.getFechaApertura());

    }

    private registro buscarUsuarioPorDPI(String DPI) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partesLine = linea.split(",");
                if (partesLine[0].equals(DPI)) {
                    return new registro(partesLine[0], partesLine[1], partesLine[2], partesLine[3], partesLine[4], partesLine[5], partesLine[6], Double.parseDouble(partesLine[7]));
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR AL LEER LOS USUARIOS: " + e.getMessage());
        }
        return null;
    }

    public void guardarCuenta(cuentaUsuario cuenta) {
        // Verificar si el número de cuenta ya existe
        if (numerosDeCuenta.contains(cuenta.getNumeroCuenta())) {
            System.out.println("Error: El número de cuenta ya existe.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaCuentas, true))) {
            String cuentaData = String.join(",",
                    cuenta.getNumeroCuenta(),
                    String.valueOf(cuenta.getMontoInicial()),
                    String.valueOf(cuenta.getTipoCuenta()),
                    cuenta.getFechaApertura(),
                    cuenta.getCodigoCliente()
            );
            bw.write(cuentaData);
            bw.newLine();

        } catch (Exception e) {
            System.out.println("ERROR AL GUARDAR LA CUENTA: " + e.getMessage());
        }
    }

    private String generarNumeroCuenta() {
        String numeros = "0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            int index = (int) (numeros.length() * Math.random());
            sb.append(numeros.charAt(index));
        }
        return sb.toString();
    }
}