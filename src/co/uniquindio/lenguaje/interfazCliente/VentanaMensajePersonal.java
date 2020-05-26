/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.uniquindio.lenguaje.interfazCliente;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;

/**
 *
 * @author Administrador
 */
public class VentanaMensajePersonal extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea areaMostrar;
	private InterfazPrincipal miInterfazPrincipal;
	private JTextField textoMensaje;
	private JButton botonEnviar;
	private String nombreAmigo;
	private JButton btnNewButton;

	public VentanaMensajePersonal(InterfazPrincipal miInterfazPrincipal) {
		super("Amigo");
		this.miInterfazPrincipal = miInterfazPrincipal;
		textoMensaje = new JTextField(30);
		botonEnviar = new JButton("Enviar");
		areaMostrar = new JTextArea();
		areaMostrar.setEditable(false);
		textoMensaje.requestFocus();
		textoMensaje.addActionListener(this);
		botonEnviar.addActionListener(this);

		JPanel panAbajo = new JPanel();
		panAbajo.setLayout(new BorderLayout());
		panAbajo.add(new JLabel("  Ingrese mensage a enviar:"), BorderLayout.NORTH);
		panAbajo.add(textoMensaje, BorderLayout.CENTER);
		panAbajo.add(botonEnviar, BorderLayout.EAST);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JScrollPane(areaMostrar), BorderLayout.CENTER);
		getContentPane().add(panAbajo, BorderLayout.SOUTH);
		
		btnNewButton = new JButton("Enviar Archivo");
		panAbajo.add(btnNewButton, BorderLayout.SOUTH);

		nombreAmigo = "";

		this.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) {
				cerrarVentana();
			}

			public void windowClosed(WindowEvent e) {
			}

			public void windowOpened(WindowEvent e) {
			}

			public void windowIconified(WindowEvent e) {
			}

			public void windowDeiconified(WindowEvent e) {
			}

			public void windowActivated(WindowEvent e) {
			}

			public void windowDeactivated(WindowEvent e) {
			}

		});

		setSize(300, 290);
		setLocation(570, 90);
	}

	public void setAmigo(String nombreAmigo) {
		this.nombreAmigo = nombreAmigo;
		this.setTitle(nombreAmigo);
	}

	private void cerrarVentana() {
		this.setVisible(false);
	}

	public void mostrarMensaje(String msg) {
		this.areaMostrar.append(msg + "\n");
	}

	public void actionPerformed(ActionEvent e) {
		String mensaje = textoMensaje.getText();
		mostrarMensaje(miInterfazPrincipal.getCliente().getUsuario() + ">" + mensaje);
		try {
			miInterfazPrincipal.getCliente().escribirFlujo(nombreAmigo, mensaje);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e1);
		}
		textoMensaje.setText("");
	}
}
