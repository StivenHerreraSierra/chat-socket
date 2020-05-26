package co.uniquindio.lenguaje;

import co.uniquindio.lenguaje.interfazCliente.InterfazLogin;

public class AplicacionCliente {
	public static void main(String args[]) throws Exception {
		try {
			InterfazLogin login = new InterfazLogin();
			login.setVisible(true);
		} catch (Exception e) {
		}
	}
}
