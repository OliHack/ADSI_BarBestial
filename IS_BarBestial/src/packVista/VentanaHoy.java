package packVista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Vector;

public class VentanaHoy extends JFrame {
	private static final long serialVersionUID = 1L;
	private static VentanaHoy miHoy;
	private JPanel contentPane;
    private JTable table;

    /**
     * Create the frame.
     */
    public VentanaHoy() {
    	setTitle("LA MEJOR PUNTUACION DE HOY");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 220);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        /* Centrar */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize(); //Tamaño del frame actual (ancho x alto)
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
            	VentanaHoy frame = new VentanaHoy();//cambiar
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void actualizarRanking(Vector<Vector<String>> pData) { //cambiar
        Vector<String> columnas = new Vector<>();
        columnas.add("Nombre");
       // columnas.add("Fecha");
        columnas.add("Puntuacion");

        table = new JTable(pData, columnas);

        contentPane.setLayout(new BorderLayout());
        contentPane.add(table.getTableHeader(), BorderLayout.PAGE_START);
        contentPane.add(table, BorderLayout.CENTER);
        table.setFillsViewportHeight(true);
    }
    
    public static VentanaHoy getHoy() {
		if (miHoy == null) {
			miHoy = new VentanaHoy();
		}
		return miHoy;
	}
}