package start;

import javax.swing.JFrame;

/**
 * @author Erick
 */
public class Inicio {
   public static void main(String args[]){
       JFrame frame = new JFrame();
        frame.setSize(500, 400);
        frame.setTitle("VLSM");
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        
        PrimerFrame.pedirDatos(frame);
   }
   
}
