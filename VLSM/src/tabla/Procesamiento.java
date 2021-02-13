package tabla;

import extras.Datos;
import extras.Oper;
import java.util.ArrayList;
import javax.swing.JFrame;
import start.PrimerFrame;

public class Procesamiento {

    public static int bitsH, hostsR;
    public static ArrayList<Datos> rn;

    public static void ordenamiento(String[][] listas, String ip,JFrame frame) {
        //Reiniciar variables
        rn = new ArrayList<>();
        //System.out.println("Masc:" + masc);
        //comienza ordenamiento
        boolean ordenado = false;
        while (!ordenado) {
            ordenado = true;
            for (int i = 0; i < listas.length - 1; i++) {
                if (Integer.parseInt(listas[i][0]) < Integer.parseInt(listas[i + 1][0])) {
                    ordenado = false;
                    String temp0 = listas[i + 1][0];
                    String temp1 = listas[i + 1][1];
                    listas[i + 1][0] = listas[i][0];
                    listas[i + 1][1] = listas[i][1];
                    listas[i][0] = temp0;
                    listas[i][1] = temp1;
                }
            }
        }
        sacarSubredes(listas, ip,frame);
    }

    //Por cada iteracion se agrega un elemento a la lista rn
    public static void sacarSubredes(String[][] listas, String ip,JFrame frame) {
        //Agregamos primera Ip para tener de donde partir
        rn.add(new Datos());
        rn.get(0).setNum(0, ip);
        //calcularIPInicial(masc);
        bitsHostsReales(Integer.parseInt(listas[0][0]));
        //Llenar toda la primer ilera de la tabla (toda la primera subred)
        Oper.sumarDesde(rn, 0, 0, 0, 1, 1);
        Oper.sumarDesde(rn, 0, 0, 0, 2, hostsR - 2);
        Oper.sumarDesde(rn, 0, 0, 0, 3, hostsR - 1);
        rn.get(0).setNombre(listas[0][1]);
        rn.get(0).setHostP(Integer.parseInt(listas[0][0]));
        rn.get(0).setHostR(hostsR);
        rn.get(0).setMasc(sacarMascara(bitsH));

        //Llenar el resto de la tabla
        for (int i = 1; i < listas.length; i++) {
            rn.add(new Datos());
            Oper.sumarDesde(rn, i - 1, 0, i, 0, hostsR);
            bitsHostsReales(Integer.parseInt(listas[i][0]));
            Oper.sumarDesde(rn, i, 0, i, 1, 1);
            Oper.sumarDesde(rn, i, 0, i, 2, hostsR - 2);
            Oper.sumarDesde(rn, i, 0, i, 3, hostsR - 1);

            rn.get(i).setNombre(listas[i][1]);
            rn.get(i).setHostP(Integer.parseInt(listas[i][0]));
            rn.get(i).setHostR(hostsR);
            rn.get(i).setMasc(sacarMascara(bitsH));
        }

       MostrarTabla.mostrar(rn, frame);
    }

    public static void bitsHostsReales(int hosts) {
        int tempH = 1, cont = 0;
        while (tempH - 2 < hosts) {//+2 para contar IP de red y broadcast
            tempH *= 2;
            cont++;
        }
        hostsR = (int) Math.pow(2, cont);//Esta contando id de red y broadcast ----------------------
        bitsH = cont;
        //System.out.println("hostsR: " + hostsR);
        //return cont;
    }

    public static String sacarMascara(int bits) {
        String mascara = "";
        int cont = 0, bitsC = ((bits-32)*-1);//------------------ CC
        int temp = bitsC;//---------------------------- CC
        while (bitsC >= 8) {
            mascara += "255.";
            bitsC -= 8;
            cont++;
        }
        if (bitsC > 0) {
            int exp = 7;
            int suma = 0;
            for (int i = 0; i < bitsC; i++) {
                suma += (int) Math.pow(2, exp);
                exp--;
            }
            mascara += suma + ".";
            cont++;
        }
        if (cont < 4) {
            while (cont != 4) {
                mascara += "0.";
                cont++;
            }
        }
        
        mascara+="/"+temp;
        //System.out.println("Mascara: " + masc+" bits: "+bits+" bitsCM: "+bitsC);
        return mascara;
    }
}
