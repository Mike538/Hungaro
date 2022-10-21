/* *****************************************************************************
 * Class: PanelParametros.java 
 * Date: 10/03/2018 02:14:40 PM
 * Copyright 2018 All rights reserved
 * 
 * Proyect: Metodo Hungaro
 * Autor: Cristhian Eduardo Castillo Erazo - 10/03/2018 
 * *****************************************************************************
 */

package com.app.view;

import com.app.controller.Controlador;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * Clase que representa el panel para la entrada de datos.  
 * @author Cristhian Eduardo Castillo Erazo.
 */
public class PanelParametros extends JPanel implements ActionListener
{
    // -------------------------------------------------------------------------
    //  Constants
    // -------------------------------------------------------------------------
    
    /**
     * Constante que envia el boton "Seleccionar Archivo".
     */
    public static final String SELECCIONAR_ARCHIVO = "Seleccionar Archivo";
    
    /**
     * Constante para ejecutar el algoritmo.
     */
    public static final String EJECUTAR_ALGORITMO = "Ejecutar Algoritmo";
    
    /**
     * Constante para limpiar el buffer de salida de la aplicación.
     */
    public static final String LIMPIAR_SALIDA = "Limpiar Salida";
    
    /**
     * Constante para seleccionar el tipo de solución del problema.
     */
    private static final String [] TIPO_SOLUCION = {"Minimizar", "Maximizar"};
    
    /**
     * Tamaño vertical de los comboBox.
     */
    public static final int VERTICAL_SIZE_COMBOBOX = 25;
    
    /**
     * Tamaño horizontal de los comboBox.
     */
    public static final int HORIZONTAL_SIZE_COMBOBOX = 160;

    /**
     * Numero de items maximos por comboBox.
     */
    public static final int ITEMS_MAXIMOS_COMBOBOX = 5;
    
    // -------------------------------------------------------------------------
    //  Attributes
    // -------------------------------------------------------------------------
    
    /**
     * Nombre del archivo seleccionado.
     */
    private String nombreArchivo;
   
    // -------------------------------------------------------------------------
    //  GUI attributes
    // -------------------------------------------------------------------------
    
    /**
     * Etiqueta tipo de solución.
     */
    private final JLabel lblTipoSolucion;
    
    /**
     * Etiqueta seleccionar archivo.
     */
    private final JLabel lblSeleccionarArchivo;
    
    /**
     * Etiqueta tiempo ejecución.
     */
    private final JLabel lblTiempoEjecucion;
    
    /**
     * Lista desplegable del tipo de solución.
     */
    private final JComboBox<String> cbmTipoSolucion;
    
    /**
     * Campo de texto ruta del archivo.
     */
    private final JTextField txtRutaArchivo;
    
    /**
     * Campo de texto que muestra el tiempo de ejecución del algoritmo.
     */
    private final JTextField txtTiempoEjecucion;
   
    /**
     * Boton seleccionar archivo.
     */
    private final JButton btnSeleccionarArchivo;
    
    /**
     * Boton para ejecutar el algoritmo.
     */
    private final JButton btnEjecutarAlgoritmo;
    
    /**
     * Boton para limpiar el buffer de salida.
     */
    private final JButton btnLimpiarSalida;
    
    /**
     * Controlador principal de la aplicación.
     */
    private final Controlador ctrl;
    
    // -------------------------------------------------------------------------
    //  Builders
    // -------------------------------------------------------------------------
    public PanelParametros(Controlador ctrl)
    {
        this.ctrl = ctrl;
        this.setBorder(new TitledBorder("Parámetros de entrada"));
        GroupLayout grupo = new GroupLayout(this);
        this.setLayout(grupo);
        
        //Creacion componentes.
        lblTipoSolucion = new JLabel("Tipo: ");
        lblSeleccionarArchivo = new JLabel("Seleccionar Archivo: ");
        lblTiempoEjecucion = new JLabel("Tiempo de ejecución: ");
        
        txtTiempoEjecucion = new JTextField(10);
        txtTiempoEjecucion.setEditable(false);
        
        DefaultComboBoxModel modeloTipo = new DefaultComboBoxModel(TIPO_SOLUCION);
        cbmTipoSolucion = new JComboBox<>(modeloTipo);
        cbmTipoSolucion.setMaximumSize(new Dimension(HORIZONTAL_SIZE_COMBOBOX, VERTICAL_SIZE_COMBOBOX));
        cbmTipoSolucion.setMinimumSize(new Dimension(HORIZONTAL_SIZE_COMBOBOX, VERTICAL_SIZE_COMBOBOX));
        cbmTipoSolucion.setToolTipText("");
        cbmTipoSolucion.setMaximumRowCount(ITEMS_MAXIMOS_COMBOBOX);
        
        txtRutaArchivo = new JTextField();
        txtRutaArchivo.setEditable(false);
        
        btnSeleccionarArchivo = new JButton("Seleccionar archivo");
        btnSeleccionarArchivo.setActionCommand(SELECCIONAR_ARCHIVO);
        btnSeleccionarArchivo.addActionListener((ActionListener)this);
        
        btnEjecutarAlgoritmo = new JButton("Ejecutar algoritmo");
        btnEjecutarAlgoritmo.setActionCommand(EJECUTAR_ALGORITMO);
        btnEjecutarAlgoritmo.addActionListener((ActionListener)this);
        
        btnLimpiarSalida = new JButton("Limpiar salida");
        btnLimpiarSalida.setActionCommand(LIMPIAR_SALIDA);
        btnLimpiarSalida.addActionListener((ActionListener)this);
                
        JPanel pnlBotones = new JPanel();
        pnlBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pnlBotones.add(btnSeleccionarArchivo);
        pnlBotones.add(btnEjecutarAlgoritmo);
        pnlBotones.add(btnLimpiarSalida);
        
        grupo.setAutoCreateContainerGaps(true);
        grupo.setAutoCreateGaps(true);
        
        //Set horizontal
        grupo.setHorizontalGroup(grupo.createSequentialGroup()
                .addGroup(grupo.createParallelGroup()
                        .addComponent(lblTipoSolucion)
                        .addComponent(lblSeleccionarArchivo)
                        .addComponent(lblTiempoEjecucion)
                )
                
                .addGroup(grupo.createParallelGroup()
                        .addComponent(cbmTipoSolucion)
                        .addComponent(txtRutaArchivo)
                        .addComponent(txtTiempoEjecucion)
                        .addComponent(pnlBotones, GroupLayout.Alignment.TRAILING)
                )
        );
        
        //Set vertical
        grupo.setVerticalGroup(grupo.createSequentialGroup()
                .addGroup(grupo.createParallelGroup()
                        .addComponent(lblTipoSolucion)
                        .addComponent(cbmTipoSolucion)
                )
                
                .addGroup(grupo.createParallelGroup()
                        .addComponent(lblSeleccionarArchivo)
                        .addComponent(txtRutaArchivo)
                )
                
                .addGroup(grupo.createParallelGroup()
                        .addComponent(lblTiempoEjecucion)
                        .addComponent(txtTiempoEjecucion)
                )
                .addGroup(grupo.createParallelGroup()
                        .addComponent(pnlBotones)
                )
        );
    }
    // -------------------------------------------------------------------------
    //  Functional methods
    // -------------------------------------------------------------------------

    /**
     * Manejo de los eventos de los botones.
     * @param e Accion que género el evento. e != null.
     */
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        String comando = e.getActionCommand();
        if(comando.equalsIgnoreCase(SELECCIONAR_ARCHIVO))
        {
            try
            {
                JFileChooser fc = new JFileChooser("./data");
                fc.setDialogTitle("Buscar archivo problema de asignación");
                fc.setMultiSelectionEnabled(false);

                int resultado = fc.showOpenDialog(this);
                if(resultado == JFileChooser.APPROVE_OPTION)
                {
                    String rutaArchivo = fc.getSelectedFile().getAbsolutePath();
                    nombreArchivo = fc.getSelectedFile().getName();
                    txtRutaArchivo.setText(rutaArchivo);
                }
            }
            catch(Exception ex)
            {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Seleccionar Archivo", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        else
        {
            if(comando.equalsIgnoreCase(EJECUTAR_ALGORITMO))
            {
                try
                {
                    long tiempo;
                    long t1 = System.currentTimeMillis();
                    String tipoProblema = cbmTipoSolucion.getSelectedItem().toString();
                    if(txtRutaArchivo.getText().trim().equalsIgnoreCase(""))
                        throw new Exception("Seleccione un archivo.");
                    ctrl.validarArchivo(txtRutaArchivo.getText(), nombreArchivo,tipoProblema);
                    ctrl.ejecutarAlgoritmo();
                    long t2 = System.currentTimeMillis();
                    tiempo = t2 - t1;
                    txtTiempoEjecucion.setText((double)tiempo + "ms");
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Ejecutar algoritmo", JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                if(comando.equalsIgnoreCase(LIMPIAR_SALIDA))
                {
                    try
                    {
                        ctrl.inicializar();
                        txtRutaArchivo.setText("");
                        txtTiempoEjecucion.setText("");
                    }
                    catch(Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Limpiar salida", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
}
