/* *****************************************************************************
 * Class: Controlador.java 
 * Date: 10/03/2018 03:02:58 PM
 * Copyright 2018 All rights reserved
 * 
 * Proyect: MetodoHungaro
 * Autor: Cristhian Eduardo Castillo Erazo - 10/03/2018 
 * *****************************************************************************
 */
package com.app.controller;

import com.app.model.Item;
import com.app.model.Linea;
import com.app.view.PanelSalida;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Clase que representa el controlador principal de la aplicación.
 *
 * @author Cristhian Eduardo Castillo Erazo.
 */
public class Controlador {
    // -------------------------------------------------------------------------
    //  Constants
    // -------------------------------------------------------------------------

    /**
     * Constante para Delimitar la cadena de entrada del archivo.
     */
    public static final String DELIMITADORES = "[ .,;?!¡¿\'\"\\[\\]]+";
    
    /**
     * Constante para delimitar un ejercicio de otro.
     */
    public static final String CABECERA_SALIDA = "*****************************************************************************************************************";
    
    /**
     * Constante para delimitar un paso de otro.
     */
    public static final String DELIMITADOR_PASO_PASO = "-------------------------------------------------------------------";
    // -------------------------------------------------------------------------
    //  Attributes
    // -------------------------------------------------------------------------
    /**
     * Objeto que permite importar el archivo seleccionado.
     */
    private FileReader archivoLectura;

    /**
     * Buffer de lectura del archivo seleccionado.
     */
    private BufferedReader bufferLectura;

    /**
     * Caracteres de una linea leida.
     */
    private String lineaLectura;

    /**
     * Matriz de enteros para problema de asignación.
     */
    private int[][] matrizReal;

    /**
     * Matriz de enteros que contiene la solución óptima del problema.
     */
    private int[][] matrizSolucionOptima;

    /**
     * Matriz de Objetos tipo Item para gestionar el proceso de asignación.
     */
    private Item[][] matrizRespaldo;

    /**
     * Panel Salida del algoritmo.
     */
    private PanelSalida pnlSalida;

    /**
     * Arreglo de coordenadas que señalan las asignaciones en la matriz.
     */
    private ArrayList<int[]> conjuntoDeCoordenadas;

    /**
     * Tamaño del problema.
     */
    private int tamanioProblema;

    /**
     * Valor óptimo de la solución.
     */
    private int valorOptimo;

    // -------------------------------------------------------------------------
    //  GUI attributes
    // -------------------------------------------------------------------------
    
    // -------------------------------------------------------------------------
    //  Builders
    // -------------------------------------------------------------------------
    /**
     * Construye el controlador principal de la aplicación.
     */
    public Controlador() {
        lineaLectura = "";
    }

    // -------------------------------------------------------------------------
    //  Functional methods
    // -------------------------------------------------------------------------
    /**
     * Conecta el Panel Salida con el controlador de la aplicación.
     *
     * @param pnlSalida Panel Salida. pnlSalida != null.
     */
    public void conenctar(PanelSalida pnlSalida) {
        this.pnlSalida = pnlSalida;
    }

    /**
     * Inicializa la vista de la aplicación.
     */
    public void inicializar() {
        pnlSalida.limpiarBuffer();

    }

    /**
     * Realiza una modificación en la matriz (solo para el caso Maximizar). Dado
     * que se busca maximizar las ganancia en el problema, se determina el
     * numero mas grande y se resta a este, el que se encuentra en cada posición
     * de la matriz, el resultado es asignado en la misma posición.
     */
    public void ajustarCasoMaximizar() {
        int mayor = 0;
        for (Item[] matrizRespaldo1 : matrizRespaldo) {
            for (Item matrizRespaldo11 : matrizRespaldo1) {
                int valorTemp = matrizRespaldo11.obtenerValor();
                if (mayor < valorTemp) {
                    mayor = valorTemp;
                }
            }
        }

        for (Item[] matrizRespaldo1 : matrizRespaldo) {
            for (Item matrizRespaldo11 : matrizRespaldo1) {
                int valorTemp = matrizRespaldo11.obtenerValor();
                int nuevo = mayor - valorTemp;
                matrizRespaldo11.actualizarValor(nuevo);
            }
        }
        pnlSalida.escribirSalida(". Conversión Problema de Minimización (Matriz Previa)");
        this.mostrarMatrizRespaldo();
        pnlSalida.escribirSalida(DELIMITADOR_PASO_PASO);
    }

    /**
     * Valida el estado del archivo entregado, este debe contar con los
     * requisitos minimos para ejecutar el algoritmo. Las restricciones que se
     * validan son: 
     * 1. El numero de filas debe ser el mismo que de columnas. 
     * 2. La matriz debe estar compuesta por numeros enteros positivos. 
     * 3. Todas las filas tienen el mismo numero de elementos.
     *
     * Posterior a estas validaciones, se ingresa la matriz suministrada al
     * programa. Muestra por medio del buffer de salida, la matriz entregada.
     *
     * @param ruta Ruta del archivo seleccionado. ruta != null && ruta != "".
     * @param nombreArchivo Nombre del archivo seleccionado. nombreArchivo != null && nombreArchivo != "".
     * @param tipo Tipo de problema a resilver (Minimizar, Maximizar). tipo !=
     * null && tipo != "".
     * @throws Exception
     */
    public void validarArchivo(String ruta,String nombreArchivo, String tipo) throws Exception {
        try {
            if (ruta.equalsIgnoreCase(null)) {
                throw new Exception("No se ha definido la ruta del archivo.");
            }
            if (ruta.trim().equalsIgnoreCase("")) {
                throw new Exception("La ruta del archivo no puede estar vacía.");
            }

            archivoLectura = new FileReader(ruta);
            bufferLectura = new BufferedReader(archivoLectura);

            int numeroFilas = 0;
            int numeroColumnas = 0;
            while ((lineaLectura = bufferLectura.readLine()) != null) {
                String[] numeroSeparados = lineaLectura.split(DELIMITADORES);
                if (numeroFilas == 0) {
                    numeroColumnas = numeroSeparados.length;
                } else if (numeroColumnas != numeroSeparados.length) {
                    throw new Exception("El número de columnas no es el mismo para cada fila.");
                }
                numeroFilas++;
            }

            if (numeroFilas != numeroColumnas) {
                throw new Exception("El número de filas y de columnas no es el mismo.");
            } else {
                matrizRespaldo = new Item[numeroFilas][numeroColumnas];
                matrizReal = new int[numeroFilas][numeroColumnas];
                matrizSolucionOptima = new int[numeroFilas][numeroColumnas];
                int filaActual = 0;
                archivoLectura = new FileReader(ruta);
                bufferLectura = new BufferedReader(archivoLectura);
                while ((lineaLectura = bufferLectura.readLine()) != null) {
                    String[] numeroSeparados = lineaLectura.split(DELIMITADORES);
                    for (int j = 0; j < matrizReal[filaActual].length; j++) {
                        int valor = Integer.parseInt(numeroSeparados[j]);
                        Item itemTemp = new Item(valor);
                        matrizReal[filaActual][j] = valor;
                        //Copia a la matriz manipulada durante el algoritmo.
                        matrizRespaldo[filaActual][j] = itemTemp;
                    }
                    filaActual++;
                }

                Date date = new Date();
                DateFormat hourDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                pnlSalida.escribirSalida(CABECERA_SALIDA);
                pnlSalida.escribirSalida("Nombre Archivo: " + nombreArchivo);
                pnlSalida.escribirSalida(CABECERA_SALIDA);
                pnlSalida.escribirSalida("Archivo generado el: " + hourDateFormat.format(date));
                pnlSalida.escribirSalida(CABECERA_SALIDA);
                pnlSalida.escribirSalida("1. Representación Problema inicial");
                mostrarMatrizReal();
                pnlSalida.escribirSalida(DELIMITADOR_PASO_PASO);
            }

            if (tipo.equalsIgnoreCase("Maximizar")) {
                ajustarCasoMaximizar();
            }
            tamanioProblema = numeroFilas;
            
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        finally
        {
            try
            {
                archivoLectura.close();
            }
            catch(IOException ex)
            {
                throw new Exception(ex.getMessage());
            }
        }
    }

    /**
     * Ejecuta paso a paso el algoritmo para obtener la solución óptima. 
     * 1. Determina el numero menor en cada fila y lo resta en la misma. 
     * 2. Determina el numero menor en cada columna y lo resta en la misma.
     * 3. Cubrir todos los ceros en la matriz, con el menor numero de lineas
     * posibles. En caso que el numero de lineas sea el mismo que el tamaño del
     * problema, la mtriz estara lista para realizar la asignación de los valores.
     * 4. Si el numero de lineas es menor que el tamaño del problema, se realiza
     * ajuste en la matriz.
     * 5. Las coordenadas de los valores asignados, son identificadas.
     * 6. Se obtiene la solución optima y el valor óptimo.
     * @throws Exception
     */
    public void ejecutarAlgoritmo() throws Exception {
        try {
            determinarMenorPorFila();
            pnlSalida.escribirSalida("2. Restar el número menor en cada fila.");
            mostrarMatrizRespaldo();
            pnlSalida.escribirSalida(DELIMITADOR_PASO_PASO);
            determinarMenorPorColumna();
            pnlSalida.escribirSalida("3. Restar el número menor en cada columna.");
            mostrarMatrizRespaldo();
            pnlSalida.escribirSalida(DELIMITADOR_PASO_PASO);
            int numeroLineas = 0;
            int iteracion = 0;
            while (tamanioProblema != numeroLineas) {
                if (iteracion == 0) {
                    numeroLineas = this.obtenerLineasMatriz();
                    iteracion++;
                } else {
                    this.ajustarMatriz();
                    numeroLineas = this.obtenerLineasMatriz();
                }
            }

            pnlSalida.escribirSalida("4. Matriz ajustada");
            mostrarMatrizRespaldo();
            pnlSalida.escribirSalida(DELIMITADOR_PASO_PASO);
            asignarValoresOptimos();
            pnlSalida.escribirSalida("5. Posición elementos asignados");
            this.ordearCoordenadasSolucionOptima();
            for (int i = 0; i < conjuntoDeCoordenadas.size(); i++) {
                int[] coTemp = conjuntoDeCoordenadas.get(i);
                pnlSalida.escribirSalida("(" + coTemp[0] + "," + coTemp[1] + ")");
            }
            pnlSalida.escribirSalida(DELIMITADOR_PASO_PASO);
            pnlSalida.escribirSalida("6. Solución Óptima (Valores)");
            valorOptimo = 0;
            for (int i = 0; i < conjuntoDeCoordenadas.size(); i++) {
                int[] coTemp = conjuntoDeCoordenadas.get(i);
                valorOptimo += matrizReal[coTemp[0]][coTemp[1]];
                matrizSolucionOptima[coTemp[0]][coTemp[1]] = 1;
                pnlSalida.escribirSalida("x" + i + " = " + matrizReal[coTemp[0]][coTemp[1]]);
            }
            pnlSalida.escribirSalida(DELIMITADOR_PASO_PASO);
            pnlSalida.escribirSalida("7. Solución Óptima");
            for (int[] matrizSolucionOptima1 : matrizSolucionOptima) {
                String salidaFila = "";
                for (int j = 0; j < matrizSolucionOptima1.length; j++) {
                    salidaFila += matrizSolucionOptima1[j] + " ";
                }
                pnlSalida.escribirSalida(salidaFila);
            }
            pnlSalida.escribirSalida(DELIMITADOR_PASO_PASO);
            pnlSalida.escribirSalida("8. Valor Óptimo");
            pnlSalida.escribirSalida("Z = " + valorOptimo);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

    }
    
    /**
     * Determina el numero menor por cada fila y lo resta en cada una de las
     * posiciones de la misma.
     */
    public void determinarMenorPorFila() {
        for (Item[] matrizRespaldo1 : matrizRespaldo) {
            int menor = 0;
            for (int j = 0; j < matrizRespaldo1.length; j++) {
                int valorTemp = matrizRespaldo1[j].obtenerValor();
                if (j == 0) {
                    menor = valorTemp;
                } else if (menor > valorTemp) {
                    menor = valorTemp;
                }
            }
            for (Item matrizRespaldo11 : matrizRespaldo1) {
                int valorTemp = matrizRespaldo11.obtenerValor();
                int numeroValor = valorTemp - menor;
                matrizRespaldo11.actualizarValor(numeroValor);
            }
        }
    }
    
    /**
     * Determina el número menor por cada columna y lo resta en cada una de las
     * posiciones de la misma.
     */
    public void determinarMenorPorColumna() {
        for (int i = 0; i < matrizRespaldo.length; i++) {
            int menor = 0;
            for (int j = 0; j < matrizRespaldo[i].length; j++) {
                int valorTemp = matrizRespaldo[j][i].obtenerValor();
                if (j == 0) {
                    menor = valorTemp;
                } else if (menor > valorTemp) {
                    menor = valorTemp;
                }
            }

            for (int j = 0; j < matrizRespaldo[i].length; j++) {
                Item itemTemp = matrizRespaldo[j][i];
                int valorTemp = itemTemp.obtenerValor();
                int numeroValor = valorTemp - menor;
                itemTemp.actualizarValor(numeroValor);
                matrizRespaldo[j][i] = itemTemp;
            }
        }
    }

    /**
     * Inicializa el estado de cada uno de los items en DEFECTO, preparandolos
     * para determina el numero de filas minimo necesario.
     */
    public void inicializarEstadoItems() {
        for (Item[] matrizRespaldo1 : matrizRespaldo) {
            for (Item matrizRespaldo11 : matrizRespaldo1) {
                matrizRespaldo11.asignarEstadoPorDefecto();
            }
        }
    }

    /**
     * Retorna el numero de lineas minimo necesario para cubrir todos los ceros.
     * @return Numero de lineas minimas. 
     */
    public int obtenerLineasMatriz() {
        int contadorLineas = 0;
        inicializarEstadoItems();
        
        Linea lineaFilas = new Linea();
        lineaFilas.setTipoLinea(Linea.COLUMNA);
        
        Linea lineaColumnas = new Linea();
        lineaColumnas.setTipoLinea(Linea.COLUMNA);
        
        int contadorElementosIgualesFilas;
        int contadorElementosIgualesColumnas;
        
        while (lineaFilas.getTipoLinea() != Linea.DEFAULT && lineaColumnas.getTipoLinea() != Linea.DEFAULT ) {
            lineaFilas.setTipoLinea(Linea.DEFAULT);
            lineaColumnas.setTipoLinea(Linea.DEFAULT);
            contadorElementosIgualesFilas = 0;
            contadorElementosIgualesColumnas = 0;
            for (int i = 0; i < matrizRespaldo.length; i++) {
                int cantidadCeros = 0;
                for (Item item : matrizRespaldo[i]) {
                    if (item.getEstado() == Item.LIBRE) {
                        int valor = item.obtenerValor();
                        if (valor == 0) {
                            cantidadCeros++;
                        }
                    }
                }
                if (lineaFilas.getCantidadCeros() < cantidadCeros) {
                    lineaFilas.setTipoLinea(Linea.FILA);
                    lineaFilas.setNumeroTipoLinea(i);
                    lineaFilas.setCantidadCeros(cantidadCeros);
                    contadorElementosIgualesFilas = 0;
                    contadorElementosIgualesFilas++;
                }
                else
                {
                    if(lineaFilas.getCantidadCeros() == cantidadCeros)
                    {
                        if(cantidadCeros > 0)
                        {
                            contadorElementosIgualesFilas++;
                        }
                    }
                }
            }

            for (int i = 0; i < matrizRespaldo.length; i++) {
                int cantidadCeros = 0;
                for (int j = 0; j < matrizRespaldo[i].length; j++) {
                    if (matrizRespaldo[j][i].getEstado() == Item.LIBRE) {
                        int valor = matrizRespaldo[j][i].obtenerValor();
                        if (valor == 0) {
                            cantidadCeros++;
                        }
                    }
                }
                if (lineaColumnas.getCantidadCeros() < cantidadCeros) {
                    lineaColumnas.setTipoLinea(Linea.COLUMNA);
                    lineaColumnas.setNumeroTipoLinea(i);
                    lineaColumnas.setCantidadCeros(cantidadCeros);
                    contadorElementosIgualesColumnas = 0;
                    contadorElementosIgualesColumnas++;
                }
                else
                {
                    if(lineaColumnas.getCantidadCeros() == cantidadCeros)
                    {
                        if(cantidadCeros > 0)
                        {
                            contadorElementosIgualesColumnas++;
                        }
                    }
                }
            }

            if (lineaFilas.getTipoLinea() == Linea.DEFAULT) {
                break;
            } else {
                Linea linea = new Linea();
                int cantidadCerosMaximoFilas = lineaFilas.getCantidadCeros();
                int cantidadCerosMaximoColumas = lineaColumnas.getCantidadCeros();
                if(cantidadCerosMaximoColumas == cantidadCerosMaximoFilas)
                {
                    if(contadorElementosIgualesFilas < contadorElementosIgualesColumnas)
                    {
                        linea.setTipoLinea(Linea.COLUMNA);
                        linea.setNumeroTipoLinea(lineaColumnas.getNumeroTipoLinea());
                        linea.setCantidadCeros(cantidadCerosMaximoColumas);
                    }
                    else
                    {
                        if(contadorElementosIgualesColumnas < contadorElementosIgualesFilas)
                        {
                            linea.setTipoLinea(Linea.FILA);
                            linea.setNumeroTipoLinea(lineaFilas.getNumeroTipoLinea());
                            linea.setCantidadCeros(cantidadCerosMaximoFilas);
                        }
                        else
                        {
                            linea.setTipoLinea(Linea.FILA);
                            linea.setNumeroTipoLinea(lineaFilas.getNumeroTipoLinea());
                            linea.setCantidadCeros(cantidadCerosMaximoFilas);
                        }
                    }
                }
                else
                {
                    if(cantidadCerosMaximoColumas > cantidadCerosMaximoFilas)
                    {
                        //Columnas
                        linea.setTipoLinea(Linea.COLUMNA);
                        linea.setNumeroTipoLinea(lineaColumnas.getNumeroTipoLinea());
                        linea.setCantidadCeros(cantidadCerosMaximoColumas);
                    }
                    else
                    {
                        //Filas
                        linea.setTipoLinea(Linea.FILA);
                        linea.setNumeroTipoLinea(lineaFilas.getNumeroTipoLinea());
                        linea.setCantidadCeros(cantidadCerosMaximoFilas);
                    }
                }
                switch (linea.getTipoLinea()) {
                    case Linea.FILA:
                        int numeroFila = linea.getNumeroTipoLinea();
                        for (int i = 0; i < matrizRespaldo.length; i++) {
                            matrizRespaldo[numeroFila][i].actualizarEstado();
                        }
                        break;
                    case Linea.COLUMNA:
                        int numeroColumna = linea.getNumeroTipoLinea();
                    for (Item[] matrizRespaldo1 : matrizRespaldo) {
                        matrizRespaldo1[numeroColumna].actualizarEstado();
                    }
                        break;
                }
                contadorLineas++;
                lineaFilas.setCantidadCeros(0);
                lineaFilas.setNumeroTipoLinea(0);
                lineaColumnas.setCantidadCeros(0);
                lineaColumnas.setNumeroTipoLinea(0);
            }
        }
        return contadorLineas;
    }

    /**
     * Ajusta la matriz, generando nuevos ceros en algunas posiciones.
     * Busca el menor elemento no marcado por alguna linea.
     * Para los elementos que han sido alcanzados por una linea, no sufren algun
     * cambio.
     * Para los elementos que han sido interceptados por dos lineas, se suma el
     * numero menor encontrado previamente.
     */
    public void ajustarMatriz() {
        int menor = -1;
        for (Item[] matrizRespaldo1 : matrizRespaldo) {
            for (Item matrizRespaldo11 : matrizRespaldo1) {
                if (matrizRespaldo11.getEstado() == Item.LIBRE) {
                    int valorTemp = matrizRespaldo11.obtenerValor();
                    if (menor == -1) {
                        menor = valorTemp;
                    } else {
                        if (menor > valorTemp) {
                            menor = valorTemp;
                        }
                    }
                }
            }
        }

        for (Item[] matrizRespaldo1 : matrizRespaldo) {
            for (Item matrizRespaldo11 : matrizRespaldo1) {
                int valorTemp = matrizRespaldo11.obtenerValor();
                switch (matrizRespaldo11.getEstado()) {
                    case Item.LIBRE:
                        int nuevoValor = valorTemp - menor;
                        matrizRespaldo11.actualizarValor(nuevoValor);
                        break;
                    case Item.MARCADO_DOS_VECES:
                        int nuevoValorDos = valorTemp + menor;
                        matrizRespaldo11.actualizarValor(nuevoValorDos);
                        break;
                }
            }
        }
    }

    /**
     * Asigna los ceros en la matriz para encontrar la solución optima y por
     * consiguiente, el valor óptimo.
     */
    public void asignarValoresOptimos() {
        conjuntoDeCoordenadas = new ArrayList<>();
        Linea linea = new Linea();
        linea.setTipoLinea(Linea.COLUMNA);
        linea.setNumeroTipoLinea(-1);
        linea.setCantidadCeros(-1);
        while (linea.getTipoLinea() != Linea.DEFAULT) {
            linea.setTipoLinea(Linea.DEFAULT); // Defaul, -1, -1
            for (int i = 0; i < matrizRespaldo.length; i++) {
                int cantidadCeros = 0;
                for (Item item : matrizRespaldo[i]) {
                    if (item.getEstadoAsignacion() == Item.ASIGNACION_LIBRE) {
                        int valor = item.obtenerValor();
                        if (valor == 0) {
                            cantidadCeros++;
                        }
                    }
                } //Cantidad de ceros por fila.
                if (linea.getCantidadCeros() == -1) {
                    if (cantidadCeros > 0) {
                        linea.setTipoLinea(Linea.FILA);
                        linea.setNumeroTipoLinea(i);
                        linea.setCantidadCeros(cantidadCeros);
                    }
                } else {
                    if (linea.getCantidadCeros() > cantidadCeros) {
                        if (cantidadCeros > 0) {
                            linea.setTipoLinea(Linea.FILA);
                            linea.setNumeroTipoLinea(i);
                            linea.setCantidadCeros(cantidadCeros);
                        }
                    }
                }
            }

            for (int i = 0; i < matrizRespaldo.length; i++) {
                int cantidadCeros = 0;
                for (int j = 0; j < matrizRespaldo[i].length; j++) {
                    if (matrizRespaldo[j][i].getEstadoAsignacion() == Item.ASIGNACION_LIBRE) {
                        int valor = matrizRespaldo[j][i].obtenerValor();
                        if (valor == 0) {
                            cantidadCeros++;
                        }
                    }
                }//Cantidad de ceros por columna
                if (linea.getCantidadCeros() == -1) {
                    if (cantidadCeros > 0) {
                        linea.setTipoLinea(Linea.COLUMNA);
                        linea.setNumeroTipoLinea(i);
                        linea.setCantidadCeros(cantidadCeros);
                    }
                } else {
                    if (linea.getCantidadCeros() > cantidadCeros) {
                        if (cantidadCeros > 0) {
                            linea.setTipoLinea(Linea.COLUMNA);
                            linea.setNumeroTipoLinea(i);
                            linea.setCantidadCeros(cantidadCeros);
                        }
                    }
                }
            }

            if (linea.getTipoLinea() == Linea.DEFAULT) {
                break;
            } else {
                switch (linea.getTipoLinea()) {
                    case Linea.FILA:
                        int numeroFila = linea.getNumeroTipoLinea();
                        int[] coordenadas = new int[2];
                        for (int i = 0; i < matrizRespaldo.length; i++) {
                            int valorTemp = matrizRespaldo[numeroFila][i].obtenerValor();
                            if (valorTemp == 0) {
                                if (matrizRespaldo[numeroFila][i].getEstadoAsignacion() == Item.ASIGNACION_LIBRE) {
                                    matrizRespaldo[numeroFila][i].setEstadoAsignacion(Item.ASIGNACION_ASIGNADO);
                                    coordenadas[0] = numeroFila;
                                    coordenadas[1] = i;
                                    break;
                                }

                            }
                        }
                        for (int i = 0; i < matrizRespaldo.length; i++) {
                            if (matrizRespaldo[coordenadas[0]][i].getEstadoAsignacion() == Item.ASIGNACION_LIBRE) {
                                int valorTemp = matrizRespaldo[coordenadas[0]][i].obtenerValor();
                                if (valorTemp == 0) {
                                    matrizRespaldo[coordenadas[0]][i].setEstadoAsignacion(Item.ASIGNACION_DESCARTADO);
                                }
                            }

                        }
                for (Item[] matrizRespaldo1 : matrizRespaldo) {
                    if (matrizRespaldo1[coordenadas[1]].getEstadoAsignacion() == Item.ASIGNACION_LIBRE) {
                        int valorTemp = matrizRespaldo1[coordenadas[1]].obtenerValor();
                        if (valorTemp == 0) {
                            matrizRespaldo1[coordenadas[1]].setEstadoAsignacion(Item.ASIGNACION_DESCARTADO);
                        }
                    }
                }

                        conjuntoDeCoordenadas.add(coordenadas);
                        break;
                    case Linea.COLUMNA:
                        int numeroColumna = linea.getNumeroTipoLinea();
                        int[] coordenadasColum = new int[2];
                        for (int i = 0; i < matrizRespaldo.length; i++) {
                            int valorTemp = matrizRespaldo[i][numeroColumna].obtenerValor();
                            if (matrizRespaldo[i][numeroColumna].getEstadoAsignacion() == Item.ASIGNACION_LIBRE) {
                                if (valorTemp == 0) {
                                    matrizRespaldo[i][numeroColumna].setEstadoAsignacion(Item.ASIGNACION_ASIGNADO);
                                    coordenadasColum[0] = i;
                                    coordenadasColum[1] = numeroColumna;
                                    break;
                                }
                            }
                        }
                        for (int i = 0; i < matrizRespaldo.length; i++) {
                            int valorTemp = matrizRespaldo[coordenadasColum[0]][i].obtenerValor();
                            if (matrizRespaldo[coordenadasColum[0]][i].getEstadoAsignacion() == Item.ASIGNACION_LIBRE) {
                                if (valorTemp == 0) {
                                    matrizRespaldo[coordenadasColum[0]][i].setEstadoAsignacion(Item.ASIGNACION_DESCARTADO);
                                }
                            }

                        }
                for (Item[] matrizRespaldo1 : matrizRespaldo) {
                    int valorTemp = matrizRespaldo1[coordenadasColum[1]].obtenerValor();
                    if (matrizRespaldo1[coordenadasColum[1]].getEstadoAsignacion() == Item.ASIGNACION_LIBRE) {
                        if (valorTemp == 0) {
                            matrizRespaldo1[coordenadasColum[1]].setEstadoAsignacion(Item.ASIGNACION_DESCARTADO);
                        }
                    }
                }
                        conjuntoDeCoordenadas.add(coordenadasColum);
                        break;
                }
                linea.setCantidadCeros(-1);
                linea.setNumeroTipoLinea(-1);
            }
        }
    }
    
    /**
     * Ordena las posiciones asignadas que contiene la solución optima del
     * problema.
     */
    public void ordearCoordenadasSolucionOptima()
    {
        for(int i = 0; i < conjuntoDeCoordenadas.size() - 1; i ++)
        {
            int[] menor = conjuntoDeCoordenadas.get(i);
            int[] elementoTemp = menor;
            int posicion = i;
            for(int j = i + 1; j < conjuntoDeCoordenadas.size(); j ++)
            {
                int x = conjuntoDeCoordenadas.get(j)[0];
                int y = conjuntoDeCoordenadas.get(j)[1];
                if(x < elementoTemp[0])
                {
                    elementoTemp = conjuntoDeCoordenadas.get(j);
                    posicion = j;
                }
                else
                {
                    if(x == elementoTemp[0])
                    {
                        if(y < elementoTemp[1])
                        {
                            elementoTemp = conjuntoDeCoordenadas.get(j);
                            posicion = j;
                        }
                    }
                }
            }
            conjuntoDeCoordenadas.set(i, elementoTemp);
            conjuntoDeCoordenadas.set(posicion, menor);
        }
    }

    /**
     * Rellena todas las posiciones de la matriz que contiene la solucion óptima
     * con 0.
     */
    public void inicializarMatrizSolucionOptima() {
        for (int[] matrizSolucionOptima1 : matrizSolucionOptima) {
            for (int j = 0; j < matrizSolucionOptima1.length; j++) {
                matrizSolucionOptima1[j] = 0;
            }
        }
    }

    /**
     * Muestra la matriz cargada a la aplicación, proveniente del archivo suministrado.
     */
    public void mostrarMatrizReal() {
        for (int[] matrizReal1 : matrizReal) {
            String salidaFila = "";
            for (int matrizReal11 : matrizReal1) {
                salidaFila += matrizReal11 + " ";
            }
            pnlSalida.escribirSalida(salidaFila);
        }
    }
    
    /**
     * Muestra los elementos de la matriz utilizada para encontrar la solución
     * óptima del problema.
     */
    public void mostrarMatrizRespaldo() {
        for (Item[] matrizRespaldo1 : matrizRespaldo) {
            String salidaFila = "";
            for (Item matrizRespaldo11 : matrizRespaldo1) {
                salidaFila += matrizRespaldo11.obtenerValor() + " ";
            }
            pnlSalida.escribirSalida(salidaFila);
        }
    }
}
