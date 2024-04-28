# QUICKPROD 🚀
!! (Version v2 OUT: https://github.com/AdrianAlonsoDev/QuickProd-v2)

QuickProd es un framework que permite maximizar la eficiencia
en la gestión de productos en diferentes contextos.

## Information 🛠️
Repositorio de páctica hecho público para mostrar las diferentes lineas de aprendizaje para terminar desarrollando la versión [QP-v2](https://github.com/AdrianAlonsoDev/QuickProd-v2)

El proyecto se divide en 3 branches. 
* main
* [discovery](https://github.com/AdrianAlonsoDev/QuickProd/tree/discovery) 
* [refactor-keycloak-redis](https://github.com/AdrianAlonsoDev/QuickProd/tree/refactor-keycloak-redis/)
## Installation 🛠️
- Instalar Docker y Docker Compose.

## Run the project 🏃
Para ejecutar el proyecto, sigue estos pasos:

1. Dentro de la carpeta de Keycloak, ejecuta el siguiente comando:
    - `docker-compose up --build -d`


2. Ahora ejecuta la aplicación Spring Boot con el siguiente comando:
    - `mvn spring-boot:run`


3. Importa la colección de Postman `QuickProd.postman_collection.json`
que se encuentra en la carpeta `resources/postman` para probar los endpoints.


4. Conectate al frontend de Keycloak en la siguiente URL:
    - `http://localhost:8080`
    - Usuario: admin
    - Contraseña: admin@123


## Docker Utils 🐳
Lista de contenedores:
- keycloak_web
- keycloak_db

#### Acceder a los contenedores
Para acceder a un contenedor, utiliza el comando:
* `docker exec -it {nombre_del_contenedor} bash`


* Para salir usa, `exit`

#### Acceder al BASH de la base de datos
* `docker exec -it postgres_keycloak_database psql -U kcadmin -d keycloak`


* Para salir, usa `\q`

## Netstat Utils 🔍
#### Obtener conexiones establecidas al contenedor de la base de datos
Para verificar las conexiones establecidas, sigue estos pasos:
1. Obtén los ID de los contenedores con: `docker ps`
2. Ejecuta uno de los siguientes comandos para ver las conexiones:
    - `docker exec {ID} netstat | findstr ESTABLISHED`
    - `docker exec {ID} netstat -an | findstr ESTABLISHED`

[🔗Ver conexiones de red de Docker ](https://geekflare.com/check-docker-network-connections/)
