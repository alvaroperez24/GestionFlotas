/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejercicios;

/**
 *
 * @author apm25
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GestorClientes {
    public static void main() {
        String archivo = "clientes.ser";
        ArrayList<Cliente> listaClientes = new ArrayList<>();

        listaClientes.add(new Cliente(1, "Pepe", "pepe@correo.com", 1500.50));
        listaClientes.add(new Cliente(2, "Pepito", "pepito@correo.com", 320.75));
        listaClientes.add(new Cliente(3, "Pepon", "pepon@correo.com", 2100.00));

        guardarClientes(listaClientes, archivo);

        ArrayList<Cliente> clientesLeidos = leerClientes(archivo);
        for (Cliente c : clientesLeidos) {
            System.out.println(c.toString());
        }
    }
    
    private GestorClientes(){}
    
    public static void guardarClientes(List<Cliente> listaClientes, String nombreArchivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            oos.writeObject(listaClientes);
            System.out.println("Lista de clientes guardada :)");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static ArrayList<Cliente> leerClientes(String nombreArchivo){
         try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            //El suppressWarnings solo es necesario si os salta un aviso en la consola, 
            //ya que readObject devuelve un objeto generico de java y al hacer el casteo puede saltar.
            @SuppressWarnings("unchecked")
            ArrayList<Cliente> clientesRecuperados = (ArrayList<Cliente>) ois.readObject();
            return  clientesRecuperados;
        } catch (ClassNotFoundException | IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}