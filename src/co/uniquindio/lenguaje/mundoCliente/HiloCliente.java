package co.uniquindio.lenguaje.mundoCliente;

import java.io.*;

import co.uniquindio.lenguaje.interfazCliente.InterfazLogin;
import co.uniquindio.lenguaje.interfazCliente.InterfazPrincipal;
import co.uniquindio.lenguaje.interfazCliente.InterfazRegistro;

class HiloCliente extends Thread {
	private DataInputStream entrada2;
	private DataInputStream entrada;
	private InterfazPrincipal miInterfazPrincipal;
	private int elimina = 0;
	private boolean centinela = true;
	private String nombreCliente;
	private BufferedInputStream entradaArchivo;
	private InterfazLogin interfazLogin;
	private InterfazRegistro interfazRegistro; 

	public HiloCliente(DataInputStream entrada, InterfazLogin login, String nombreCliente) {
		this.entrada2 = entrada;
		this.interfazLogin = login;
		this.nombreCliente = nombreCliente;	
	}
	
	public void setNombreCliente (String nombreCliente)
	{
		this.nombreCliente = nombreCliente;
	}

	public void run() {
		String mensaje = "", nombreAmigo = "";
		boolean check, cumpleanos, cambio;
		int opcion = 0;
		while (centinela) {
			try {
				opcion = entrada2.readInt();
				if (opcion == 1) {// mensaje enviado
					mensaje = entrada2.readUTF();
					miInterfazPrincipal.getMiInterfazCliente().mostrarMsg(mensaje);
					miInterfazPrincipal.getMiInterfazCliente().setVisible(true);
				} else if (opcion == 2) {// se agrega
					mensaje = entrada2.readUTF();
					miInterfazPrincipal.agregarUser(mensaje);
				}
				if (opcion == 3) {// mensaje privado
					nombreAmigo = entrada2.readUTF();
					mensaje = entrada2.readUTF();
					miInterfazPrincipal.mensajeAmigoPersonal(nombreAmigo, mensaje);
				}
				if(opcion == 4) {// verificarIngreso
					check = entrada2.readBoolean();
					cumpleanos = entrada2.readBoolean();
					interfazLogin.iniciar(check, cumpleanos);
				}
				if(opcion == 5){ //verificar registro
					check = entrada2.readBoolean();
					interfazRegistro.registrarse(check);
				}
				if(opcion == 6) { // verificar cumplea�os
					cambio=entrada2.readBoolean();
					System.out.println("Ppal: " + miInterfazPrincipal);
					miInterfazPrincipal.cambiarFecha(cambio);
				}
				if (elimina == 1) {
					elimina = 0;
					break;
				}
			} catch (IOException e) {
				centinela = false;
			}
		}

	}
	
	public static void archivoEnviado(String origen, String name, String nombreArchivo) {
		
	}
	
	public static void archivoRecibido(String origen, String name, String nombreArchivo){
		
	}

	public int getElimina() {
		return elimina;
	}

	public void setElimina(int elimina) {
		this.elimina = elimina;
	}

	public InterfazLogin getInterfazLogin() {
		return interfazLogin;
	}

	public void setInterfazLogin(InterfazLogin interfazLogin) {
		this.interfazLogin = interfazLogin;
	}

	public InterfazRegistro getInterfazRegistro() {
		return interfazRegistro;
	}

	public void setInterfazRegistro(InterfazRegistro interfazRegistro) {
		this.interfazRegistro = interfazRegistro;
	}

	
	public InterfazPrincipal getInterfazPrincipal() {
		return this.miInterfazPrincipal;
	}

	public void setInterfazPrincipal (InterfazPrincipal interfazPrincipal) {
		this.miInterfazPrincipal = interfazPrincipal;
	}
}