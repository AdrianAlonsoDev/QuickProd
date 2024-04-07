# QUICKPROD
QuickProd es un framework que permite maximizar la eficiencia en la gestión de productos en diferentes contextos.

# Netstat Utils
https://geekflare.com/check-docker-network-connections/

# Docker Utils
### Docker command to caccess to the container
```bash
docker exec -it postgres_keycloak_database bash
exit
```
### Docker command to access the database
```bash
docker exec -it postgres_keycloak_database psql -U kcadmin -d keycloak
\q
```
### Docker cmd with findstr to see established connections.

#### Get docker containers
```bash
docker ps
```
#### Get stablished connections from outside.
```bash
docker exec ID netstat | findstr ESTABLISHED
docker exec ID netstat -an | findstr ESTABLISHED
```