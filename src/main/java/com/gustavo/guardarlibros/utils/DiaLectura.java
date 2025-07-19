/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gustavo.guardarlibros.utils;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author gusta
 */
public class DiaLectura {

    // FECHA EN QUE SE REALIZO UNA LECTURA
    private LocalDate fecha;
    // CANTIDAD DE PAGIANAS LEIDAS ESE DIA
    private Integer paginasLeidas;

    public DiaLectura() {
    }

    public DiaLectura(LocalDate fecha, Integer paginasLeidas) {
        this.fecha = fecha;
        this.paginasLeidas = paginasLeidas;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getPaginasLeidas() {
        return paginasLeidas;
    }

    public void setPaginasLeidas(Integer paginasLeidas) {
        this.paginasLeidas = paginasLeidas;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.fecha);
        hash = 89 * hash + Objects.hashCode(this.paginasLeidas);
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
        final DiaLectura other = (DiaLectura) obj;
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        return Objects.equals(this.paginasLeidas, other.paginasLeidas);
    }

    @Override
    public String toString() {
        return "DiaLectura{" + "fecha=" + fecha + ", paginasLeidas=" + paginasLeidas + '}';
    }

}
