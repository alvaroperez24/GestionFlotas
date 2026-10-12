package reto;

import java.nio.file.Path;
import java.nio.file.Paths;

public class GestionFlotas {

	public static void main(String[] args) {
		
		//Rutas
		final Path rutaConfi = Paths.get("datos", "config", "empresa.dat");
		final Path rutaInventario = Paths.get("datos", "json", "flota.json");
		final Path rutaBackUp = Paths.get("datos", "backup", "flota_backup.ser");
		final Path rutaXML = Paths.get("datos", "reports", "informe_flota.xml");


		Vehiculo vehiculo = new Vehiculo(
			    "1234ABC",
			    Marca.seat,
			    2020,
			    50000,
			    "familiar"
		);
		

		GestorConfiguracion configuracion = GestorConfiguracion.getConfiguracion();
		configuracion.guardarConf(rutaConfi);
		configuracion.leerConfig(rutaConfi);

		GestorInventarioJSON inventario = new GestorInventarioJSON();
		
		inventario.guardarVehiculo(vehiculo);
		inventario.crearJson(rutaInventario);
		inventario.leerGson(rutaInventario);
		
		GestorBackup backup = new GestorBackup();
		
		backup.crearBackUp(inventario.getListaVehiculos(), rutaBackUp);
		backup.leerBackUp(rutaBackUp);
		
		GestorReportesXML xml = new GestorReportesXML();
		
		xml.generacionDom(inventario.getListaVehiculos(), rutaXML);
		xml.leerXML(rutaXML);
	}

}
