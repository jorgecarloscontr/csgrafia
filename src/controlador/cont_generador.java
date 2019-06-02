/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Encriptacion.Encriptador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import vista.Ventana_llaves;

/**
 *
 * @author jorge
 */
public class cont_generador {
    private javax.swing.JTextField field_p;
    private javax.swing.JTextField field_q;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton verificar;
    private javax.swing.JLabel estado1;
    private javax.swing.JLabel estado2;
    
    private boolean bandera_p;
    private boolean bandera_q;
    private boolean bandera_combo;
    private boolean bandera_button;
    private Encriptador e1;
    public cont_generador(Ventana_llaves ventana){
        field_p = ventana.getField_p();
        field_q = ventana.getField_q();
        jButton2 =ventana.getjButton2();
        jComboBox1 =ventana.getjComboBox1();
        jLabel1 = ventana.getjLabel1();
        jLabel2 = ventana.getjLabel2();
        jLabel3 = ventana.getjLabel3();
        jLabel4 = ventana.getjLabel4();
        jLabel5= ventana.getjLabel5();
        jLabel6 = ventana.getjLabel6();
        jPanel1 = ventana.getjPanel1();
        jPanel2 = ventana.getjPanel2();
        verificar= ventana.getjButton1();
        estado1= ventana.getEstado1();
        estado2 = ventana.getEstado2();
        jButton2.setEnabled(false);
        verificar.setEnabled(false);
        bandera_p=false;
        bandera_q =false;
        bandera_combo =false;
        bandera_button = false;
        e1 = new Encriptador();
        jComboBox1.removeAllItems();
        jComboBox1.setEnabled(false);
        Document doc_p = field_p.getDocument();
        doc_p.addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                bandera_p = operaciones.validar_numeros(field_p.getText());
                if(bandera_p && bandera_q ){
                       verificar.setEnabled(true);
                }else{
                    verificar.setEnabled(false);
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                bandera_p = operaciones.validar_numeros(field_p.getText());
                if(bandera_p && bandera_q ){
                       verificar.setEnabled(true);
                }else{
                    verificar.setEnabled(false);
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        Document doc_q = field_q.getDocument();
        doc_q.addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                bandera_q = operaciones.validar_numeros(field_q.getText());
                if(bandera_p && bandera_q ){
                        verificar.setEnabled(true);

                }else{
                    verificar.setEnabled(true);
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                bandera_q = operaciones.validar_numeros(field_q.getText());
                if(bandera_p && bandera_q ){
                        verificar.setEnabled(true);

                }else{
                    verificar.setEnabled(true);
                }             
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        jButton2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                e1.publicKey = Integer.parseInt((String)jComboBox1.getSelectedItem());
                e1.privateKey=e1.getInveMult((int)e1.publicKey,(int)e1.fi);
                if(e1.privateKey<1){
                    JOptionPane.showMessageDialog(jButton2,"La llave elegida es conflictiva, elige otra", "Error",  JOptionPane.ERROR_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(jButton2,"Clave publica: "+e1.publicKey +"\nClave privada: "+e1.privateKey+"\ncomplemento: "+e1.q*e1.p, "Aceptado",  JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        verificar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e1.primo(Integer.parseInt(field_q.getText())) && e1.primo(Integer.parseInt(field_p.getText()))){
                    jComboBox1.setEnabled(true);
                    e1.p=Integer.parseInt(field_p.getText());
                    e1.q=Integer.parseInt(field_q.getText());
                     e1.fi=(e1.p-1)*(e1.q-1); 
                    generar_keys();
                    jButton2.setEnabled(true);
                }
                else{
                    JOptionPane.showMessageDialog(verificar, "No son primos", "Error", JOptionPane.ERROR_MESSAGE);
                    jComboBox1.setEnabled(false);
                    jButton2.setEnabled(false);
                } 
            }
        });
    }

    public void generar_keys(){

        jComboBox1.removeAllItems();
        e1.coutPKeys(jComboBox1);
        
    }/*
    public void generar_llaves(){


                cout<<"\nEscribe la llave que quieres usar: ";
                do{
                    do{
                        e1.publicKey=capturar2();
                        cout<<endl;
                        tmpInt=getMCD(e1.fi,e1.publicKey);
                        if(tmpInt!=1){
                            cout<<"No es una posible llave, introduce una válida: ";
                        }
                    }while(tmpInt!=1);
                    e1.privateKey=e1.getInveMult(e1.publicKey,e1.fi);
                    if(e1.privateKey<1){
                        cout<<"La llave elegida es conflictiva, elige otra: ";
                    }
                }while(e1.privateKey<1);
                cout<<"Bien!, tu llave pública es "<<e1.publicKey<<" y la privada es "<<e1.privateKey<<", ambas con el complemento "<<(e1.p*e1.q)<<".\n\n";
                ESPERA;
                break;
    }*/
    
}
