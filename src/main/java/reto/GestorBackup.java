package reto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GestorBackup {
	
	public void crearBackUp(ArrayList<Vehiculo> vehiculos, Path ruta) {
	    try {
	        Files.createDirectories(ruta.getParent());

	        try (ObjectOutputStream os = new ObjectOutputStream(
	                new FileOutputStream(ruta.toFile()))) {

	            os.writeObject(vehiculos);
	        }

	    } catch (IOException e) {
	        System.out.println(e.getMessage());
	    }
	}
	
	public void leerBackUp(Path ruta) {
		try(ObjectInputStream is = new ObjectInputStream(new FileInputStream(ruta.toFile()))) {
			ArrayList<Vehiculo> vehiculos = (ArrayList<Vehiculo>) is.readObject();
			
			for(Vehiculo vehiculo: vehiculos) {
				System.out.println(vehiculo.toString());
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}catch(ClassNotFoundException e){
			System.out.println("Error de la clase " + e.getMessage());
		}
	}
	
}
