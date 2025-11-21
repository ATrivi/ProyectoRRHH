#Usamos una imagen base con Maven y JDK 17
FROM maven:3.9-eclipse-temurin-17 AS build
#Define el directorio de trabajo en el contenedor
WORKDIR /app
#Copia el pom.xml
COPY pom.xml .
# Descarga las dependencias sin ejecutar tests.
#-q silencioso, -B modo batch (no interactivo), -DskipTests no corre pruebas, dependency:go-offline predescarga todo para acelerar la siguiente fase.
RUN mvn -q -B -DskipTests dependency:go-offline
#Copia el código fuente al contenedor
COPY src ./src
#Compila y empaqueta el proyecto. JAR en target/
RUN mvn -q -B -DskipTests package

# Segunda etapa, ahora con una imagen solo JRE 17 (más ligera que JDK/Maven).
# Así el contenedor de ejecución es más pequeño y rápido.
FROM eclipse-temurin:17-jre
#Directorio de trabajo donde dejaremos el JAR para ejecutarlo
WORKDIR /app
#Trae el JAR ya construido desde la etapa build y lo copia como app.jar.
#--from=build = “cógelo de la etapa de compilación”.
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
#Comando con el que arranca el contenedor: ejecutar tu JAR con Java
ENTRYPOINT ["java","-jar","/app/app.jar"]
