package ru.testJr1.model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.eclipse.swt.widgets.TableItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import ru.testJr1.controller.FrmStart;

public class Sqlite {
	public static Connection connection;
	public static boolean dbIsExist;
	public static Connection createConnection() {
		File f = new File("db/testJr.db");
		if (f.exists())
			dbIsExist = true;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:db/testJr.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	public static Connection createTables() {
		if (connection == null)
			connection = createConnection();
		if (dbIsExist)
			return connection;
		try {
			connection.createStatement().execute(CREATE_realty_factors);
			connection.createStatement().execute(CREATE_old_factors);
			connection.createStatement().execute(CREATE_square_factors);
			connection.createStatement().execute(CREATE_persons);
			connection.createStatement().execute(CREATE_adresses);
			connection.createStatement().execute(CREATE_contracts);
			connection.createStatement().execute(CREATE_contracts_full_view);
			
			String insert_realty_factors = "INSERT INTO realty_factors (name, multiplier) VALUES (?,?)";
			PreparedStatement pstmt0 = connection.prepareStatement(insert_realty_factors);
			pstmt0.setString(1, "Квартира"); pstmt0.setDouble(2, 1.7); pstmt0.executeUpdate();
			pstmt0.setString(1, "Дом");      pstmt0.setDouble(2, 1.5); pstmt0.executeUpdate();
			pstmt0.setString(1, "Комната");  pstmt0.setDouble(2, 1.3); pstmt0.executeUpdate();
			
			String insert_old_factors = "INSERT INTO old_factors (name, multiplier) VALUES (?,?)";
			PreparedStatement pstmt1 = connection.prepareStatement(insert_old_factors);
			pstmt1.setString(1, "Меньше 2000"); pstmt1.setDouble(2, 1.3); pstmt1.executeUpdate();
			pstmt1.setString(1, "2000-2014");   pstmt1.setDouble(2, 1.6); pstmt1.executeUpdate();
			pstmt1.setString(1, "2015");        pstmt1.setDouble(2, 2.0); pstmt1.executeUpdate();
			
			String insert_square_factors = "INSERT INTO square_factors (name, multiplier) VALUES (?,?)";
			PreparedStatement pstmt2 = connection.prepareStatement(insert_square_factors);
			pstmt2.setString(1, "Менее 50 кв.м.");  pstmt2.setDouble(2, 1.2); pstmt2.executeUpdate();
			pstmt2.setString(1, "50-100 кв.м.");    pstmt2.setDouble(2, 1.5); pstmt2.executeUpdate();
			pstmt2.setString(1, "Более 100 кв.м."); pstmt2.setDouble(2, 2.0); pstmt2.executeUpdate();

			String insert_persons = "INSERT INTO persons (fio, birth_date, passport_serial, "
					+ "passport_number) VALUES (?,?,?,?)";
			PreparedStatement pstmt3 = connection.prepareStatement(insert_persons);
			pstmt3.setString(1, "Иван Иванов");	   
			pstmt3.setString(2, "1920-02-29"); 
			pstmt3.setString(3, "111 11"); 
			pstmt3.setString(4, "111111"); 
			pstmt3.executeUpdate();
			pstmt3.setString(1, "Петр Петров");    
			pstmt3.setString(2, "1917-12-01"); 
			pstmt3.setString(3, "222 22"); 
			pstmt3.setString(4, "222222"); 
			pstmt3.executeUpdate();
			pstmt3.setString(1, "Сергей Сергеев");
			pstmt3.setString(2, "1905-03-08"); 
			pstmt3.setString(3, "333 33"); 
			pstmt3.setString(4, "333333"); 
			pstmt3.executeUpdate();
			
			String insert_adresses = "INSERT INTO adresses ("
					+ "state," +	"idx," + "statecount," + "district,"
					+ "city," + "street," + "building," + "corp,"
					+ "structure," + "house) VALUES (?,?,?,?, ?,?,?,?, ?,?)";
			PreparedStatement pstmt4 = connection.prepareStatement(insert_adresses);
			pstmt4.setString(1,  "Russia");	   
			pstmt4.setString(2,  "617047"); 
			pstmt4.setString(3,  "Permskiy cry"); 
			pstmt4.setString(4,  "Permskiy"); 
			pstmt4.setString(5,  "Perm"); 
			pstmt4.setString(6,  "Lenin st."); 
			pstmt4.setString(7,  "1000"); 
			pstmt4.setString(8,  "A"); 
			pstmt4.setString(9,  "BFG"); 
			pstmt4.setString(10, "90"); 
			pstmt4.executeUpdate();
			
			pstmt4.setString(1,  "Russia");	   
			pstmt4.setString(2,  "617022"); 
			pstmt4.setString(3,  "Permskiy cry"); 
			pstmt4.setString(4,  "Permskiy"); 
			pstmt4.setString(5,  "Perm"); 
			pstmt4.setString(6,  "Stalin pr-t"); 
			pstmt4.setString(7,  "1"); 
			pstmt4.setString(8,  ""); 
			pstmt4.setString(9,  ""); 
			pstmt4.setString(10, "1"); 
			pstmt4.executeUpdate();
			
			pstmt4.setString(1,  "Russia");	   
			pstmt4.setString(2,  "617033"); 
			pstmt4.setString(3,  "Permskiy cry"); 
			pstmt4.setString(4,  "Permskiy"); 
			pstmt4.setString(5,  "Perm"); 
			pstmt4.setString(6,  "Gorky park"); 
			pstmt4.setString(7,  "222"); 
			pstmt4.setString(8,  "B"); 
			pstmt4.setString(9,  ""); 
			pstmt4.setString(10, "1"); 
			pstmt4.executeUpdate();
			
			String insert_contract = "INSERT INTO contracts ("
					+ "tender, " + "create_date, " + "actual_date, "
					+ "prize, " + "realty_factor_id," + "old_year, "
					+ "square, " +	"calculate_date, " + "fio_id, "
					+ "adress_id, " + "comment) VALUES (?,?,?,?, ?,?,?,?, ?,?,?)";

			PreparedStatement pstmt = connection.prepareStatement(insert_contract);
			pstmt.setDouble(1, 2000.0);
			pstmt.setString(2, "2018-12-03");
			pstmt.setString(3, "2019-03-30");
			pstmt.setDouble(4, 783);
			pstmt.setInt   (5, 1);
			pstmt.setInt   (6, 9);
			pstmt.setInt   (7, 49);
			pstmt.setString(8, "2018-12-03");
			pstmt.setInt   (9, 1);
			pstmt.setInt   (10, 1);
			pstmt.setString(11, "comments1");
			pstmt.executeUpdate();
			
			pstmt.setDouble(1, 3000.0);
			pstmt.setString(2, "2018-11-14");
			pstmt.setString(3, "2019-02-10");
			pstmt.setDouble(4, 882);
			pstmt.setInt   (5, 2);
			pstmt.setInt   (6, 10);
			pstmt.setInt   (7, 48);
			pstmt.setString(8, "2018-12-03");
			pstmt.setInt   (9, 2);
			pstmt.setInt   (10, 2);
			pstmt.setString(11, "comments2");
			pstmt.executeUpdate();
			
			pstmt.setDouble(1, 2000.0);
			pstmt.setString(2, "2018-10-01");
			pstmt.setString(3, "2019-01-20");
			pstmt.setDouble(4, 781);
			pstmt.setInt   (5, 3);
			pstmt.setInt   (6, 11);
			pstmt.setInt   (7, 47);
			pstmt.setString(8, "2018-12-03");
			pstmt.setInt   (9, 3);
			pstmt.setInt   (10, 3);
			pstmt.setString(11, "comments3");
			pstmt.executeUpdate();

			PreparedStatement pstmt5 = connection.prepareStatement(CREATE_contracts_table_view);
			pstmt5.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static final String CREATE_contracts_table_view = 
			"CREATE VIEW contracts_table_view AS" + "\r\n" + 
			"SELECT" + "\r\n" +
			"contract_id," + "\r\n" + 
			"strftime('%d.%m.%Y', create_date) AS date_of_creation," + "\r\n" +
			"persons.fio AS fio," + "\r\n" +
			"prize," + "\r\n" +
			"strftime('%d.%m.%Y', create_date) || ' - ' || strftime('%d.%m.%Y', actual_date) AS actual_time" + "\r\n" +
			"FROM contracts" + "\r\n" +
			"INNER JOIN persons ON persons.person_id = contracts.fio_id";
	public static final String CREATE_contracts_full_view = 
			"CREATE VIEW contracts_full_view AS" + "\r\n" + 
			"SELECT\r\n" + 
			"tender,\r\n" + 
			"strftime('%d.%m.%Y', create_date) AS date_of_creation,\r\n" + 
			"strftime('%d.%m.%Y', actual_date) AS date_of_actual,\r\n" + 
			"realty_factors.name AS realty_factor_name,\r\n" + 
			"old_year,\r\n" + 
			"square,\r\n" + 
			"strftime('%d.%m.%Y', calculate_date) AS date_of_calculate,\r\n" + 
			"prize,\r\n" + 
			"contract_id,\r\n" + 
			"strftime('%d.%m.%Y', contracts.create_date) AS date_of_creation1,\r\n" + 
			"persons.fio AS fio,\r\n" + 
			"persons.birth_date AS birth_date,\r\n" + 
			"persons.passport_serial AS passport_serial,\r\n" + 
			"persons.passport_number AS passport_number,\r\n" + 
			"adresses.state AS state,\r\n" + 
			"adresses.idx AS idx,\r\n" + 
			"adresses.statecount AS statecount,\r\n" + 
			"adresses.district AS district,\r\n" + 
			"adresses.city AS city,\r\n" + 
			"adresses.street AS street,\r\n" + 
			"adresses.building AS building,\r\n" + 
			"adresses.corp AS corp,\r\n" + 
			"adresses.structure AS structure,\r\n" + 
			"adresses.house AS house,\r\n" + 
			"comment\r\n" + 
			"FROM contracts\r\n" + 
			"INNER JOIN persons ON persons.person_id = contracts.fio_id\r\n" + 
			"INNER JOIN adresses ON adresses.adress_id = contracts.adress_id\r\n" + 
			"INNER JOIN realty_factors ON realty_factors.realty_factor_id = contracts.realty_factor_id";
	public static final String CREATE_contracts = 
			"CREATE TABLE contracts (" + "\r\n" + 
			"contract_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + "\r\n" +
			"tender REAL," + "\r\n" + 
			"create_date TEXT," + "\r\n" +
			"actual_date TEXT," + "\r\n" +
			"prize REAL," + "\r\n" +
			"realty_factor_id INTEGER NOT NULL," + "\r\n" +
			"old_year INTEGER NOT NULL," + "\r\n" +
			"square INTEGER NOT NULL," + "\r\n" +
			"calculate_date TEXT," + "\r\n" +
			"fio_id INTEGER NOT NULL," + "\r\n" +
			"adress_id INTEGER NOT NULL," + "\r\n" +
			"comment TEXT," + "\r\n" +
			"FOREIGN KEY(realty_factor_id) REFERENCES realty_factors(realty_factor_id) " + "\r\n" +
			"ON DELETE NO ACTION ON UPDATE NO ACTION," + 
			"FOREIGN KEY(fio_id) REFERENCES persons(person_id) " + "\r\n" +
			"ON DELETE NO ACTION ON UPDATE NO ACTION," + 
			"FOREIGN KEY(adress_id) REFERENCES adresses(adress_id) " + "\r\n" +
			"ON DELETE NO ACTION ON UPDATE NO ACTION" + 
			");";
	public static final String CREATE_adresses = 
			"CREATE TABLE adresses (" + "\r\n" + 
			"adress_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + "\r\n" + 
			"state TEXT," + "\r\n" + 
			"idx TEXT," + "\r\n" + 
			"statecount TEXT," + "\r\n" + 
			"district TEXT," + "\r\n" + 
			"city TEXT," + "\r\n" + 
			"street TEXT," + "\r\n" + 
			"building TEXT," + "\r\n" + 
			"corp TEXT," + "\r\n" + 
			"structure TEXT," + "\r\n" + 
			"house TEXT" + "\r\n" + 
			");";
	public static final String CREATE_realty_factors = 
			"CREATE TABLE realty_factors (" + "\r\n" + 
			"realty_factor_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + "\r\n" + 
			"name TEXT," + "\r\n" + 
			"multiplier REAL" + "\r\n" + 
			");";
	public static final String CREATE_old_factors = 
			"CREATE TABLE old_factors (" + "\r\n" + 
			"old_factor_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + "\r\n" + 
			"name TEXT," + "\r\n" + 
			"multiplier REAL" + "\r\n" + 
			");";
	public static final String CREATE_square_factors = 
			"CREATE TABLE square_factors (" + "\r\n" + 
			"square_factor_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + "\r\n" + 
			"name TEXT," + "\r\n" + 
			"multiplier REAL" + "\r\n" + 
			");";
	public static final String CREATE_persons = 
			"CREATE TABLE persons (" + "\r\n" + 
			"person_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + "\r\n" + 
			"fio TEXT," + "\r\n" + 
			"birth_date TEXT," + "\r\n" + 
			"passport_serial TEXT," + "\r\n" + 
			"passport_number TEXT" + "\r\n" + 
			");";	
	
	private static SessionFactory sessionFactory = null;  
	private static ServiceRegistry serviceRegistry = null;  
	  
	private static SessionFactory configureSessionFactory() throws HibernateException {  
	    Configuration configuration = new Configuration();  
	    configuration.configure();  
	    
	    Properties properties = configuration.getProperties();
	    
		serviceRegistry = new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry();          
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);  
	    
	    return sessionFactory;  
	}
	@SuppressWarnings("unchecked")
	public static Connection browseContractTableView() {
		if (connection == null) 
			createTables();
		try {
			PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM contracts_table_view");
			final ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				final String 
					contractId = rs.getString("contract_id"),
					crateDate = rs.getString("date_of_creation"),
					FIO = rs.getString("fio"),
					prize = rs.getString("prize"),
					actalDate = rs.getString("actual_time");
				FrmStart.display.asyncExec(() -> {
					TableItem item = new TableItem(FrmStart.window.tableBrowse, 0);
					item.setText( new String[] {
							contractId,
							crateDate,
							FIO,
							prize,
							actalDate
					});
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		 
		configureSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
		    transaction = session.beginTransaction();
		    
		    DataLists.contactList = session.createQuery("from Contracts").list();
			DataLists.contractsTableViewList = session.createQuery("from ContractsTableView").list();
		    DataLists.contractsTableFullList = session.createQuery("from ContractsFullView").list();
		 
		    transaction.commit();
		}
		catch (RuntimeException e) {
		    if (transaction != null) {
		        transaction.rollback();
		    }
		    throw e; 
		}
		finally {
			session.close();
		}
		return connection;
	}
}
