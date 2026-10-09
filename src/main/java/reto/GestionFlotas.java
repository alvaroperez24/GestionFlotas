package reto;

public class GestionFlotas {

	public static void main(String[] args) {

		Vehiculo vehiculo = new Vehiculo(
			    "1234ABC",
			    Marca.seat,
			    2020,
			    50000,
			    "familiar"
		);
		

		GestorConfiguracion configuracion = GestorConfiguracion.getConfiguracion();
		configuracion.guardarConf();
		configuracion.leerConfig();

		GestorInventarioJSON inventario = new GestorInventarioJSON();
		
		inventario.guardarVehiculo(vehiculo);
		inventario.crearJson();
		inventario.leerGson();
		
		GestorBackup backup = new GestorBackup();
		
		backup.crearBackUp(inventario.getListaVehiculos());
		backup.leerBackUp();
		
		GestorReportesXML xml = new GestorReportesXML();
		
		xml.generacionDom(inventario.getListaVehiculos());
		xml.leerXML();
	}

}
