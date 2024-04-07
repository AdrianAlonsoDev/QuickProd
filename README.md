# QUICKPROD 🚀
QuickProd es un framework que permite maximizar la eficiencia en la gestión de productos en diferentes contextos.

(Desarrollado para la prueba técnica de DEKRA)

## Installation 🛠️
- Instalar Docker y Docker Compose.

## Run the project 🏃
Para ejecutar el proyecto, sigue estos pasos:

1. Dentro de la carpeta de Keycloak, ejecuta el siguiente comando:
    - `docker-compose up --build -d`

2. Ahora ejecuta la aplicación Spring Boot con el siguiente comando:
    - `mvn spring-boot:run`


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
