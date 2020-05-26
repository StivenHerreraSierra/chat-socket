package co.uniquindio.lenguaje.mundoServidor;

public class Usuario {

	private String usuario;
	private String pass;
	private Fecha fechaCumpleanos;


	public Usuario(String usuario, String pass, Fecha fechaCumpleanos) {
		this.usuario = usuario;
		this.pass = pass;
		this.fechaCumpleanos = fechaCumpleanos;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Fecha getFechaCumpleanos() {
		return fechaCumpleanos;
	}

	public void setFechaCumpleanos(Fecha fechaCumpleanos) {
		this.fechaCumpleanos = fechaCumpleanos;
	}

}
