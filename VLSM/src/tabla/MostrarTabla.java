package tabla;

import extras.Datos;
import extras.Oper;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import start.PrimerFrame;

public class MostrarTabla {

    public static Font font1 = new Font("Courier", Font.BOLD, 14);
    public static Font font2 = new Font("Courier", Font.PLAIN, 12);
    public static JTextArea tPedir = new JTextArea();
    public static JLabel labelPedido = new JLabel();

    public static void mostrar(ArrayList<Datos> rn, JFrame frame) {//host aqui considera ID y broadcast
        //frame.getContentPane().removeAll();
        frame.setSize(700, 600);
        Object[] columnNames = {"#", "ID red", "1ra IP", "last IP", "Broadcast", "Mascara", "HP", "HR","nombre"};
        Object[][] datos = new Object[rn.size()][9];
        //Para calcular hosts sobreantes
        JLabel nota = new JLabel("<html>Los hosts mostrados en tabla son los hosts usables, </br>"
                + "No se estan contando ID de red ni Broadcast</html>");
        nota.setBounds(10, 10, 200, 50);
        nota.setFont(font2);

        int pedidos = 0, reales = 0;
        JLabel lPedidos = new JLabel();
        JLabel lReales = new JLabel();
        JLabel sobrante = new JLabel();

        //Meter en la tabla
        for (int i = 0; i < rn.size(); i++) {
            datos[i][0] = i + 1;
            datos[i][1] = rn.get(i).getNum(0);
            datos[i][2] = rn.get(i).getNum(1);
            datos[i][3] = rn.get(i).getNum(2);
            datos[i][4] = rn.get(i).getNum(3);
            datos[i][5] = rn.get(i).getMasc();
            datos[i][6] = rn.get(i).getHostP();
            datos[i][7] = rn.get(i).getHostR() - 2;
            datos[i][8] = rn.get(i).getNombre();
            pedidos += rn.get(i).getHostP();
            reales += rn.get(i).getHostR() - 2;
        }
        sobrante.setText("THSobrante: " + String.valueOf(reales - pedidos));
        sobrante.setBounds(230, 10, 150, 20);
        lPedidos.setText("THP: " + String.valueOf(pedidos));
        lReales.setText("THR: " + String.valueOf(reales));
        lPedidos.setBounds(230, 40, 70, 20);
        lReales.setBounds(230, 70, 70, 20);

        JTable table = new JTable(datos, columnNames);
        table.setEnabled(true);
        TableColumnModel columnModel = table.getColumnModel();
        JScrollPane scrollPane = new JScrollPane();
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(130);
        columnModel.getColumn(6).setPreferredWidth(50);
        columnModel.getColumn(7).setPreferredWidth(50);
        columnModel.getColumn(8).setPreferredWidth(100);
        //Colores
        columnModel.getColumn(2).setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(Color.lightGray);
                return this;
            }
        });
        columnModel.getColumn(3).setCellRenderer(
                new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                setText(value.toString());
                setBackground(Color.lightGray);
                return this;
            }
        });

        table.setColumnModel(columnModel);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane.setViewportView(table);
        scrollPane.setBounds(5, 110, 500, 210);

        //Para peticiones particulares
        JLabel explicacion = new JLabel("<html>Ingresa:<br/>#renglon - #hostUsable<br/><br/>Solo IPs usables,<br/>Formato: 1-1</html>");
        explicacion.setBounds(555, 230, 130, 130);
        explicacion.setFont(font2);
        // -- -- --
        JLabel labelPedir = new JLabel("Host especifico");
        labelPedir.setBounds(550, 120, 130, 20);
        labelPedir.setFont(font1);
        //JTextArea tPedir = new JTextArea();
        tPedir.setBounds(550, 160, 60, 20);//Nomenclatura que recibe es 0-0 (R-H)

        JButton bPedir = new JButton("Pedirs");
        bPedir.setBounds(550, 190, 110, 20);
        bPedir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == bPedir) {
                    pedir(rn, frame, tPedir.getText());
                }
            }
        });
        JButton restart = new JButton("Restart");
        restart.setBounds(550, 500, 110, 20);
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == restart) {
                    PrimerFrame.pedirDatos(frame);
                }
            }
        });
        frame.add(labelPedir);
        frame.add(tPedir);
        frame.add(bPedir);
        frame.add(scrollPane);
        frame.add(explicacion);
        frame.add(restart);
        frame.add(nota);
        frame.add(sobrante);
        frame.add(lPedidos);
        frame.add(lReales);
        frame.repaint();
    }

    public static void pedir(ArrayList<Datos> rn, JFrame frame, String peticion) {
        String respuesta;
        String host;

        try {
            String[] t = peticion.split("-");
            int[] p = new int[2];
            p[0] = Integer.parseInt(t[0]);
            p[1] = Integer.parseInt(t[1]);
            p[0]--;
            if (p[1] <= rn.get(p[0]).getHostR() - 2) {
                System.out.println("hostR: "+rn.get(p[0]).getHostR());
                labelPedido.setFont(font2);
                //JLabel labelPedido = new JLabel();
                labelPedido.setBounds(30, 380, 480, 60);
                host = Oper.sumar(rn, p[0], 0, p[1]);
                respuesta = "<html>" + rn.get(p[0]).getNum(0) + " -- " + rn.get(p[0]).getNum(1) + " -- " + rn.get(p[0]).getNum(2) + " -- " + rn.get(p[0]).getNum(3);
                respuesta += "<br/>R#: " + (p[0] + 1) + ", Host #: " + p[1] + " ---> " + host + "</html>";
                //Verificamos que el host pedido este dentro de los hosts usables
                labelPedido.setText(respuesta);
            }else{
                labelPedido.setText("El host especificado esta fuera de rango los hosts disponibles");
            }
            //reiniciar variables
            frame.add(labelPedido);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException aa) {
            JOptionPane.showMessageDialog(null, "Error en formato");
        }

        frame.repaint();
    }

    public static void calcularHostSobrantes(JFrame frame) {

    }
}
