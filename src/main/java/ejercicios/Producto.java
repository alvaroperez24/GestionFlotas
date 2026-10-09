/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejercicios;

/**
 *
 * @author apm25
 */
import com.google.gson.annotations.SerializedName;

public class Producto {
    // La anotación cambia el nombre de la clave en el JSON resultante
    @SerializedName("codigo_producto")
    private int id;
    
    private String nombre;
    private double precio;

    public Producto(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    // Getters, Setters y toString
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }

    @Override
    public String toString() {
        return "Producto [ID=" + id + ", Nombre=" + nombre + ", Precio=" + precio + "]";
    }
}