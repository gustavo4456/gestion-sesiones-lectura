/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gustavo.guardarlibros.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author gusta
 */
public class EstiloTablaCompleto extends DefaultTableCellRenderer {

    public static void aplicarEstilo(JTable tabla) {
        // Fuente general
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.setRowHeight(28);

        // Color de líneas
        tabla.setGridColor(new Color(220, 220, 220));

        // Selección
        tabla.setSelectionBackground(new Color(100, 150, 220));
        tabla.setSelectionForeground(Color.WHITE);

        // ENCABEZADO PERSONALIZADO (con borde asegurado)
        JTableHeader header = tabla.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setFont(new Font("Segoe UI", Font.BOLD, 16));
                c.setBackground(new Color(60, 63, 65)); // Gris oscuro
                c.setForeground(Color.WHITE);

                if (c instanceof javax.swing.JComponent jc) {
                    jc.setOpaque(true);
                    // Bordes izquierdo, derecho e inferior
                    jc.setBorder(new MatteBorder(0, 1, 2, 1, new Color(180, 180, 180)));
                }

                // Establecer altura del encabezado
                header.setPreferredSize(new Dimension(100, 35));

                return c;
            }
        });

        // CELDAS PERSONALIZADAS (filas alternadas)
        DefaultTableCellRenderer celdaRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(new Color(250, 250, 250)); // Blanco
                    } else {
                        c.setBackground(new Color(235, 235, 235)); // Gris claro
                    }
                    c.setForeground(new Color(50, 50, 50));
                } else {
                    c.setBackground(new Color(100, 150, 220));
                    c.setForeground(Color.WHITE);
                }

                setBorder(noFocusBorder);
                setHorizontalAlignment(LEFT);
                return c;
            }
        };

        // Aplicar renderer a todas las columnas
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(celdaRenderer);
        }
    }

    public static void ajustarAnchoColumnas(JTable tabla) {
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Desactiva resize automático

        for (int col = 0; col < tabla.getColumnCount(); col++) {
            int anchoMax = 0;

            // Medir ancho del encabezado
            TableCellRenderer headerRenderer = tabla.getTableHeader().getDefaultRenderer();
            Component headerComp = headerRenderer.getTableCellRendererComponent(
                    tabla, tabla.getColumnName(col), false, false, -1, col);
            anchoMax = headerComp.getPreferredSize().width;

            // Medir ancho de las celdas
            for (int row = 0; row < tabla.getRowCount(); row++) {
                TableCellRenderer cellRenderer = tabla.getCellRenderer(row, col);
                Component cellComp = tabla.prepareRenderer(cellRenderer, row, col);
                int anchoCelda = cellComp.getPreferredSize().width;
                anchoMax = Math.max(anchoMax, anchoCelda);
            }

            // Agregar margen extra
            anchoMax += 20;

            // Establecer ancho preferido
            tabla.getColumnModel().getColumn(col).setPreferredWidth(anchoMax);
        }
    }

}
