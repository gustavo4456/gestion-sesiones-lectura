/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.gustavo.guardarlibros.utils;

import com.gustavo.guardarlibros.modelo.Estado;
import com.gustavo.guardarlibros.modelo.Lectura;
import com.gustavo.guardarlibros.modelo.Libro;
import java.time.LocalDate;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
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

    List<Lectura> getListadoTodasLecturasPorPerfil(Integer idPerfil);

    List<Lectura> getListadoLibrosTerminadosPorPerfil(Integer idPerfil);

    List<Lectura> getListadoTodosLosLibrosPorPerfil(Integer idPerfil);

    Map<String, IntSummaryStatistics> getMapaAutoresMinutosTotales(Integer idPerfil);

    Optional<Integer> getUltimaPaginaGuardadaPorLibroEnLectura(Integer idPerfil, Libro libro);

    Optional<LocalDate> getFechaMasReciente(Integer idPerfil, Libro libro);

    void actualizarEstadoYFechaDeUnaLectura(Lectura lecturaAEditar, Estado estado);

    void eliminarTodasLasLecturasPorPerfilYLibro(Integer idPerfil, Libro libro);

    void editarUnaLectura(Lectura lecturaEditada);

    List<DiaLectura> getPaginasLeidasEnUnDiaPorLibroYPerfil(Integer idPerfil, Libro libro);

    List<DiaLectura> getPaginasLeidasEnUnDiaDeTodosLosLibrosPorPerfil(Integer idPerfil);

    Map<String, Integer> obtenerUltimosMesesYConteoDeLecturasParaCadaMes(int cantidadMeses);

//    public static List<String> obtenerUltimosMeses(int cantidadMeses) {
//        List<String> resultado = new ArrayList<>();
//        LocalDate fechaActual = LocalDate.now(); // Obtiene la fecha actual
//
//        // Formato para mostrar el mes y el año (ej. "enero 2024")
//        // Puedes personalizar el formato según tus necesidades
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
//
//        for (int i = 0; i < cantidadMeses; i++) {
//            LocalDate fechaIterada = fechaActual.minusMonths(i); // Resta 'i' meses a la fecha actual
//            
//            // Obtiene el mes (en español si la configuración regional lo permite,
//            // de lo contrario será el nombre del mes en inglés)
//            Month mes = fechaIterada.getMonth();
//            int anio = fechaIterada.getYear();
//
//            // Formatea la fecha para obtener la cadena "mes año"
//            String mesAnioFormateado = fechaIterada.format(formatter);
//            
//            resultado.add(mesAnioFormateado);
//        }
//        return resultado;
//    }
}
