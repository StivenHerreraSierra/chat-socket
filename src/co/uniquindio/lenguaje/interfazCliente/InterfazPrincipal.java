package co.uniquindio.lenguaje.interfazCliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JButton;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.ListModel;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import co.uniquindio.lenguaje.mundoCliente.Cliente;
import co.uniquindio.lenguaje.mundoServidor.Usuario;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class InterfazPrincipal extends javax.swing.JFrame
		implements ActionListener, ListSelectionListener, WindowFocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton cambiaFoto;
	private String imagen;
	private JLabel miFoto;
	private JPanel miPanelFoto;
	private JScrollPane miScroll;
	private JButton enviarATodos;
	private JButton mandarArchivo;
	private JButton cambiarFecha;
	private JList miLista;
	private JLabel miMensaje;
	private Cliente cliente;
	private InterfazCliente miInterfazCliente;
	private JButton cierraSesion;
	private ArrayList<String> nomUsers;
	private VentanaMensajePersonal ventPrivada;
	private String usuario;
	private String contrasena;

	public InterfazPrincipal(Cliente cliente) {
		this.cliente = cliente;
		this.cliente.setInterfazPrincipal(this);
		initGUI();
		/**
		 * Cuando se crear un cliente se deben pedir todos los usuarios para actualizar
		 * la ventana
		 */
		ponerActivos(cliente.pedirUsuarios());

		miInterfazCliente = new InterfazCliente(this);
		miInterfazCliente.setTitle(cliente.getUsuario());
		setVisible(true);

		addWindowFocusListener(this);

	}

	private void initGUI() {
		try {
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			getContentPane().setLayout(null);
			this.setTitle(cliente.getUsuario());
			{
				miPanelFoto = new JPanel();
				getContentPane().add(miPanelFoto);
				miPanelFoto.setBounds(5, 0, 212, 98);
				miPanelFoto.setLayout(null);
				miPanelFoto.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				{
					cambiaFoto = new JButton();
					miPanelFoto.add(cambiaFoto);
					cambiaFoto.setText("Imagen para mostrar");
					cambiaFoto.setBounds(7, 60, 184, 18);
					cambiaFoto.addActionListener(this);
				}
				{
					miFoto = new JLabel();
					miPanelFoto.add(miFoto);
					miFoto.setIcon(new ImageIcon(getClass().getClassLoader().getResource("fotos/pelota.JPG")));
					miFoto.setBounds(14, 14, 183, 40);
				}
			}
			{
				miScroll = new JScrollPane();
				getContentPane().add(miScroll);
				miScroll.setBounds(5, 136, 283, 341);
				{
					ListModel miListaModel = new DefaultComboBoxModel(new String[] { "", "" });
					miLista = new JList();
					miScroll.setViewportView(miLista);
					miLista.setModel(miListaModel);
					miLista.setPreferredSize(new java.awt.Dimension(280, 336));
					miLista.addListSelectionListener(this);
				}
			}
			{
				enviarATodos = new JButton();
				getContentPane().add(enviarATodos);
				enviarATodos.setText("Enviar Mensaje a Todos los contactos");
				enviarATodos.setBounds(12, 483, 239, 21);
				enviarATodos.addActionListener(this);
			}
			{	
				cambiarFecha = new JButton();
				getContentPane().add(cambiarFecha);
				cambiarFecha.setText("Cambiar fecha de nacimiento");
				cambiarFecha.setBounds(12, 520, 200, 21);
				cambiarFecha.addActionListener(this);
			}
			    mandarArchivo = new JButton();
			    getContentPane().add(mandarArchivo);
			    mandarArchivo.setText("Enviar archivo a todos los contactos");
			    mandarArchivo.setBounds(12, 500, 239, 21);
			    mandarArchivo.addActionListener(this);
			{
				miMensaje = new JLabel();
				getContentPane().add(miMensaje);
				miMensaje.setBounds(12, 515, 305, 19);
			}
			{
				cierraSesion = new JButton();
				getContentPane().add(cierraSesion);
				cierraSesion.setText("Cerrar Sesion");
				cierraSesion.setBounds(12, 110, 177, 21);
				cierraSesion.addActionListener(this);
			}
			pack();
			this.setSize(332, 580);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == cambiaFoto) {
			JFileChooser fc = new JFileChooser("./fotos");
			fc.setDialogTitle("Buscar foto de la Persona");
			fc.setMultiSelectionEnabled(false);

			int resultado = fc.showOpenDialog(this);
			if (resultado == JFileChooser.APPROVE_OPTION) {
				imagen = fc.getSelectedFile().getAbsolutePath();
				// foto.setText( imagen );
				miFoto.setIcon(new ImageIcon(imagen));
			}

		}
		if (e.getSource() == enviarATodos) {

			miInterfazCliente.setVisible(true);
			ponerActivos(cliente.pedirUsuarios());

		}
		if(e.getSource() == cambiarFecha) {
			String fecha = JOptionPane.showInputDialog("Ingrese su nueva fecha dia/mes/a�o");
			cliente.cambiarFecha(fecha);
		}
		if(e.getSource() == mandarArchivo) {
			
			JFileChooser selec = new JFileChooser("src/archivosServidor");
			selec.setDialogTitle("Seleccione el archivo para enviar");
			selec.setMultiSelectionEnabled(false);
			int a = selec.showOpenDialog(this);
			if(a == JFileChooser.APPROVE_OPTION) {
				String archivo = selec.getSelectedFile().getAbsolutePath();
				cliente.mandarArchivoVarios(archivo);
			}
			
		}
		if (e.getSource() == cierraSesion) {
			
			System.out.println(cliente.getUsuario());
			retiraUser(cliente.getUsuario());
			ponerActivos(cliente.pedirUsuarios());
			ponerDatosList(this.miLista, nomUsers);
			try {
				cliente.borrarCliente();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			setVisible(false);

		}
	}

	public void cambiarFecha(boolean cambiar) {
		if(cambiar) {
			JOptionPane.showMessageDialog(null, "Fecha cambiada!");
		}
	}
	
	public void mostrarMsg(String msg) {
		this.miMensaje.setText(msg + "\n");
	}

	public void agregarUser(String user) {
		// nomUsers.add(user);
		// ponerDatosList(this.lstActivos,nomUsers);
	}

	public void mensajeAmigoPersonal(String amigo, String msg) {
		if (ventPrivada == null) {
			ventPrivada = new VentanaMensajePersonal(this);
		}
		ventPrivada.setAmigo(amigo);
		ventPrivada.mostrarMensaje(msg);
		ventPrivada.setVisible(true);
	}

	public void ponerActivos(ArrayList<String> vector) {
		nomUsers = vector;
		ponerDatosList(this.miLista, nomUsers);

	}

	private void ponerDatosList(JList list, final ArrayList<String> datos) {
		list.setModel(new AbstractListModel() {
			/**
			 * 
			 */
			// private static final long serialVersionUID = 1L;	
			@Override
			public int getSize() {
				return datos.size();
			}

			@Override
			public Object getElementAt(int i) {
				return datos.get(i);
			}
		});
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void retiraUser(String user) { // JOptionPane.showMessageDialog(null, user);
		nomUsers.remove(user);

		ponerDatosList(this.miLista, nomUsers);
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public InterfazCliente getMiInterfazCliente() {
		return miInterfazCliente;
	}

	public void setMiInterfazCliente(InterfazCliente miInterfazCliente) {
		this.miInterfazCliente = miInterfazCliente;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

		{
			int pos = this.miLista.getSelectedIndex();
			if (pos >= 0) {
				ventPrivada = new VentanaMensajePersonal(this);
				ventPrivada.setAmigo(nomUsers.get(pos));
				ventPrivada.setVisible(true);
			}
		}
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {

		ponerActivos(cliente.pedirUsuarios());
	}

	@Override
	public void windowLostFocus(WindowEvent e) {

		ponerActivos(cliente.pedirUsuarios());
	}

}
