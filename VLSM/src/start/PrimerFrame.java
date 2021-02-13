package start;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import tabla.Procesamiento;

public class PrimerFrame {
    static String newip;
    public static int bitsExtra;
    public static void pedirDatos(JFrame frame) {
        frame.getContentPane().removeAll();
        bitsExtra=0;
//Pedir numero de subredes 
        JLabel label1 = new JLabel("Numero de subredes:");
        label1.setBounds(10, 10, 125, 20);
        JTextField tSub = new JTextField();
        tSub.setBounds(145, 10, 30, 20);
        JButton bSub = new JButton("OK");
        bSub.setBounds(185, 10, 55, 20);
        
        JLabel lip = new JLabel("IP: ");
        lip.setBounds(10,40,70,20);
        JLabel lmasc = new JLabel("Mascara de red: ");
        lmasc.setBounds(10,70,130,20);
        
        JTextField tip = new JTextField();
        tip.setBounds(40,40,90,20);
        JTextField tmasc = new JTextField();
        tmasc.setBounds(120,70,50,20);

        //Valor predeterminado
        tSub.setText("3");
        tip.setText("10.11.11.0");
        tmasc.setText("16");
        
        bSub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == bSub) {
                    int masc = Integer.parseInt(tmasc.getText())/8;
                    bitsExtra = Integer.parseInt(tmasc.getText())%8;
                    try {
                        mostrarRenglones(frame, Integer.parseInt(tSub.getText()),tip.getText(),masc);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Debes ingresar un numero entero");
                    }
                }
            }
        });
        frame.add(lmasc);
        frame.add(lip);
        frame.add(tip);
        frame.add(tmasc);
        frame.add(label1);
        frame.add(tSub);
        frame.add(bSub);
        frame.repaint();
    }

    public static void mostrarRenglones(JFrame frame, int subredes,String ip, int mascara) {
        frame.getContentPane().removeAll();
        //Modificar tamañod del frame
        if(subredes>16){
            int medida = subredes-16;
            frame.setSize(500, 400+(medida*21));
        }
        
        //Lista de espacios JText
        ArrayList<JTextField> hosts = new ArrayList<>();
        ArrayList<JTextField> nombres = new ArrayList<>();
        ArrayList<JLabel> red = new ArrayList<>();
        
        //Mostrar ip y mascara
        JLabel lmasc = new JLabel("Mascara: "+(String.valueOf((mascara*8)+bitsExtra)));
        lmasc.setBounds(350,50,120,20);
        JLabel lip = new JLabel();
        lip.setBounds(350,20,150,20);
        String[] secip = ip.split("\\.");
        newip="";
        int masMasc=0;
        if(PrimerFrame.bitsExtra!=0){
            masMasc=1;
        }
        for(int i=0;i<4;i++){
            if(i<mascara+masMasc){
                newip += secip[i]+".";
            }else{
                newip += "0.";
            }
        }
        lip.setText("IP dada: "+newip);
        
        //Renglones
        JLabel labelRed= new JLabel("#R");
        labelRed.setBounds(10,10,40,20);
        JLabel labelHost = new JLabel("#H");
        labelHost.setBounds(60,10,40,20);
        JLabel labelNombre = new JLabel("NombreR");
        labelNombre.setBounds(110,10,80,20);
        for (int i = 0; i < subredes; i++) {
            red.add(new JLabel(String.valueOf(i+1)));
            hosts.add(new JTextField());
            nombres.add(new JTextField());
            red.get(i).setBounds(15,40+(i*20),40,15);
            hosts.get(i).setBounds(50,40+(i*20),35,16);
        // Valor predeterminado    
            //nombres.get(i).setText("");
            nombres.get(i).setBounds(100,40+(i*20),100,16);
            
            frame.add(red.get(i));
            frame.add(hosts.get(i));
            frame.add(nombres.get(i));
        }
        
        JButton button = new JButton("Ok");
        button.setBounds(230,40+((subredes-1)*20),60,20);
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == button) {
                    String[][] datos = new String[subredes][2];
                    for(int i=0; i<subredes;i++){
                        datos[i][0] = hosts.get(i).getText();
                        //System.out.println("hots: "+hosts.get(i).getText());
                        datos[i][1] = nombres.get(i).getText();
                       /* hosts.get(i).setEditable(false);
                        nombres.get(i).setEditable(false);*/
                       frame.remove(hosts.get(i));
                       frame.remove(nombres.get(i));
                       frame.remove(red.get(i));
                    }
                    frame.remove(labelRed);
                    frame.remove(labelHost);
                    frame.remove(labelNombre);
                    //aquí llamamos a otra clase para compenzar a trabajr datos
                    Procesamiento.ordenamiento(datos,newip,frame);
                }
                frame.remove(button);
            }
        });
        
        frame.add(labelRed);
        frame.add(labelHost);
        frame.add(labelNombre);
        frame.add(button);
        frame.add(lip);
        frame.add(lmasc);
        frame.repaint();
    }
    
}
