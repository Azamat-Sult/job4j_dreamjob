CREATE TABLE if not exists post (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   created DATE
);

CREATE TABLE if not exists candidate (
   id SERIAL PRIMARY KEY,
   name TEXT,
   photo TEXT
);

CREATE TABLE if not exists siteUser (
   id SERIAL PRIMARY KEY,
   email TEXT UNIQUE,
   name TEXT,
   password TEXT
);