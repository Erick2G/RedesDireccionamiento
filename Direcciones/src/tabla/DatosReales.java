package tabla;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class DatosReales {

    public static Font font1 = new Font("ITALIC", Font.ROMAN_BASELINE, 14);
    public static Font font2 = new Font("Courier", Font.PLAIN, 12);
    public static int hostsReal, subredesReal, bitSubred;
    public static String mascara = "";
    public static JLabel labelSub = new JLabel();
    public static JLabel labelH = new JLabel();

    public static void resolver(int claveMascara, int[] ip, int hosts, int subredes, JFrame frame) {
        hostsReal = 0;
        subredesReal=0;
        bitSubred=0;
        mascara="";
        frame.getContentPane().removeAll();
        //imprimir IP ingresada
        JLabel labelIP = new JLabel("IP dada: " + ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3]+" /"+((4-claveMascara)*8));
        labelIP.setBounds(10, 5, 170, 30);
        labelIP.setFont(font1);

        //etiquetas de los valores recibidos
        JLabel labelHostsR = new JLabel();
        JLabel labelSubredesR = new JLabel();
        labelHostsR.setBounds(10, 45, 160, 20);
        labelSubredesR.setBounds(10, 70, 150, 20);
        labelHostsR.setFont(font2);
        labelSubredesR.setFont(font2);

        labelHostsR.setText("Hosts pedidos: " + String.valueOf(hosts));
        labelSubredesR.setText("Subredes pedidas: " + String.valueOf(subredes));
        //etiquetas de los calulados
        JLabel labelHosts = new JLabel();
        JLabel labelSubredes = new JLabel();
        labelHosts.setBounds(220, 45, 160, 20);
        labelSubredes.setBounds(220, 70, 150, 20);
        //calculos
        if (hosts != 0) {
            labelSubredes.setText("Subredes: " + String.valueOf(sacarSubredes(claveMascara, hosts,frame)));
            labelHosts.setText("Hosts por red: " + String.valueOf(hostsReal));
            
        } else {
            labelHosts.setText("Hosts por red: " + String.valueOf(sacarHosts(claveMascara, subredes,frame)));
            labelSubredes.setText("Subredes: " + String.valueOf(subredesReal));
        }

        //Resolver mascara de red
        JLabel labelMascara = new JLabel();
        labelMascara.setBounds(220, 5, 190, 45);
        labelMascara.setText("<html>Mascara de Red:<br/>"+mascara+"</html>");
        //Conseguir los datos sobrenates
        JLabel hostsSobrantes = new JLabel();
        JLabel subredSobrantes = new JLabel();
        JLabel sobrante = new JLabel("Sobrante");
        sobrante.setBounds(400, 10, 180, 20);
        hostsSobrantes.setBounds(420,45,160,20);
        subredSobrantes.setBounds(420,70,150,20);
        hostsSobrantes.setText(String.valueOf(hostsReal-hosts));
        subredSobrantes.setText(String.valueOf(subredesReal-subredes));
        //mnostrar bits de red y hosts
        //JLabel labelSub = new JLabel();
        labelSub.setBounds(500,15,100,20);
        //JLabel labelH = new JLabel("bits H: "+String.valueOf(hostsReal));
        labelH.setBounds(500,30,100,20);
        
        //Mandamos los datos para procesar la tabla
        frame.add(labelHosts);
        frame.add(labelSubredes);
        frame.add(labelHostsR);
        frame.add(labelSubredesR);
        frame.add(labelIP);
        frame.add(labelMascara);
        frame.add(subredSobrantes);
        frame.add(hostsSobrantes);
        frame.add(sobrante);
        frame.add(labelSub);
        frame.add(labelH);
        frame.repaint();
        
        CrearTabla.resolver(subredesReal,hostsReal,claveMascara,ip,frame);
    }

    public static void sacarMascara(int bits, int clave) {    
        int cont = 0, bitsC = ((4 - clave) * 8) + bits;
        while (bitsC >= 8) {
            mascara += "255.";
            bitsC -= 8;
            cont++;
        }
        if (bitsC > 0) {
            int exp = 7;
            int suma = 0;
            for (int i = 0; i < bitsC; i++) {
                suma += (int)Math.pow(2, exp);
                exp--;
            }
            mascara += suma+".";
            cont++;
        }
        if(cont<4){
           while(cont!=4){
               mascara+="0.";
               cont++;
           }
        }
    }

//Para sacar los valores reales de hosts y subredes
    public static double sacarHosts(int claveMascara, int subredes,JFrame frame) {
        boolean flag = true;
        int exponente = 1;
        while (flag) {
            if ((Math.pow(2, exponente)) >= subredes) {
                flag = false;
                subredesReal = (int) Math.pow(2, exponente);
                labelSub.setText("bits R: "+String.valueOf(exponente));
            } else {
                exponente++;
            }
        }
        if (exponente <= (claveMascara * 8) - 1) {
            double resultado = (claveMascara * 8) - exponente;
            labelH.setText("bits H: "+String.valueOf(resultado));
            resultado = Math.pow(2, resultado)-2;//Para hacer hosts usbles falta -2
            hostsReal=(int)resultado;         
            sacarMascara(exponente, claveMascara);
            return resultado;
        } else {
            //CONSIDERAR PANTALLA DE ERROR ACA
            JOptionPane.showMessageDialog(null, "<html>Error calculando Hosts,<br/> desde Clase ResolverTabla.sacarHosts</html>");
            //PrimerFrame.pedirDatos(frame);
            return 0;
        }
    }

    public static double sacarSubredes(int claveMascara, int hosts,JFrame frame) {//FALTA MODIFICACION------------------------------
        boolean flag = true;
        int exponente = 1;
        while (flag) {
            if ((Math.pow(2, exponente) - 2) >= hosts) {
                flag = false;
                hostsReal = (int) Math.pow(2, exponente)-2; //si queremos hosts usables falta el -2
                labelH.setText("bits H: "+String.valueOf(exponente));
//System.out.println("Asignación de hosts: "+hostsReal);
            } else {
                exponente++;
            }
        }
        if (exponente <= (8 * claveMascara)) {//confirmar que los bits utilizables no exceden los de hosts
            //sacar las subredes
            double bitsRed = (8 * claveMascara) - exponente;
            sacarMascara((int) bitsRed, claveMascara);
            labelSub.setText("bits R: "+String.valueOf(bitsRed));
            bitsRed = Math.pow(2, bitsRed);
            subredesReal= (int)bitsRed;
            return (int) bitsRed;
        } else {
            //CONSIDERAR PANTALLA DE ERROR ACA
            JOptionPane.showMessageDialog(null, "<html>Error calculando Subredes,<br/> desde Clase ResolverTabla.sacarHosts</html>");
            //PrimerFrame.pedirDatos(frame);
            return 0;
        }
    }
}
