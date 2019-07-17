# **Bancalet Virtual**

Application for the sale of food products

## **Accounts**

_Admin:_

* User: _administrador_

* Password: _administrador_

_User:_

* User: _usuario_

* Password: _usuario_

## **Authors**

* Lurbe Sempere, Manel
* Martínez Baños, Manuel José
* Catalán Gallach, Izan

## **Version Log**

### **V1**

* Views with JSP.
* Persistence with JDBC.

### **V2**

* Persistence with Hibernate.
* New features and BUG fixes.

### **V3**

* Remake using API REST with Jersey.
* Client REST with IONIC 4.

## **API REST**

[API REST Bancalet Virtual documented](https://github.com/mlurbe97/BANCALET-REST-API/blob/master/README.md)

## **Production**

* Clone the repo:

	- git clone https://mlurbeITBA@bitbucket.org/itba/paw-2019a-6.git

* On the root folder of the project package the application with :

	- mvn clean
	- mvn compile
	- mvn clean package

* You need to configure the following files in order to launch it:

	- Install postgres

	- Create user postgres: myuser mypass

	- Create if necesary DB: Bancalet

	- DB tables are in the folder: `Bancalet/persistence/src/main/resources/schema.sql`

	- Create the tables in a postgres database: \i Bancalet/persistence/src/main/resources/schema.sql

	- Propierties: `Bancalet/webapp/src/main/resources/config/application.propierties`

	- Copy the app.war file that was generated in the `Bancalet/webapp/target` folder on your web server

	- Deploy the app.war in tomcat, generally it will be at: `http://localhost:8080/app/`.


## **Development**

* Install postgres:

	- Create user postgres: myuser mypass

	- Create if necesary DB: Bancalet

	- Create if necesary DB: BancaletTest (for mvn test only)

* For developing:

	- Must use jdk version 1.8

	- If you are using Eclipse you can install Run-Jetty from Eclipse Marketplace for more comfort or configure a apache tomcat server or other

* Clone repository into your eclipse workspace:

 	- git clone https://mlurbeITBA@bitbucket.org/itba/paw-2019a-6.git

	- You can configure your `.gitignore` to ignore files like `.classpath` and more temporally files

	- In eclipse, select Open > Existing Maven Projects > project folder location

* You need to configure the following files in order to launch it:

	- DB tables: `Bancalet/persistence/src/main/resources/schema.sql`

	- Propierties: `Bancalet/webapp/src/main/resources/config/application.propierties`

* Launch

	- Eclipse > Run As > Run on Jetty

	- If everything went well, generally it will be at:  `http://loaclhost:8080/webapp/` and use the application

## **Deploy pampero ITBA**

* Clone the repo:

	- ssh mlurbe@pampero.it.itba.edu.ar

	- git clone https://mlurbeITBA@bitbucket.org/itba/paw-2019a-6.git

* On the root folder of the project package the application with :

	- mvn clean
	- mvn compile
	- mvn clean package

* Copy the app.war file that was generated in the `Bancalet/webapp/target` folder by sft to:

	- sftp paw-2019a-6@10.16.1.110

	- put Bancalet/webapp/target/app.war

* Create/Update the tables in the postgres database:

	- Run database script if necessary to backup any table data.

	- psql -h 10.16.1.110 -U paw-2019a-6

	- \i Bancalet/persistence/src/main/resources/schema.sql

	- Run a script if necessary to restore a backup of the data in the tables
