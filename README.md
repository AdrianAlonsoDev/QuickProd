docker exec -it postgres_keycloak_database bash
exit
docker exec -it postgres_keycloak_database psql -U kcadmin -d keycloak
\q

apt update
apt install net-tools
netstat -an

docker ps
docker exec ID netstat | findstr ESTABLISHED
docker exec ID netstat -an | findstr ESTABLISHED