package fichero;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Fichero4 {
	/*
	[
	{
	"nombre": "Ãlvaro,
	"dni": "12345678",
	"edad": 23
	},
	{
	"nombre": "Ãlvaro",
	"dni": "12345678",
	"edad": 23
	}
	]
	*/
	
	
	public static void formatos() {
		Alumno alu1= new Alumno("Pedro", 90, "1234A");
		Alumno alu2= new Alumno("Pedro", 90, "1234A");
		Alumno alu3= new Alumno("Pedro", 90, "1234A");
		Alumno alu4= new Alumno("Pedro", 90, "1234A");
		Alumno alu5= new Alumno("Pedro", 90, "1234A");
		
		List<Alumno> alumnos = new ArrayList<>();
		alumnos.add(alu1);
		alumnos.add(alu2);
		alumnos.add(alu3);
		alumnos.add(alu4);
		alumnos.add(alu5);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String jsonAlu = gson.toJson(alumnos);
		
		Path ruta = Paths.get("datos", "Alumnos.json");
		
		Fichero3.escrituraNIO(ruta, jsonAlu);
	}
}
