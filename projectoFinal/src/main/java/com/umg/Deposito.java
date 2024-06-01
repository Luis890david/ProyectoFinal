/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

/**
 *
 * @author hp
 */
public class Deposito {

    static void obtenerMontoRestante() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String rutaCuentas = System.getProperty("user.dir") + "/Registro/cuentas.txt";
    String rutaTransaccion = System.getProperty("user.dir") + "/Registro/TransaccionesDiarias.txt";

    public void depositos() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese su número de cuenta:");
        String usuarioCuenta = scanner.nextLine();

        System.out.println("--------------------------");
        System.out.println("Seleccione la operación:");
        System.out.println("1. Depositar");
        System.out.println("2. Retirar");
        System.out.println("--------------------------");
        System.out.println("\ningresa su eleccion: ");
        int operacion = scanner.nextInt();

        System.out.println("Ingrese el monto:");
        double monto = scanner.nextDouble();

        switch (operacion) {
            case 1:
                actualizarMonto(usuarioCuenta, monto, true);  // Depositar
                break;
            case 2:
                actualizarMonto(usuarioCuenta, monto, false); // Retirar
                break;
            default:
                System.out.println("Operación no válida.");
        }
    }

    public void actualizarMonto(String usuarioCuenta, double monto, boolean esDeposito) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaCuentas))) {
            String linea;
            StringBuilder datosActualizado = new StringBuilder();
            boolean cuentaEncontrada = false;

            while ((linea = br.readLine()) != null) {
                String[] partesLinea = linea.split(",");

                if (partesLinea[0].equals(usuarioCuenta)) {
                    double montoActual = Double.parseDouble(partesLinea[1]);
                    double nuevoMonto = esDeposito ? montoActual + monto : montoActual - monto;

                    // Verificar que haya suficiente saldo para retirar
                    if (!esDeposito && nuevoMonto < 0) {
                        System.out.println("Fondos insuficientes para realizar la operación.");
                        return;
                    }

                    partesLinea[1] = Double.toString(nuevoMonto);  // Actualizar el monto
                    cuentaEncontrada = true;

                    // Registrar la transacción
                    String tipoTransaccion = esDeposito ? "DEPOSITO" : "RETIRO";
                    String transaccion = tipoTransaccion + "," + usuarioCuenta + "," + monto + "," + LocalDate.now();
                    guardarTransaccion(transaccion);
                }

                datosActualizado.append(String.join(",", partesLinea)).append("\n");
            }

            if (!cuentaEncontrada) {
                System.out.println("La cuenta del usuario no coincide con ninguna registrada.");
                return;
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaCuentas))) {
                bw.write(datosActualizado.toString());
            }

            System.out.println("Cuenta del usuario actualizada correctamente.");
        } catch (Exception e) {
            System.out.println("Error al actualizar la cuenta: " + e.getMessage());
        }

    }

    public void guardarTransaccion(String transaccion) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaTransaccion, true))) {
            bw.write(transaccion);

            // Obtener el número de cuenta desde la transacción
            String[] partesTransaccion = transaccion.split(",");
            String numeroCuenta = partesTransaccion[1];

            // Obtener el monto restante en la cuenta
            double montoRestante = obtenerMontoRestante(numeroCuenta);

            // Agregar la información del monto restante al archivo de transacciones
            bw.write(", Monto restante en la cuenta: " + montoRestante);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar la transacción: " + e.getMessage());
        }
    }

    public double obtenerMontoRestante(String numeroCuenta) {
        double saldoActual = 0.0;

        try (BufferedReader br = new BufferedReader(new FileReader(rutaCuentas))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partesLinea = linea.split(",");
                if (partesLinea[0].equals(numeroCuenta)) {
                    saldoActual = Double.parseDouble(partesLinea[1]);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de cuentas: " + e.getMessage());
        }

        return saldoActual;
    }
}
