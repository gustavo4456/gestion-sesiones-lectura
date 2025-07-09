/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.gustavo.guardarlibros.utils;

import com.gustavo.guardarlibros.modelo.Estado;
import com.gustavo.guardarlibros.modelo.Lectura;
import com.gustavo.guardarlibros.modelo.Libro;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author gustavo
 */
public interface ILecturaUtil {

    void crearArchivo(Lectura nuevaLectura, boolean seMantinenLosDatos);

    void crearArchivoPorLista(List<Lectura> listaLectura, boolean seMantinenLosDatos);

    List<Lectura> leerArchivo();

    Lectura getLecturaPorId(Integer id);

    List<Integer> getIds();

    List<Lectura> getListadoLibrosPorLeerPorPerfil(Integer idPerfil);

    List<Lectura> getListadoLibrosEnLecturaPorPerfilYLibro(Integer idPerfil, Libro libro);
    
    List<Lectura> getListadoLibrosTerminadosYNoTermiandosPorPerfil(Integer idPerfil);

    List<Lectura> getListadoLibrosTerminadosPorPerfil(Integer idPerfil);
    
    List<Lectura> getListadoTodosLosLibrosPorPerfil(Integer idPerfil);

    Optional<Integer> getUltimaPaginaGuardadaPorLibroEnLectura(Integer idPerfil, Libro libro);

    Optional<LocalDate> getFechaMasReciente(Integer idPerfil, Libro libro);

    void actualizarEstadoYFechaDeUnaLectura(Lectura lecturaAEditar, Estado estado);

    void eliminarTodasLasLecturasPorPerfilYLibro(Integer idPerfil, Libro libro);

    void editarUnaLectura(Lectura lecturaEditada);

}
