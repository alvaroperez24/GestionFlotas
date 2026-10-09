package fichero;

import static fichero.Fichero2.maintenance;
import static fichero.Fichero3.nio;
import ejercicios.Ejercicios;
import java.io.*;
import java.util.Scanner;
import java.util.*;

public class Ficheros {
	

	public static void main(String[] args) {
		File carpeta = crearCarpeta("datos");
		File archivo = crearArchivo(carpeta, "Alumnos.txt");
		//System.out.println(archivo.getAbsolutePath());
		
		//Escritura en ficheros
		//escribirFichero(archivo, null);
		
		//Lectura de ficheros
		//ArrayList<String> datos = new ArrayList<>();
		//datos = recogerDatos(archivo);
		
		//Escritura binaria
		//introducirEdades(new File(carpeta, "datos.mbd"), datos);
		
		//Lectura binaria
		//leerFicheroBinario(new File(carpeta, "datos.mbd"));
		
		//maintenance();
		
		//nio();
		
		//Fichero4.formatos();
		
		Ejercicios.mainEjercicios();
	}

	//Crear una carpeta
	public static File crearCarpeta(String nombre) {
		File carpeta = new File(nombre);
		if(!carpeta.exists()) carpeta.mkdir();
		return carpeta;
	}
	
	//Crear una carpeta dentro de otra carpeta
	public static File crearCarpeta(File carpeta,String nombre) {
		File carpetanew = new File(carpeta, nombre);
		if(!carpetanew.exists()) carpetanew.mkdir();
		return carpetanew;
	}
	
	//Crear un archivo dentro de una carpeta
	public static File crearArchivo(File carpeta, String archivo) {
		File archivo1 = new File(carpeta, archivo);
		
		try{
			if(!archivo1.exists()) {
				archivo1.createNewFile();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return archivo1;
	}
	
	//Escribir en un fichero
	public static void escribirFichero(File a, String contenido) {
		Scanner sc = new Scanner(System.in);
		try (PrintWriter salida = new PrintWriter(new FileWriter(a.getAbsoluteFile(), false))) {
			for(int i = 0; i < 4; i++) {
				System.out.println("Introduce el nombre del alumno: ");
				String nombre = sc.nextLine();
				salida.println("Nombre: " +nombre);
				System.out.println();
			}
			salida.flush(); //flush() es el que hace que el texto se escriba en el archivo
			salida.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			sc.close();
			System.out.println("Usuarios guardados con éxito");
		}
	}
	
	//Leer datos de un archivo con Buffer
	public static void leerDatos(File archivo) {
		try (BufferedReader bf = new BufferedReader(new FileReader(archivo.getAbsoluteFile()))) {
			String linea;
			while ((linea = bf.readLine()) != null) {
				System.out.println(linea);
			}
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//Recoger datos de una archivo con Buffer
	public static ArrayList<String> recogerDatos(File archivo) {
		ArrayList<String> datos = new ArrayList<>();
		try (BufferedReader bf = new BufferedReader(new FileReader(archivo.getAbsoluteFile()))) {
			String linea;
			while ((linea = bf.readLine()) != null) {
				datos.add(linea);
			}
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return datos;
	}
	
	//Eliminar carpeta aunque no este vacía
	public static void eliminarCarpeta(File carpeta) {

	    File[] archivos = carpeta.listFiles();

	    if (archivos != null) {
	        for (File archivo : archivos) {

	            if (archivo.isDirectory()) {
	                eliminarCarpeta(archivo);
	            } else {
	                archivo.delete();
	            }
	        }
	    }

	    carpeta.delete();
	}
	
	//Listar archivos dentro de una carpeta
	public static void listarCarpeta(File carpeta) {
		File [] archivos = carpeta.listFiles();
		
		if (archivos != null) {
            for (File archivo : archivos) {
                System.out.println(archivo.getName());
            }
        }
	}
	
	public static void introducirEdades(File fichero, ArrayList<String> datos) {

	    try (DataOutputStream ds =
	            new DataOutputStream(new FileOutputStream(fichero, false))) {

	        for (String alumno : datos) {

	            ds.writeUTF(alumno);
	            ds.writeInt(25);
	        }

	    } catch (IOException e) {
	        System.out.println(e.getMessage());
	    }
	}
	
	private static void leerFicheroBinario(File archivo) {

	    try (DataInputStream di = new DataInputStream(new FileInputStream(archivo))) {

	        while (true) {

	            String alumno = di.readUTF();
	            int edad = di.readInt();

	            System.out.println(alumno + ", Edad: " + edad);
	        }

	    } catch (EOFException e) {
	        System.out.println("Fin del archivo.");

	    } catch (IOException e) {
	        System.out.println(e.getMessage());
	    }
	}

}
