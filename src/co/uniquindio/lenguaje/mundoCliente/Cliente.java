/*
 * Cliente.java
 *
 * Created on 21 de marzo de 2008, 12:11 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package co.uniquindio.lenguaje.mundoCliente;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import co.uniquindio.lenguaje.interfazCliente.InterfazLogin;
import co.uniquindio.lenguaje.interfazCliente.InterfazPrincipal;
import co.uniquindio.lenguaje.interfazCliente.InterfazRegistro;
import co.uniquindio.lenguaje.mundoServidor.Fecha;
import co.uniquindio.lenguaje.mundoServidor.Usuario;

/**
 *
 * @author Administrador
 */
public class Cliente {
	public String ipServidor;
	private DataInputStream entrada = null;
	private DataInputStream entrada2 = null;
	private DataOutputStream salida = null;
	private DataOutputStream salida2 = null;
	private Socket comunication = null;// para la comunicacion
	private Socket comunication2 = null;// permite recibir el mensaje
	private Socket comunication3 = null;//permite enviar archivos
	private String usuario;
	private String contrasena;
	private Fecha fecha;
	private InterfazLogin miInterfazLogin;
	private HiloCliente miHiloCliente;
	private InterfazLogin InterfazLogin;
	private InterfazRegistro miInterfazRegistro;
	private InterfazRegistro interfazRegistro;
	private BufferedInputStream bis=null;
	private BufferedOutputStream bos=null;
	private InterfazPrincipal interfazPrincipal;

	public Cliente() {
		
	}
	/**
	 * Cuando un nuevo cliente se conecta se escribe el nombre del cliente
	 * 
	 * @param nombreCliente
	 * @throws IOException
	 * @throws Exception
	 */
	public void crearConexion(String usuario, String contrasena) throws IOException, Exception {
		crearCanales();
		this.usuario = usuario;
		this.contrasena = contrasena;
		miHiloCliente = new HiloCliente(entrada2, InterfazLogin, usuario);
		miHiloCliente.start();
		salida.writeInt(4);
		salida.writeUTF(usuario);
		salida.writeUTF(contrasena);
	}

	public void crearConexionRegistro(String usuario, String contrasena, Fecha fechaNacimiento) throws IOException {
		crearCanales();
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.fecha = fechaNacimiento;
		
		miHiloCliente = new HiloCliente(entrada2, InterfazLogin, usuario);
		miHiloCliente.setInterfazRegistro(interfazRegistro);
		miHiloCliente.start();
		
		salida.writeInt(5);
		salida.writeUTF(usuario);
		salida.writeUTF(contrasena);
		salida.writeInt(fechaNacimiento.getDia());
		salida.writeInt(fechaNacimiento.getMes());
		salida.writeInt(fechaNacimiento.getAnio());	
	}
	
	public void borrarCliente() throws IOException {
		entrada.close();
	}

	public String getUsuario() {
		return usuario;
	}

	/*
	 * Se requieren para enviar mensaje a todos los usuarios Es el servidor quien
	 * los env�a.
	 */
	public ArrayList<String> pedirUsuarios() {
		
		ArrayList<String> users = new ArrayList<String>();
		try {
			salida.writeInt(2);
			int numUsers = entrada.readInt();
			for (int i = 0; i < numUsers; i++) {
				users.add(entrada.readUTF());
			}
			
		} catch (IOException ex) {
//			 System.out.println("Error en el metodo");
		}
		return users;
	}

	/**
	 * El cliente da clic en la interfaz cliente para enviar mensaje a todos los
	 * contactos y llega el mensaje. El mensaje se le envia al hilo servidor. El
	 * hilo debe leerlo
	 * 
	 * @param mens
	 * @throws Exception
	 */
	public void escribirFlujo(String mens) throws Exception {
		salida.writeInt(1);
		salida.writeUTF(mens);
	}

	public void escribirFlujo(String amigo, String mens) throws Exception {
		try {
			salida.writeInt(3);
			salida.writeUTF(amigo);
			salida.writeUTF(mens);
		} catch (IOException e) {
			throw new Exception("error...." + e);
		}
	}
	
	public void crearCarpetaCliente (String nombre) {
		
		File directorio = new File ("src/archivosCliente/" + nombre);
		File docArchivosEnviados = new File ("src/archivosCliente/" + directorio.getName() + "/archivosEnviados");
		File docArchivosRecibidos = new File ("src/archivosCliente/" + directorio.getName() + "/archivosRecibidos");
		
		if(!directorio.exists()) {
			if(directorio.mkdirs() && docArchivosEnviados.mkdirs() && docArchivosRecibidos.mkdirs()) {
				System.out.println("Directorio creado");
			}
			else {
				System.out.println("Error al crear el directorio");
			}
		}
		
	}
		
	public void crearCanales() {
		try {
			comunication = new Socket(ipServidor, 8081);
			comunication2 = new Socket(ipServidor, 8082);
			comunication3 = new Socket(ipServidor, 8083);
			entrada = new DataInputStream(comunication.getInputStream());
			salida = new DataOutputStream(comunication.getOutputStream());
			salida2 = new DataOutputStream(comunication2.getOutputStream());
			entrada2 = new DataInputStream(comunication2.getInputStream());
			bis = new BufferedInputStream(comunication3.getInputStream());
			bos = new BufferedOutputStream(comunication3.getOutputStream());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void cambiarFecha (String fecha) {
		try {
			salida.writeInt(6);
			salida.writeUTF(usuario);
			salida.writeUTF(contrasena);
			salida.writeUTF(fecha);	
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void mandarArchivoVarios(String path) {
			
	}
	public InterfazLogin getMiInterfazLogin() {
		return miInterfazLogin;
	}
	public void setMiInterfazLogin(InterfazLogin miInterfazLogin) {
		this.miInterfazLogin = miInterfazLogin;
	}
	public InterfazLogin getInterfazLogin() {
		return InterfazLogin;
	}
	public void setInterfazLogin(InterfazLogin interfazLogin) {
		this.InterfazLogin = interfazLogin;
	}
	public InterfazRegistro getMiInterfazRegistro() {
		return miInterfazRegistro;
	}
	public void setMiInterfazRegistro(InterfazRegistro miInterfazRegistro) {
		this.miInterfazRegistro = miInterfazRegistro;
	}
	public InterfazRegistro getInterfazRegistro() {
		return interfazRegistro;
	}
	public void setInterfazRegistro(InterfazRegistro interfazRegistro) {
		this.interfazRegistro = interfazRegistro;
	}

	public void setInterfazPrincipal(InterfazPrincipal interfazPrincipal) {
		this.interfazPrincipal = interfazPrincipal;
		this.miHiloCliente.setInterfazPrincipal (interfazPrincipal);
	}
	
	public InterfazPrincipal getInterfazPrincipal() {
		return interfazPrincipal;
	}
}
 