/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gustavo.guardarlibros.vista.grillas;

import com.gustavo.guardarlibros.modelo.Lectura;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gustavo
 */
public class GrillaLibrosTerminados extends AbstractTableModel {

    private List<Lectura> lecturas = new ArrayList<>();
    private String[] columnas = {"Nombre", "Autor", "Fecha"};

    public GrillaLibrosTerminados() {
    }

    public GrillaLibrosTerminados(List<Lectura> lecturas) {
        this.lecturas = lecturas;
    }

    public void setLecturas(List<Lectura> nuevasLecturas) {
        this.lecturas = nuevasLecturas;

        // Notificar a la tabla que los datos han cambiado
        fireTableDataChanged();
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return columnas[0];
            case 1:
                return columnas[1];
            case 2:
                return columnas[2];

            default:
                return "";
        }
    }

    @Override
    public int getRowCount() {
        return lecturas.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Lectura l = lecturas.get(rowIndex);

        switch (columnIndex) {

            case 0:
                return l.getLibro().getNombre();
            case 1:
                return l.getLibro().getAutor();
            case 2:
                return l.getFechaInicio();
            default:
                return "";
        }
    }

    public Lectura getLectura(int rowIndex) {
        if (lecturas.isEmpty()) {
            return new Lectura();
        }

        Lectura l = lecturas.get(rowIndex);

        return l;
    }

}
