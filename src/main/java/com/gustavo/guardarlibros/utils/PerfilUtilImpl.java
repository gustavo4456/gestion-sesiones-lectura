/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gustavo.guardarlibros.utils;

import com.gustavo.guardarlibros.modelo.Perfil;
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
public class PerfilUtilImpl implements IPerfilUtil {

    @Override
    public void crearArchivo(Perfil nuevoPerfil) {
        String nombreArchivo = NombresArchivos.PERFIL.getNombreArchivo();

        String contenido = String.valueOf(nuevoPerfil.getId()).trim();
        contenido += "," + nuevoPerfil.getNombre().trim() + "\n";

        try (FileWriter writer = new FileWriter(nombreArchivo, true)) {
            writer.write(contenido);
            System.out.println("Archivo '" + nombreArchivo + "' creado y escrito con éxito.");
        } catch (IOException e) {
            System.err.println("Ocurrió un error al crear/escribir el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Perfil> leerArchivo() {

        String nombreArchivo = NombresArchivos.PERFIL.getNombreArchivo();

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
                System.out.println(conseguirAtributosPerfil(lineas));
            } catch (IOException e) {
                System.err.println("Ocurrió un error al leer el archivo: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return conseguirAtributosPerfil(lineas);
    }

    private List<Perfil> conseguirAtributosPerfil(List<String> liString) {

        //Numero de atributos que tiene la clase perfil. se recupera cada elemento y se lo pasa al atributo correspondiente
        int cantidadAtributosPerfil = 2;
        /*
        Este es el orden 
        String contenido = String.valueOf(nuevoLibro.getId());
        contenido += "," + nuevoLibro.getNombre();
        
         */
        List<Perfil> listaPerfiles = liString.stream()
                .filter(a -> !a.isEmpty())
                .map(a -> Arrays.asList(a.split(",")))
                .filter(a -> a.size() == cantidadAtributosPerfil)
                .map(a -> {

                    Perfil perfil = new Perfil();
                    perfil.setId(Integer.valueOf(a.get(0)));
                    perfil.setNombre(a.get(1));

                    return perfil;

                })
                .collect(Collectors.toList());

        return listaPerfiles;
    }

    @Override
    public Perfil getPerfilPorId(Integer id) {

        List<Perfil> perfiles = leerArchivo();

        Optional<Perfil> perfilEncontrado = perfiles.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (perfilEncontrado.isPresent()) {
            return perfilEncontrado.get();
        } else {
            return new Perfil("No se pudo encontrar el perfil.");
        }
    }
    
    @Override
    public List<Integer> getIds() {
        List<Perfil> listaLibros = leerArchivo();

        List<Integer> listadoIds = listaLibros.stream()
                .distinct()
                .map(Perfil::getId)
                .collect(Collectors.toList());

        return listadoIds;

    }

}
