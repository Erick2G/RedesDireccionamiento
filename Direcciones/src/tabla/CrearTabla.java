package tabla;

import extras.Datos;
import extras.Oper;
import java.util.ArrayList;
import javax.swing.JFrame;

public class CrearTabla {
    public static int claveM,subredes,hosts, clave;
    public static int[] ip;
    public static ArrayList<Datos> renglones = new ArrayList<>();
    
    public static void resolver(int sub, int ho,int cla, int[] ipp,JFrame frame){
        subredes=sub;
        hosts=ho+2; //+2 para contar dentro al ID de Red y al Broadcast
        clave=cla;
        ip=ipp;
        claveM=4-clave;
        //Setear a mano la primera direccion IP
        renglones.clear();
        renglones.add(new Datos(0));
        String primerIP="";
        for(int i =0;i<4;i++){
            if(i<claveM){
                primerIP+=String.valueOf(ip[i])+".";
            }else{
                if(i==3){
                    primerIP+="0";
                }else{
                    primerIP+="0.";
                }
                
            }
        }
        renglones.get(0).setNum(0,primerIP);
            Oper.sumarDesde(renglones,0,0,0,1,1);
            Oper.sumarDesde(renglones,0,0,0,2,hosts-2);
            Oper.sumarDesde(renglones,0,0,0,3,hosts-1);
        //A partir de la primera sacamos las siguientes
        //System.out.println("Hosts totales: "+hosts+" Subredes: "+subredes);
        
        //Primero renglones
        sacarRenglones();
        //sacarColumnas();
        //Creamos e imprimimos tabla O POSIBILIDAD DE HACERLO EN OTRA CLASE
        MostrarTabla.mostrar(renglones,hosts,frame);
        //imprimir();
    }
    
    public static void sacarRenglones(){
        for(int i = 1;i<subredes;i++){
            renglones.add(new Datos(i));
            Oper.sumarDesde(renglones,i-1,0,i,0,hosts);
            //System.out.println("Hosts: "+hosts);
            //sacar columnas
            Oper.sumarDesde(renglones,i,0,i,1,1);
            Oper.sumarDesde(renglones,i,0,i,2,hosts-2);
            Oper.sumarDesde(renglones,i,0,i,3,hosts-1);
        }
    }

    public static void imprimir(){
        for(int i=0; i<renglones.size();i++){
            System.out.println((i+1)+": "+
                    renglones.get(i).getNum(0)+" | "+
                    renglones.get(i).getNum(1)+" | "+
                    renglones.get(i).getNum(2)+" | "+
                    renglones.get(i).getNum(3)+" | ");
        }
    }
}