package packVista;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import packControlador.Controlador;



public class VentanaConfiguracion extends JFrame{
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField textFieldNombre;
	private JTextField textFieldDesc;
	private JButton button1 ;
	private JButton button2 ;
	private JButton button3 ;
	private JButton button4;
	private JComboBox<Integer> cBox;
	private JLabel label;
	private ArrayList<String> imagenes;
	private String imagenAct;
	private ArrayList<Integer> numeros;
	private String nombre;
	private String desc;
	
	
	public static void main(String[] args){
    	EventQueue.invokeLater(() -> {
            try {
                VentanaConfiguracion frame = new VentanaConfiguracion();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
	
    
    public VentanaConfiguracion(){
    super("Crear una Configuración");
    
    
    //Nombre ----
    nombre = "ConfNueva";//+Controlador.getMiControlador().getUsuarioAct().getCuantasConfig()+1;
    JLabel lblNombre = new JLabel("Nombre:");
    lblNombre.setBounds(230,60,100,40);
    add(lblNombre);
    textFieldNombre = new JTextField();
    textFieldNombre.setBounds(230,90,100,25);
    add(textFieldNombre);
    
    //Descripción ----
    desc = "Otra configuración más.";
    JLabel lblDesc = new JLabel("Descripción:");
    lblDesc.setBounds(230,110,100,40);
    add(lblDesc);
    textFieldDesc = new JTextField();
    textFieldDesc.setBounds(230,140,200,50);
    add(textFieldDesc);
    
    //Botones
    button1 = new JButton("Buscar Imagen");
    button1.setBounds(20,20,200,40);
    add(button1);
    button2 = new JButton("Aceptar");
    button2.setEnabled(false);
    button2.setBounds(660,400,100,40);
    add(button2);
    button3 = new JButton("Añadir Imagen");
    button3.setBounds(20,400,200,40);
    button3.setEnabled(false);
    add(button3);
    button4 = new JButton("Cancelar");
    button4.setBounds(540,400,100,40);
    add(button4);
    
    //Tabla
    table = new JTable();
    table.setBounds(540,20,220,360);
    add(table);
    table.setFillsViewportHeight(true);
	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    //Label Img
    label = new JLabel();
    label.setBounds(50,70,670,250);
    add(label);

    //ComboBox Números
    cBox = new JComboBox<Integer>();
    for(int i=1; i<13;i++){
    	cBox.addItem(i);
    }
    
    cBox.setBounds(230,20,100,40);
    add(cBox);
    
    imagenes = new ArrayList<String>();
    numeros = new ArrayList<Integer>();
    
    button4.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e) {
    		imagenes = new ArrayList<String>();
    		numeros = new ArrayList<Integer>();
    		button3.setEnabled(false);
    		button2.setEnabled(false);
    		label.setIcon(null);
    		setVisible(false);
    	}
    });
    
    button2.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e) {
    		
	    	if(imagenes.size()!=12){
	    		JOptionPane.showMessageDialog(rootPane, "Debes introducir imágenes para todas las cartas.");
	    	}
	    	else{
	    		try {
					Controlador.getMiControlador().crearConf(imagenes, numeros, nombre, desc, Controlador.getMiControlador().getUsuarioAct());
					JOptionPane.showMessageDialog(rootPane, "Configuración realizada con éxito.");
					setVisible(false);
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
	    	}
    	 }
    });
    
    button3.addActionListener(new ActionListener(){
    	 public void actionPerformed(ActionEvent e) {
    		 
    		 int cartaSel = cBox.getSelectedIndex()+1;
    		 button2.setEnabled(true);
    		 button3.setEnabled(false);
    		 label.setIcon(null);
    		 
    		 if(numeros.contains(cartaSel)){ 
    			 
    			 imagenes.remove(numeros.indexOf(cartaSel));
    			 imagenes.add(numeros.indexOf(cartaSel), imagenAct);
    			 
    		 }
    		 else{
    			 numeros.add(cartaSel);
        		 imagenes.add(imagenAct);
    		 }
    		 
    		 //GESTIÓN DE LA TABLA CADA VEZ QUE SE AÑADE UNA IMAGEN
    		 Vector<String> fila;
    	     Vector<Vector<String>> todo = new Vector<>();
    		 for(int i=0; i < imagenes.size(); i++) {
	                fila = new Vector<>();

	                String imagen = imagenes.get(i);
	                String numero = numeros.get(i).toString();

	                fila.add(imagen);
	                fila.add(numero);

	                todo.add(fila);
	            }
    		 
    		 	Vector<String> columnas = new Vector<>();
			 	columnas.add("Imágenes");
		        columnas.add("Cartas");
    		 
    		 table.setModel(new DefaultTableModel(todo,columnas){
    				
    			 
    				
    				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
					boolean[] columnEditables = new boolean[] {
    					false, false
    				};
    				public boolean isCellEditable(int row, int column) {
    					return columnEditables[column];
    				}
    			});
    	 }
    });
    
    button1.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e) {
        
          JFileChooser file = new JFileChooser();
          file.setCurrentDirectory(new File(System.getProperty("user.home")));
          //filter the files
          FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg");
          file.addChoosableFileFilter(filter);
          file.setFileFilter(filter);
          int result = file.showSaveDialog(null);
          
          
           //if the user click on save in Jfilechooser          
          if(result == JFileChooser.APPROVE_OPTION){
        	  
        	  
        	  button3.setEnabled(true);
        	  
              File selectedFile = file.getSelectedFile();
              String path = selectedFile.getAbsolutePath();
              
              InputStream inStream = null;
          	  OutputStream outStream = null;
          		
              	try{
              		
              	    File afile =new File(path);
              	    String[] archivo = path.split("\\\\");
              	    imagenAct = archivo[archivo.length-1];
              	    
              	    
              	    for(int i = 0; i<archivo.length; i++){
              	    	System.out.println(archivo[i]);
              	    }
              	    System.out.println(archivo[archivo.length-1]);
              	    File bfile =new File("resources/images/"+archivo[archivo.length-1]);
              		
              	    inStream = new FileInputStream(afile);
              	    outStream = new FileOutputStream(bfile);
                  	
              	    byte[] buffer = new byte[1024];
              		
              	    int length;
              	    //copy the file content in bytes 
              	    while ((length = inStream.read(buffer)) > 0){
              	  
              	    	outStream.write(buffer, 0, length);
              	 
              	    }
              	 
              	    inStream.close();
              	    outStream.close();
              	    
              	    System.out.println("File is copied successful!");
            	    
              	}catch(IOException ex){
            	    ex.printStackTrace();
            	}
              	
              label.setIcon(ResizeImage(path));
          }
           //if the user click on save in Jfilechooser


          else if(result == JFileChooser.CANCEL_OPTION){
              System.out.println("No File Select");
          }
        }
    });
    
    setLayout(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setSize(800,500);
    setVisible(true);
    }
     
     // Methode to resize imageIcon with the same size of a Jlabel
    public ImageIcon ResizeImage(String ImagePath)
    {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(150, 250, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }
    

   }


