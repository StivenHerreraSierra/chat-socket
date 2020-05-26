package co.uniquindio.lenguaje.interfazCliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import co.uniquindio.lenguaje.mundoCliente.Cliente;
import co.uniquindio.lenguaje.mundoServidor.Fecha;
import co.uniquindio.lenguaje.mundoServidor.Usuario;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JPasswordField;

public class InterfazRegistro extends JFrame implements ActionListener {

	private static final long serialVersionIUD = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtPass;
	private JButton btnAceptar;
	private InterfazLogin login;
	private JTextField txtDia;
	private JTextField txtMes;
	private JTextField txtAnio;
	private JLabel lblNewLabel_6;
	private String ruta = "src/usuarios.txt";
	private Cliente cliente;

	/**
	 * Create the frame.
	 */
	public InterfazRegistro(InterfazLogin login) {
		this.cliente = new Cliente();
		cliente.setInterfazLogin(login);
		this.cliente.setInterfazRegistro(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 312, 324);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(175, 221, 89, 23);
		contentPane.add(btnAceptar);
		btnAceptar.addActionListener(this);

		JLabel lblNewLabel = new JLabel("Usuario:");
		lblNewLabel.setBounds(27, 81, 73, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a:");
		lblNewLabel_1.setBounds(27, 112, 89, 14);
		contentPane.add(lblNewLabel_1);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(100, 75, 164, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("REGISTRO");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.ITALIC, 30));
		lblNewLabel_3.setBounds(63, 25, 164, 28);
		contentPane.add(lblNewLabel_3);

		txtPass = new JPasswordField();
		txtPass.setBounds(100, 106, 164, 20);
		contentPane.add(txtPass);
		
		JLabel lblNewLabel_2 = new JLabel("Dia:");
		lblNewLabel_2.setBounds(27, 173, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_4 = new JLabel("Mes:");
		lblNewLabel_4.setBounds(27, 198, 46, 14);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("A\u00F1o:");
		lblNewLabel_5.setBounds(27, 225, 46, 14);
		contentPane.add(lblNewLabel_5);
		
		txtDia = new JTextField();
		txtDia.setBounds(61, 167, 86, 20);
		contentPane.add(txtDia);
		txtDia.setColumns(10);
		
		txtMes = new JTextField();
		txtMes.setBounds(61, 195, 86, 20);
		contentPane.add(txtMes);
		txtMes.setColumns(10);
		
		txtAnio = new JTextField();
		txtAnio.setBounds(61, 222, 86, 20);
		contentPane.add(txtAnio);
		txtAnio.setColumns(10);
		
		lblNewLabel_6 = new JLabel("Fecha Nacimiento:");
		lblNewLabel_6.setBounds(27, 148, 120, 14);
		contentPane.add(lblNewLabel_6);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAceptar) {
			
			Fecha fecha = new Fecha(Short.parseShort(txtDia.getText()), Short.parseShort(txtMes.getText()), Short.parseShort(txtAnio.getText()));
			try {
				cliente.crearConexionRegistro(txtUsuario.getText(), txtPass.getText(), fecha);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void registrarse(boolean check) {
		if(check) {
			JOptionPane.showMessageDialog(null, "No se ha podido registrar");
		}else {
			JOptionPane.showMessageDialog(null, "Usuario Registrado");
			InterfazPrincipal interfazPrincipal = new InterfazPrincipal(cliente);
			interfazPrincipal.setVisible(true);
			this.setVisible(false);
		}
	}
	

}
