# montyhall
https://www.youtube.com/watch?v=w3cgpEF30uY&feature=youtu.be

# condiciones de prueba.

- la maquina host en dónde esté corriendo la aplicación debe tener disponible el puerto que asignes en el comando de ejecución. De no ser así la aplicación no arrancará por choque de puertos.

# Instrucciones : 

- Ejecutar comando : $mvn clean package

- Ejecutar comando : java -Dserver.port=${appPort} -jar  .../raizDelProyecto/montyhall/target/montyhall-0.0.1-SNAPSHOT.jar

- Escribir en el navegador (HTTP GET) : http:localhost:8080/

# Detalles a nivel de frond end :

- se utilizó angular (una bazuca para matar a una mosca. Podría ser solo javascript y html).
- no se hace uso de frameworks css.
- repositorio del código fuente del front-end : 
