package co.uniquindio.lenguaje.interfazServidor;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import javax.swing.WindowConstants;

import co.uniquindio.lenguaje.mundoServidor.HiloServidor;
import co.uniquindio.lenguaje.mundoServidor.Servidor;

import javax.swing.SwingUtilities;

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
public class InterfazServidor extends javax.swing.JFrame implements WindowListener {
	private JScrollPane miScroll;
	private JList miList;
	private JLabel usuariosLabel;
	private Servidor miServidor;

	public InterfazServidor() {
		super();
		initGUI();
		addWindowListener(this);
		setVisible(true);

		miServidor = new Servidor();
		try {
			miServidor.runServer(8081,8082);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			this.setTitle("Servidor activo");
			getContentPane().setBackground(new java.awt.Color(219, 206, 253));
			{
				miScroll = new JScrollPane();
				getContentPane().add(miScroll);
				miScroll.setBounds(49, 49, 235, 265);
				{
					ListModel miListModel = new DefaultComboBoxModel(new String[] { "", "" });
					miList = new JList();
					miScroll.setViewportView(miList);
					miList.setModel(miListModel);
				}
			}
			{
				usuariosLabel = new JLabel();
				getContentPane().add(usuariosLabel);
				usuariosLabel.setText("Usuarios Conectados");
				usuariosLabel.setBounds(100, 12, 126, 14);
			}
			pack();
			this.setSize(333, 400);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
		try {
			String data[] = { "", "" };
			if (miServidor != null)
				miList.setListData(miServidor.getUser().enviarNombresUsuarios());
			else
				miList.setListData(data);
		} catch (Exception ex) {
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		miServidor = null;
		System.exit(0);
		JOptionPane.showMessageDialog(null, "El servidor muere");

	}

	@Override
	public void windowClosing(WindowEvent e) {
		miServidor = null;
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

}
