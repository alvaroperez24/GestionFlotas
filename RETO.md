# Reto: gestión de flotas

Esta guía explica las clases del paquete `reto` y el camino que sigue la información. El código está en `src/main/java/reto` y los archivos se escriben en la carpeta `datos` relativa al directorio desde el que se lanza el programa. Por eso, al ejecutarlo desde la carpeta `Ficheros`, las rutas son `Ficheros/datos/...`.

## 1. Recorrido del programa

La ejecución empieza en `GestionFlotas.main`:

1. Crea un `Vehiculo` con matrícula, marca, año, kilómetros y categoría.
2. Obtiene la configuración única mediante `GestorConfiguracion.getConfiguracion()`.
3. Guarda y lee los datos generales de la empresa en `datos/config/empresa.dat`.
4. Crea un `GestorInventarioJSON`, mete el vehículo en sus colecciones, escribe el inventario en `datos/json/flota.json` y lo vuelve a leer.

El orden importa: el vehículo se crea antes de la configuración, así que los contadores de `Vehiculo` ya incluyen ese coche cuando se inicializa la configuración.

## 2. `Vehiculo` y `Marca`

`Vehiculo` es el modelo: cada instancia guarda los datos de un coche. Los campos `matricula`, `marca`, `anyo`, `kms_recoridos` y `categoria` son datos de cada objeto. `Marca` es un `enum`, una lista cerrada de marcas válidas; así se evita escribir cualquier texto como marca.

El constructor con parámetros asigna los campos y actualiza dos contadores `static`: `total_vehiculos` y `total_km`. `static` significa que el valor se comparte entre todos los vehículos, en vez de pertenecer a uno solo. `setKilometros` resta del acumulado los kilómetros anteriores del objeto y suma los nuevos, manteniendo el total correcto cuando se modifica el vehículo.

Los getters devuelven valores y los setters los cambian. `toString` crea una descripción legible. `equals` compara el contenido de dos vehículos y `hashCode` calcula un código coherente con esa comparación; esto importa si se guardan en estructuras basadas en hash.

Gson necesita un constructor vacío para reconstruir objetos al leer JSON. La anotación `@SerializedName("kms_recorridos")` decide el nombre de la propiedad JSON aunque el campo Java tenga una errata en su nombre.

## 3. `GestorConfiguracion`: instancia única y archivo binario

La clase guarda nombre de empresa, versión y totales. Sus constructores son privados para que el resto del programa no pueda crear instancias arbitrariamente. `getConfiguracion()` crea la instancia la primera vez y luego siempre devuelve esa misma instancia: es el patrón Singleton.

`guardarConf()` crea la carpeta si hace falta y usa `DataOutputStream` para escribir los valores en binario, en este orden: dos textos con `writeUTF`, y dos enteros con `writeInt`. `leerConfig()` usa `DataInputStream` para leerlos en el mismo orden y con los métodos correspondientes (`readUTF`, `readInt`). En un archivo binario no hay etiquetas visibles que indiquen el tipo de cada dato, así que cambiar el orden o el tipo en un lado requiere cambiar también el otro.

Los `try-with-resources` cierran los flujos automáticamente. Las excepciones `IOException` se capturan para mostrar el error. `EOFException` es un caso particular: indica que se intentó leer más allá del final del archivo.

## 4. `GestorInventarioJSON`: colección en memoria y JSON

La clase mantiene dos estructuras:

- `ArrayList<Vehiculo>` conserva todos los vehículos y permite recorrerlos en orden.
- `HashMap<String, Vehiculo>` indexa cada vehículo por matrícula; una búsqueda por clave suele ser directa. Si se repite una matrícula, el mapa conserva el último valor asociado a esa clave.

`guardarVehiculo()` añade el objeto a la lista y al mapa. `crearJson()` crea las carpetas padre y usa Gson para convertir la lista Java en texto JSON, guardándolo en `flota.json`. `setPrettyPrinting()` hace que el archivo tenga sangría legible.

`leerGson()` lee el texto y pide a Gson que lo convierta en un array de `Vehiculo`. Luego reconstruye la lista y el mapa. Si el archivo no existe, vacía ambas estructuras. Se comprueba que el vehículo y su matrícula no sean `null` antes de añadirlo al mapa.

## 5. Formatos y archivos generados

`empresa.dat` es binario: no está pensado para editarse a mano; su estructura depende del orden de escrituras y lecturas explicado arriba. `flota.json` es texto y puede abrirse para inspeccionar los vehículos. La carpeta `datos/config` o `datos/json` se crea al guardar, por lo que no hace falta prepararla manualmente.

## 6. Cómo ejecutarlo

Desde la carpeta `Ficheros`, compila y ejecuta `reto.GestionFlotas` con Maven/Eclipse. Maven descarga/usa Gson según la dependencia declarada en `pom.xml`. Si se lanza desde otra carpeta, las rutas relativas `datos/...` apuntarán a esa otra carpeta; ejecuta desde `Ficheros` para usar los datos incluidos en este proyecto.

## 7. Detalles a tener presentes

- Los contadores `static` empiezan de cero en cada ejecución. Al leer el JSON, el constructor vacío suma uno por cada vehículo reconstruido, pero no suma sus kilómetros; por eso los contadores no representan necesariamente el inventario completo.
- `GestorConfiguracion` toma los totales cuando se crea la instancia; si después se crean más vehículos, habría que actualizar los totales de configuración antes de guardarla.
- `leerGson()` restaura la lista y el mapa en memoria, pero no recalcula de forma fiable los contadores estáticos de `Vehiculo`: el constructor vacío incrementa el número de vehículos al deserializar, mientras que los kilómetros no se acumulan.
- `Serializable` está declarado en `Vehiculo`, aunque el inventario actual se guarda con Gson y no con serialización Java nativa.

## 8. Lectura guiada de las instrucciones del código

Esta sección explica también la sintaxis, para que puedas leer cada bloque sin dar nada por sabido.

### `GestionFlotas.main`

- `public class GestionFlotas` declara una clase pública. El nombre coincide con el archivo `GestionFlotas.java`, como exige Java para una clase pública.
- `public static void main(String[] args)` es la firma que busca la JVM para iniciar el programa. `public` permite encontrarlo, `static` permite llamarlo sin crear un objeto, `void` indica que no devuelve un valor y `args` puede recibir argumentos escritos al lanzar el programa.
- `Vehiculo vehiculo = new Vehiculo(...)` crea el objeto con `new`, llama al constructor con parámetros y guarda la referencia en la variable `vehiculo`. El orden de los argumentos debe coincidir con el constructor: matrícula, marca, año, kilómetros, categoría.
- `Marca.seat` toma una constante del enum. No lleva comillas porque no es un `String`.
- `GestorConfiguracion configuracion = ...` guarda la referencia devuelta por el método estático `getConfiguracion()`. El tipo de la variable indica qué operaciones se pueden hacer con ella.
- `configuracion.guardarConf()` y `configuracion.leerConfig()` son llamadas a métodos de esa instancia. Los paréntesis vacíos indican que no necesitan argumentos.
- `new GestorInventarioJSON()` crea el gestor del inventario. A diferencia de la configuración, de este gestor sí se pueden crear objetos directamente porque su constructor no se ha declarado privado.
- Las últimas tres llamadas siguen el flujo: meter el vehículo en memoria, escribirlo a disco y cargar el archivo de nuevo.

### Declaraciones y métodos de `Vehiculo`

- `import ...` permite usar nombres de otras bibliotecas sin escribir su nombre completo cada vez. `Serializable` y `Objects` vienen de Java; `SerializedName` viene de Gson.
- `implements Serializable` declara que la clase acepta el mecanismo de serialización nativa de Java. En el reto se usa Gson, así que esta interfaz no es la que crea `flota.json`.
- `private` limita el acceso directo desde otras clases; así se controla el acceso con métodos. `String`, `int` y `Marca` son los tipos de los datos.
- `static` pone un campo en la clase en vez de en cada vehículo; `final` impide cambiar el valor de la constante `serialVersionUID`.
- `serialVersionUID` identifica la versión de la clase en la serialización Java nativa. No tiene relación con la versión del programa.
- `@SerializedName("kms_recorridos")` es una anotación que Gson consulta para elegir el nombre de esa propiedad en el JSON.
- El constructor vacío permite crear primero un objeto sin parámetros, necesario para que Gson pueda rellenar luego sus campos al leer JSON. En este código ese constructor incrementa `total_vehiculos`.
- En el constructor completo, `this.matricula` significa el campo del nuevo objeto y `matricula` el argumento recibido. Las asignaciones repiten eso para cada dato. `+=` equivale a asignar la suma (`total_km = total_km + kms_recoridos`); `++` equivale a sumar uno al contador.
- Un getter suele devolver un campo (`return`) y un setter lo reemplaza. Se usan para encapsular los campos privados. `return` termina el método y entrega su resultado a quien lo llamó.
- `getKilometros()` declara que devuelve `double`, aunque el campo se almacena como entero. Java convierte el `int` devuelto a `double` automáticamente.
- `setKilometros(...)` primero resta del total los kilómetros viejos, luego cambia el campo y finalmente suma el nuevo valor. Si solo sumara el nuevo, el total acumularía también el valor antiguo.
- `@Override` avisa al compilador que el método sustituye uno heredado de `Object`; ayuda a detectar errores al escribir el nombre o la firma.
- En `equals`, `this` es el objeto actual. Las comprobaciones tempranas devuelven `true` o `false` para resolver identidad, nulos y clases distintas antes de comparar campos. El cast `(Vehiculo) obj` permite acceder a los campos como vehículo. `Objects.equals` compara textos de forma segura incluso si alguno es `null`; `==` sirve para el enum porque sus constantes son instancias únicas.
- `Objects.hash(...)` combina en un entero los mismos datos usados por `equals`. Es necesario que `equals` y `hashCode` se mantengan consistentes para usar vehículos correctamente en `HashSet` o como claves de `HashMap`.

### Declaraciones y métodos de `GestorConfiguracion`

- Los imports `DataInputStream` y `DataOutputStream` son flujos que leen y escriben tipos de datos; `Files`, `Path` y `Paths` ayudan a trabajar con archivos; `IOException` agrupa errores de entrada/salida; `EOFException` indica que el archivo terminó antes de completar una lectura.
- `private static GestorConfiguracion configuracion` guarda la única instancia. Al principio vale `null` porque aún no se le ha asignado objeto.
- `Path` representa una ubicación de archivo sin abrirlo. `Paths.get("datos", "config")` forma la ruta usando el separador correcto del sistema operativo. `resolve("empresa.dat")` añade el nombre del archivo a la carpeta.
- Ambos constructores son privados. El vacío existe en la clase; el que recibe nombre y versión inicializa los campos y copia en ese momento los acumulados de `Vehiculo`.
- `getConfiguracion()` es `static`, así que puede llamarse desde la clase. `if (configuracion == null)` pregunta si hace falta crearla. Después del `return`, futuras llamadas devuelven la misma referencia.
- Los getters y setters de esta clase tienen el mismo propósito que los de `Vehiculo`: leer o cambiar datos privados. `setConfiguracion` cambia la referencia estática, aunque en el flujo normal del Singleton no hace falta llamarlo.
- `try (...) { ... }` con recursos declara un flujo que se cerrará al salir del bloque, incluso si ocurre un error. Esto evita dejar archivos abiertos.
- `Files.createDirectories(...)` crea la carpeta y las carpetas padre que falten; si ya existen, no falla por ese motivo.
- `Files.newOutputStream(rutaFinal)` abre el destino para escribir. `DataOutputStream` añade métodos que codifican textos y enteros. Cada `write` escribe datos binarios, no palabras separadas por comas.
- En lectura, `Files.newInputStream` abre el archivo, `DataInputStream` interpreta sus bytes y cada `read...` devuelve el siguiente valor del tipo indicado. Por eso la secuencia de lecturas debe ser exactamente: texto, texto, entero, entero.
- `catch (IOException e)` recibe errores de archivo; `e.getMessage()` muestra el mensaje concreto. `EOFException` se captura primero porque es un tipo más específico de `IOException`; si se invirtiera el orden, la captura general interceptaría antes los casos de fin de archivo.

### Declaraciones y métodos de `GestorInventarioJSON`

- `ArrayList` y `HashMap` son colecciones de Java. Los símbolos `<Vehiculo>` y `<String, Vehiculo>` son genéricos: indican qué tipos se guardan y permiten que el compilador compruebe su uso.
- `new ArrayList<>()` y `new HashMap<>()` crean colecciones vacías. El `<>` permite inferir los tipos del lado izquierdo.
- `GsonBuilder` configura Gson antes de construirlo. `setPrettyPrinting()` pide JSON con espacios y saltos de línea para facilitar su lectura; `create()` entrega el objeto Gson configurado.
- `guardarVehiculo` llama a `add` para añadir a la lista y a `put` para asociar matrícula y vehículo en el mapa. Ambas estructuras apuntan al mismo objeto.
- `ruta.getParent()` obtiene la carpeta `datos/json`, sin el nombre `flota.json`. Se crea esa carpeta antes de intentar escribir el archivo.
- `gson.toJson(listaVehiculos)` transforma la lista en una cadena JSON. `Files.writeString` guarda la cadena. Si la ruta ya existía, el contenido se reemplaza.
- `Files.exists(ruta)` pregunta si ya hay inventario. Si no lo hay, se inicializan colecciones vacías y `return` termina el método para no intentar abrir un archivo ausente.
- `Files.readString(ruta)` carga el texto del archivo. `fromJson(texto, Vehiculo[].class)` pide a Gson que lo interprete como un array de vehículos. `.class` pasa a Gson la información del tipo que debe reconstruir.
- Si Gson devuelve `null`, se reemplaza por una lista vacía; de lo contrario se crea una lista nueva y el `for (Vehiculo vehiculo : vehiculosCargados)` visita cada elemento del array para copiarlo.
- Antes de `put`, la condición comprueba que el vehículo y su matrícula no sean `null`. `&&` significa “y”: solo entra al bloque cuando ambas condiciones son verdaderas.
- Los getters finales exponen las colecciones para que el código llamador pueda consultarlas. Como devuelven las colecciones reales, el llamador también podría modificarlas.

### Qué significan las llaves y los puntos y coma

Las llaves `{}` delimitan una clase, método, constructor, condición, bucle o bloque `try/catch`. Cada bloque define dónde empieza y acaba ese contexto. El punto y coma `;` termina la mayoría de las instrucciones Java. Los comentarios `//` terminan al final de la línea; los comentarios `/* ... */` y `/** ... */` pueden ocupar varias líneas y no se ejecutan.

## 9. Una ejecución de ejemplo, paso a paso

Al iniciar, los contadores de `Vehiculo` valen cero. El constructor completo crea el coche: el contador de vehículos pasa a uno y el total de kilómetros a 50 000. Después, el Singleton copia esos dos valores a su configuración. `guardarConf()` deja cuatro valores binarios en el archivo, y `leerConfig()` los lee y los imprime. Luego el gestor JSON guarda el coche en una lista y un mapa, crea el archivo JSON y lo vuelve a cargar. Durante la carga Gson crea el objeto leyendo sus propiedades; el constructor vacío incrementa el contador de vehículos, pero no suma los kilómetros. Así, en esta ejecución, el contador estático puede pasar a dos tras leer el JSON, mientras el total de kilómetros sigue en 50 000. Esta diferencia es importante: los contadores estáticos no son una fuente persistente fiable y habría que recalcularlos explícitamente si se quieren sincronizar con el inventario cargado.
