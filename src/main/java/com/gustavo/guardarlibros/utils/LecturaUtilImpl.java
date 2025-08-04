/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gustavo.guardarlibros.utils;

import com.gustavo.guardarlibros.modelo.Estado;
import com.gustavo.guardarlibros.modelo.Lectura;
import com.gustavo.guardarlibros.modelo.Libro;
import com.gustavo.guardarlibros.modelo.Perfil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author gustavo
 */
public class LecturaUtilImpl implements ILecturaUtil {

    @Override
    public void crearArchivo(Lectura nuevaLectura, boolean seMantinenLosDatos) {
        String nombreArchivo = NombresArchivos.LECTURA.getNombreArchivo();

        String contenido = String.valueOf(nuevaLectura.getId());
        contenido += "," + String.valueOf(nuevaLectura.getLibro().getId());
        contenido += "," + String.valueOf(nuevaLectura.getPerfil().getId());
        contenido += "," + nuevaLectura.getPaginaActual();
        contenido += "," + nuevaLectura.getFechaInicio();
        contenido += "," + nuevaLectura.getMinutosLeidos();
        contenido += "," + nuevaLectura.getEstado().toString() + "\n";

        try (FileWriter writer = new FileWriter(nombreArchivo, seMantinenLosDatos)) {
            writer.write(contenido);
//            System.out.println("Archivo '" + nombreArchivo + "' creado y escrito con éxito.");
        } catch (IOException e) {
            System.err.println("Ocurrió un error al crear/escribir el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Lectura> leerArchivo() {

        String nombreArchivo = NombresArchivos.LECTURA.getNombreArchivo();

        List<String> lineas = new ArrayList<>();

        File archivo = new File(nombreArchivo);

        if (archivo.exists()) {
//            System.out.println("--- Leyendo archivo línea por línea ---");
            try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
                String linea;
                int numeroLinea = 1;
                while ((linea = reader.readLine()) != null) {
                    // System.out.println("Línea " + numeroLinea + ": " + linea);
                    lineas.add(linea);
                    numeroLinea++;
                }
                //  System.out.println("Lectura de archivo completada.");
                //  System.out.println("Tamanio: " + lineas.size());
                //  System.out.println(conseguirAtributosLectura(lineas));
            } catch (IOException e) {
                System.err.println("Ocurrió un error al leer el archivo: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return conseguirAtributosLectura(lineas);
    }

    @Override
    public Lectura getLecturaPorId(Integer id) {

        List<Lectura> listaLectura = leerArchivo();

        Optional<Lectura> lecturaEncontrada = listaLectura.stream()
                .distinct()
                .filter(l -> l.getId().equals(id))
                .findFirst();

        if (lecturaEncontrada.isPresent()) {
            return lecturaEncontrada.get();
        } else {
            return new Lectura();
        }
    }

    private List<Lectura> conseguirAtributosLectura(List<String> liString) {

        LibroUtilImpl paraLibro = new LibroUtilImpl();
        PerfilUtilImpl paraPerfil = new PerfilUtilImpl();

        //Numero de atributos que tiene la clase lectura. se recupera cada elemento y se lo pasa al atributo correspondiente
        int cantidadAtributosLectura = 7;
        /*
        Este es el orden 
        String contenido = String.valueOf(nuevaLectura.getId());
        contenido += "," + String.valueOf(nuevaLectura.getLibro().getId());
        contenido += "," + String.valueOf(nuevaLectura.getPerfil().getId());
        contenido += "," + nuevaLectura.getPaginaActual();
        contenido += "," + nuevaLectura.getFechaInicio();
        contenido += "," + nuevaLectura.getMinutosLeidos();
           conteido += "," + nuevaLectura.getEstado().toString() + "\n";
         */
        List<Lectura> listaLecturas = liString.stream()
                .filter(a -> !a.isEmpty())
                .map(a -> Arrays.asList(a.split(",")))
                .filter(a -> a.size() == cantidadAtributosLectura)
                .map(a -> {

                    Lectura lectura = new Lectura();
                    lectura.setId(Integer.valueOf(a.get(0)));
                    lectura.setLibro(paraLibro.getLibroPorId(Integer.valueOf(a.get(1))));
                    lectura.setPerfil(paraPerfil.getPerfilPorId(Integer.valueOf(a.get(2))));
                    lectura.setPaginaActual(Integer.valueOf(a.get(3)));
                    lectura.setFechaInicio(LocalDate.parse(a.get(4)));
                    lectura.setMinutosLeidos(Integer.valueOf(a.get(5)));
                    lectura.setEstado(Estado.valueOf(a.get(6)));

                    return lectura;

                })
                .sorted(Comparator.comparing(Lectura::getFechaInicio).reversed())
                .collect(Collectors.toList());

        return listaLecturas;
    }

    @Override
    public List<Integer> getIds() {
        List<Lectura> listaLectura = leerArchivo();

        List<Integer> listadoIds = listaLectura.stream()
                .distinct()
                .map(Lectura::getId)
                .collect(Collectors.toList());

        return listadoIds;

    }

    @Override
    public List<Lectura> getListadoLibrosPorLeerPorPerfil(Integer idPerfil) {
        List<Lectura> listadoLectura = leerArchivo();

        List<Lectura> lecturas = listadoLectura.stream()
                .distinct()
                .filter(l -> l.getPerfil().getId().equals(idPerfil) && l.getEstado().equals(Estado.NO_LEIDO))
                .sorted(Comparator.comparing(Lectura::getFechaInicio).reversed())
                .collect(Collectors.toList());

        return lecturas;
    }

    @Override
    public List<Lectura> getListadoLibrosEnLecturaPorPerfilYLibro(Integer idPerfil, Libro libro) {
        List<Lectura> listadoLectura = leerArchivo();

        List<Lectura> lecturas = listadoLectura.stream()
                .distinct()
                .filter(l -> l.getPerfil().getId().equals(idPerfil) && l.getEstado().equals(Estado.LEYENDO) && l.getLibro().equals(libro))
                .sorted(Comparator.comparing(Lectura::getFechaInicio).reversed())
                .collect(Collectors.toList());

        return lecturas;
    }

    @Override
    public List<Lectura> getListadoLibrosTerminadosPorPerfil(Integer idPerfil) {
        List<Lectura> listadoLectura = leerArchivo();

        List<Lectura> lecturas = listadoLectura.stream()
                .distinct()
                .filter(l -> l.getPerfil().getId().equals(idPerfil) && l.getEstado().equals(Estado.TERMINADO))
                .sorted(Comparator.comparing(Lectura::getFechaInicio).reversed())
                .collect(Collectors.toList());

        return lecturas;
    }

    @Override
    public Optional<Integer> getUltimaPaginaGuardadaPorLibroEnLectura(Integer idPerfil, Libro libro) {
        List<Lectura> lecturas = leerArchivo();

        return lecturas.stream()
                .distinct()
                .filter(l -> l.getPerfil().getId().equals(idPerfil) && l.getLibro().equals(libro) && l.getEstado().equals(Estado.LEYENDO))
                .map(l -> l.getPaginaActual())
                .sorted(Comparator.reverseOrder())
                .findFirst();

    }

    @Override
    public void actualizarEstadoYFechaDeUnaLectura(Lectura lecturaAEditar, Estado estado) {
        // Aca le tengo que pasar la lectura que viene del la lista del libros no leidos
        List<Lectura> todasLasLecturas = leerArchivo();

        List<Lectura> listaLecFiltrada = todasLasLecturas.stream()
                .distinct()
                .map(l -> {
                    if (l.getId().equals(lecturaAEditar.getId())) {
                        Lectura lec = new Lectura();

                        lec.setId(l.getId());
                        lec.setLibro(l.getLibro());
                        lec.setPerfil(l.getPerfil());
                        lec.setFechaInicio(LocalDate.now());
                        lec.setMinutosLeidos(l.getMinutosLeidos());
                        lec.setPaginaActual(l.getPaginaActual());
                        lec.setEstado(estado);

                        return lec;
                    }
                    return l;
                })
                .sorted(Comparator.comparing(Lectura::getFechaInicio).reversed())
                .collect(Collectors.toList());

        crearArchivoPorLista(listaLecFiltrada, false);

    }

    @Override
    public void crearArchivoPorLista(List<Lectura> listaLectura, boolean seMantinenLosDatos) {
        String nombreArchivo = NombresArchivos.LECTURA.getNombreArchivo();

        String contenido = "";

        for (int i = 0; i < listaLectura.size(); i++) {
            contenido += String.valueOf(listaLectura.get(i).getId());
            contenido += "," + String.valueOf(listaLectura.get(i).getLibro().getId());
            contenido += "," + String.valueOf(listaLectura.get(i).getPerfil().getId());
            contenido += "," + listaLectura.get(i).getPaginaActual();
            contenido += "," + listaLectura.get(i).getFechaInicio();
            contenido += "," + listaLectura.get(i).getMinutosLeidos();
            contenido += "," + listaLectura.get(i).getEstado().toString() + "\n";
        }

        try (FileWriter writer = new FileWriter(nombreArchivo, seMantinenLosDatos)) {
            writer.write(contenido);
            System.out.println("Archivo '" + nombreArchivo + "' creado y escrito con éxito.");
        } catch (IOException e) {
            System.err.println("Ocurrió un error al crear/escribir el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarTodasLasLecturasPorPerfilYLibro(Integer idPerfil, Libro libro) {

        List<Lectura> listadoLectura = leerArchivo();

        List<Lectura> lecturas = listadoLectura.stream()
                .distinct()
                .filter(l -> !(l.getPerfil().getId().equals(idPerfil) && l.getEstado().equals(Estado.LEYENDO) && l.getLibro().equals(libro)))
                .sorted(Comparator.comparing(Lectura::getFechaInicio).reversed())
                .collect(Collectors.toList());

        crearArchivoPorLista(lecturas, false);

    }

    @Override
    public Optional<LocalDate> getFechaMasReciente(Integer idPerfil, Libro libro) {

        List<Lectura> lecturas = leerArchivo();

        Optional<LocalDate> lecturaReciente = lecturas.stream()
                .distinct()
                .filter(l -> l.getPerfil().getId().equals(idPerfil) && l.getEstado().equals(Estado.LEYENDO) && l.getLibro().equals(libro))
                .sorted(Comparator.comparing(Lectura::getFechaInicio).reversed())
                .map(Lectura::getFechaInicio)
                .findFirst();

        return lecturaReciente;
    }

    @Override
    public void editarUnaLectura(Lectura lecturaEditada) {

        List<Lectura> lecturas = leerArchivo();

        List<Lectura> listaLecturaModificada = lecturas.stream()
                .distinct()
                .map(l -> {
                    if (l.getId().equals(lecturaEditada.getId())) {
                        return lecturaEditada;
                    } else {
                        return l;
                    }
                })
                .collect(Collectors.toList());

        crearArchivoPorLista(listaLecturaModificada, false);
    }

    @Override
    public List<Lectura> getListadoLibrosTerminadosYNoTermiandosPorPerfil(Integer idPerfil) {
        List<Lectura> listadoLectura = leerArchivo();

        List<Lectura> lecturas = listadoLectura.stream()
                .distinct()
                .filter(l -> l.getPerfil().getId().equals(idPerfil) && l.getEstado().equals(Estado.TERMINADO) || l.getPerfil().getId().equals(idPerfil) && l.getEstado().equals(Estado.NO_LEIDO))
                .sorted(Comparator.comparing(Lectura::getFechaInicio).reversed())
                .collect(Collectors.toList());

        return lecturas;
    }

    @Override
    public List<Lectura> getListadoTodosLosLibrosPorPerfil(Integer idPerfil) {
        List<Lectura> lecturas = leerArchivo();

        return lecturas.stream()
                .distinct()
                .filter(l -> l.getPerfil().getId().equals(idPerfil) && l.getEstado().equals(Estado.LEYENDO))
                .sorted(Comparator.comparing(Lectura::getFechaInicio))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, IntSummaryStatistics> getMapaAutoresMinutosTotales(Integer idPerfil) {

        List<Lectura> lecturas = leerArchivo();

        return lecturas.stream()
                .distinct()
                .filter(l -> l.getPerfil().getId().equals(idPerfil) && l.getEstado().equals(Estado.LEYENDO))
                .sorted(Comparator.comparing(Lectura::getFechaInicio))
                .collect(Collectors.groupingBy(l -> l.getLibro().getAutor().trim().toLowerCase(), Collectors.summarizingInt(Lectura::getMinutosLeidos)));
    }

    @Override
    public List<DiaLectura> getPaginasLeidasEnUnDiaPorLibroYPerfil(Integer idPerfil, Libro libro) {

        List<Lectura> listaDeLecturas = getListadoLibrosEnLecturaPorPerfilYLibro(idPerfil, libro);
        List<DiaLectura> listaDiaLecturas = new ArrayList<>();

        if (!listaDeLecturas.isEmpty()) {
            // Solo siguiente porque la lista esta ordenada de primero la mas reciente
            Lectura actual = null;
            Lectura siguiente = null;

            for (int i = 0; i < listaDeLecturas.size(); i++) {

                actual = listaDeLecturas.get(i);

                try {
                    siguiente = listaDeLecturas.get(i + 1);
                } catch (IndexOutOfBoundsException e) {
                    siguiente = null;
                }

                if (siguiente != null) {
                    Integer cantidadPaginas = actual.getPaginaActual() - siguiente.getPaginaActual();
                    listaDiaLecturas.add(new DiaLectura(actual.getFechaInicio(), cantidadPaginas, actual.getMinutosLeidos()));
                } else {
                    listaDiaLecturas.add(new DiaLectura(actual.getFechaInicio(), actual.getPaginaActual(), actual.getMinutosLeidos()));
                }

            }

        } else {
            System.out.println("La lista esta vacia no se puede calcular la cantidad de paginas leidas en un dia determinado.");
        }

        return listaDiaLecturas.stream().sorted(Comparator.comparing(DiaLectura::getFecha).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<DiaLectura> getPaginasLeidasEnUnDiaDeTodosLosLibrosPorPerfil(Integer idPerfil) {
        List<Lectura> listaDeLecturas = getListadoTodasLecturasPorPerfil(idPerfil);
        List<DiaLectura> listaDiaLecturas = new ArrayList<>();

        if (!listaDeLecturas.isEmpty()) {
            // Solo siguiente porque la lista esta ordenada de primero la mas reciente
            Lectura actual = null;
            Lectura siguiente = null;

            for (int i = 0; i < listaDeLecturas.size(); i++) {

                actual = listaDeLecturas.get(i);

                try {
                    siguiente = listaDeLecturas.get(i + 1);
                } catch (IndexOutOfBoundsException e) {
                    siguiente = null;
                }

                if (siguiente != null) {
                    Integer cantidadPaginas = actual.getPaginaActual() - siguiente.getPaginaActual();
                    listaDiaLecturas.add(new DiaLectura(actual.getFechaInicio(), cantidadPaginas, actual.getMinutosLeidos()));
                } else {
                    listaDiaLecturas.add(new DiaLectura(actual.getFechaInicio(), actual.getPaginaActual(), actual.getMinutosLeidos()));
                }

            }

        } else {
            System.out.println("La lista esta vacia no se puede calcular la cantidad de paginas leidas en un dia determinado.");
        }

        return listaDiaLecturas.stream().sorted(Comparator.comparing(DiaLectura::getFecha).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<Lectura> getListadoTodasLecturasPorPerfil(Integer idPerfil) {

        List<Lectura> listadoLectura = leerArchivo();

        List<Lectura> lecturas = listadoLectura.stream()
                .distinct()
                .filter(l -> l.getPerfil().getId().equals(idPerfil) && l.getEstado().equals(Estado.LEYENDO))
                .sorted(Comparator.comparing(Lectura::getFechaInicio).reversed())
                .collect(Collectors.toList());

        return lecturas;
    }

    @Override
    public Map<String, Integer> getObtenerUltimosMesesYConteoDeLecturasParaCadaMes(int cantidadMeses, Integer idPerfil) {
        List<Lectura> lecturas = getListadoTodasLecturasPorPerfil(idPerfil);

        // 1. Calcular el conteo de lecturas existentes por YearMonth
        Map<YearMonth, Long> conteoExistente = lecturas.stream()
                .distinct() // Asegura que cada lectura única se cuente una sola vez
                .collect(Collectors.groupingBy(
                        l -> YearMonth.from(l.getFechaInicio()),
                        Collectors.counting()
                ));

        // 2. Generar la secuencia de los últimos 'cantidadMeses' y combinarlos con el conteo
        // Usamos YearMonth.now() para obtener el mes y año actual.
        // Locale.getDefault() para obtener el idioma predeterminado del sistema,
        // o Locale.of("es", "ES") para forzar español.
        Locale locale = Locale.of("es", "ES"); // Forzar el locale a español para los nombres de los meses
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", locale);

        YearMonth mesActual = YearMonth.now(); // Hora actual: 2025-07-22

        // Creamos un LinkedHashMap para asegurar que los meses se devuelvan en orden cronológico (de más antiguo a más reciente)
        Map<String, Integer> resultadoFinal = IntStream.range(0, cantidadMeses)
                .mapToObj(i -> mesActual.minusMonths(cantidadMeses - 1 - i)) // Genera los meses de más antiguo a más reciente
                .collect(Collectors.toMap(
                        // Clave: Mes y Año formateado (ej. "julio 2025")
                        mes -> mes.format(formatter),
                        // Valor: Conteo de lecturas (0 si no hay para ese mes)
                        mes -> conteoExistente.getOrDefault(mes, 0L).intValue(), // Convierte Long a Integer
                        (oldValue, newValue) -> oldValue, // Función de fusión (no debería usarse si las claves son únicas)
                        LinkedHashMap::new // Asegura el orden de inserción
                ));

        return resultadoFinal;
    }

    @Override
    public long getTotalDeLecturasPorPerfil(Integer idPerfil) {
        List<Lectura> lecturas = getListadoTodasLecturasPorPerfil(idPerfil);

        return lecturas.stream()
                .distinct()
                .count();

    }

    @Override
    public Optional<Integer> getTotalDeMinutosLeidosPorPerfil(Integer idPerfil) {
        List<Lectura> lecturas = getListadoTodasLecturasPorPerfil(idPerfil);

        return lecturas.stream()
                .distinct()
                .map(Lectura::getMinutosLeidos)
                .reduce((s1, s2) -> s1 + s2);
    }

    @Override
    public long getCantidadLibrosTerminados(Integer idPerfil) {
        List<Lectura> lecturas = getListadoLibrosTerminadosPorPerfil(idPerfil);

        return lecturas.stream()
                .distinct()
                .count();
    }

    @Override
    public double getPromedioMinutosLeidos(Integer idPerfil) {
        Optional<Integer> minutosTotalesOpt = getTotalDeMinutosLeidosPorPerfil(idPerfil);
        long cantidadDeLecturas = getTotalDeLecturasPorPerfil(idPerfil);

        if (minutosTotalesOpt.isPresent()) {
            return minutosTotalesOpt.get() / cantidadDeLecturas;
        } else {
            return 0;
        }
    }

    @Override
    public double getPorcentajeCompletadoPorLibroYPerfil(Integer idPerfil, Libro libro) {

        List<Lectura> lecturas = getListadoLibrosEnLecturaPorPerfilYLibro(idPerfil, libro);

        Optional<Lectura> lecturaOpt = lecturas.stream()
                .distinct()
                .sorted(Comparator.comparing(Lectura::getFechaInicio).reversed())
                .findFirst();

        if (lecturaOpt.isEmpty()) {
            return 0;
        }

        double totalPaginas = lecturaOpt.get().getLibro().getCantidadPaginas();
        double paginaActual = lecturaOpt.get().getPaginaActual();

        return paginaActual / totalPaginas;

    }

    @Override
    public long getTotalDePaginasLeidasPorPerfil(Integer idPerfil) {
        List<Lectura> lecturas = getListadoTodasLecturasPorPerfil(idPerfil);

        Map<Libro, Optional<Lectura>> mapaLectura = lecturas.stream()
                .distinct()
                .sorted(Comparator.comparing(Lectura::getFechaInicio).reversed())
                .collect(Collectors.groupingBy(Lectura::getLibro, Collectors.maxBy(Comparator.comparing(Lectura::getPaginaActual))));

        int totalPaginas = mapaLectura.values().stream()
                .mapToInt(opt -> opt.isPresent() ? opt.get().getPaginaActual() : 0)
                .sum();

        return totalPaginas;
    }

    @Override
    public List<Lectura> getTodosLosLibros() {
        List<Lectura> listadoLectura = leerArchivo();

        List<Lectura> lecturas = listadoLectura.stream()
                .distinct()
                .filter(l -> l.getEstado().equals(Estado.TERMINADO) || l.getEstado().equals(Estado.NO_LEIDO))
                .sorted(Comparator.comparing(Lectura::getFechaInicio).reversed())
                .collect(Collectors.toList());

        return lecturas;
    }

    @Override
    public Optional<Perfil> getPerfil(Libro libro) {
        List<Lectura> lecturas = leerArchivo();

        return lecturas.stream()
                .distinct()
                .filter(l -> l.getEstado().equals(Estado.NO_LEIDO) || l.getEstado().equals(Estado.TERMINADO))
                .filter(l -> l.getLibro().equals(libro))
                .map(Lectura::getPerfil)
                .findFirst();
    }

    @Override
    public void editarPerfilesDeLasLecturasPorLibro(Libro libroEditado, Perfil perfilEditado) {
        List<Lectura> lecturas = leerArchivo();

        List<Lectura> lecturasEditada = lecturas.stream()
                .distinct()
                .map(l -> {
                    if (l.getLibro().getId().equals(libroEditado.getId())) {
                        l.setPerfil(perfilEditado);
                        return l;
                    } else {
                        return l;
                    }
                })
                .collect(Collectors.toList());

        crearArchivoPorLista(lecturasEditada, false);
    }

    @Override
    public Optional<Lectura> getLecturaLibroLeidoyNoLeido(Libro libro) {
        List<Lectura> lecturas = leerArchivo();

        return lecturas.stream()
                .distinct()
                .filter(l -> l.getEstado().equals(Estado.NO_LEIDO) || l.getEstado().equals(Estado.TERMINADO))
                .filter(l -> l.getLibro().getId().equals(libro.getId()))
                .findFirst();
    }

    @Override
    public void eliminarLecturasPorPerfil(Perfil perfil) {
        List<Lectura> lecturas = leerArchivo();

        List<Lectura> lecturasFiltradas = lecturas.stream()
                .distinct()
                .filter(l -> !l.getPerfil().getId().equals(perfil.getId()))
                .sorted(Comparator.comparing(Lectura::getFechaInicio).reversed())
                .collect(Collectors.toList());

        crearArchivoPorLista(lecturasFiltradas, false);

    }

    @Override
    public boolean existeAlgunaLecturaPorPerfil(Perfil perfil) {
        List<Lectura> lecturas = leerArchivo();

        return lecturas.stream()
                .distinct()
                .anyMatch(l -> l.getPerfil().getId().equals(perfil.getId()));

    }

    @Override
    public void eliminarLecturasPorLibro(Libro libro) {
        List<Lectura> lecturas = leerArchivo();

        List<Lectura> lecturasFiltradas = lecturas.stream()
                .distinct()
                .filter(l -> !l.getLibro().getId().equals(libro.getId()))
                .sorted(Comparator.comparing(Lectura::getFechaInicio).reversed())
                .collect(Collectors.toList());

        crearArchivoPorLista(lecturasFiltradas, false);
    }

}
