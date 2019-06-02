/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author jorge
 */
class operaciones {
    static BufferedImage imageActual=null;

    public static BufferedImage abrirImagen(){
        BufferedImage bmp=null;
        JFileChooser selector=new JFileChooser();
        //Le damos un título
        selector.setDialogTitle("Seleccione una imagen");
        //Filtramos los tipos de archivos
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("JPG & GIF & BMP & PNG ","jpg", "gif", "bmp","png");
        selector.setFileFilter(filtroImagen);
        //Abrimos el cuadro de diálog
        int flag=selector.showOpenDialog(null);
        //Comprobamos que pulse en aceptar
        if(flag==JFileChooser.APPROVE_OPTION){
            try {
                //Devuelve el fichero seleccionado
                File imagenSeleccionada=selector.getSelectedFile();
                //Asignamos a la variable bmp la imagen leida
                bmp = ImageIO.read(imagenSeleccionada);
            } catch (IOException e) {
                
            }
                  
        }
        //Asignamos la imagen cargada a la propiedad imageActual
        imageActual=bmp;
        //Retornamos el valor
        return bmp;
    }
    public static BufferedImage escalaGrises(String cadena){
        int r,g,b,colorSRGB,cont=0,c=0,tamano,t=0;
        String binario="";
        Color colorAux;
        tamano =cadena.length();
        boolean bandera2=false;
        //cadena+="0";
        for( int i = 0; i < imageActual.getWidth(); i++ ){
            for( int j = 0; j < imageActual.getHeight(); j++ ){
                colorAux=new Color(imageActual.getRGB(i, j));

                r=colorAux.getRed();
                g=colorAux.getGreen();
                b=colorAux.getBlue();

                //Cambiamos a formato sRGB
                if(cont<tamano){
                    binario= converitnb(convertird(cadena.charAt(cont)));
                    t=c+3;
                    for(int e=c;e<t && c!=8;e++){

                        if(c<=8){
                            if(e%3==0){
                                if(binario.charAt(e)==48){
                                    if(r%2!=0){
                                        r-=1;
                                    }
                                }else{
                                    if(r%2!=1){
                                        r+=1;
                                    }
                                } 
                            }else{
                                if(e%3==1){
                                if(binario.charAt(e)==48){
                                    if(g%2!=0){
                                            g-=1;                                            
                                        }
                                    }else{
                                        if(g%2!=1){
                                            g+=1;
                                        }
                                    } 

                                }else{
                                    if(binario.charAt(e)==48){
                                        if(b%2!=0){
                                            b-=1;
                                        }
                                    }else{
                                        if(b%2!=1){
                                            b+=1;
                                        }
                                    }                            
                                }
                            }
                        }
                        c++;
                    }
                    if(c==8){
                        cont++;
                        c=0;
                    }
                }else{
                    bandera2=true;
                }if(cont==tamano && bandera2){
                    if(r%2==0)
                        r+=1;
                    if(g%2==0)
                        g+=1;
                    if(b%2==0)
                        b+=1;
                    cont++;
                }
                colorSRGB=(r << 16) | (g << 8) | b;
                //System.out.println(colorSRGB);
                imageActual.setRGB(i, j,colorSRGB);
                Color tcolor =new Color(imageActual.getRGB(i, j));
               // System.out.println(tcolor.getRed()+"    " +tcolor.getGreen()+ "   "+ tcolor.getBlue());
            }
        }
        return imageActual;
    }
     public static String converitnb(int numero1){
        int temp = numero1,cont=0;
        long res;
        String numero2="";
        while(temp!=0){
            if(temp >= 2){
                res = temp%2;
                temp = temp/2;
            }else{
                res = temp;
                temp= 0;
            }
            numero2=res+numero2;
            cont++;
        }
        while(cont<8){
            numero2=0+numero2;
            cont++;
        }

        return numero2;
    }    
    public static char convertirt(long n){
        char c=0;
        if((n>=0 && n<=9)){
            c = (char)(n+48);
        }  
        return c;
    }
    public static int convertird(char c){
        int n= 21;// no es apto
        if((c>=48 && c<=57)){
            n = c-48;
        }else{
            if(c>64 && c<76){
                n=c-55;
            }else{
                if(c>96 && c<108){
                    n= c-87;
                }
            }
        }
        return n;
    }
    public static String docultar(){
        int r,g,b,cont=0,numero=0;
        String binario="",tmp="";
        boolean bandera = true;
        Color colorAux;
        for( int i = 0; i < imageActual.getWidth(); i++ ){
            for( int j = 0; j < imageActual.getHeight(); j++ ){
                colorAux=new Color(imageActual.getRGB(i, j));
                r=colorAux.getRed();
                g=colorAux.getGreen();
                b=colorAux.getBlue();
                Color tcolor =new Color(imageActual.getRGB(i, j));
                //System.out.println(tcolor.getRed()+"    " +tcolor.getGreen()+ "   "+ tcolor.getBlue());
                cont++;
                //Cambiamos a formato sRGB
                if(bandera){
                    if(r%2==0){
                        tmp+="0";
                    }else{
                        tmp+="1";
                    }                    
                    if(g%2==0){
                        tmp+="0";
                    }else{
                        tmp+="1";
                    }                    
                    if(cont<3){
                        if(b%2==0){
                            tmp+="0";
                        }else{
                            tmp+="1";
                        }
                    }else{
                        cont=0;
                        numero=convertirde(tmp);
                        System.out.println(tmp);
                        
                        if(numero>=0 && numero<=9){
                            binario+=String.valueOf(numero);
                            tmp="";

                        }else{
                            bandera=false;
                        }
                    }
                }
            }
        }
        System.out.println("jorge"+binario);
        
        return binario;
    }
    public static int convertirde(String aux){//convierte a decimal
        int tamano = aux.length()-1;
        int tmp1;
        int numero1=0;
        int tmp2 = 0;
        int t = 0;
        for(int i=0; i<aux.length();i++){
            tmp1= convertird(aux.charAt(tamano));
            if(i!=0){
               t=  t*2;
               tmp2 = tmp1*t;
               //tmp1 = tmp1*t;
               //tmp2 = new BigInteger("300");
            }else{
                t=1;
                tmp2 = tmp1;
            }
            numero1 = numero1+tmp2;
            tamano--;
        }
        return numero1;
    }
    public static boolean validar_numeros(String cadena_){
        for(int i=0;i<cadena_.length();i++){
            if(cadena_.charAt(i)<48 || cadena_.charAt(i)>57){
                return false;
            }
           }
        return !cadena_.isEmpty();           
    }
}
