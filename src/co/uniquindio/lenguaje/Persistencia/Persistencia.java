package co.uniquindio.lenguaje.Persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import co.uniquindio.lenguaje.mundoServidor.Fecha;

public class Persistencia {
	
	public static ArrayList<String> leerArchivoTexto(String ruta) throws IOException{
		File miArchivo = new File (ruta);
		FileReader miFileReader = new FileReader(miArchivo);
		BufferedReader miBufferedReader = new BufferedReader(miFileReader);
		String linea;
		ArrayList<String> misLineas = new ArrayList<String>();
		while((linea=miBufferedReader.readLine())!=null) {
			misLineas.add(linea);
		}
		
		miBufferedReader.close();
		miFileReader.close();
		return misLineas;
	}
	
	
    public static void registrar(String ruta, ArrayList<String> lista, String usuario, String contrasena, Fecha fechaCumpleanos) {
		
		lista.add(usuario+","+contrasena+","+fechaCumpleanos.getDia()+"/"+fechaCumpleanos.getMes()+"/"+fechaCumpleanos.getAnio());
		try {
			escribirArchivo(ruta, lista, false);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
    public static void escribirArchivo(String nombre, ArrayList<String> miTexto, boolean add) throws IOException {
    	File miArchivo = new File(nombre);
    	FileWriter miFileWriter = new FileWriter(miArchivo, add);
    	BufferedWriter miBufferedWriter = new BufferedWriter(miFileWriter);
    	
    	for (String linea : miTexto) {
			miBufferedWriter.write(linea+"\n");
		}
    	miBufferedWriter.close();
    	miFileWriter.close();
    }
}
