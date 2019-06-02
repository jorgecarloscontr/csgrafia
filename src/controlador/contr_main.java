/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Encriptacion.Encriptador;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import vista.Ventana_llaves;
import vista.principal;

/**
 *
 * @author jorge
 */
public final class contr_main {
    private JButton Button_dencriptar;
    private JButton boton_Ingresar;
    private JButton boton_guardar;
    private JButton boton_llaves;
    private ButtonGroup buttonGroup1;
    private JButton button_encriptar;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel_imagen;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JRadioButton jR_desencriptar;
    private JRadioButton jR_encriptar;
    private JScrollPane jScrollPane1;
    private JTextField jT_complemento1;
    private JTextField jT_complemento_2;
    private JTextField jT_privada;
    private JTextField jT_publica;
    private JTextPane jTextPane1;
    private BufferedImage bmp;
    private boolean bandera_publica;
    private boolean bandera_privada;
    private boolean bandera_com1;
    private boolean bandera_com2;
    public contr_main(principal ventana){
        Button_dencriptar = ventana.getButton_dencriptar();
        boton_Ingresar = ventana.getBoton_Ingresar();
        boton_guardar = ventana.getBoton_guardar();
        boton_llaves= ventana.getGenerar_llave();
        buttonGroup1= ventana.getButtonGroup1();
        button_encriptar = ventana.getButton_encriptar();
        jLabel2 = ventana.getjLabel2();
        jLabel3 = ventana.getjLabel3();
        jLabel4 = ventana.getjLabel4();
        jLabel5 = ventana.getjLabel5();
        jLabel_imagen = ventana.getjLabel_imagen();
        jPanel1 = ventana.getjPanel1();
        jPanel2 = ventana.getjPanel2();
        jPanel3 = ventana.getjPanel3();
        jR_desencriptar = ventana.getjR_desencriptar();
        jR_encriptar = ventana.getjR_encriptar();
        jScrollPane1 = ventana.getjScrollPane1();
        jT_complemento1 = ventana.getjT_complemento1();
        jT_complemento_2 = ventana.getjT_complemento_2();
        jT_privada = ventana.getjT_privada();
        jT_publica = ventana.getjT_publica();
        jTextPane1 = ventana.getjTextPane1(); 
        bandera_privada = false;
        bandera_publica = false;
        bandera_com1 = false;
        bandera_com2 = false;
        inabilitar1(false);
        inabilitar2(false);

        boton_Ingresar.addActionListener((ActionEvent e) -> {
            bmp=operaciones.abrirImagen();
            if(bmp!=null){
                jLabel_imagen.setIcon(new ImageIcon (bmp.getScaledInstance(jLabel_imagen.getWidth(),jLabel_imagen.getHeight(), Image.SCALE_DEFAULT)));
                if(bandera_publica && bandera_com1)
                    button_encriptar.setEnabled(true);                
                if(bandera_privada && bandera_com2)
                    Button_dencriptar.setEnabled(true);
            }
        });
         jR_encriptar.addActionListener((ActionEvent e) -> {
             inabilitar1(true);
             inabilitar2(false);
        });
        jR_desencriptar.addActionListener((ActionEvent e) -> {
            inabilitar1(false);
            inabilitar2(true);
            jTextPane1.setText("");
        });
        button_encriptar.addActionListener((ActionEvent e) -> {
            Encriptador funcion =new Encriptador();
            String enc;
                    funcion.publicKey = Long.parseLong(jT_publica.getText());
                    funcion.n = Long.parseLong(jT_complemento1.getText());
                    if(operaciones.imageActual!=null){
                        enc = funcion.encriptar(jTextPane1.getText());
                        BufferedImage imtmp =operaciones.escalaGrises(enc);
                        jTextPane1.setText(enc);
                    }
        });
        Button_dencriptar.addActionListener((ActionEvent e) -> {
            if(operaciones.imageActual!=null){
                String cdde;
                Encriptador funcion =new Encriptador();
                    funcion.privateKey = Long.parseLong(jT_privada.getText());
                    funcion.n = Long.parseLong(jT_complemento_2.getText());
                    cdde=operaciones.docultar();
                    if(cdde.length()!=0){
                        jTextPane1.setText(funcion.desEncriptar(cdde));
                    }
                    else{
                        jTextPane1.setText("NULL");
                    }
            }
        });
        boton_guardar.addActionListener((ActionEvent e)-> {
            if(operaciones.imageActual!=null){
                JFileChooser selector=new JFileChooser();
                String path;
                selector.setDialogTitle("Seleccione la carpeta a guardar");
                //selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if(selector.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){ 
                    path = selector.getSelectedFile().getAbsolutePath(); 
                    System.out.println(path);
                    //File cout = new File(path+"\\funciona.bmp");
                    File cout = new File(path+".bmp");

                    try {
                        ImageIO.write(operaciones.imageActual, "png", cout);
                    } catch (IOException ex) {
                        Logger.getLogger(contr_main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }else{
                JOptionPane.showMessageDialog(jLabel_imagen, "No hay ninguna imagen", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        boton_llaves.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Ventana_llaves ventana_aux = new Ventana_llaves();
                
            }
        
        
        });
        Document doc_publica = jT_publica.getDocument();
        doc_publica.addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                bandera_publica = operaciones.validar_numeros(jT_publica.getText());
                if(bandera_publica && bandera_com1 && operaciones.imageActual!=null){
                    button_encriptar.setEnabled(true);
                }else{
                    button_encriptar.setEnabled(false);
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                bandera_publica = operaciones.validar_numeros(jT_publica.getText());
                if(bandera_publica && bandera_com1 && operaciones.imageActual!=null){
                    button_encriptar.setEnabled(true);
                }else{
                    button_encriptar.setEnabled(false);
                }                
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        Document doc_oom1 = jT_complemento1.getDocument();
        doc_oom1.addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                bandera_com1 = operaciones.validar_numeros(jT_complemento1.getText());
                if(bandera_publica && bandera_com1 && operaciones.imageActual!=null){
                    button_encriptar.setEnabled(true);
                }else{
                    button_encriptar.setEnabled(false);
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                bandera_com1 = operaciones.validar_numeros(jT_complemento1.getText());
                if(bandera_publica && bandera_com1 && operaciones.imageActual!=null){
                    button_encriptar.setEnabled(true);
                }else{
                    button_encriptar.setEnabled(false);
                }              
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        }); 
        Document doc_privada = jT_privada.getDocument();
        doc_privada.addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                bandera_privada = operaciones.validar_numeros(jT_privada.getText());
                if(bandera_privada && bandera_com2 && operaciones.imageActual!=null){
                    Button_dencriptar.setEnabled(true);
                }else{
                    Button_dencriptar.setEnabled(false);
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                bandera_privada = operaciones.validar_numeros(jT_privada.getText());
                if(bandera_privada && bandera_com2 && operaciones.imageActual!=null){
                    Button_dencriptar.setEnabled(true);
                }else{ 
                    Button_dencriptar.setEnabled(false);
                }               
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        Document doc_oom2 = jT_complemento_2.getDocument();
        doc_oom2.addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                bandera_com2 = operaciones.validar_numeros(jT_complemento_2.getText());
                if(bandera_privada && bandera_com2 && operaciones.imageActual!=null){
                    Button_dencriptar.setEnabled(true);
                }else{
                    Button_dencriptar.setEnabled(false);
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                bandera_com2 = operaciones.validar_numeros(jT_complemento_2.getText());
                if(bandera_privada && bandera_com2 && operaciones.imageActual!=null){
                    Button_dencriptar.setEnabled(true);
                }else{
                    Button_dencriptar.setEnabled(false);
                }            
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });        
        
    }
    private void inabilitar1(boolean bandera){
        jT_complemento1.setEnabled(bandera);
        jT_complemento1.setText("");
        jT_publica.setEnabled(bandera); 
        jT_publica.setText("");
        button_encriptar.setEnabled(false); 
    }
    private void inabilitar2(boolean bandera){
        jT_complemento_2.setEnabled(bandera);
        jT_complemento_2.setText("");
        jT_privada.setEnabled(bandera);
        jT_privada.setText("");
        Button_dencriptar.setEnabled(false); 

    }
}
