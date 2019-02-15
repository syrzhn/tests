CREATE TABLE adresses (
adress_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
state TEXT,
idx TEXT,
statecount TEXT,
district TEXT,
city TEXT, 
street TEXT, 
building TEXT, 
corp TEXT,
structure TEXT, 
house TEXT);

CREATE TABLE realty_factors (
realty_factor_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
name TEXT,
multiplier REAL);

CREATE TABLE old_factors (
old_factor_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
name TEXT,
multiplier REAL);

CREATE TABLE square_factors (
square_factor_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
name TEXT,
multiplier REAL);

CREATE TABLE persons (
person_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
fio TEXT,
birth_date TEXT,
passport_serial TEXT,
passport_number TEXT);

CREATE TABLE contracts (
contract_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
tender REAL,
create_date TEXT,
actual_date TEXT,
prize REAL,
realty_factor_id INTEGER NOT NULL,
old_year INTEGER NOT NULL,
square INTEGER NOT NULL,
calculate_date TEXT,
fio_id INTEGER NOT NULL,
adress_id INTEGER NOT NULL,
comment TEXT,
FOREIGN KEY(realty_factor_id) REFERENCES realty_factors(realty_factor_id)
ON DELETE NO ACTION ON UPDATE NO ACTION,
FOREIGN KEY(fio_id) REFERENCES persons(person_id)
ON DELETE NO ACTION ON UPDATE NO ACTION,
FOREIGN KEY(adress_id) REFERENCES adresses(adress_id)
ON DELETE NO ACTION ON UPDATE NO ACTION
);

INSERT INTO adresses (state,idx,statecount,district,
city,street,building,corp,
structure,house) 
VALUES ('Russia','617047','Permskiy cry','Permskiy','Perm','Lenin st.',
'1000','A','BFG','90');

INSERT INTO contracts (tender,create_date,actual_date,
prize,realty_factor_id,old_year,
square,calculate_date,fio_id,
adress_id,comment) 
VALUES (2000.0,'2018-12-03,'2019-03-30',781,
2,9,49,'2018-12-03',3,1,'comment1');

SELECT
contract_id,
strftime('%d.%m.%Y', create_date) AS date_of_creation,
persons.fio AS fio,
prize,
strftime('%d.%m.%Y', create_date) || ' - ' || strftime('%d.%m.%Y', actual_date) AS actual_time
FROM contracts
INNER JOIN persons ON persons.person_id = contracts.fio_id;