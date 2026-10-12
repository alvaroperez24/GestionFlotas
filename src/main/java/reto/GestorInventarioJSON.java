package reto;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;
import java.nio.file.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



public class GestorInventarioJSON {
	
	private ArrayList<Vehiculo> listaVehiculos = new ArrayList<>();
	
	private HashMap<String, Vehiculo> mapaVehiculos = new HashMap<>();
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	
	public void guardarVehiculo(Vehiculo coche) {
		listaVehiculos.add(coche);
		mapaVehiculos.put(coche.getMatricula(), coche);
	}
	
	public void crearJson(Path ruta) {
		try {
			Files.createDirectories(ruta.getParent());
			Files.writeString(ruta, gson.toJson(listaVehiculos));
		} catch (IOException e) {
			System.err.println("No se pudo guardar el inventario JSON: " + e.getMessage());
		}
	}
	
	public void leerGson(Path ruta) {
		try {
			if (!Files.exists(ruta)) {
				listaVehiculos = new ArrayList<>();
				mapaVehiculos = new HashMap<>();
				return;
			}
			Vehiculo[] vehiculosCargados = gson.fromJson(Files.readString(ruta), Vehiculo[].class);
			if (vehiculosCargados == null) {
				listaVehiculos = new ArrayList<>();
			} else {
				listaVehiculos = new ArrayList<>();
				for (Vehiculo vehiculo : vehiculosCargados) {
					listaVehiculos.add(vehiculo);
				}
			}
			mapaVehiculos = new HashMap<>();
			for (Vehiculo vehiculo : listaVehiculos) {
				if (vehiculo != null && vehiculo.getMatricula() != null) {
					mapaVehiculos.put(vehiculo.getMatricula(), vehiculo);
				}
			}
		} catch (IOException e) {
			System.err.println("No se pudo leer el inventario JSON: " + e.getMessage());
		}
	}

	public ArrayList<Vehiculo> getListaVehiculos() {
		return listaVehiculos;
	}

	public HashMap<String, Vehiculo> getMapaVehiculos() {
		return mapaVehiculos;
	}
}
