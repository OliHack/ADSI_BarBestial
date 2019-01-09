package packVista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Vector;

public class VentanaSiempre extends JFrame {
	private static final long serialVersionUID = 1L;
	private static VentanaSiempre miSiempre;
	private JPanel contentPane;
    private JTable table;

    /**
     * Create the frame.
     */
    public VentanaSiempre() {
    	setTitle("TOP 10 DE TODOS LOS TIEMPOS");
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
            	VentanaSiempre frame = new VentanaSiempre();//cambiar
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void actualizarRanking(Vector<Vector<String>> pData) { //cambiar
        Vector<String> columnas = new Vector<>();
        columnas.add("Nombre");
        columnas.add("Fecha");
        columnas.add("Puntuacion");

        table = new JTable(pData, columnas);

        contentPane.setLayout(new BorderLayout());
        contentPane.add(table.getTableHeader(), BorderLayout.PAGE_START);
        contentPane.add(table, BorderLayout.CENTER);
        table.setFillsViewportHeight(true);
    }
    
    public static VentanaSiempre getSiempre() {
		if (miSiempre == null) {
			miSiempre = new VentanaSiempre();
		}
		return miSiempre;
	}
}