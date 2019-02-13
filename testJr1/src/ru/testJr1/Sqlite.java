package ru.testJr1;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.widgets.TableItem;

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
			connection.createStatement().execute(CREATE_contracts);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	public static Connection insertSimpleData() {
		if (connection == null)
			connection = createTables();
		if (dbIsExist)
			return connection;
		try {
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

			String insert_persons = "INSERT INTO persons (fio, birth_date) VALUES (?,?)";
			PreparedStatement pstmt3 = connection.prepareStatement(insert_persons);
			pstmt3.setString(1, "Иван Иванов");	   pstmt3.setString(2, "1920-02-29"); pstmt3.executeUpdate();
			pstmt3.setString(1, "Петр Петров");    pstmt3.setString(2, "1917-12-01"); pstmt3.executeUpdate();
			pstmt3.setString(1, "Сергей Сергеев"); pstmt3.setString(2, "1905-03-08"); pstmt3.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	public static Connection createContract(int fio_id, double prize, String date1, String date2) {
		if (connection == null)
			connection = insertSimpleData();
		if (dbIsExist)
			return connection;
		String insert_contract = "INSERT INTO contracts (fio_id, prize, create_date, actual_date) VALUES (?,?,?,?)";
		try {
			PreparedStatement pstmt = connection.prepareStatement(insert_contract);
			pstmt.setInt(1, fio_id);
			pstmt.setDouble(2, prize);
			pstmt.setString(3, date1);
			pstmt.setString(4, date2);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	public static Connection createContractTableView() {
		if (connection == null) {
			insertSimpleData();
			createContract(1, 1367.23, "2015-02-03", "2015-02-08");
			createContract(2, 2314.42, "2015-02-16", "2015-07-15");
			createContract(3, 2891.76, "2015-03-01", "2015-12-31");
		}
		if (dbIsExist)
			return connection;
		try {
			PreparedStatement pstmt = connection.prepareStatement(CREATE_contracts_table_view);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	public static Connection browseContractTableView() {
		if (connection == null) 
			createContractTableView();
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
		return connection;
	}
	public static final String CREATE_contracts = 
			"CREATE TABLE contracts (" + "\r\n" + 
			"contract_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + "\r\n" + 
			"fio_id INTEGER NOT NULL," + "\r\n" + 
			"prize REAL," + "\r\n" + 
			"create_date TEXT," + "\r\n" + 
			"actual_date TEXT," + "\r\n" +
			"FOREIGN KEY(fio_id) REFERENCES persons(person_id) " + "\r\n" +
			"ON DELETE NO ACTION ON UPDATE NO ACTION" + 
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
}
