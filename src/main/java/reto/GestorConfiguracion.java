package reto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GestorConfiguracion {
	private static GestorConfiguracion configuracion;
	
	private String nom_empresa;
	private String version_soft;
	private int total_vehiculos;
	private int total_km;
	
	private GestorConfiguracion() {}



	private GestorConfiguracion(String nombre, String version) {
		this.nom_empresa = nombre;
		this.version_soft = version;
		this.total_vehiculos = Vehiculo.getTotal_vehiculos();
		this.total_km = Vehiculo.getTotal_km();
	}



	public static GestorConfiguracion getConfiguracion() {
		if (configuracion==null) {
			configuracion = new GestorConfiguracion("WEWE", "1.2");
		}
		return configuracion;
	}



	public void setConfiguracion(GestorConfiguracion configuracion) {
		GestorConfiguracion.configuracion = configuracion;
	}



	public String getNom_empresa() {
		return nom_empresa;
	}



	public void setNom_empresa(String nom_empresa) {
		this.nom_empresa = nom_empresa;
	}



	public String getVersion_soft() {
		return version_soft;
	}



	public void setVersion_soft(String version_soft) {
		this.version_soft = version_soft;
	}



	public int getTotal_vehiculos() {
		return total_vehiculos;
	}



	public void setTotal_vehiculos(int total_vehiculos) {
		this.total_vehiculos = total_vehiculos;
	}



	public int getKil_acumulados() {
		return total_km;
	}



	public void setKil_acumulados(int kil_acumulados) {
		this.total_km = kil_acumulados;
	}
	
	
	public void guardarConf(Path ruta) {
        try {
            Files.createDirectories(ruta);
			try(DataOutputStream dos = new DataOutputStream(Files.newOutputStream(ruta))) {
			dos.writeUTF(getNom_empresa());
			dos.writeUTF(getVersion_soft());
			dos.writeInt(getTotal_vehiculos());
			dos.writeInt(getKil_acumulados());
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void leerConfig(Path ruta) {
		try(DataInputStream dis = new DataInputStream(Files.newInputStream(ruta))){
			System.out.println("Nombre: " + dis.readUTF());
			System.out.println("Version: " + dis.readUTF());
			System.out.println("Vehiculos: " + dis.readInt());
			System.out.println("Kilometros: " + dis.readInt());
		}catch (EOFException e) {
			System.out.println(e.getMessage());
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
