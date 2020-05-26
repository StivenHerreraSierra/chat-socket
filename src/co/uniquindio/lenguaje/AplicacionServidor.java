package co.uniquindio.lenguaje;

import java.io.IOException;
import co.uniquindio.lenguaje.interfazServidor.InterfazServidor;

public class AplicacionServidor {

	public static void main(String args[]) throws IOException {
		InterfazServidor miInterfazServidor = new InterfazServidor();
		miInterfazServidor.setVisible(true);
	}
}
