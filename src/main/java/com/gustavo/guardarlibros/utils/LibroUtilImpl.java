/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gustavo.guardarlibros.utils;

import com.gustavo.guardarlibros.modelo.Libro;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author gustavo
 */
public class LibroUtilImpl implements ILibroUtil {

    @Override
    public void crearArchivo(Libro nuevoLibro) {

        String nombreArchivo = NombresArchivos.LIBROS.getNombreArchivo();

        String contenido = String.valueOf(nuevoLibro.getId());
        contenido += "," + nuevoLibro.getNombre();
        contenido += "," + nuevoLibro.getAutor();
        contenido += "," + nuevoLibro.getCantidadPaginas() + "\n";

        try (FileWriter writer = new FileWriter(nombreArchivo, true)) {
            writer.write(contenido);
            System.out.println("Archivo '" + nombreArchivo + "' creado y escrito con éxito.");
        } catch (IOException e) {
            System.err.println("Ocurrió un error al crear/escribir el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Libro> leerArchivo() {

        String nombreArchivo = NombresArchivos.LIBROS.getNombreArchivo();

        List<String> lineas = new ArrayList<>();

        File archivo = new File(nombreArchivo);

        if (archivo.exists()) {
            System.out.println("--- Leyendo archivo línea por línea ---");
            try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
                String linea;
                int numeroLinea = 1;
                while ((linea = reader.readLine()) != null) {
                    System.out.println("Línea " + numeroLinea + ": " + linea);
                    lineas.add(linea);
                    numeroLinea++;
                }
                System.out.println("Lectura de archivo completada.");
                System.out.println("Tamanio: " + lineas.size());
                System.out.println(conseguirAtributosLibro(lineas));
            } catch (IOException e) {
                System.err.println("Ocurrió un error al leer el archivo: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return conseguirAtributosLibro(lineas);

    }

    private List<Libro> conseguirAtributosLibro(List<String> liString) {

        //Numero de atributos que tiene la clase libro. se recupera cada elemento y se lo pasa al atributo correspondiente
        int cantidadAtributosLibro = 4;
        /*
        Este es el orden 
        String contenido = String.valueOf(nuevoLibro.getId());
        contenido += "," + nuevoLibro.getNombre();
        contenido += "," + nuevoLibro.getAutor();
        contenido += "," + nuevoLibro.getCantidadPaginas() + "\n";
         */
        List<Libro> listaLibros = liString.stream()
                .filter(a -> !a.isEmpty())
                .map(a -> Arrays.asList(a.split(",")))
                .filter(a -> a.size() == cantidadAtributosLibro)
                .map(a -> {

                    Libro libro = new Libro();
                    libro.setId(Integer.valueOf(a.get(0)));
                    libro.setNombre(a.get(1));
                    libro.setAutor(a.get(2));
                    libro.setCantidadPaginas(Integer.valueOf(a.get(3)));

                    return libro;

                })
                .collect(Collectors.toList());

        return listaLibros;
    }

    @Override
    public Libro getLibroPorId(Integer id) {

        List<Libro> libros = leerArchivo();

        Optional<Libro> libroEncontrado = libros.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst();

        if (libroEncontrado.isPresent()) {
            return libroEncontrado.get();
        } else {
            return new Libro();
        }

    }

    @Override
    public List<Integer> getIds() {
        List<Libro> listaLibros = leerArchivo();

        List<Integer> listadoIds = listaLibros.stream()
                .distinct()
                .map(Libro::getId)
                .collect(Collectors.toList());

        return listadoIds;

    }

}
