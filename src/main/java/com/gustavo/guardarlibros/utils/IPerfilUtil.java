/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.gustavo.guardarlibros.utils;

import com.gustavo.guardarlibros.modelo.Perfil;
import java.util.List;

/**
 *
 * @author gustavo
 */
public interface IPerfilUtil {

    void crearArchivo(Perfil nuevoPerfil);

    List<Perfil> leerArchivo();

    Perfil getPerfilPorId(Integer id);

    List<Integer> getIds();

    void crearArchivoPorLista(List<Perfil> perfiles, boolean seMantienenLosDatos);

    void editarPerfil(Perfil perfilEditado);
}
