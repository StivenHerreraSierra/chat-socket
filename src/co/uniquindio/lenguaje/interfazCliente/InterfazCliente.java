package co.uniquindio.lenguaje.interfazCliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;

import javax.swing.JButton;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import co.uniquindio.lenguaje.mundoCliente.Cliente;

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
public class InterfazCliente extends javax.swing.JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton cambiaFoto;
	private String imagen;
	private JPanel miPanelDigitacion;
	private JScrollPane miScrollDigitacion;
	private JLabel mensajesError;
	private JButton enviaMensajes;
	private JTextArea miAreaDigitacion;
	private JLabel miFoto;
	private JPanel miPanelFoto;
	private JTextArea miArea;
	private JScrollPane miScroll;
	private JPanel miPanel;
	private Cliente cliente;
	private InterfazPrincipal miInterfazPrincipal;

	/**
	 * Auto-generated main method to display this JFrame
	 */

	public InterfazCliente(InterfazPrincipal miInterfazPrincipal) {
		super();
		this.miInterfazPrincipal = miInterfazPrincipal;
		initGUI();
		cliente = miInterfazPrincipal.getCliente();
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			{
				miPanel = new JPanel();
				getContentPane().add(miPanel);
				miPanel.setBounds(39, 21, 317, 200);
				miPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				miPanel.setLayout(null);
				{
					miScroll = new JScrollPane();
					miPanel.add(miScroll);
					miScroll.setPreferredSize(new java.awt.Dimension(286, 181));
					miScroll.setBounds(15, 7, 286, 181);
					{
						miArea = new JTextArea();
						miScroll.setViewportView(miArea);
					}
				}
			}
			{
				miPanelFoto = new JPanel();
				getContentPane().add(miPanelFoto);
				miPanelFoto.setBounds(368, 29, 194, 220);
				miPanelFoto.setLayout(null);
				miPanelFoto.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				{
					cambiaFoto = new JButton();
					miPanelFoto.add(cambiaFoto);
					cambiaFoto.setText("Imagen para mostrar");
					cambiaFoto.setBounds(8, 185, 184, 18);
					cambiaFoto.addActionListener(this);
				}
				{
					miFoto = new JLabel();
					miPanelFoto.add(miFoto);
					miFoto.setIcon(new ImageIcon(getClass().getClassLoader().getResource("fotos/pelota.JPG")));
					miFoto.setBounds(14, 14, 172, 159);
				}
			}
			{
				miPanelDigitacion = new JPanel();
				getContentPane().add(miPanelDigitacion);
				miPanelDigitacion.setBounds(39, 267, 458, 119);
				miPanelDigitacion.setLayout(null);
				miPanelDigitacion.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				{
					miScrollDigitacion = new JScrollPane();
					miPanelDigitacion.add(miScrollDigitacion);
					miScrollDigitacion.setBounds(20, 14, 270, 95);
					{
						miAreaDigitacion = new JTextArea();
						miScrollDigitacion.setViewportView(miAreaDigitacion);
						miAreaDigitacion.setPreferredSize(new java.awt.Dimension(286, 82));
					}
				}
				{
					enviaMensajes = new JButton();
					miPanelDigitacion.add(enviaMensajes);
					enviaMensajes.setText("Enviar");
					enviaMensajes.setBounds(336, 22, 109, 21);

					enviaMensajes.addActionListener(this);
				}
			}
			{
				mensajesError = new JLabel();
				getContentPane().add(mensajesError);
				mensajesError.setBounds(51, 400, 479, 14);
			}
			pack();
			this.setSize(582, 459);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mostrarMsg(String msg) {
		this.miArea.append(msg + "\n");
	}

	public void setNombreUser(String user) {
		mensajesError.setText("Nombre");
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
				miFoto.setIcon(new ImageIcon(imagen));
			}

		}
		if (e.getSource() == enviaMensajes) {
			String mensaje = miAreaDigitacion.getText();
			try {
				/**
				 * Se escribe el flujo, esto significa que en la clase cliente se escribe 1 y se
				 * envia el mensaje salida.writeInt(1); salida.writeUTF(mens); La clase cliente
				 * maneja 3 numeros 1-->enviar mensaje a todos los contactos 2-->hace referencia
				 * a si hay un nuevo usuario 3-->mensaje a usuario particular
				 */
				cliente.escribirFlujo(mensaje);
			} catch (Exception e1) {
				mensajesError.setText(e1.getMessage());
			}
			miAreaDigitacion.setText("");
		}
	}
}
