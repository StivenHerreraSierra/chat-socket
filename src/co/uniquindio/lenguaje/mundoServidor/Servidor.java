
package co.uniquindio.lenguaje.mundoServidor;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.*;

import co.uniquindio.lenguaje.Persistencia.Persistencia;

public class Servidor extends JFrame {
	/**
	 * Tiene un hilo servidor que mantiene el listado de todos los clientes, que se
	 * llama clientes activos
	 */
	private HiloServidor user;
	private ArrayList<Usuario> usuarios;
	private String ruta = "src/usuarios.txt";
	
	/**
	 * Es para hacer el ciclo infinito del run
	 */
	private boolean listening = true;

	public void runServer(int puerto1, int puerto2) throws Exception, IOException {
		File file = new File(ruta);
		if(!file.exists()) {
			ArrayList<String> archivo = new ArrayList<String>();
			Persistencia.escribirArchivo("src/usuarios.txt", archivo ,false);
		}
		cargarUsuarios();
		/**
		 * ServerSocket se incluye en java.net. Sev serv y serv2 pueden considerarse
		 * como dos receptores, los cuales abren respectivamente dos puertos (8081 y
		 * 8082). Posteriormente ambos objetos de tipo ServerSocket inician el proceso
		 * de escucha a trav�s de sus respectivos puertos. Cuando se efectue el accept
		 * se bloquear� esperando que se ejecuten llamadas.
		 */
		ServerSocket serv = null;// para comunicacion
		ServerSocket serv2 = null;// para enviar mensajes
		ServerSocket serv3 = null;// para enviar archivos 
		listening = true;
		serv = new ServerSocket(8081);
		serv2 = new ServerSocket(8082);
		serv3 = new ServerSocket(8083);
		
		
		/**
		 * Se crea un ciclo infinito a la espera de conexion, accept indica que se est�
		 * a la espera de aceptar una conexion
		 */
		while (listening) {
			Socket sock = null, sock2 = null;
			sock = serv.accept();
			sock2 = serv2.accept();
			/**
			 * Cuando se da una conexion entonces se produce un Socket, que utiliza el
			 * servidor para comunicarse con el cliente , de este socket se consigue el
			 * flujo de entrada salida Realmente aqui se crearon 2 canales. Un hiloServidor
			 * se crea cada vez que un cliente se conecta
			 */
			user = new HiloServidor(sock, sock2, this);
			user.start();
			
		}
	}

	public boolean comprobarUsuario(String usuario, String contrasena) {
		
		if(usuarios!=null) {
			for (int i = 0; i < usuarios.size(); i++) {
				if(usuarios.get(i).getUsuario().equals(usuario) && usuarios.get(i).getPass().equals(contrasena)) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	public Fecha comprobarCumpleanosUsuario(String usuario, String contrasena) {
		if(usuarios!=null) {
			for (int i = 0; i < usuarios.size(); i++) {
				if(usuarios.get(i).getUsuario().equals(usuario) && usuarios.get(i).getPass().equals(contrasena)) {
					return usuarios.get(i).getFechaCumpleanos();
				}
				
			}
		}
		return null;
	}
	
	public void cargarUsuarios() {
		ArrayList<String> archivo = new ArrayList<String>();
		try {
			archivo = Persistencia.leerArchivoTexto(ruta);
			if(archivo.size()>0) {
				usuarios = generarUsuarios(archivo);	
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public ArrayList<Usuario> generarUsuarios(ArrayList<String> archivo){
		
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		String nombre, contrasena;
		Fecha fechaNacimiento;
		String[] datos;
		Usuario usuario;
		for (int i = 0; i < archivo.size(); i++) {
			datos = archivo.get(i).split(",");
			nombre = datos[0];
			contrasena = datos[1];
			fechaNacimiento = generarFecha(datos[2]);
			usuario = new Usuario(nombre, contrasena, fechaNacimiento);
			usuarios.add(usuario);
		}
		return usuarios;
	}
	
	public Fecha generarFecha (String fecha) {
		Fecha fechaNacimiento;
		int dia, mes, anio;
		String[] datos;
		datos=fecha.split("/");
		dia=Integer.parseInt((datos[0]));
		mes=Integer.parseInt((datos[1]));
		anio=Integer.parseInt((datos[2]));
		fechaNacimiento = new Fecha(dia, mes, anio);
		return fechaNacimiento;	
	}
	
	public void cambiarFechaUsuario(String usuario, String contrasena, String fecha) {
		ArrayList<String> archivo = new ArrayList<String>();
		String [] datos;
		String cambio;
		boolean centinela = false;
		try {
			archivo = Persistencia.leerArchivoTexto(ruta);
			for (int i = 0; i < archivo.size() && centinela == false; i++) {
				datos = archivo.get(i).split(",");
				if(datos[0].equals(usuario) && datos[1].equals(contrasena)) {
					datos[2]=fecha;
					cambio = datos[0]+","+datos[1]+","+datos[2];
					archivo.set(i, cambio);
					centinela = true;
				}
			}
			Persistencia.escribirArchivo(ruta, archivo, false);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
	public boolean isListening() {
		return listening;
	}

	public void setListening(boolean listening) {
		this.listening = listening;
	}

	public HiloServidor getUser() {
		return user;
	}

	public void setUser(HiloServidor user) {
		this.user = user;
	}

}
