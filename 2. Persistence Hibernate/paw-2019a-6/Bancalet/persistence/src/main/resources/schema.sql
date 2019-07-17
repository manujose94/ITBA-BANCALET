CREATE TABLE IF NOT EXISTS users (
  userId SERIAL PRIMARY KEY,
  username varchar(100) UNIQUE,
  email varchar(100) UNIQUE,
  telf varchar(100),
  city varchar(100),
  country varchar(100),
  code varchar(100),
  password varchar(100),
  role VARCHAR(30),
  direccion VARCHAR(200),
  numImg NUMERIC,
  urlImg VARCHAR,
  image BYTEA,
  rate NUMERIC,
  lat FLOAT,
  lon FLOAT,
  estado NUMERIC
);

CREATE TABLE IF NOT EXISTS items (
  itemId SERIAL PRIMARY KEY,
  idVendedor NUMERIC NOT NULL,
  name   varchar(100),
  tipo NUMERIC,
  price  REAL,
  description   varchar(500),
  fechaCaducidad date,
  fechaPublicacion date,
  image BYTEA,
  urlImg VARCHAR,
  estado NUMERIC,
  numeroVisitas NUMERIC
);

CREATE TABLE IF NOT EXISTS historial (
  idHistorico SERIAL PRIMARY KEY,
  idComprador NUMERIC NOT NULL,
  idVendedor   NUMERIC NOT NULL,
  itemId NUMERIC NOT NULL,
  valoracion varchar(500),
  estrellas NUMERIC,
  fechaCompra  date NOT NULL
);

CREATE TABLE IF NOT EXISTS ayuda (
  idAyuda SERIAL PRIMARY KEY,
  userId NUMERIC NOT NULL,
  asunto VARCHAR (200),
  name VARCHAR (100),
  email VARCHAR(100),
  fechaContacto date NOT NULL,
  mensaje VARCHAR(200),
  estado NUMERIC NOT NULL,
  fechaResolucion date,
  informe VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS contacto (
  idContacto SERIAL PRIMARY KEY,
  idComprador NUMERIC,
  idVendedor NUMERIC NOT NULL,
  itemId NUMERIC NOT NULL,
  fechaContacto date NOT NULL,
  mensaje VARCHAR(200),
  estado NUMERIC,
  read NUMERIC
);

CREATE TABLE IF NOT EXISTS itemimage (
  idImage SERIAL PRIMARY KEY,
  userId NUMERIC NOT NULL,
  itemId NUMERIC NOT NULL,
  image BYTEA NOT NULL
);

INSERT INTO users (userId,username, email, telf, city, country, code,password,role,direccion,numImg,urlImg,image,rate,lat,lon,estado) VALUES (NEXTVAL('users_userid_seq'),'administrador', 'administrador@administrador.com', '123456', 'Buenos Aires','Argentina','CABA123','$2a$10$s7v6Xsi2JKHxK9BU3RYP9.ZDHfvVmZppoL9m4Sw0Eshst2a4oUB6O','ADMIN','Calle corrientes numero 1','0','','','0','0','0','1') ON CONFLICT DO NOTHING;
INSERT INTO users (userId,username, email, telf, city, country, code,password,role,direccion,numImg,urlImg,image,rate,lat,lon,estado) VALUES (NEXTVAL('users_userid_seq'),'usuario', 'user@user.user', '123456', 'Buenos Aires','Argentina','CABA123','$2a$10$A8plO/WNh8bKc0/Nw/D4wumOSsjX8dON55Oq3tKuk9/7s741bmjSu','USER','Calle corrientes numero 2','0','','','0','0','0','1') ON CONFLICT DO NOTHING;
