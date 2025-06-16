/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gustavo.guardarlibros.modelo;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author gustavo
 */
public class Lectura {

    private Integer id;
    private Libro libro;
    private Perfil perfil;
    private Integer paginaActual;
    private LocalDate fechaInicio;
    private Integer minutosLeidos;
    private Estado estado;

    public Lectura() {
    }

    public Lectura(Integer id, Libro libro, Perfil perfil, Integer paginaActual, LocalDate fechaInicio, Integer minutosLeidos, Estado estado) {
        this.id = id;
        this.libro = libro;
        this.perfil = perfil;
        this.paginaActual = paginaActual;
        this.fechaInicio = fechaInicio;
        this.minutosLeidos = minutosLeidos;
        this.estado = estado;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Integer getMinutosLeidos() {
        return minutosLeidos;
    }

    public void setMinutosLeidos(Integer minutosLeidos) {
        this.minutosLeidos = minutosLeidos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId(List<Integer> ids) {

        Optional<Integer> nuevoId = ids.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .findFirst();

        nuevoId.ifPresentOrElse((res) -> this.id = res + 1, () -> this.id = 1);
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Integer getPaginaActual() {
        return paginaActual;
    }

    public void setPaginaActual(Integer paginaActual) {
        this.paginaActual = paginaActual;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.libro);
        hash = 53 * hash + Objects.hashCode(this.perfil);
        hash = 53 * hash + Objects.hashCode(this.paginaActual);
        hash = 53 * hash + Objects.hashCode(this.fechaInicio);
        hash = 53 * hash + Objects.hashCode(this.minutosLeidos);
        hash = 53 * hash + Objects.hashCode(this.estado);
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
        final Lectura other = (Lectura) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.libro, other.libro)) {
            return false;
        }
        if (!Objects.equals(this.perfil, other.perfil)) {
            return false;
        }
        if (!Objects.equals(this.paginaActual, other.paginaActual)) {
            return false;
        }
        if (!Objects.equals(this.fechaInicio, other.fechaInicio)) {
            return false;
        }
        if (!Objects.equals(this.minutosLeidos, other.minutosLeidos)) {
            return false;
        }
        return this.estado == other.estado;
    }

    @Override
    public String toString() {
        return "Lectura{" + "id=" + id + ", libro=" + libro + ", perfil=" + perfil + ", paginaActual=" + paginaActual + ", fechaInicio=" + fechaInicio + ", minutosLeidos=" + minutosLeidos + ", estado=" + estado + '}';
    }

}
