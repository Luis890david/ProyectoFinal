/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;


public class menu {

    String rutaArchivo = System.getProperty("user.dir") + "/Registro/usuario.txt";
    registro user = new registro();
    Scanner leer = new Scanner(System.in);
    String DPI;
    String nombre;
    String apellido;
    String telefono;
    String direccion;
    String correo;
    String ocupacion;
    double ingresoMensual;
    int eleccion = 0;

    public void ingreso() {

        do {

            System.out.println("--------------------------");
            System.out.println("1- Registro Cliente ");
            System.out.println("2- Ver listado de usuarios ");
            System.out.println("3- Actualizar datos ");
            System.out.println("4- apertura");
            System.out.println("5- Depocitos y Retiros");
            System.out.println("6- Reportes");
            System.out.println("0- Salir");
            System.out.println("--------------------------");

            System.out.println("\ningresa su eleccion: ");
            eleccion = leer.nextInt();

            switch (eleccion) {

                case 0:
                    System.out.println("QUE TENGA UN BONITO DIA");
                    break;

                case 1:
                    System.out.println("Crear usuario \n");

                    user = crearUser();

                    System.out.println(user.getData());
                    break;

                case 2: {
                    verUsuarios();
                    break;
                }

                case 3:
                    System.out.println("Ingrese su nuevo datos \n");

                    Updata();

                    break;

                case 4:

                    Apertura creacion = new Apertura();
                    creacion.apertura();

                    break;

                case 5:

                    Deposito monto = new Deposito();
                    monto.depositos();

                    break;

                case 6:
                    Reportes reporte = new Reportes();
                    reporte.reportes();

                    break;

                default:
                    System.out.println("Esta eleccion no es correcta");

            }

        } while (eleccion != 0);

    }

    public registro crearUser() {
        Scanner leer2 = new Scanner(System.in);

        System.out.println("Ingrese el DPI: ");
        DPI = leer2.nextLine();
        /*crear variables temporales para agregar todos los campos de crear usuario*/
        System.out.println("Ingrese su nombre: ");
        nombre = leer2.nextLine();

        System.out.println("Ingrese su apellido: ");
        apellido = leer2.nextLine();

        System.out.println("Ingrese su direccion: ");
        direccion = leer2.nextLine();

        System.out.println("Ingrese su telefono: ");
        telefono = leer2.nextLine();

        System.out.println("Ingrese su correo: ");
        correo = leer2.nextLine();

        System.out.println("Cual es su ocupacion: ");
        ocupacion = leer2.nextLine();

        System.out.println("Cual es su ingreso: ");
        ingresoMensual = leer2.nextDouble();

        registro temporal = new registro(DPI, nombre, apellido, direccion, telefono, correo, ocupacion, ingresoMensual);
        /*ACA AGREGAR DATOS DEL CONSTRUCTOR*/

        guardar(temporal);

        return temporal;
    }

    public void guardar(registro user) {

        FileWriter escritor = null;
        BufferedWriter bw = null;

        try {

            escritor = new FileWriter(rutaArchivo, true);
            bw = new BufferedWriter(escritor);

            String Usuario = user.getDPI() + "," + user.getNombre() + "," + user.getApellido() + "," + user.getDireccion() + "," + user.getTelefono() + "," + user.getCorreo() + "," + user.getOcupacion() + "," + user.getIngresoMensual();

            bw.write(Usuario);
            bw.newLine();

        } catch (Exception e) {
            System.out.println("ERROR AL AGUARDAR EL USUARIO: " + e.getMessage());
        } finally {
            //cerrar el archivo
            try {
                bw.close();
            } catch (Exception e) {
                System.out.println("ERROR AL CERRAR EL ARCHIVO. " + e.getMessage());
            }
        }
    }

    public void verUsuarios() {

        FileReader lector = null;
        BufferedReader br = null;

        try {

            lector = new FileReader(rutaArchivo);
            br = new BufferedReader(lector);

            String linea;
            while ((linea = br.readLine()) != null) {

                String[] partesLine = linea.split(",");

                String DPI = partesLine[0];
                String nombre = partesLine[1];
                String apellido = partesLine[2];
                String direccion = partesLine[3];
                String telefono = partesLine[4];
                String correo = partesLine[5];
                String ocupacion = partesLine[6];
                String ingresoMensuale = partesLine[7];

                System.out.println(
                        "DPI: " + DPI
                        + ",\t nombre: " + nombre
                        + ",\t apellido:" + apellido
                        + ",\t direccion: " + direccion
                        + ",\t telefono: " + telefono
                        + ",\t correo: " + correo
                        + ",\t ocupacion: " + ocupacion
                        + ",\t ingresoMensuale: " + ingresoMensuale
                );

            }
        } catch (Exception e) {
            System.out.println("ERROR AL LEER LAS TAREA: " + e.getMessage());
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                System.out.println("ERROR AL CERRAR EL ARCHIVO. " + e.getMessage());
            }

        }

    }

    public void Updata() {

        Scanner nuevo = new Scanner(System.in);

        System.out.println("Ingrese el DPI del usuario a actualizar: ");
        String dpiUsuario = nuevo.nextLine();

        System.out.println("Ingrese la nueva dirección: ");
        String nuevaDireccion = nuevo.nextLine();

        System.out.println("Ingrese el nuevo teléfono: ");
        String nuevoTelefono = nuevo.nextLine();

        System.out.println("Ingrese el nuevo correo: ");
        String nuevoCorreo = nuevo.nextLine();

        System.out.println("Ingrese la nueva ocupación: ");
        String nuevaOcupacion = nuevo.nextLine();

        System.out.println("Ingrese el nuevo ingreso mensual: ");
        double nuevoIngreso = nuevo.nextDouble();

        actualizarRegistro(dpiUsuario, nuevaDireccion, nuevoTelefono, nuevoCorreo, nuevaOcupacion, nuevoIngreso);
    }

    public void actualizarRegistro(String dpiUsuario, String nuevaDireccion, String nuevoTelefono,
            String nuevoCorreo, String nuevaOcupacion, double nuevoIngreso) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {

            String linea;
            StringBuilder datosActualizados = new StringBuilder();
            boolean usuarioEncontrado = false;

            while ((linea = br.readLine()) != null) {
                String[] partesLine = linea.split(",");

                if (partesLine[0].equals(dpiUsuario)) {
                    partesLine[3] = nuevaDireccion;
                    partesLine[4] = nuevoTelefono;
                    partesLine[5] = nuevoCorreo;
                    partesLine[6] = nuevaOcupacion;
                    partesLine[7] = Double.toString(nuevoIngreso);
                    usuarioEncontrado = true;
                }

                datosActualizados.append(String.join(",", partesLine)).append("\n");
            }

            br.close();

            if (!usuarioEncontrado) {
                System.out.println("El DPI Del usuario a actualizar no coincide con ningun ");
                return;
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
                bw.write(datosActualizados.toString());
            }

            System.out.println("Usuario actualizado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al actualizar el usuario: " + e.getMessage());
        }
    }

}
