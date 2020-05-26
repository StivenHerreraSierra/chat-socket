package co.uniquindio.lenguaje.mundoServidor;

import java.io.*;
import java.net.*;
import java.util.*;
import co.uniquindio.lenguaje.Persistencia.Persistencia;

/**
 *
 * @author Sonia Jaramillo
 */
public class HiloServidor extends Thread implements Serializable {
	/**
	 * Tiene los extremos de la conexion TCP, se crearon en el servidor
	 */
	private Socket scli = null;
	private Socket scli2 = null;
	private Socket sCli3 = null;
	private BufferedInputStream bis = null;
	private String usuario;
	private String nombreUsuario;
	private Fecha fechaUsuario;
	private String contrasena;
	private Servidor miServidor;
	private DataInputStream entrada = null;
	private DataOutputStream salida = null;
	private DataOutputStream salida2 = null;
	public static ArrayList<HiloServidor> clientesActivos = new ArrayList<HiloServidor>();
	private boolean centinela = true;
	private static String ruta = "src/usuarios.txt";

	/**
	 * Es el constructor
	 * 
	 * @param sCliente
	 * @param sCliente2
	 * @param serv
	 * @throws FileNotFoundException 
	 */
	public HiloServidor(Socket sCliente, Socket sCliente2 ,Servidor serv){
		scli = sCliente;
		scli2 = sCliente2;
		this.miServidor = serv;
		usuario = "";
		/**
		 * Crear un hilo servidor implica que un nuevo cliente se conecto
		 */
		clientesActivos.add(this);
	}
	
	/**
	 * Va a estar pendiente de lo que hagan los clientes
	 */
	public void run() {
		int opcion = 0, numUsers = 0;
		String amigo = "", mencli = "";
		String usuario;
		String contrasena;
		String fecha;
		int dia, mes, anio;
		Fecha fechaActual, fechaCumpleanos = null;
		
		try {
			entrada = new DataInputStream(scli.getInputStream());
			salida = new DataOutputStream(scli.getOutputStream());
			salida2 = new DataOutputStream(scli2.getOutputStream());
//			bis = new BufferedInputStream(sCli3.getInputStream());
			/**
			 * Este nombre del usuario se escribio inicialmente desde la interfazPRincipal
			 * Cliente al momento de cliente.crearConexion(nombreUsuario);. Cuando esto
			 * ocurre el cliente crea 2 sockets con estas instrucciones comunication = new
			 * Socket(ipServidor, 8081); comunication2 = new Socket(ipServidor, 8082);
			 * entrada = new DataInputStream(comunication.getInputStream()); salida = new
			 * DataOutputStream(comunication.getOutputStream()); entrada2 = new
			 * DataInputStream(comunication2.getInputStream());
			 * 
			 */
			/**
			 * Se enviar el listado de usuarios activos a cada uno de los usuario Se est�
			 * enviando por salida2
			 */
			while (centinela) {
				try {
					/**
					 * Esta opcion la escribe el cliente desde la interfaz cliente
					 */
					opcion = entrada.readInt();
					switch (opcion) {
					case 1:// Se envia mensaje a todos los contactos
							// Se hace lectura adicional para el mensaje que se lee
						/**
						 * Esto ocurrio en el cliente salida.writeInt(1); salida.writeUTF(mens);
						 */
						mencli = entrada.readUTF();
						enviarMensaje(mencli);
						break;
					case 2:// envio de lista de activos
						/**
						 * Esto ocurrio en el cliente salida.writeInt(2); int numUsers =
						 * entrada.readInt(); for (int i = 0; i < numUsers; i++) {
						 * users.add(entrada.readUTF()); }
						 */
						numUsers = clientesActivos.size();
						salida.writeInt(numUsers);
						for (int i = 0; i < numUsers; i++) {
							salida.writeUTF(clientesActivos.get(i).getNameUser());
						}
						break;

					case 3: // envia mensaje a contacto seleccionado
						/**
						 * Esto ocurrio en el cliente salida.writeInt(3); salida.writeUTF(amigo);
						 * salida.writeUTF(mens);
						 */

						amigo = entrada.readUTF();// captura nombre de amigo
						mencli = entrada.readUTF();// mensaje enviado
						enviarMsg(amigo, mencli);
						break;

					case 4: //verifica los usuarios
						usuario = entrada.readUTF();
						contrasena= entrada.readUTF();
						boolean check = miServidor.comprobarUsuario(usuario, contrasena);
						this.salida2.writeInt(4);
						this.salida2.writeBoolean(check);
						if(!check) {
							this.salida2.writeBoolean(false);
							clientesActivos.remove(this);
						}else {
							this.nombreUsuario = usuario;
							fechaActual = obtenerFecha();
							fechaUsuario = obtenerCumpleanos(usuario, contrasena);
							boolean cumpleanos = validarCumpleanos (fechaActual, fechaUsuario);
							this.salida2.writeBoolean(cumpleanos);
						}
						break;
						
					case 5: // verifica el registro
						
						usuario = entrada.readUTF();
						contrasena = entrada.readUTF();
						dia = entrada.readInt();
						mes = entrada.readInt();
						anio = entrada.readInt();
						
						fechaCumpleanos= new Fecha(dia, mes, anio);
						
						ArrayList<String> archivo2 = new ArrayList<String>();
						archivo2 = Persistencia.leerArchivoTexto(ruta);
						
						boolean check2 = miServidor.comprobarUsuario(usuario, contrasena);
						if(check2) {
							this.salida2.writeInt(5);
							this.salida.writeBoolean(check2);
							clientesActivos.remove(this);
						}else {
							this.nombreUsuario=usuario;
							Persistencia.registrar(ruta, archivo2, usuario, contrasena, fechaCumpleanos);
							miServidor.cargarUsuarios();
							this.salida2.writeInt(5);
							this.salida2.writeBoolean(check2);
						}
					    break;
						
					case 6: //verificar cumplea�os
						usuario = entrada.readUTF();
						contrasena = entrada.readUTF();
						fecha = entrada.readUTF();
						miServidor.cambiarFechaUsuario(usuario, contrasena, fecha);
						miServidor.cargarUsuarios();
						this.salida2.writeInt(6);
						this.salida2.writeBoolean(true);
						break;
										
					}
				} catch (IOException e) {
					centinela = false;
				}
			}

			clientesActivos.remove(this);
			scli.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Fecha obtenerFecha () {
		Calendar tiempo = Calendar.getInstance();
		int dia, mes, anio;
		Fecha fechaActual;
		dia = tiempo.get(Calendar.DATE);
		mes = tiempo.get(Calendar.MONTH) + 1;
		anio = tiempo.get(Calendar.YEAR);
		
		fechaActual = new Fecha(dia, mes, anio);
		
		return fechaActual;
	}
	
	public Fecha obtenerCumpleanos(String usuario, String contrasena) {
		try {
			Fecha fechaNacimiento = miServidor.comprobarCumpleanosUsuario(usuario, contrasena);
			return fechaNacimiento;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	/**
	 * Para cada uno de los clientes activos se les indica que se les va a enviar un
	 * mensaje y luego se les escribe. Es el hilo del cliente el que est� pendiente
	 * 
	 * @param mencli2
	 * @throws IOException
	 */
	public void enviarMensaje(String mencli2) throws IOException {
		HiloServidor miUsuario = null;

		for (int i = 0; i < clientesActivos.size(); i++) {
			miUsuario = clientesActivos.get(i);
			miUsuario.getSalida2().writeInt(1);// opcion de mensaje
			miUsuario.getSalida2().writeUTF("" + this.getNameUser() + " >" + mencli2);
		}
	}

	/**
	 * Por salida2 le mando todos los usuarios
	 * 
	 * @throws IOException
	 */
	public void enviarUsuariosActivos() throws IOException {
		HiloServidor miUsuario = null;
		for (int i = 0; i < clientesActivos.size(); i++) {
			miUsuario = clientesActivos.get(i);
			if (miUsuario != this) // ya se lo envie
			{
				miUsuario.getSalida2().writeInt(2);// opcion de agregar
				miUsuario.getSalida2().writeUTF(this.getNameUser());
			}
		}
	}

	public String[] enviarNombresUsuarios() {
		String array[] = new String[clientesActivos.size()];
		for (int i = 0; i < clientesActivos.size(); i++) {
			array[i] = clientesActivos.get(i).getNameUser();
		}
		return array;
	}

	private void enviarMsg(String amigo, String mencli) throws IOException {
		HiloServidor miUsuario = null;
		for (int i = 0; i < clientesActivos.size(); i++) {
			miUsuario = clientesActivos.get(i);
			if (miUsuario.getNameUser().equals(amigo)) {
				miUsuario.getSalida2().writeInt(3);// opcion de mensaje a contacto especifico
				miUsuario.getSalida2().writeUTF(this.getNameUser());
				miUsuario.getSalida2().writeUTF("" + this.getNameUser() + ">" + mencli);
			}

		}
	}
	
	public void enviarValidacionIngreso(boolean check) {
		HiloServidor miUsuario = null;
		for (int i = 0; i < clientesActivos.size(); i++) {
			System.out.println(1);
			try {
				miUsuario = clientesActivos.get(i);
				if(miUsuario == this) {
					miUsuario.salida2.writeInt(5);
					miUsuario.salida2.writeBoolean(check);
					if(check) {
						miUsuario.salida2.writeInt(2);//opcion de agregar
						miUsuario.salida2.writeUTF(this.getName());
					}
					continue; //ya se lo envie
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(2);
	}
	
	public void enviarValidacionRegistro(boolean check) {
		HiloServidor miUsuario = null;
		for (int i = 0; i < clientesActivos.size(); i++) {
			try {
				miUsuario = clientesActivos.get(i);
				if(miUsuario == this) {
					miUsuario.salida2.writeInt(5);
					miUsuario.salida2.writeBoolean(check);
					if(check) {
						miUsuario.salida2.writeInt(2);//opcion de agregar
						miUsuario.salida2.writeUTF(this.getName());
					}
					continue; //ya se lo envie
				}
				miUsuario.salida2.writeInt(5);//opcion de agregar
				miUsuario.salida2.writeBoolean(check);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean validarCumpleanos(Fecha fechaActual, Fecha fechaUsuario) {
		if(fechaActual.getDia() == fechaUsuario.getDia() &&
		   fechaActual.getMes() == fechaUsuario.getMes()) {
			return true;
		}
		return false;
	}

	public String getNameUser() {
		return nombreUsuario;
	}

	public void setNameUser(String name) {
		nombreUsuario = name;
	}

	public DataOutputStream getSalida() {
		return salida;
	}

	public void setSalida(DataOutputStream salida) {
		this.salida = salida;
	}

	public DataOutputStream getSalida2() {
		return salida2;
	}

	public void setSalida2(DataOutputStream salida2) {
		this.salida2 = salida2;
	}
}