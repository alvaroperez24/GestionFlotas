/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejercicios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.Arrays;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author apm25
 */
public class Ejercicios {
    
    private Ejercicios(){}
    
    public static void mainEjercicios(){
        //De aqui llamamos a los ejercicios
        //ejercicio11();
        //ejercicio12();
        //ejercicio21();
        //ejercicio22();
        //ejercicio31();
        //ejercicio32();
        //ejercicio33();
        //ejercicio41();
        ejercicio42();
        
    }
    
    private static double calcularMedia(String[] nums){
        double suma = 0;
        double media;
        
        for(int i = 1; i < nums.length; i++) {
            suma += Double.parseDouble(nums[i]);
        }
        media = suma/(nums.length-1);
        System.out.println(media);
        return media;
    }
    
    private static void ejercicio11(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce una ruta absoluta o relativa: ");
        String ruta = scanner.nextLine();
        
        File archivo = new File(ruta);
        
        if (!archivo.exists()) {
            System.out.println("Advertencia: La ruta especificada no existe.");
        } else {
            if (archivo.isFile()) {
                System.out.println("Es un FICHERO.");
                System.out.println("Nombre: " + archivo.getName());
                System.out.println("Ruta absoluta: " + archivo.getAbsolutePath());
                System.out.println("Tamaño: " + (archivo.length() / 1024.0) + " KB");
                System.out.println("Permisos - Lectura: " + archivo.canRead() + " | Escritura: " + archivo.canWrite());
            } else if (archivo.isDirectory()) {
                System.out.println("Es un DIRECTORIO.");
                String[] elementos = archivo.list();
                if (elementos != null) {
                    System.out.println("Número total de elementos: " + elementos.length);
                    System.out.println("Contenido:");
                    for (String elemento : elementos) {
                        System.out.println(" - " + elemento);
                    }
                }
            }
        }
        scanner.close();
    }
    private static void ejercicio12(){
        File srcDir = new File("proyecto/src");
        File binDir = new File("proyecto/bin");
        File docsDir = new File("proyecto/docs");
       
        srcDir.mkdirs();
        binDir.mkdirs();
        docsDir.mkdirs();
        System.out.println("Estructura de directorios creada");

        File readme = new File(docsDir, "readme.txt");
        
        //Forma correcta de crear el fichero, 
        //por que? porque el metodo ademas de crearlo devuelve un booleano 
        //y porque puede fallar el metodo
        try {
            if (readme.createNewFile()) {
                System.out.println("Fichero readme.txt creado correctamente.");
            }
        } catch (IOException e) {
            System.out.println("Error al crear readme.txt: " + e.getMessage());
        }

        File proyectoDir = new File("proyecto");
        System.out.println("Intentando eliminar carpeta raíz");
        if (!proyectoDir.delete()) {
            System.out.println("No se puede borrar la carpeta raíz porque no está vacía.");
        }

        System.out.println("Borrando subcarpetas");
        if (readme.delete()) {
            System.out.println("readme eliminado");
        }
        
        docsDir.delete();
        binDir.delete();
        srcDir.delete();
        System.out.println("Subdirectorios eliminados.");

        if (proyectoDir.delete()) {
            System.out.println("Carpeta raíz eliminada");
        }
    }
    private static void ejercicio21(){
        String[] apartados;
        try(BufferedReader br = new BufferedReader(new FileReader("ejercicio1/alumnos.csv")); PrintWriter writer = new PrintWriter(new FileWriter("ejercicio1/resultados.txt"));){
            String linea;
            while ((linea = br.readLine()) != null) {
                apartados = linea.split(",");
                double media = calcularMedia(apartados);
                writer.println("Nombre: [" + apartados[0] + "] - Media: [" + String.format("%.2f", media) + "]");
            }
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    private static void ejercicio22(){
        String nombreFichero = "precios.dat";
        Scanner scanner = new Scanner(System.in);

        System.out.print("Cuántos pares de numeros vas a introducir?: ");
        int n = scanner.nextInt();

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombreFichero))) {
            for (int i = 1; i <= n; i++) {
                System.out.print("Introduce un número entero para el registro " + i + ": ");
                int numero = scanner.nextInt();
                
                System.out.print("Introduce un precio (decimal) para el registro " + i + ": ");
                double precio = scanner.nextDouble();

                dos.writeInt(numero);
                dos.writeDouble(precio);
            }     
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        double sumaTotal = 0;
        int cantidadPrecios = 0;

        try (DataInputStream dis = new DataInputStream(new FileInputStream(nombreFichero))) {
            while (true) {
                int numeroLeido = dis.readInt();
                double precioLeido = dis.readDouble();
                System.out.println("Registro leído -> Entero: " + numeroLeido + " | Precio: " + precioLeido);
                sumaTotal += precioLeido;
                cantidadPrecios++;
            }
        } catch (EOFException e) {
            System.out.println("Se ha alcanzado el fin del fichero");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("Suma total de los precios: " + sumaTotal);
        
        if (cantidadPrecios > 0) {
            double media = sumaTotal / cantidadPrecios;
            System.out.println("Media de los precios: " + media);
        } else {
            System.out.println("No se leyeron datos para calcular la media.");
        }
        scanner.close();
    }
    private static void ejercicio31(){
        GestorClientes.main();
    }
    private static void ejercicio32(){
        String archivo = "empleados.dat";
        final int TAMANO_REGISTRO = 12; // 4 bytes (int) + 8 bytes (double)

        // Se usa el modo "rw" (lectura y escritura)
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw");
             Scanner scanner = new Scanner(System.in)) {

            // 1. ESCRIBIR 5 EMPLEADOS INICIALES
            // Volvemos al inicio por si el archivo ya existía y tenía datos
            raf.seek(0); 
            for (int i = 1; i <= 5; i++) {
                raf.writeInt(i);                  // Escribe ID (4 bytes)
                raf.writeDouble(1000.0 + (i*100)); // Escribe Salario inicial (8 bytes)
            }
            System.out.println("Se han escrito 5 empleados en el fichero.");

            // 2. CONSULTAR EL EMPLEADO CON ID = 3
            int idBuscado = 3;
            // Cálculo del offset: (3 - 1) * 12 = 24 bytes de desplazamiento desde el byte 0
            long posicionBuscada = (idBuscado - 1) * TAMANO_REGISTRO;
            
            raf.seek(posicionBuscada);
            int idLeido = raf.readInt();
            double salarioActual = raf.readDouble();
            
            System.out.println("ID: " + idLeido + " | Salario actual: " + salarioActual);
            System.out.print("Introduce el nuevo salario: ");
            double nuevoSalario = scanner.nextDouble();

            //Posicionamos el puntero exactamente donde empieza el double del salario para el ID 3
            // Esto es: inicio del registro (24) + tamaño del ID (4) = byte 28
            raf.seek(posicionBuscada + 4);
            raf.writeDouble(nuevoSalario);
            System.out.println("¡Salario modificado con éxito directamente en disco!");

            raf.seek(posicionBuscada);
            System.out.println("\nComprobando registro actualizado...");
            System.out.println("ID: " + raf.readInt() + " | Nuevo Salario: " + raf.readDouble());

        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        }
    }
    private static void ejercicio33(){
        // Definimos el punto de partida (el directorio actual ".")
        Path directorioRaiz = Paths.get("."); 
        
        // Calculamos el instante exacto hace 24 horas
        Instant limite24Horas = Instant.now().minus(24, ChronoUnit.HOURS);

        System.out.println("Explorando el directorio: " + directorioRaiz.toAbsolutePath());
        System.out.println("Buscando ficheros .txt o .log modificados en las últimas 24 horas...\n");

        // Files.walk crea un Stream perezoso (lazy) con el árbol de directorios
        // Usamos try-with-resources porque el Stream de Files.walk debe cerrarse para liberar los descriptores de archivo
        try (Stream<Path> rutas = Files.walk(directorioRaiz)) {
            long totalLineas = rutas.filter(Files::isRegularFile).filter(p -> {
                    String nombre = p.getFileName().toString().toLowerCase();
                    return nombre.endsWith(".txt") || nombre.endsWith(".log");
                }).filter(p -> {
                    try {
                        FileTime ultimaModificacion = Files.getLastModifiedTime(p);
                        return ultimaModificacion.toInstant().isAfter(limite24Horas);
                    } catch (IOException e) {
                        return false; // Si no podemos leer los atributos, lo descartamos
                    }
                })                
                .mapToLong(p -> {
                    try (Stream<String> lineas = Files.lines(p)) {
                        return lineas.count();
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                        return 0L;
                    }
                })
                .sum();

            System.out.println("Total de líneas: " + totalLineas);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    private static void escrituraGson(List<Producto> inventario, String nombreArchivo, Gson gson){
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            gson.toJson(inventario, writer);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    private static List<Producto> lecturaGson(String nombreArchivo, Gson gson){
        try (FileReader reader = new FileReader(nombreArchivo)) {
            Producto[] productosLeidos = gson.fromJson(reader, Producto[].class);
            List<Producto> productosRecuperados = Arrays.asList(productosLeidos);
            return productosRecuperados;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    private static void ejercicio41(){
        String archivoJson = "productos.json";
        List<Producto> inventario = new ArrayList<>();
        
        inventario.add(new Producto(101, "Teclado Mecánico", 45.99));
        inventario.add(new Producto(102, "Ratón Óptico", 15.50));
        inventario.add(new Producto(103, "Monitor 24 pulgadas", 120.00));

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        escrituraGson(inventario, archivoJson, gson);

        List<Producto> productosRecuperados = lecturaGson(archivoJson, gson);
        
        for (Producto p : productosRecuperados) {
            System.out.println(p.toString());
        }
    }
    
    public static void escrituraDOM(Document doc){
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            //Esto es una clase envoltorio para poder escribir
            StreamResult result = new StreamResult(new File("ejercicio4/facturas.xml")); 

            transformer.transform(source, result);
        }catch(TransformerException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void parteDOM(File archivo) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivo);

            NodeList listaFacturas = doc.getElementsByTagName("factura");
            double totalImportes = 0;

            for (int i = 0; i < listaFacturas.getLength(); i++) {
                Element factura = (Element) listaFacturas.item(i);
                
                // 1. Obtener y sumar el importe
                NodeList listaImportes = factura.getElementsByTagName("importe");
                if (listaImportes.getLength() > 0) {
                    String importeStr = listaImportes.item(0).getTextContent();
                    totalImportes += Double.parseDouble(importeStr);
                }

                // 2. Comprobar si ya existe la etiqueta PAGADA
                NodeList listaPagadas = factura.getElementsByTagName("PAGADA");
                if (listaPagadas.getLength() == 0) {
                    Element etiquetaPagada = doc.createElement("PAGADA");
                    etiquetaPagada.appendChild(doc.createTextNode("SI"));
                    factura.appendChild(etiquetaPagada);
                } else {
                    System.out.println("Factura " + (i + 1) + ": Ya estaba pagada previamente.");
                }
            }
            
            System.out.println("Total de las facturas: " + totalImportes);

            // 3. Guardar cambios en el archivo XML físico
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource origen = new DOMSource(doc);
            StreamResult destino = new StreamResult(archivo);
            transformer.transform(origen, destino);

        } catch (IOException | NumberFormatException | ParserConfigurationException 
               | DOMException | SAXException | TransformerException e) {
            System.err.println("Error en parteDOM: " + e.getMessage());
        }
    }
    
    static class ManejadorFacturas extends DefaultHandler {
        
        private boolean leyendoImporte = false;
        private double totalImportes = 0;

        //metodo para devolver el total cuando el parser termine
        public double getTotalImportes() {
            return totalImportes;
        }

        //le decimos cuando empieza el elemento que nos interesa, en este caso, importe y encendemos el lector
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if (qName.equalsIgnoreCase("importe")) {
                leyendoImporte = true;
            }
        }

        //Aqui va lo que leemos
        @Override
        public void characters(char[] ch, int start, int length) {
            if (leyendoImporte) {
                //convertimos los caracteres leidos a texto y lo sumamos
                String importeStr = new String(ch, start, length);
                totalImportes += Double.parseDouble(importeStr);
            }
        }
        
        //aqui cerramos el elemento
        @Override
        public void endElement(String uri, String localName, String qName) {
            if (qName.equalsIgnoreCase("importe")) {
                leyendoImporte = false;
            }
        }
    }
    
    public static void parteSAX(File archivo) {
        try {
            //esto prepara y contruye el lector
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //el objeto que abre el xml y lo recorre, esto lanza eventos a la clase DefaultHandle, 
            //la cual hemos sobreescrito arriba.
            SAXParser saxParser = factory.newSAXParser();

            //la clase que maneja lo que hay dentro del XML
            ManejadorFacturas handler = new ManejadorFacturas();
            
            //procesamos el archivo
            saxParser.parse(archivo, handler);

            System.out.println("Total de las facturas (calculado con SAX): " + handler.getTotalImportes());

        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.getMessage();
        }
    }
    
    private static void ejercicio42(){
        File archivoXML = new File("ejercicio4/facturas.xml");

        parteDOM(archivoXML);
        parteSAX(archivoXML);
    }

}