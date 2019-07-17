CREATE TABLE IF NOT EXISTS users (
  userid INTEGER IDENTITY PRIMARY KEY,
  username varchar(100) UNIQUE,
  email varchar(100) UNIQUE,
  telf varchar(100),
  city varchar(100),
  country varchar(100),
  code varchar(100),
  password varchar(100),
  role varchar(30) NOT NULL,
  direccion VARCHAR(200),
  numimg NUMERIC,
  urlimg VARCHAR(400),
  image BINARY
);

CREATE TABLE IF NOT EXISTS items (
  itemid INTEGER IDENTITY PRIMARY KEY,
  idvendedor NUMERIC NOT NULL,
  name   varchar(100),
  tipo NUMERIC,
  price  REAL,
  description   varchar(500),
  fecha_caducidad date,
  fecha_publicacion date,
  image BINARY,
  estado varchar(30),
  numeroVisitas NUMERIC,
);

CREATE TABLE IF NOT EXISTS historial (
  idhistorico INTEGER IDENTITY PRIMARY KEY,
  idcomprador NUMERIC NOT NULL,
  idvendedor   NUMERIC NOT NULL,
  iditem NUMERIC NOT NULL,
  valoracion varchar(500),
  estrellas NUMERIC,
  fecha_compra  date NOT NULL
);

CREATE TABLE IF NOT EXISTS ayuda (
  idayuda INTEGER IDENTITY PRIMARY KEY,
  iduser NUMERIC,
  asunto VARCHAR (200),
  name VARCHAR (30),
  email VARCHAR(50),
  fecha_contacto date NOT NULL,
  mensaje VARCHAR(200),
  estado NUMERIC NOT NULL,
  fecha_resolucion date,
  informe VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS contacto (
  idcontacto INTEGER IDENTITY PRIMARY KEY,
  idcomprador NUMERIC,
  idvendedor NUMERIC NOT NULL,
  iditem NUMERIC NOT NULL,
  fecha_contacto date NOT NULL,
  mensaje VARCHAR(200)
);
