package fichero;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class Fichero3 {
	public static void nio() {
		Path ruta = Paths.get("datos", "datos.txt");
		
		//Escrutura
		escrituraNIO(ruta, "Hola caracola");
		
		//lectura
		lecturaNIO(ruta);
	}
	
	public static void escrituraNIO(Path ruta, String contenidos) {
		try {
			Files.write(ruta, contenidos.getBytes(StandardCharsets.UTF_8));
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void lecturaNIO(Path ruta) {
		try(Stream<String> lineas = Files.lines(ruta)){
			lineas.forEach(System.out::println);
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
