Algoritmos sobre Grafos con Spring Boot

Aplicación web desarrollada con **Java 17+** y **Spring Boot 3.x** que expone una **API REST** para ejecutar algoritmos clásicos sobre grafos definidos por el usuario mediante archivos JSON.

El sistema permite probar algoritmos de búsqueda de fuerza bruta y algoritmos voraces, entre ellos:

* DFS, búsqueda en profundidad.
* BFS, búsqueda en amplitud.
* Búsqueda por profundidad limitada.
* Búsqueda por profundidad iterativa.
* A*, búsqueda informada para camino de menor costo.
* Kruskal, árbol de expansión mínimo.
* Prim, árbol de expansión mínimo.

Además, el proyecto incluye una interfaz web mínima con **Thymeleaf**, donde el usuario puede pegar un JSON, seleccionar el algoritmo y visualizar el resultado de manera gráfica y textual.



1. Objetivo del proyecto

El objetivo principal del proyecto es desarrollar una aplicación web capaz de representar grafos y ejecutar sobre ellos distintos algoritmos estudiados en la materia de algoritmos.

La aplicación recibe como entrada un grafo en formato JSON, procesa la información en el backend mediante servicios independientes para cada algoritmo y devuelve una respuesta estándar también en formato JSON.

El sistema permite analizar:

* El orden de visita de los nodos.
* El camino encontrado entre un nodo origen y un nodo destino.
* El costo total del camino o del árbol generado.
* Las aristas resultantes en algoritmos de árbol de expansión mínimo.
* El número de pasos realizados por el algoritmo.
* El tiempo aproximado de ejecución.



2. Tecnologías utilizadas

El proyecto fue construido utilizando las siguientes tecnologías y dependencias:

| Tecnología      | Uso dentro del proyecto                                      |
| --------------- | ------------------------------------------------------------ |
| Java 17+        | Lenguaje principal del backend                               |
| Spring Boot 3.x | Framework principal de la aplicación                         |
| Spring Web      | Creación de controladores REST y endpoints HTTP              |
| Thymeleaf       | Interfaz web para probar los algoritmos                      |
| Spring Data JPA | Persistencia del historial de ejecuciones                    |
| MySQL Driver    | Conexión con base de datos MySQL                             |
| Lombok          | Reducción de código repetitivo en DTO, entidades y servicios |
| Maven           | Gestión de dependencias y construcción del proyecto          |

3. Dependencias principales

Las dependencias utilizadas en el archivo `pom.xml` son:

```xml
<dependencies>

    <!-- Spring Web: controladores REST y MVC -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Thymeleaf: interfaz web mínima -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>

    <!-- Spring Data JPA: persistencia de historial -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- MySQL Driver -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Lombok: reducción de código repetitivo -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

</dependencies>
```



4. Estructura general del proyecto

La estructura principal del proyecto es la siguiente:

```text
src/main/java/com/grafos/algoritmos
│
├── controller
│   ├── GraphAlgorithmController.java
│   └── ViewController.java
│
├── dto
│   ├── AlgorithmResponse.java
│   ├── EdgeDto.java
│   ├── ErrorResponse.java
│   ├── GraphRequest.java
│   └── NodeDto.java
│
├── entity
│   └── ExecutionHistory.java
│
├── exception
│   ├── GlobalExceptionHandler.java
│   └── InvalidGraphException.java
│
├── graph
│   ├── Graph.java
│   ├── GraphBuilder.java
│   └── GraphUtils.java
│
├── repository
│   └── ExecutionHistoryRepository.java
│
└── service
    ├── AStarService.java
    ├── BfsService.java
    ├── DepthLimitedService.java
    ├── DfsService.java
    ├── ExecutionHistoryService.java
    ├── IterativeDeepeningService.java
    ├── KruskalService.java
    ├── PrimService.java
    └── UnionFind.java
```



5. Requisitos previos

Antes de ejecutar el proyecto, se debe contar con lo siguiente instalado:

* Java 17 o Java 21.
* Maven.
* MySQL Server.
* IntelliJ IDEA, Eclipse, VS Code o cualquier IDE compatible con proyectos Maven.
* Postman, Thunder Client o navegador web para probar la API.

Para verificar la versión de Java instalada:

```bash
java -version
```

Para verificar Maven:

```bash
mvn -version
```

---

6. Configuración de base de datos

El proyecto utiliza MySQL para registrar un historial de ejecuciones de los algoritmos.

Primero, se debe crear la base de datos:

```sql
CREATE DATABASE grafos_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Luego, configurar el archivo:

```text
src/main/resources/application.properties
```

Ejemplo de configuración:

```properties
spring.application.name=algoritmos-grafos

server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/grafos_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

spring.thymeleaf.cache=false
```

En caso de que el usuario de MySQL tenga contraseña, debe colocarse en:

```properties
spring.datasource.password=TU_CONTRASEÑA
```

---

7. Instrucciones de ejecución

7.1 Clonar el repositorio

```bash
git clone https://github.com/usuario/nombre-del-repositorio.git
```

Ingresar a la carpeta del proyecto:

```bash
cd nombre-del-repositorio
```

7.2 Compilar el proyecto

```bash
mvn clean install
```
7.3 Ejecutar el proyecto

```bash
mvn spring-boot:run
```

También puede ejecutarse desde el IDE abriendo la clase principal:

```text
AlgoritmosGrafosApplication.java
```

Y presionando el botón de ejecución.


7.4 Acceder a la aplicación

La interfaz web estará disponible en:

```text
http://localhost:8080/
```

La API REST estará disponible desde:

```text
http://localhost:8080/api
```

---

8. Modelo JSON de entrada

Todos los algoritmos reciben un JSON con la siguiente estructura general:

```json
{
  "directed": false,
  "weighted": true,
  "start": "A",
  "goal": "E",
  "limit": 4,
  "heuristic": "EUCLIDEAN",
  "nodes": [
    { "id": "A", "x": 100, "y": 200 },
    { "id": "B", "x": 250, "y": 100 }
  ],
  "edges": [
    { "from": "A", "to": "B", "weight": 4 }
  ]
}
```

### Descripción de campos

| Campo       | Tipo    | Descripción                                           |
| ----------- | ------- | ----------------------------------------------------- |
| `directed`  | Boolean | Indica si el grafo es dirigido                        |
| `weighted`  | Boolean | Indica si el grafo tiene pesos                        |
| `start`     | String  | Nodo de inicio                                        |
| `goal`      | String  | Nodo destino                                          |
| `limit`     | Integer | Límite de profundidad para búsquedas limitadas        |
| `heuristic` | String  | Heurística para A*: `NONE`, `MANHATTAN` o `EUCLIDEAN` |
| `nodes`     | Array   | Lista de nodos del grafo                              |
| `edges`     | Array   | Lista de aristas del grafo                            |

---

## 9. Modelo JSON de respuesta

Todos los algoritmos devuelven una respuesta estándar:

```json
{
  "algorithm": "BFS",
  "visitedOrder": ["A", "B", "C", "D"],
  "path": ["A", "B", "D"],
  "resultEdges": [],
  "totalCost": 9.0,
  "executionTimeMs": 2,
  "steps": 4,
  "found": true,
  "depthFound": null,
  "message": "Camino encontrado con BFS."
}
```

### Descripción de campos de respuesta

| Campo             | Descripción                                                               |
| ----------------- | ------------------------------------------------------------------------- |
| `algorithm`       | Nombre del algoritmo ejecutado                                            |
| `visitedOrder`    | Orden en el que fueron visitados los nodos                                |
| `path`            | Camino encontrado desde el nodo origen hasta el destino                   |
| `resultEdges`     | Aristas resultantes, usado principalmente en Kruskal y Prim               |
| `totalCost`       | Costo total del camino o árbol generado                                   |
| `executionTimeMs` | Tiempo aproximado de ejecución en milisegundos                            |
| `steps`           | Número de pasos o iteraciones principales                                 |
| `found`           | Indica si se encontró una solución                                        |
| `depthFound`      | Profundidad donde se encontró la solución, usado en profundidad iterativa |
| `message`         | Mensaje descriptivo del resultado                                         |

---

## 10. Endpoints disponibles

| Método | Endpoint                   | Descripción                                  |
| ------ | -------------------------- | -------------------------------------------- |
| POST   | `/api/dfs`                 | Ejecuta búsqueda en profundidad              |
| POST   | `/api/bfs`                 | Ejecuta búsqueda en amplitud                 |
| POST   | `/api/depth-limited`       | Ejecuta búsqueda por profundidad limitada    |
| POST   | `/api/iterative-deepening` | Ejecuta búsqueda por profundidad iterativa   |
| POST   | `/api/a-star`              | Ejecuta algoritmo A*                         |
| POST   | `/api/kruskal`             | Ejecuta algoritmo de Kruskal                 |
| POST   | `/api/prim`                | Ejecuta algoritmo de Prim                    |
| GET    | `/api/history`             | Consulta las últimas ejecuciones registradas |

---

# 11. Ejemplos de peticiones

Los ejemplos pueden probarse desde Postman, Thunder Client o directamente desde la interfaz web.

---

## 11.1 DFS

Endpoint:

```http
POST http://localhost:8080/api/dfs
```

Body:

```json
{
  "directed": false,
  "weighted": false,
  "start": "A",
  "goal": "F",
  "limit": null,
  "heuristic": "NONE",
  "nodes": [
    { "id": "A", "x": 100, "y": 200 },
    { "id": "B", "x": 250, "y": 100 },
    { "id": "C", "x": 250, "y": 300 },
    { "id": "D", "x": 400, "y": 100 },
    { "id": "E", "x": 400, "y": 300 },
    { "id": "F", "x": 550, "y": 200 }
  ],
  "edges": [
    { "from": "A", "to": "B", "weight": 1 },
    { "from": "A", "to": "C", "weight": 1 },
    { "from": "B", "to": "D", "weight": 1 },
    { "from": "C", "to": "E", "weight": 1 },
    { "from": "D", "to": "F", "weight": 1 },
    { "from": "E", "to": "F", "weight": 1 }
  ]
}
```

Ejemplo con `curl`:

```bash
curl -X POST http://localhost:8080/api/dfs \
-H "Content-Type: application/json" \
-d '{"directed":false,"weighted":false,"start":"A","goal":"F","limit":null,"heuristic":"NONE","nodes":[{"id":"A","x":100,"y":200},{"id":"B","x":250,"y":100},{"id":"C","x":250,"y":300},{"id":"D","x":400,"y":100},{"id":"E","x":400,"y":300},{"id":"F","x":550,"y":200}],"edges":[{"from":"A","to":"B","weight":1},{"from":"A","to":"C","weight":1},{"from":"B","to":"D","weight":1},{"from":"C","to":"E","weight":1},{"from":"D","to":"F","weight":1},{"from":"E","to":"F","weight":1}]}'
```

---

## 11.2 BFS

Endpoint:

```http
POST http://localhost:8080/api/bfs
```

Body:

```json
{
  "directed": false,
  "weighted": false,
  "start": "A",
  "goal": "G",
  "limit": null,
  "heuristic": "NONE",
  "nodes": [
    { "id": "A", "x": 100, "y": 220 },
    { "id": "B", "x": 250, "y": 120 },
    { "id": "C", "x": 250, "y": 320 },
    { "id": "D", "x": 400, "y": 80 },
    { "id": "E", "x": 400, "y": 220 },
    { "id": "F", "x": 400, "y": 360 },
    { "id": "G", "x": 600, "y": 220 }
  ],
  "edges": [
    { "from": "A", "to": "B", "weight": 1 },
    { "from": "A", "to": "C", "weight": 1 },
    { "from": "B", "to": "D", "weight": 1 },
    { "from": "B", "to": "E", "weight": 1 },
    { "from": "C", "to": "F", "weight": 1 },
    { "from": "E", "to": "G", "weight": 1 },
    { "from": "F", "to": "G", "weight": 1 }
  ]
}
```

Resultado esperado aproximado:

```json
{
  "algorithm": "BFS",
  "visitedOrder": ["A", "B", "C", "D", "E", "F", "G"],
  "path": ["A", "B", "E", "G"],
  "totalCost": 3.0,
  "found": true
}
```

---

## 11.3 Profundidad limitada

Endpoint:

```http
POST http://localhost:8080/api/depth-limited
```

Body:

```json
{
  "directed": false,
  "weighted": false,
  "start": "A",
  "goal": "F",
  "limit": 2,
  "heuristic": "NONE",
  "nodes": [
    { "id": "A", "x": 100, "y": 220 },
    { "id": "B", "x": 250, "y": 120 },
    { "id": "C", "x": 250, "y": 320 },
    { "id": "D", "x": 400, "y": 120 },
    { "id": "E", "x": 400, "y": 320 },
    { "id": "F", "x": 550, "y": 220 }
  ],
  "edges": [
    { "from": "A", "to": "B", "weight": 1 },
    { "from": "A", "to": "C", "weight": 1 },
    { "from": "B", "to": "D", "weight": 1 },
    { "from": "C", "to": "E", "weight": 1 },
    { "from": "D", "to": "F", "weight": 1 },
    { "from": "E", "to": "F", "weight": 1 }
  ]
}
```

Con `limit` igual a `2`, el algoritmo no debería encontrar el nodo `F`, debido a que el destino se encuentra a mayor profundidad.

Si se cambia el límite a `3`, el algoritmo puede encontrar el camino:

```text
A -> B -> D -> F
```

---

## 11.4 Profundidad iterativa

Endpoint:

```http
POST http://localhost:8080/api/iterative-deepening
```

Body:

```json
{
  "directed": false,
  "weighted": false,
  "start": "A",
  "goal": "F",
  "limit": 5,
  "heuristic": "NONE",
  "nodes": [
    { "id": "A", "x": 100, "y": 220 },
    { "id": "B", "x": 250, "y": 120 },
    { "id": "C", "x": 250, "y": 320 },
    { "id": "D", "x": 400, "y": 120 },
    { "id": "E", "x": 400, "y": 320 },
    { "id": "F", "x": 550, "y": 220 }
  ],
  "edges": [
    { "from": "A", "to": "B", "weight": 1 },
    { "from": "A", "to": "C", "weight": 1 },
    { "from": "B", "to": "D", "weight": 1 },
    { "from": "C", "to": "E", "weight": 1 },
    { "from": "D", "to": "F", "weight": 1 },
    { "from": "E", "to": "F", "weight": 1 }
  ]
}
```

Este algoritmo ejecuta varias búsquedas limitadas, aumentando gradualmente la profundidad hasta encontrar el nodo objetivo o hasta alcanzar el límite máximo indicado.

---

## 11.5 A*

Endpoint:

```http
POST http://localhost:8080/api/a-star
```

Body:

```json
{
  "directed": false,
  "weighted": true,
  "start": "A",
  "goal": "F",
  "limit": null,
  "heuristic": "EUCLIDEAN",
  "nodes": [
    { "id": "A", "x": 100, "y": 220 },
    { "id": "B", "x": 250, "y": 120 },
    { "id": "C", "x": 250, "y": 320 },
    { "id": "D", "x": 420, "y": 120 },
    { "id": "E", "x": 420, "y": 320 },
    { "id": "F", "x": 650, "y": 220 }
  ],
  "edges": [
    { "from": "A", "to": "B", "weight": 4 },
    { "from": "A", "to": "C", "weight": 2 },
    { "from": "B", "to": "D", "weight": 5 },
    { "from": "C", "to": "E", "weight": 3 },
    { "from": "D", "to": "F", "weight": 3 },
    { "from": "E", "to": "F", "weight": 4 },
    { "from": "B", "to": "E", "weight": 10 },
    { "from": "C", "to": "D", "weight": 8 }
  ]
}
```

Resultado esperado aproximado:

```text
Camino: A -> C -> E -> F
Costo total: 9
```

El algoritmo A* utiliza la siguiente función de evaluación:

```text
f(n) = g(n) + h(n)
```

Donde:

* `g(n)` es el costo real acumulado desde el nodo inicial hasta el nodo actual.
* `h(n)` es la estimación heurística desde el nodo actual hasta el nodo destino.
* `f(n)` es el valor usado para priorizar qué nodo explorar primero.

Heurísticas admitidas:

```text
NONE
MANHATTAN
EUCLIDEAN
```

---

## 11.6 Kruskal

Endpoint:

```http
POST http://localhost:8080/api/kruskal
```

Body:

```json
{
  "directed": false,
  "weighted": true,
  "start": null,
  "goal": null,
  "limit": null,
  "heuristic": "NONE",
  "nodes": [
    { "id": "A", "x": 100, "y": 220 },
    { "id": "B", "x": 250, "y": 100 },
    { "id": "C", "x": 250, "y": 340 },
    { "id": "D", "x": 450, "y": 100 },
    { "id": "E", "x": 450, "y": 340 },
    { "id": "F", "x": 650, "y": 220 }
  ],
  "edges": [
    { "from": "A", "to": "B", "weight": 4 },
    { "from": "A", "to": "C", "weight": 2 },
    { "from": "B", "to": "C", "weight": 1 },
    { "from": "B", "to": "D", "weight": 5 },
    { "from": "C", "to": "E", "weight": 10 },
    { "from": "D", "to": "E", "weight": 3 },
    { "from": "D", "to": "F", "weight": 8 },
    { "from": "E", "to": "F", "weight": 7 },
    { "from": "C", "to": "D", "weight": 6 }
  ]
}
```

Resultado esperado aproximado:

```text
Aristas seleccionadas:
B - C = 1
A - C = 2
D - E = 3
B - D = 5
E - F = 7

Costo total: 18
```

Kruskal ordena las aristas de menor a mayor peso y agrega una arista al árbol solo si no forma un ciclo. Para detectar ciclos, se utiliza la estructura de datos `Union-Find`.

---

## 11.7 Prim

Endpoint:

```http
POST http://localhost:8080/api/prim
```

Body:

```json
{
  "directed": false,
  "weighted": true,
  "start": "A",
  "goal": null,
  "limit": null,
  "heuristic": "NONE",
  "nodes": [
    { "id": "A", "x": 100, "y": 220 },
    { "id": "B", "x": 250, "y": 100 },
    { "id": "C", "x": 250, "y": 340 },
    { "id": "D", "x": 450, "y": 100 },
    { "id": "E", "x": 450, "y": 340 },
    { "id": "F", "x": 650, "y": 220 }
  ],
  "edges": [
    { "from": "A", "to": "B", "weight": 4 },
    { "from": "A", "to": "C", "weight": 2 },
    { "from": "B", "to": "C", "weight": 1 },
    { "from": "B", "to": "D", "weight": 5 },
    { "from": "C", "to": "E", "weight": 10 },
    { "from": "D", "to": "E", "weight": 3 },
    { "from": "D", "to": "F", "weight": 8 },
    { "from": "E", "to": "F", "weight": 7 },
    { "from": "C", "to": "D", "weight": 6 }
  ]
}
```

Prim parte desde un nodo inicial y va seleccionando la arista de menor peso que conecte un nodo visitado con un nodo no visitado.

El costo total esperado del árbol de expansión mínimo debe coincidir con Kruskal:

```text
Costo total esperado: 18
```

---

## 12. Ejemplo de error de validación

Si se intenta ejecutar Kruskal o Prim con un grafo dirigido, el sistema debe responder con un error, ya que ambos algoritmos requieren grafos no dirigidos y ponderados.

Endpoint:

```http
POST http://localhost:8080/api/kruskal
```

Body incorrecto:

```json
{
  "directed": true,
  "weighted": true,
  "start": null,
  "goal": null,
  "limit": null,
  "heuristic": "NONE",
  "nodes": [
    { "id": "A", "x": 100, "y": 200 },
    { "id": "B", "x": 300, "y": 200 },
    { "id": "C", "x": 500, "y": 200 }
  ],
  "edges": [
    { "from": "A", "to": "B", "weight": 5 },
    { "from": "B", "to": "C", "weight": 3 },
    { "from": "A", "to": "C", "weight": 10 }
  ]
}
```

Respuesta esperada:

```json
{
  "timestamp": "2026-01-01T10:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Este algoritmo requiere un grafo no dirigido.",
  "path": "/api/kruskal"
}
```

---

# 13. Análisis de complejidad

Para el análisis se utilizan las siguientes variables:

| Variable | Significado                                |
| -------- | ------------------------------------------ |
| `V`      | Número de vértices o nodos del grafo       |
| `E`      | Número de aristas del grafo                |
| `b`      | Factor de ramificación promedio            |
| `d`      | Profundidad donde se encuentra la solución |
| `l`      | Límite máximo de profundidad               |

---

## 13.1 DFS

DFS explora un camino hasta llegar lo más profundo posible antes de retroceder.

### Complejidad temporal

```text
O(V + E)
```

En el peor caso, DFS puede visitar todos los vértices y recorrer todas las aristas.

### Complejidad espacial

```text
O(V)
```

Se requiere memoria para almacenar los nodos visitados, el mapa de padres y la pila de llamadas recursivas.

### Observación

DFS no garantiza encontrar el camino más corto. Su resultado depende del orden en que se encuentren almacenadas las aristas en el JSON de entrada.

---

## 13.2 BFS

BFS explora el grafo por niveles, visitando primero los vecinos inmediatos antes de avanzar a mayor profundidad.

### Complejidad temporal

```text
O(V + E)
```

En el peor caso, recorre todos los nodos y todas las aristas.

### Complejidad espacial

```text
O(V)
```

Utiliza una cola, una estructura de visitados y un mapa de padres.

### Observación

BFS sí garantiza encontrar el camino más corto en cantidad de aristas cuando el grafo no está ponderado.

---

## 13.3 Profundidad limitada

La búsqueda por profundidad limitada es una variante de DFS que detiene la exploración al alcanzar una profundidad máxima.

### Complejidad temporal

```text
O(b^l)
```

Donde `b` es el factor de ramificación y `l` es el límite de profundidad.

En una representación por lista de adyacencia, si el límite permite recorrer todo el grafo, el comportamiento puede aproximarse a:

```text
O(V + E)
```

### Complejidad espacial

```text
O(l)
```

La memoria principal está asociada a la ruta actual de búsqueda y a la pila recursiva hasta el límite indicado.

### Observación

Si el límite es demasiado bajo, el algoritmo puede no encontrar una solución aunque exista.

---

## 13.4 Profundidad iterativa

La profundidad iterativa ejecuta varias búsquedas de profundidad limitada, aumentando gradualmente el límite hasta encontrar la solución.

### Complejidad temporal

```text
O(b^d)
```

Aunque repite búsquedas anteriores, su complejidad se mantiene dominada por el nivel más profundo, donde normalmente se concentra la mayor cantidad de nodos.

### Complejidad espacial

```text
O(d)
```

Al igual que DFS, conserva bajo consumo de memoria porque solo mantiene la ruta actual.

### Observación

Combina ventajas de BFS y DFS, ya que puede encontrar soluciones poco profundas sin usar una cola grande como BFS.

---

## 13.5 A*

A* utiliza una cola de prioridad para seleccionar el siguiente nodo con menor valor estimado.

La función principal del algoritmo es:

```text
f(n) = g(n) + h(n)
```

### Complejidad temporal

Con cola de prioridad:

```text
O((V + E) log V)
```

En el peor caso, si la heurística no ayuda o si se usa `NONE`, A* puede comportarse de forma similar a Dijkstra.

### Complejidad espacial

```text
O(V)
```

Se almacenan las distancias acumuladas, los padres, el conjunto cerrado y los nodos pendientes en la cola de prioridad.

### Observación

La eficiencia depende de la calidad de la heurística. Una heurística adecuada reduce la cantidad de nodos explorados.

---

## 13.6 Kruskal

Kruskal construye un árbol de expansión mínimo ordenando las aristas por peso y seleccionando aquellas que no formen ciclos.

### Complejidad temporal

```text
O(E log E)
```

El costo dominante es el ordenamiento de las aristas.

Con `Union-Find`, las operaciones de unión y búsqueda son muy eficientes, casi constantes en la práctica.

### Complejidad espacial

```text
O(V + E)
```

Se almacenan las aristas ordenadas, la estructura `Union-Find` y las aristas seleccionadas para el MST.

### Observación

Kruskal funciona correctamente en grafos no dirigidos y ponderados. Si el grafo no es conexo, genera un bosque de expansión mínimo en lugar de un árbol completo.

---

## 13.7 Prim

Prim construye un árbol de expansión mínimo empezando desde un nodo inicial y agregando siempre la arista más barata que conecte el conjunto visitado con un nodo no visitado.

### Complejidad temporal

Con lista de adyacencia y cola de prioridad:

```text
O(E log V)
```

Cada arista puede insertarse en la cola de prioridad y cada extracción tiene costo logarítmico.

### Complejidad espacial

```text
O(V + E)
```

Se almacenan los nodos visitados, la cola de prioridad y las aristas resultantes.

### Observación

Prim es eficiente para grafos conectados y ponderados. Si el grafo no es conexo, solo construye el MST del componente conectado al nodo inicial.

---

## 14. Tabla comparativa de complejidad

| Algoritmo             | Tipo                     | Complejidad temporal | Complejidad espacial | Garantiza camino óptimo           |
| --------------------- | ------------------------ | -------------------: | -------------------: | --------------------------------- |
| DFS                   | Fuerza bruta / recorrido |             O(V + E) |                 O(V) | No                                |
| BFS                   | Fuerza bruta / recorrido |             O(V + E) |                 O(V) | Sí, si no hay pesos               |
| Profundidad limitada  | Fuerza bruta             |               O(b^l) |                 O(l) | No                                |
| Profundidad iterativa | Fuerza bruta             |               O(b^d) |                 O(d) | Sí, por profundidad mínima        |
| A*                    | Búsqueda informada       |     O((V + E) log V) |                 O(V) | Sí, si la heurística es admisible |
| Kruskal               | Voraz / MST              |           O(E log E) |             O(V + E) | Sí, MST                           |
| Prim                  | Voraz / MST              |           O(E log V) |             O(V + E) | Sí, MST                           |

---

## 15. Validaciones implementadas

El sistema valida distintos casos antes de ejecutar los algoritmos:

* El grafo no puede estar vacío.
* El nodo inicial debe existir en el grafo.
* El nodo destino debe existir cuando el algoritmo lo requiere.
* Las aristas deben referenciar nodos existentes.
* Los pesos negativos no se permiten en A*, Kruskal ni Prim.
* Kruskal y Prim requieren grafos no dirigidos y ponderados.
* La profundidad limitada requiere un límite mayor o igual a cero.
* El JSON debe tener una estructura válida.

---

## 16. Historial de ejecuciones

El proyecto registra de forma opcional cada ejecución en la base de datos MySQL.

Se almacena:

* Nombre del algoritmo.
* JSON de entrada.
* JSON de respuesta.
* Costo total.
* Número de pasos.
* Tiempo de ejecución.
* Fecha de ejecución.

Para consultar el historial reciente:

```http
GET http://localhost:8080/api/history
```

---

## 17. Interfaz web

La aplicación incluye una interfaz web básica construida con Thymeleaf.

Desde la interfaz se puede:

* Seleccionar el algoritmo.
* Pegar el JSON de entrada.
* Ejecutar la petición.
* Visualizar la respuesta JSON.
* Observar una representación gráfica básica del grafo.
* Resaltar el camino o las aristas resultantes del algoritmo.

Ruta de acceso:

```text
http://localhost:8080/
```


18. Posibles mejoras futuras

Algunas mejoras que pueden agregarse posteriormente son:

* Autenticación de usuarios.
* Exportación de resultados en PDF o Excel.
* Guardado de grafos personalizados.
* Visualización interactiva con arrastrar y soltar nodos.
* Implementación de Dijkstra, Bellman-Ford o Floyd-Warshall.
* Comparación visual de tiempos entre algoritmos.
* Pruebas unitarias para cada servicio.
* Documentación automática con Swagger/OpenAPI.


19. Conclusión

Este proyecto permite aplicar conceptos fundamentales de estructuras de datos, algoritmos de grafos, APIs REST y desarrollo web con Spring Boot.

La separación del código en controladores, servicios, DTO, utilidades y repositorios permite mantener una arquitectura ordenada y extensible. Además, el uso de JSON como formato de entrada y salida facilita la prueba de los algoritmos desde herramientas externas o desde la interfaz web incluida.

El sistema demuestra de forma práctica cómo se comportan diferentes algoritmos sobre grafos, permitiendo comparar recorridos, caminos, costos, pasos de ejecución y árboles de expansión mínimos.
