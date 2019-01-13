package packVista;

import java.awt.EventQueue;
import packControlador.Controlador;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.util.Vector;

public class VentanaSeleccionConfig extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private Vector<Vector<String>> todo;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
				try {
					VentanaSeleccionConfig frame = new VentanaSeleccionConfig();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaSeleccionConfig() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
        Vector<Vector<String>> todo = new Vector<>();
		
		JButton btnNewButton = new JButton("Seleccionar Configuración");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int confSel = table.getSelectedRow();
				if(confSel!=-1){
					table.getModel().getValueAt(confSel, 0);
				}
				else{
					JOptionPane.showMessageDialog(rootPane, "Debes seleccionar una Configuración.");
				}
			}
		});
		btnNewButton.setBounds(138, 213, 165, 23);
		getContentPane().add(btnNewButton);
		
		
	}

	@SuppressWarnings("serial")
	public void configUsuario(ResultSet rs) throws SQLException {
		
		Vector<String> fila;
				 while (rs.next()) {
		                fila = new Vector<>();

		                String idConfig = rs.getString("idConfig");
		                System.out.println(idConfig);
		                String nombre = rs.getString("nombre");
		                System.out.println(nombre);
		                String fecha = rs.getString("fecha");
		                System.out.println(fecha);

		                fila.add(idConfig);
		                fila.add(nombre);
		                fila.add(fecha);

		                todo.add(fila);
		            }
				 
					Vector<String> columnas = new Vector<String>();
				 	columnas.add("IdConfig");
			        columnas.add("Nombre");
			        columnas.add("Fecha");
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(todo,columnas) {
			/**
			 * 
			 */
			
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.setBounds(59, 41, 312, 146);
		getContentPane().add(table);
		
	}
}