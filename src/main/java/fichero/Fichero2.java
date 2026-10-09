package fichero;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Fichero2 {
	
	public static void maintenance() {
		Alumno alu1 = new Alumno("Álvaro", 19, "12345678A");
		
		//Escribir objetos
		escribirObjetos(alu1);
		
		//Leer objetos
		leerObjetos();
	}
	
	public static void escribirObjetos(Alumno alu) {
		try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("datos/Objetos.obj"))){
			os.writeObject(alu);
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void leerObjetos() {
		try(ObjectInputStream oi = new ObjectInputStream(new FileInputStream("datos/Objetos.obj"))){
			Alumno alu = (Alumno) oi.readObject();
			System.out.println(alu.toString());
		}catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}
