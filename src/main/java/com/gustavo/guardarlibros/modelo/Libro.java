/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gustavo.guardarlibros.modelo;

import java.util.List;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author gustavo
 */
public class Libro {

    private Integer id;
    private String nombre;
    private String autor;
    private Integer cantidadPaginas;

    public Libro() {
    }

    public Libro(String nombre, String autor, Integer cantidadPaginas) {
        this.nombre = nombre;
        this.autor = autor;
        this.cantidadPaginas = cantidadPaginas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(List<Integer> listaIds) {
        Optional<Integer> proximoId = listaIds.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .findFirst();

        proximoId.ifPresentOrElse(n -> this.id = n + 1, () -> this.id = 1);

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getCantidadPaginas() {
        return cantidadPaginas;
    }

    public void setCantidadPaginas(Integer cantidadPaginas) {
        this.cantidadPaginas = cantidadPaginas;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.nombre);
        hash = 47 * hash + Objects.hashCode(this.autor);
        hash = 47 * hash + Objects.hashCode(this.cantidadPaginas);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Libro other = (Libro) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.autor, other.autor)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.cantidadPaginas, other.cantidadPaginas);
    }

    @Override
    public String toString() {
        return this.nombre + " de " + this.autor;
    }

}
