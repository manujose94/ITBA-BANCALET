# Bancalet Virtual

Application for the sale of food products for the subject PAW
Group: paw-2019a-6

## Accounts

_Admin:_

* User: _admin_

* Password: _admin_

_User:_

* User: _user123_

* Password: _user123_

## Authors

* Lurbe Sempere, Manel
* Martínez Baños, Manuel José
* Catalán Gallach, Izan

## Version Log

### V1

* Views with JSP
* Persistence with JDBC

## Production

On the root folder of the project package the application with `mvn package`.
The final war will be in `/webapp/target/app.war`

## Development

DB tables: /persistence/src/main/resources/schema.sql
Propierties: /webapp/src/main/resources/config/application.propierties
Deploy the app.war in tomcat, generally it will be `http://localhost:8080/app/`. Then start tomcat and get working

## Deploy pampero ITBA

* Clonar el repositorio:
ssh mlurbe@pampero.itba.edu.ar
git clone https://mlurbeITBA@bitbucket.org/itba/paw-2019a-6.git

* Compilar dentro de la carpeta Bancalet:
mvn clean
mvn compile:compile
mvn install
mvn package

* Copiar por sft el .war generado:
sftp paw-2019a-6@10.16.1.110
put Bancalet/webapp/target/app.war

* Crear las tablas en la base de datos:
\i Bancalet/persistence/src/main/resources/schema.sql
psql -h 10.16.1.110 -U paw-2019a-6
