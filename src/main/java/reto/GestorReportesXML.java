package reto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

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
import org.xml.sax.helpers.DefaultHandler;

import org.xml.sax.Attributes;

import org.xml.sax.SAXException;



public class GestorReportesXML {

	private final Path ruta = Paths.get("datos", "reports", "informe_flota.xml");
	
	private HashSet<String> categorias = new HashSet<String>();
	
	public void generacionDom(ArrayList<Vehiculo> vehiculos) {
		try {
			Files.createDirectories(ruta.getParent());
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			
			Element flota = doc.createElement("flota");
			doc.appendChild(flota);
			
			for(Vehiculo vehiculo: vehiculos) {
				Element vechicElement = doc.createElement("vehiculo");
				flota.appendChild(vechicElement);
				
				Element matricula = doc.createElement("matricula");
				matricula.setTextContent(vehiculo.getMatricula());
				vechicElement.appendChild(matricula);
				
				Element marca = doc.createElement("marca");
				marca.setTextContent(vehiculo.getMarca().toString());
				vechicElement.appendChild(marca);
				
				Element anyo = doc.createElement("anyo");
				anyo.setTextContent(String.valueOf(vehiculo.getAnyo()));
				vechicElement.appendChild(anyo);
				
				Element kms = doc.createElement("kilometros");
				kms.setTextContent(String.valueOf(vehiculo.getKms_recoridos()));
				vechicElement.appendChild(anyo);
				
				Element categoria = doc.createElement("categoria");
				categoria.setTextContent(vehiculo.getCategoria());
				vechicElement.appendChild(categoria);
			}
			
			TransformerFactory transformerfactory = TransformerFactory.newInstance();
			Transformer transformer = transformerfactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
			DOMSource source = new DOMSource(doc);
			
			StreamResult result = new StreamResult(ruta.toFile());
			
			transformer.transform(source, result);
			
		} catch (IOException | NumberFormatException | ParserConfigurationException | DOMException | TransformerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public HashSet<String> getCategorias() {
		return categorias;
	}

	public void setCategorias(HashSet<String> categorias) {
		this.categorias = categorias;
	}

	static class gestorEventos extends DefaultHandler{
		
	}
	

	public static class GetEventos extends DefaultHandler {

	    private HashSet<String> categorias = new HashSet<>();
	    private StringBuilder texto = new StringBuilder();

	    public HashSet<String> getCategorias() {
	        return categorias;
	    }

	    @Override
	    public void startElement(String uri, String localName,
	                             String qName, Attributes attributes)
	            throws SAXException {

	        texto.setLength(0);
	    }

	    @Override
	    public void characters(char[] ch, int start, int length)
	            throws SAXException {

	        texto.append(ch, start, length);
	    }

	    @Override
	    public void endElement(String uri, String localName,
	                           String qName) throws SAXException {

	        if (qName.equals("categoriaMantenimiento")) {

	            String categoria = texto.toString().trim();

	            if (!categoria.isEmpty()) {
	                categorias.add(categoria);
	            }
	        }

	        texto.setLength(0);
	    }
	}
	
    public void leerXML() {

        try {
            SAXParserFactory factory =
                    SAXParserFactory.newInstance();

            SAXParser parser = factory.newSAXParser();

            GetEventos eventos = new GetEventos();

            parser.parse(ruta.toFile(), eventos);

            HashSet<String> categorias = eventos.getCategorias();

            System.out.println("Categorías de mantenimiento:");

            for (String categoria : categorias) {
                System.out.println(categoria);
            }

        } catch (ParserConfigurationException e) {
            System.out.println("Error de configuración: " + e.getMessage());

        } catch (SAXException e) {
            System.out.println("Error al procesar el XML: " + e.getMessage());

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

	
}
