package start;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import tabla.DatosReales;

public class PrimerFrame {

    //primerFrame es el frame que pide los datos
    public static void pedirDatos(JFrame frame) {
        frame.getContentPane().removeAll();
        JLabel indicaciones = new JLabel("<html>IP<br/><br/><br/>Subredes<br/><br/><br/>Hosts</html>");
        indicaciones.setBounds(15, 15, 100, 115);
        JLabel mascara = new JLabel("Mascara");
        mascara.setBounds(230, 20, 70, 15);
        //areas para escribir
        JTextArea ip = new JTextArea();
        ip.setBounds(100, 20, 90, 20);
        JTextArea subredes = new JTextArea();
        subredes.setBounds(100, 65, 90, 20);
        JTextArea hosts = new JTextArea();
        hosts.setBounds(100, 110, 90, 20);
        JTextArea tMascara = new JTextArea();
        tMascara.setBounds(305, 20, 50, 20);
        //boton para capturar respuestas
        JButton bCapturar = new JButton("Listo");
        bCapturar.setBounds(250, 110, 70, 20);
        
        JLabel labelError = new JLabel("<html>Forzosamente tenemos que<br/> mandar o hosts o subredes con 0</html>");
        labelError.setBounds(400, 20, 200, 60);
        frame.add(labelError);
        frame.repaint();      
        //Datos temporales para no perder tiempo escribiendo en las pruebas
        ip.setText("10.13.10.0");
        tMascara.setText("16");
        subredes.setText("512");
        hosts.setText("0");
//Capturar datos y enviarlos
        bCapturar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == bCapturar) {
                    try {
                        prepararDatos(ip.getText(), Integer.parseInt(subredes.getText()), Integer.parseInt(hosts.getText()), Integer.parseInt(tMascara.getText()), frame);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Algun elemento no cummple con el formato");
                    }
                }
            }
        });
        //Mandar a metodo que resueve la tabla
        /*
       Mamdamos ip, mascara, subredes, host, bandera_SubredHost
      Le mandamos ip, mascaraRed transformada, hosts, subredes,  
         */
        frame.add(tMascara);
        frame.add(mascara);
        frame.add(bCapturar);
        frame.add(hosts);
        frame.add(subredes);
        frame.add(ip);
        frame.add(indicaciones);
        frame.repaint();
    }

    //Preparamos los datos recibitos en los JTextArea
    public static void prepararDatos(String ip, int subredes, int hosts, int mascara, JFrame frame) {
        //mascara
        int claveMascara = mascara / 8;
        claveMascara -= 4;
        claveMascara *= -1;
        //IP
        String[] tempIP = ip.split("\\.");
        int[] ipOcteto = new int[4];
        for (int i = 0; i < 4; i++) {
            ipOcteto[i] = Integer.parseInt(tempIP[i]);
        }
        DatosReales.resolver(claveMascara, ipOcteto, hosts, subredes, frame);
    }

}
