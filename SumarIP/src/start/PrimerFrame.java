package start;

import extras.Oper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class PrimerFrame {
   // public static JLabel respuesta;
    static int respuestas;
    //primerFrame es el frame que pide los datos
    public static void pedirDatos(JFrame frame) {
        //reiniciar frame junto con su tamaño
        frame.getContentPane().removeAll();
        frame.setSize(500, 300);
        //Etiquetas y espacios para escribit
        JLabel labelBase = new JLabel("IP: ");
        labelBase.setBounds(10,10,40,25);
        JTextField base = new JTextField();
        base.setBounds(50,10,80,25);
        JLabel labelSumar = new JLabel("Sumar: ");
        labelSumar.setBounds(140,10,50,25);
        JTextField sumar = new JTextField();
        sumar.setBounds(190,10,60,25);
        
        JButton button = new JButton("OK");
        button.setBounds(270,10,60,20);
        
        respuestas=1;
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == button) {
                    try{
                        resolver(base.getText(), Integer.parseInt(sumar.getText()), frame);
                        respuestas++; 
                    }catch(NumberFormatException e){
                        JOptionPane.showMessageDialog(null, "Hay campos vacios");
                    }
                    
                }
            }
        });
        
        JButton restart = new JButton("RESTART");
        restart.setBounds(350,10,90,20);
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == restart) {
                    pedirDatos(frame);
                }
            }
        });
        
        frame.add(labelBase);
        frame.add(base);
        frame.add(button);
        frame.add(labelSumar);
        frame.add(sumar);
        frame.add(restart);
        frame.repaint();
    }
    public static void resolver(String ip, int suma, JFrame frame) {

        JLabel respuesta = new JLabel();
        respuesta.setBounds(10,(respuestas*20)+40,210,20);
        
        String tempResp= ip + " + "+suma+" = ";
        String[] division = ip.split("\\.");
        tempResp +=Oper.sumar(division, suma);
        respuesta.setText(tempResp);
        
        if(respuestas>9){
            frame.setSize(500,(respuestas*20)+100);
        }
        
        frame.add(respuesta);
        frame.repaint();
    }
    //Preparamos los datos recibitos en los JTextArea
    

}
