/* *****************************************************************************
 * Class: InterfazApp.java 
 * Date: 10/03/2018 02:12:23 PM
 * Copyright 2018 All rights reserved
 * 
 * Proyect: Metodo Hungaro
 * Autor: Cristhian Eduardo Castillo Erazo - 10/03/2018 
 * *****************************************************************************
 */

package com.app.view;

import com.app.controller.Controlador;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * Clase que representa la ventana principal de la aplicación.  
 * @author Cristhian Eduardo Castillo Erazo.
 */
public class InterfazApp extends JFrame
{
    // -------------------------------------------------------------------------
    //  Constants
    // -------------------------------------------------------------------------

    // -------------------------------------------------------------------------
    //  Attributes
    // -------------------------------------------------------------------------
    
    /**
     * Panel parametros.
     */
    private final PanelParametros pnlParametros;
    
    /**
     * Panel Salida.
     */
    private final PanelSalida pnlSalida;
    
    /**
     * Controlador principal de la aplicación.
     */
    private final Controlador ctrl;
    
    // -------------------------------------------------------------------------
    //  GUI attributes
    // -------------------------------------------------------------------------
    
    // -------------------------------------------------------------------------
    //  Builders
    // -------------------------------------------------------------------------

    /**
     * Construye la ventana principal de la aplicación.
     */
    public InterfazApp()
    {
        ctrl = new Controlador();
        this.setTitle("Método Húngaro");
        this.setLayout(new BorderLayout());
        
        pnlParametros = new PanelParametros(ctrl);
        pnlSalida = new PanelSalida();
        
        this.getContentPane().add(pnlParametros, BorderLayout.NORTH);
        this.getContentPane().add(pnlSalida, BorderLayout.CENTER);
        
        this.setSize(new Dimension(600,500));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        ctrl.conenctar(pnlSalida);
    }
    // -------------------------------------------------------------------------
    //  Functional methods
    // -------------------------------------------------------------------------
    
    /**
     * Este método ejecuta la aplicación, creando una nueva interfaz.
     * @param args No son necesarios.
     */
    public static void main(String [] args)
    {
        InterfazApp aplicacion = new InterfazApp();
        aplicacion.setVisible(true);
    }
}
