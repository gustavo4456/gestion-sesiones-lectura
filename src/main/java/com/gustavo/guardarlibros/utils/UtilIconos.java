/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gustavo.guardarlibros.utils;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author gusta
 */
public class UtilIconos {

    private static Image iconoApp = null;

    public static void aplicarIcono(JFrame frame) {
        if (iconoApp == null) {
            iconoApp = Toolkit.getDefaultToolkit().getImage(
                    UtilIconos.class.getResource("/librors.png")
            );
        }
        frame.setIconImage(iconoApp);
    }

    public static void aplicarIcono(JDialog dialog) {
        if (iconoApp == null) {
            iconoApp = Toolkit.getDefaultToolkit().getImage(
                    UtilIconos.class.getResource("/librors.png")
            );
        }
        dialog.setIconImage(iconoApp);
    }
}
