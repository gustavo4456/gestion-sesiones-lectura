/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.gustavo.guardarlibros.utils;

import com.gustavo.guardarlibros.modelo.Libro;
import java.util.List;

/**
 *
 * @author gustavo
 */
public interface ILibroUtil {
    void crearArchivo(Libro nuevoLibro);
    List<Libro> leerArchivo();
    Libro getLibroPorId(Integer id);
    List<Integer> getIds();
}
