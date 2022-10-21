/* *****************************************************************************
 * Class: Linea.java 
 * Date: 10/03/2018 08:00:27 PM
 * Copyright 2018 All rights reserved
 * 
 * Proyect: MetodoHungaro
 * Autor: Cristhian Eduardo Castillo Erazo - 10/03/2018 
 * *****************************************************************************
 */
package com.app.model;

/**
 * Clase que representa una linea para validar si es posible realizar la
 * asignacion.
 *
 * @author Cristhian Eduardo Castillo Erazo.
 */
public class Linea {
    // -------------------------------------------------------------------------
    //  Constants
    // -------------------------------------------------------------------------

    /**
     * Constante que indica el sentido de la linea: FILA.
     */
    public static final int FILA = 0;

    /**
     * Constante que indica el sentido de la linea: COLUMNA.
     */
    public static final int COLUMNA = 1;

    /**
     * Constante que indica el tipo de linea DEFAULT.
     */
    public static final int DEFAULT = 2;
    // -------------------------------------------------------------------------
    //  Attributes
    // -------------------------------------------------------------------------

    /**
     * Tipo de linea.
     */
    private int tipoLinea;

    /**
     * No. de Fila o Columna de la linea.
     */
    private int numeroTipoLinea;

    /**
     * Cantidad de cero que marca esta linea.
     */
    private int cantidadCeros;
    // -------------------------------------------------------------------------
    //  GUI attributes
    // -------------------------------------------------------------------------

    // -------------------------------------------------------------------------
    //  Builders
    // -------------------------------------------------------------------------
    /**
     * Construye una nueva linea.
     */
    public Linea() {
        tipoLinea = DEFAULT;
        numeroTipoLinea = 0;
        cantidadCeros = 0;
    }

    // -------------------------------------------------------------------------
    //  Functional methods
    // -------------------------------------------------------------------------
    /**
     * Obtiene el tipo de linea.
     *
     * @return Retorna el tipo de linea.
     */
    public int getTipoLinea() {
        return tipoLinea;
    }

    /**
     * Actualiza el tipo de linea.
     *
     * @param tipoLinea Nuevo tipo de linea.
     */
    public void setTipoLinea(int tipoLinea) {
        this.tipoLinea = tipoLinea;
    }

    /**
     * Obtiene el numero de fila o columna de acuerdo con el tipo de linea.
     *
     * @return retorna el numero de linea (Fila o columna).
     */
    public int getNumeroTipoLinea() {
        return numeroTipoLinea;
    }

    /**
     * Actualiza el numero de fila o columna.
     *
     * @param numeroTipoLinea El numero de fila o columna.
     */
    public void setNumeroTipoLinea(int numeroTipoLinea) {
        this.numeroTipoLinea = numeroTipoLinea;
    }

    /**
     * Obtiene la cantidad de ceros de la linea.
     *
     * @return Retorna la cantidad de ceros de la linea.
     */
    public int getCantidadCeros() {
        return cantidadCeros;
    }

    /**
     * Actualiza la cantidad de ceros de la linea.
     *
     * @param cantidadCeros Nueva cantidad de ceros en la linea.
     */
    public void setCantidadCeros(int cantidadCeros) {
        this.cantidadCeros = cantidadCeros;
    }
}
