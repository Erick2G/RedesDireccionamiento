package start;
import javax.swing.JFrame;

public class Inicio {
   public static void main(String args[]){
       JFrame frame = new JFrame();
        //frame.setSize(500, 300);
        frame.setTitle("SUMA IP");
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        PrimerFrame.pedirDatos(frame);
   }
   
}
