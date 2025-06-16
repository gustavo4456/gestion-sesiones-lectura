/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.gustavo.guardarlibros.utils;

/**
 *
 * @author gustavo
 */
public enum NombresArchivos {

    LIBROS("libros_para_leer.txt"), LECTURA("libros_en_lecutra.txt"), PERFIL("perfiles.txt");

    private String nombreArchivo;

    private NombresArchivos(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

}
