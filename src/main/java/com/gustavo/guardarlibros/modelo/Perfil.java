/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gustavo.guardarlibros.modelo;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author gustavo
 */
public class Perfil {

    private Integer id;
    private String nombre;

    public Perfil() {
    }

    public Perfil(String nombre) {
        this.nombre = nombre.replace(',', ' ');
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId(List<Integer> listadoIds) {
        Optional<Integer> ultimoID = listadoIds.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .findFirst();

        ultimoID.ifPresentOrElse(ult -> this.id = ult + 1, () -> this.id = 1);

    }

    public String getNombre() {
        return nombre.trim();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.replace(',', ' ');
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.nombre);
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
        final Perfil other = (Perfil) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return nombre;
    }

}
