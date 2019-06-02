/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Encriptacion;

import java.util.Scanner;
import javax.swing.JComboBox;

/**
 *
 * @author jorge
 */
public class Encriptador {
    public long n,p,q;
    public String txtEnc;
    public String txtDeEnc;
    public String alfabeto=(" 0123456789abcdefghijklmnopqrstuvwxyz*?.,\n:;-_'ABCDEFGHIJKLMNOPQRSTUVWXYZ");

    public long publicKey, privateKey, fi;

    public void inicializar(){
        txtDeEnc="";
        txtEnc="";
        p=0;
        q=0;
        publicKey=0;
        privateKey=0;
        fi=0;
    }
    public void coutPKeys(JComboBox combo){
        int e=1,g=0;
        int _mcd;
        do{
            _mcd=getMCD((int) fi,e);
            if(_mcd==1){
                g++;
                combo.addItem(e+"");
            }
            e++;
        }while(e<fi);
    }

    public long getInveMult( int numero, int modulo){   //Utilizando el algoritmo de euclides extendido
        long  tabla[][]={ {0,0,1,0},
                                {0,0,0,1},
                                {0,0,0,0}};
        int g;
        tabla[0][1]=modulo;
        tabla[1][1]=numero;
        do{
            tabla[2][0]=tabla[0][1]/tabla[1][1];
            tabla[2][1]=tabla[0][1]%tabla[1][1];
            tabla[2][2]=(-tabla[2][0]*tabla[1][2])+tabla[0][2];
            tabla[2][3]=(-tabla[2][0]*tabla[1][3])+tabla[0][3];
            if(tabla[2][1]!=0){
                g=0;
                while(g<2){
                    tabla[g][0]=tabla[g+1][0];
                    tabla[g][1]=tabla[g+1][1];
                    tabla[g][2]=tabla[g+1][2];
                    tabla[g][3]=tabla[g+1][3];
                    g++;
                }
            }
        }while(tabla[2][1]!=0);
        return tabla[1][3];
    }

    public long  expoModu(long  a,long  k,long  n){
        // convertimos "k" a binario
        long numero=k;

        long[] bin= new long[300];
        long ind=0;

        while(numero>=2){
            bin[(int)ind++]=numero%2;
            numero/=2;
        }
        bin[(int)ind]=numero;

        long  tam=ind+1;
        long  b=1;

        if(k==0)
            return b;

        long  A=a;
        for(int i=(int) (tam-1);i>=0;i--){
            b=(b*b)%n;
            if(bin[i]==1)
                b=(A*b)%n;
        }
        return b;
    }
    public String encriptar(String texto_){
        txtDeEnc = "";
        txtEnc="";
                txtDeEnc=valiText(texto_);
                //cout<<"Se valida: "<<txtDeEnc<<endl;

                // representamos numericamente el mensaje
                long[] valPosChar= new long[txtDeEnc.length()]; //Guardara las posiciones de los caracteres
                long[] valParEnc = new long[txtDeEnc.length()/2];  //Guardara los valores de parejas de caracteres
                //cout<<endl;
                //Asignar posiciones individuales
                for(int i=0;i<txtDeEnc.length();i++){
                    valPosChar[i]=getPos(txtDeEnc.charAt(i));
                    //cout<<valPosChar[i]<<" ";
                }
                //cout<<endl;
                // Agrupamos de 2 en 2 el mensaje numerico
                for(int i=0;i<(txtDeEnc.length()/2);i++){
                    valParEnc[i]=valPosChar[i*2]*100+valPosChar[i*2+1];
                    //cout<<valParEnc[i]<<" ";
                }
                //cout<<endl;
                // Aplicamos el cifrado a cada valor de las parejas de caracteres
                for(int i=0;i<(txtDeEnc.length()/2);i++){
                    valParEnc[i]=expoModu(valParEnc[i],publicKey,n);
                    txtEnc=txtEnc+String.valueOf(digitos((int) valParEnc[i]))+String.valueOf(valParEnc[i]);

                }
                System.out.println(txtEnc);
                return txtEnc;

    }
    public String desEncriptar(String cadena){
        int g,cont=0;
        String tmp1;
        txtDeEnc="";
        txtEnc=cadena;
        boolean bandera =true;

            int i=0;
            do{
                g=0;
                tmp1="";
                cont = cadena.charAt(i)-48;
                i++;
                if(cadena.length()>=(cont+i) && cont!=0){
                    while(g<cont){
                        tmp1+=cadena.charAt(i);
                        g++;
                        i++;
                    }
                    System.out.println(tmp1);
                    long  tmp=Long.parseLong(tmp1),num1,num2;

                    tmp=expoModu(tmp,privateKey,n);

                    num1=tmp/100;
                    num2=tmp%100;
                    txtDeEnc += String.valueOf(alfabeto.charAt((int) (num1%alfabeto.length())));
                    txtDeEnc+= String.valueOf(alfabeto.charAt((int) (num2%alfabeto.length())));}
                else{
                    bandera=false;
                    txtDeEnc="NULL";
                }
            }while(i<txtEnc.length() && bandera);
            System.out.println(txtDeEnc+"***********");
            return txtDeEnc;            
    }
    
    public int getMayor(int x, int y){
        if(x>y){
            return x;
        }return y;
    }
    public int getMenor(int x, int y){
        if(y>x){
            return y;
        }return x;
    }
    public int getMCD(int x,int y){
        int residuo,mcd;
        do{
            mcd=y;
            residuo=x%y;
            x=y;
            y=residuo;
        }while(residuo>0&&mcd>0);
        return mcd;
    }
    public boolean primo(int num_){
        int g,h;
        g=1;
        h=0;
        while(g<=num_){
            if(num_%g==0){
                h++;
            }
            if(g<num_&&h>1){
                return false;
            }
            g++;
        }
        return true;
    }
    public int getPos(char elemento)
    {
      for(int i=0;i<alfabeto.length();i++)
        if(String.valueOf(alfabeto.charAt(i)).equals(elemento+"")){
            return i;}
       return -1;
    }

    public String  valiText(String texto)
    {
      String textoValido="";

    // eliminamos los espacios del texto plano
      for(int i=0;i<texto.length();i++){
        if(getPos(texto.charAt(i))!=-1)
            textoValido+=texto.charAt(i);
        else
            textoValido+=alfabeto.charAt(alfabeto.length()-1);
        }

     // completamos con x al final para que sea potencia de 2
       int tam=textoValido.length();
       if(tam%2!=0)
        textoValido+="x";

      return textoValido;
    }
    int digitos(int num){
        int g=0;
        while(num>0){
            num/=10;
            g++;
        }
        return g;
    }
}
