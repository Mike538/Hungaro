/* *****************************************************************************
 * Class: Item.java 
 * Date: 10/03/2018 03:08:35 PM
 * Copyright 2018 All rights reserved
 * 
 * Proyect: MetodoHungaro
 * Autor: Cristhian Eduardo Castillo Erazo - 10/03/2018 
 * *****************************************************************************
 */
package com.app.model;

/**
 * Clase que representa un item de la matriz.
 *
 * @author Cristhian Eduardo Castillo Erazo.
 */
public class Item {
    // -------------------------------------------------------------------------
    //  Constants
    // -------------------------------------------------------------------------

    /**
     * Constante que representa el estado libre de un item.
     */
    public static final int LIBRE = 0;

    /**
     * Constante que indica que un item a sido alcanzado por una linea.
     */
    public static final int MARCADO_UNA_VEZ = 1;

    /**
     * Constante que indica que un item a sido alcanzado por dos lineas.
     */
    public static final int MARCADO_DOS_VECES = 2;

    /**
     * Constante que indica que un item no ha sido asignado.
     */
    public static final int ASIGNACION_LIBRE = 3;

    /**
     * Constante que indica que un item ha sido descartado.
     */
    public static final int ASIGNACION_DESCARTADO = 4;

    /**
     * Constante que indica que un item ha sido asignado.
     */
    public static final int ASIGNACION_ASIGNADO = 5;

    // -------------------------------------------------------------------------
    //  Attributes
    // -------------------------------------------------------------------------
    /**
     * Valor del item.
     */
    private int valor;

    /**
     * Estado del item.
     */
    private int estado;

    /**
     * Estado de asignacion del item.
     */
    private int estadoAsignacion;

    // -------------------------------------------------------------------------
    //  GUI attributes
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    //  Builders
    // -------------------------------------------------------------------------
    /**
     * Construye un item.
     *
     * @param valorItem Valor del item.
     */
    public Item(int valorItem) {
        this.valor = valorItem;
        estado = LIBRE;
        estadoAsignacion = ASIGNACION_LIBRE;
    }

    // -------------------------------------------------------------------------
    //  Functional methods
    // -------------------------------------------------------------------------
    /**
     * Obtiene el valor de un item.
     *
     * @return Valor del item.
     */
    public int obtenerValor() {
        return this.valor;
    }

    /**
     * Actualiza el valor de un item.
     *
     * @param valor Nuevo valor del item.
     */
    public void actualizarValor(int valor) {
        this.valor = valor;
    }

    /**
     * Obtiene el estado actual de un item.
     *
     * @return
     */
    public int getEstado() {
        return estado;
    }

    /**
     * Actualiza el estado de un item.
     *
     * @param estado
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el estado de asignacion en un item.
     *
     * @return Retorna el estado de asignacion de un item.
     */
    public int getEstadoAsignacion() {
        return estadoAsignacion;
    }

    /**
     * Actualiza el estado de asignación de un item.
     *
     * @param estadoAsignacion
     */
    public void setEstadoAsignacion(int estadoAsignacion) {
        this.estadoAsignacion = estadoAsignacion;
    }

    /**
     * Cambia el estado de un item.
     */
    public void actualizarEstado() {
        if (this.estado == LIBRE) {
            this.estado = MARCADO_UNA_VEZ;
        } else if (this.estado == MARCADO_UNA_VEZ) {
            this.estado = MARCADO_DOS_VECES;
        }
    }

    /**
     * Cambia el estado LIBRE de un item.
     */
    public void asignarEstadoPorDefecto() {
        this.estado = LIBRE;
    }
}
