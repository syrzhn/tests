BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "contracts" (
	"contract_id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"tender"	REAL,
	"create_date"	TEXT,
	"actual_date"	TEXT,
	"prize"	REAL,
	"realty_factor_id"	INTEGER NOT NULL,
	"old_year"	INTEGER NOT NULL,
	"square"	INTEGER NOT NULL,
	"calculate_date"	TEXT,
	"fio_id"	INTEGER NOT NULL,
	"adress_id"	INTEGER NOT NULL,
	"comment"	TEXT,
	FOREIGN KEY("realty_factor_id") REFERENCES "realty_factors"("realty_factor_id") ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY("fio_id") REFERENCES "persons"("person_id") ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY("adress_id") REFERENCES "adresses"("adress_id") ON DELETE NO ACTION ON UPDATE NO ACTION
);
CREATE TABLE IF NOT EXISTS "adresses" (
	"adress_id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"state"	TEXT,
	"idx"	TEXT,
	"statecount"	TEXT,
	"district"	TEXT,
	"city"	TEXT,
	"street"	TEXT,
	"building"	TEXT,
	"corp"	TEXT,
	"structure"	TEXT,
	"house"	TEXT
);
CREATE TABLE IF NOT EXISTS "persons" (
	"person_id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"fio"	TEXT,
	"birth_date"	TEXT,
	"passport_serial"	TEXT,
	"passport_number"	TEXT
);
CREATE TABLE IF NOT EXISTS "square_factors" (
	"square_factor_id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"name"	TEXT,
	"multiplier"	REAL
);
CREATE TABLE IF NOT EXISTS "old_factors" (
	"old_factor_id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"name"	TEXT,
	"multiplier"	REAL
);
CREATE TABLE IF NOT EXISTS "realty_factors" (
	"realty_factor_id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"name"	TEXT,
	"multiplier"	REAL
);
INSERT INTO "contracts" ("contract_id","tender","create_date","actual_date","prize","realty_factor_id","old_year","square","calculate_date","fio_id","adress_id","comment") VALUES (1,2000.0,'2018-12-03','2019-03-30',783.0,1,9,49,'2018-12-03',1,1,'comments1'),
 (2,3000.0,'2018-11-14','2019-02-10',882.0,2,10,48,'2018-12-03',2,2,'comments2'),
 (3,2000.0,'2018-10-01','2019-01-20',781.0,3,11,47,'2018-12-03',3,3,'comments3');
INSERT INTO "adresses" ("adress_id","state","idx","statecount","district","city","street","building","corp","structure","house") VALUES (1,'Russia','617047','Permskiy cry','Permskiy','Perm','Lenin st.','1000','A','BFG','90'),
 (2,'Russia','617022','Permskiy cry','Permskiy','Perm','Stalin pr-t','1','','','1'),
 (3,'Russia','617033','Permskiy cry','Permskiy','Perm','Gorky park','222','B','','1');
INSERT INTO "persons" ("person_id","fio","birth_date","passport_serial","passport_number") VALUES (1,'Иван Иванов','1920-02-29','111 11','111111'),
 (2,'Петр Петров','1917-12-01','222 22','222222'),
 (3,'Сергей Сергеев','1905-03-08','333 33','333333');
INSERT INTO "square_factors" ("square_factor_id","name","multiplier") VALUES (1,'Менее 50 кв.м.',1.2),
 (2,'50-100 кв.м.',1.5),
 (3,'Более 100 кв.м.',2.0);
INSERT INTO "old_factors" ("old_factor_id","name","multiplier") VALUES (1,'Меньше 2000',1.3),
 (2,'2000-2014',1.6),
 (3,'2015',2.0);
INSERT INTO "realty_factors" ("realty_factor_id","name","multiplier") VALUES (1,'Квартира',1.7),
 (2,'Дом',1.5),
 (3,'Комната',1.3);
CREATE VIEW contracts_table_view AS SELECT
 contract_id,
 strftime('%d.%m.%Y', create_date) AS date_of_creation,
 persons.fio AS fio,
 prize,
 strftime('%d.%m.%Y', create_date) || ' - ' || strftime('%d.%m.%Y', actual_date) AS actual_time
 FROM contracts
 INNER JOIN persons ON persons.person_id = contracts.fio_id;
CREATE VIEW contracts_full_view AS SELECT
 tender,
 strftime('%d.%m.%Y', create_date) AS date_of_creation,
 strftime('%d.%m.%Y', actual_date) AS date_of_actual,
 realty_factors.name AS realty_factor_name,
 old_year,
 square,
 strftime('%d.%m.%Y', calculate_date) AS date_of_calculate,
 prize,
 contract_id,
 strftime('%d.%m.%Y', contracts.create_date) AS date_of_creation1,
 persons.fio AS fio,
 strftime('%d.%m.%Y', persons.birth_date) AS birth_date,
 persons.passport_serial AS passport_serial,
 persons.passport_number AS passport_number,
 adresses.state AS state,
 adresses.idx AS idx,
 adresses.statecount AS statecount,
 adresses.district AS district,
 adresses.city AS city,
 adresses.street AS street,
 adresses.building AS building,
 adresses.corp AS corp,
 adresses.structure AS structure,
 adresses.house AS house,
 comment
 FROM contracts
 INNER JOIN persons ON persons.person_id = contracts.fio_id
 INNER JOIN adresses ON adresses.adress_id = contracts.adress_id
 INNER JOIN realty_factors ON realty_factors.realty_factor_id = contracts.realty_factor_id;
COMMIT;

