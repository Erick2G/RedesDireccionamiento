
package extras;

import java.util.ArrayList;

public class Oper {
    public static String sumar(ArrayList<Datos> rn,int renglon, int columna,int hosts){
        String[] temp = rn.get(renglon).getNum(columna).split("\\.");
        int[] num = new int[4];
        num[0] =Integer.parseInt(temp[0]);
        num[1] =Integer.parseInt(temp[1]);
        num[2] =Integer.parseInt(temp[2]);
        num[3] =Integer.parseInt(temp[3]);
 
        int octeto =3;
        hosts += num[octeto];
        while(hosts!=0){
            if(hosts<256){
                num[octeto]=hosts;
                hosts=0;
            }else{
                int sobrante = hosts % 256;
                num[octeto]=sobrante;
                hosts-= sobrante;
                hosts = hosts/256;
                octeto--;
                hosts = hosts+ num[octeto];
            }
        }
        String resultado = String.valueOf(num[0])+"."+
                String.valueOf(num[1])+"."+
                String.valueOf(num[2])+"."+
                String.valueOf(num[3]);
        //rn.get(renglon).setNum(columna, resultado);
        return resultado;
    }
    public static void sumarDesde(ArrayList<Datos> rn,int desde,int columnaDesde,int renglon, int columna,int hosts){
        //System.out.println("Recibe: "+rn.get(desde).getNum(columna));
        String[] temp = rn.get(desde).getNum(columnaDesde).split("\\.");
        int[] num = new int[4];
        num[0] =Integer.parseInt(temp[0]);
        num[1] =Integer.parseInt(temp[1]);
        num[2] =Integer.parseInt(temp[2]);
        num[3] =Integer.parseInt(temp[3]);
        
        int octeto =3;
        hosts += num[octeto];
        //System.out.println("h: "+hosts);
        while(hosts!=0){ 
            if(hosts<256){
                num[octeto]=hosts;
                hosts=0;
            }else{
                int sobrante = hosts % 256;
                num[octeto]=sobrante;
                hosts-= sobrante;
                hosts = hosts/256;
                octeto--;
                hosts = hosts+ num[octeto];
            }
        }
        String resultado = String.valueOf(num[0])+"."+
                String.valueOf(num[1])+"."+
                String.valueOf(num[2])+"."+
                String.valueOf(num[3]);
        rn.get(renglon).setNum(columna, resultado);
        //System.out.println("Guarda: "+resultado);
    }
}
