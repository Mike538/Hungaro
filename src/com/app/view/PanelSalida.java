/* *****************************************************************************
 * Class: PanelSalida.java 
 * Date: 10/03/2018 02:14:52 PM
 * Copyright 2018 All rights reserved
 * 
 * Proyect: MetodoHungaro
 * Autor: Cristhian Eduardo Castillo Erazo - 10/03/2018 
 * *****************************************************************************
 */

package com.app.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 * Clase que representa el panel de salida del algoritmo.
 * @author Cristhian Eduardo Castillo Erazo.
 */
public class PanelSalida extends JPanel implements ActionListener
{
    // -------------------------------------------------------------------------
    //  Constants
    // -------------------------------------------------------------------------

    /**
     * Constante para exportar el buffer de salida.
     */
    public static final String EXPORTAR_ARCHIVO = "Exportar Archivo";
    
    // -------------------------------------------------------------------------
    //  Attributes
    // -------------------------------------------------------------------------

    // -------------------------------------------------------------------------
    //  GUI attributes
    // -------------------------------------------------------------------------
    
    /**
     * Campo de texto que representa el buffer de salida de la aplicación.
     */
    private final JTextArea txtBufferSalida;
    
    /**
     * Boton que ejecuta la exportación del buffer de salida.
     */
    private final JButton btnExportar;
    // -------------------------------------------------------------------------
    //  Builders
    // -------------------------------------------------------------------------

    /**
     * Construye el Panel de salida.
     */
    public PanelSalida()
    {
        this.setBorder(new TitledBorder("Salida algoritmo"));
        this.setLayout(new BorderLayout());
        
        txtBufferSalida = new JTextArea(10,50);
        txtBufferSalida.setLineWrap(true);
        txtBufferSalida.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(txtBufferSalida);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        btnExportar = new JButton("Exportar archivo");
        btnExportar.setActionCommand(EXPORTAR_ARCHIVO);
        btnExportar.addActionListener((ActionListener)this);
        
        JPanel pnlSur = new JPanel();
        pnlSur.setLayout(new FlowLayout(FlowLayout.TRAILING));
        pnlSur.add(btnExportar);
        
        this.add(scroll, BorderLayout.CENTER);
        this.add(pnlSur, BorderLayout.SOUTH);
    }
    
    // -------------------------------------------------------------------------
    //  Functional methods
    // -------------------------------------------------------------------------
    
    /**
     * Escribe una linea en el buffer de salida de la aplicación.
     * @param linea 
     */
    public void escribirSalida(String linea)
    {
        txtBufferSalida.append(linea + "\n");
    }
    
    /**
     * Limpia el buffer de salida de la aplicación.
     */
    public void limpiarBuffer()
    {
        this.txtBufferSalida.setText("");
    }

    /**
     * Manejo de los eventos de los botones.
     * @param e Acción que genero el evento. e != null.
     */
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        String comando = e.getActionCommand();
        if(comando.equalsIgnoreCase(EXPORTAR_ARCHIVO))
        {
            try
            {
                if(txtBufferSalida.getText().trim().equalsIgnoreCase(""))
                    throw new Exception("El buffer de salida esta vacío.");
                JFileChooser fc = new JFileChooser("./data");
                fc.setDialogTitle("Guardar archivo problema de asignación");
                fc.setMultiSelectionEnabled(false);
                fc.showSaveDialog(this);
                File guardar = fc.getSelectedFile();
                if(guardar != null)
                {
                    try (FileWriter save = new FileWriter( guardar + ".txt")) {
                        save.write(txtBufferSalida.getText());
                    }
                    JOptionPane.showMessageDialog(null,"El archivo se ha guardado exitosamente.","Exportar archivo",JOptionPane.INFORMATION_MESSAGE);
                }
            }
            catch(Exception ex)
            {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Exportar archivo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
