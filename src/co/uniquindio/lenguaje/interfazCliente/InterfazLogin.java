package co.uniquindio.lenguaje.interfazCliente;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import co.uniquindio.lenguaje.mundoCliente.Cliente;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class InterfazLogin extends JFrame implements ActionListener {

	public static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JButton btnIngresar;
	private JButton btnRegistrar; 
	private Cliente cliente;
	private JTextField txtPass;
	
	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public InterfazLogin() throws Exception {
		
	    this.cliente = new Cliente();
	    this.cliente.setInterfazLogin(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 209);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnIngresar = new JButton("Ingresar");
		btnIngresar.setBounds(335, 123, 89, 23);
		contentPane.add(btnIngresar);
		btnIngresar.addActionListener(this);

		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setBounds(335, 75, 89, 23);
		contentPane.add(btnRegistrar);
		btnRegistrar.addActionListener(this);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(109, 76, 186, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);

		JLabel lblNewLabel = new JLabel("Usuario:");
		lblNewLabel.setBounds(10, 82, 89, 14);
		contentPane.add(lblNewLabel);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(10, 130, 89, 14);
		contentPane.add(lblContrasea);

		JLabel lblNewLabel_1 = new JLabel("BIENVENIDO");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 28));
		lblNewLabel_1.setBounds(45, 11, 261, 38);
		contentPane.add(lblNewLabel_1);
		
		txtPass = new JTextField();
		txtPass.setBounds(109, 124, 186, 20);
		contentPane.add(txtPass);
		txtPass.setColumns(10);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnIngresar) {		
			try {
				cliente.crearConexion(txtUsuario.getText(), txtPass.getText());
				
			}catch(Exception e1) {
				e1.printStackTrace();
			}	
		}
		if ( e.getSource() == btnRegistrar )
		{
			try {
				InterfazRegistro interfazRegistro = new InterfazRegistro(this);
				interfazRegistro.setVisible(true);
				dispose();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public void iniciar(boolean check, boolean cumpleanos) {
		if(check) {
			if(cumpleanos) {
				JOptionPane.showMessageDialog(null, "Felicidades, hoy estas cumpliendo años!");
				InterfazPrincipal interfazPrincipal = new InterfazPrincipal(cliente);
				interfazPrincipal.setVisible(true);
				this.setVisible(false);
			}else {
				InterfazPrincipal interfazPrincipal = new InterfazPrincipal(cliente);
				interfazPrincipal.setVisible(true);
				this.setVisible(false);
			}
		}else {
			JOptionPane.showMessageDialog(null, "Usuario no registrado");
		}
	}
	
	public Cliente getCliente ()
	{
		return cliente;
	}
	
}
	
	
	

