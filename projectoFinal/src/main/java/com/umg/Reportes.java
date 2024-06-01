/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author hp
 */
public class Reportes {

    String rutaCuentas = System.getProperty("user.dir") + "/Registro/cuentas.txt";
    String rutaTransaccion = System.getProperty("user.dir") + "/Registro/TransaccionesDiarias.txt";

    public void reportes() {

        Scanner rep = new Scanner(System.in);

        System.out.println("--------------------------");
        System.out.println("Seleccione la operación:");
        System.out.println("1- Estado De Cuenta");
        System.out.println("2- Registro De Depocito y Retiros");
        System.out.println("3- Saldo De Todas Las Cuentas");
        System.out.println("--------------------------");
        System.out.println("\ningresa su eleccion: ");
        int decicion = rep.nextInt();

        switch (decicion) {
            case 1:
                EstadoCuenta();
                break;
            case 2:
                OperacionDia();
                break;
            case 3:
                generarReporteExcel();
                break;
            default:
                System.out.println("Operación no válida.");
        }

    }

    public void EstadoCuenta() {
        Scanner cuenta1 = new Scanner(System.in);

        System.out.println("Ingrese el número de cuenta:");
        String numeroCuenta = cuenta1.nextLine();

        // Verificar si la cuenta existe antes de proceder
        if (!cuentaExiste(numeroCuenta)) {
            System.out.println("La cuenta no fue encontrada.");
            return;
        }

        // Obtener registros de transacciones agrupados por mes
        Map<YearMonth, List<String>> registrosPorMes = obtenerRegistrosPorMes(numeroCuenta);

        // Imprimir los registros de cada mes
        for (Map.Entry<YearMonth, List<String>> entry : registrosPorMes.entrySet()) {
            System.out.println("Mes: " + entry.getKey());
            for (String transaccion : entry.getValue()) {
                System.out.println(transaccion);
            }
            System.out.println(); // Espacio entre meses
        }
    }

    public Map<YearMonth, List<String>> obtenerRegistrosPorMes(String numeroCuenta) {
        Map<YearMonth, List<String>> registrosPorMes = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader br = new BufferedReader(new FileReader(rutaTransaccion))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partesLinea = linea.split(",");
                if (partesLinea.length >= 4 && partesLinea[1].equals(numeroCuenta)) { // Verificar la longitud del array
                    String tipoTransaccion = partesLinea[0];
                    String cuenta = partesLinea[1];
                    double monto = Double.parseDouble(partesLinea[2]);
                    LocalDate fecha = LocalDate.parse(partesLinea[3], formatter);

                    if (!cuenta.equals(numeroCuenta)) {
                        continue;
                    }
                    // Obtener el año y el mes de la fecha
                    YearMonth mes = YearMonth.from(fecha);

                    // Formatear la transacción para imprimir
                    String transaccion = "Tipo: " + tipoTransaccion + ", Cuenta: " + numeroCuenta + ", Monto: " + monto + ", Fecha: " + fecha;

                    // Agregar la transacción al mes correspondiente
                    registrosPorMes.computeIfAbsent(mes, k -> new ArrayList<>()).add(transaccion);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de transacciones: " + e.getMessage());
        }

        return registrosPorMes;
    }

    public void OperacionDia() {
        Scanner estado = new Scanner(System.in);

        System.out.println("Ingrese el número de cuenta:");
        String numeroCuenta = estado.nextLine();

        // Verificar si la cuenta existe antes de proceder
        if (!cuentaExiste(numeroCuenta)) {
            System.out.println("La cuenta no fue encontrada.");
            return;
        }

        // Mostrar saldo actual
        mostrarSaldoActual(numeroCuenta);

        // Mostrar transacciones
        mostrarTransacciones(numeroCuenta);

        mostrarMontoRestante(numeroCuenta);
    }

    public void mostrarSaldoActual(String numeroCuenta) {
        boolean cuentaEncontrada = false; // Variable para verificar si se encontró la cuenta

        try (BufferedReader br = new BufferedReader(new FileReader(rutaCuentas))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partesLinea = linea.split(",");
                if (partesLinea[0].equals(numeroCuenta)) {
                    double saldo = Double.parseDouble(partesLinea[1]);
                    System.out.println("Saldo actual de la cuenta " + numeroCuenta + ": " + saldo);
                    cuentaEncontrada = true; // La cuenta fue encontrada
                    break; // Salir del bucle una vez que se encuentre la cuenta
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de cuentas: " + e.getMessage());
        }

        if (!cuentaEncontrada) { // Si la cuenta no fue encontrada
            System.out.println("No se encontraron registros para la cuenta especificada.");
        }
    }

    public void mostrarTransacciones(String numeroCuenta) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaTransaccion))) {
            String linea;
            System.out.println("Transacciones realizadas:");
            while ((linea = br.readLine()) != null) {
                String[] partesLinea = linea.split(",");
                if (partesLinea.length >= 4 && partesLinea[1].equals(numeroCuenta)) {
                    String tipoTransaccion = partesLinea[0];
                    double monto = Double.parseDouble(partesLinea[2]);
                    String fecha = partesLinea[3];
                    System.out.println("Tipo: " + tipoTransaccion + ", Monto: " + monto + ", Fecha: " + fecha);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de transacciones: " + e.getMessage());
        }
    }

    public void mostrarMontoRestante(String numeroCuenta) {
        boolean cuentaEncontrada = false; // Variable para verificar si se encontró la cuenta
        double saldoActual = 0.0;

        try (BufferedReader br = new BufferedReader(new FileReader(rutaCuentas))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partesLinea = linea.split(",");
                if (partesLinea[0].equals(numeroCuenta)) {
                    saldoActual = Double.parseDouble(partesLinea[1]);
                    cuentaEncontrada = true; // La cuenta fue encontrada
                    break; // Salir del bucle una vez que se encuentre la cuenta
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de cuentas: " + e.getMessage());
        }

        if (cuentaEncontrada) { // Si la cuenta fue encontrada
            System.out.println("Monto restante en la cuenta " + numeroCuenta + ": " + saldoActual);
        } else { // Si la cuenta no fue encontrada
            System.out.println("No se encontraron registros para la cuenta especificada.");
        }
    }

    public void generarReporteExcel() {
        // Se declara la variable en donde se almacenarán los datos obtenidos
        List<cuentaUsuario> lista = new ArrayList<>();

        // Obtener los datos
        lista = obtenerListaCuentas();

        // Establecer el nombre del reporte
        String nombreReporte = System.getProperty("user.dir") + "/reporte.xlsx";

        // Crear un nuevo libro de trabajo
        Workbook workbook = new XSSFWorkbook();

        // Crear una hoja
        Sheet hoja = workbook.createSheet("Reporte");

        // Establecer los títulos del encabezado
        String[] titulos = {"Número de Cuenta", "Saldo Actual"};

        // Crear la fila del encabezado
        Row filaEncabezados = hoja.createRow(0);

        // Llenar la fila del encabezado
        for (int i = 0; i < titulos.length; i++) {
            Cell celda = filaEncabezados.createCell(i);
            celda.setCellValue(titulos[i]);
            hoja.autoSizeColumn(i);
        }

        // Llenar las filas con los datos de las cuentas
        int numFila = 1; // La primera fila es la de los encabezados
        for (cuentaUsuario cuenta : lista) {
            Row fila = hoja.createRow(numFila++);

            fila.createCell(0).setCellValue(cuenta.getNumeroCuenta());
            fila.createCell(1).setCellValue(cuenta.getMontoInicial());

            // Ajustar el tamaño de las columnas
            for (int i = 0; i < titulos.length; i++) {
                hoja.autoSizeColumn(i);
            }
        }

        // Escribir el archivo en el sistema de archivos
        try (FileOutputStream fileOut = new FileOutputStream(nombreReporte)) {
            workbook.write(fileOut);
            System.out.println("Reporte generado exitosamente en: " + nombreReporte);
        } catch (IOException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }
    }

    private List<cuentaUsuario> obtenerListaCuentas() {
        List<cuentaUsuario> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaCuentas))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partesLine = linea.split(",");

                String numeroCuenta = partesLine[0];
                double montoInicial = Double.parseDouble(partesLine[1]);
                int tipoCuenta = Integer.parseInt(partesLine[2]);
                String fechaApertura = partesLine[3];
                String codigoCliente = partesLine[4];

                cuentaUsuario cuenta = new cuentaUsuario(montoInicial, tipoCuenta, codigoCliente);
                cuenta.setNumeroCuenta(numeroCuenta);
                cuenta.setFechaApertura(fechaApertura);

                lista.add(cuenta);
            }
        } catch (Exception e) {
            System.out.println("ERROR AL LEER LAS CUENTAS: " + e.getMessage());
        }

        return lista;
    }
    // Método auxiliar para verificar si una cuenta existe

    private boolean cuentaExiste(String numeroCuenta) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaCuentas))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partesLinea = linea.split(",");
                if (partesLinea[0].equals(numeroCuenta)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de cuentas: " + e.getMessage());
        }
        return false;
    }
}
