package reto;

import java.io.Serializable;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

public class Vehiculo implements Serializable {

	private static final long serialVersionUID = 1L;
	private static int total_vehiculos = 0;
	private static int total_km = 0;
	
	private String matricula;
	private Marca marca;
	private int anyo;
	@SerializedName ("kms_recorridos")
	private int kms_recoridos;
	private String categoria;
	
	public Vehiculo() {
		total_vehiculos++;
	}

	public Vehiculo(String matricula, Marca marca, int anyo, int kms_recoridos, String categoria) {
		this.matricula = matricula;
		this.marca = marca;
		this.anyo = anyo;
		this.kms_recoridos = kms_recoridos;
		this.categoria = categoria;
		total_km += kms_recoridos;
		total_vehiculos++;
	}



	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public int getAnyo() {
		return anyo;
	}

	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Vehiculo [matricula=" + matricula + ", marca=" + marca + ", anyo=" + anyo + ", kms_recoridos="
				+ kms_recoridos + ", categoria=" + categoria + "]";
	}

	public static int getTotal_vehiculos() {
		return total_vehiculos;
	}

	public static void setTotal_vehiculos(int total_vehiculos) {
		Vehiculo.total_vehiculos = total_vehiculos;
	}

	public static int getTotal_km() {
		return total_km;
	}

	public static void setTotal_km(int total_km) {
		Vehiculo.total_km = total_km;
	}

	public int getKms_recoridos() {
		return kms_recoridos;
	}

	public void setKms_recoridos(int kms_recoridos) {
		total_km -= this.kms_recoridos;
		this.kms_recoridos = kms_recoridos;
		total_km += kms_recoridos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(anyo, categoria, kms_recoridos, marca, matricula);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehiculo other = (Vehiculo) obj;
		return anyo == other.anyo && Objects.equals(categoria, other.categoria) && kms_recoridos == other.kms_recoridos
				&& marca == other.marca && Objects.equals(matricula, other.matricula);
	}
}
