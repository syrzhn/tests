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
			connection.createStatement().execute(CREATE_realtyFactor);
			connection.createStatement().execute(CREATE_oldFactor);
			connection.createStatement().execute(CREATE_squareFactor);
			connection.createStatement().execute(CREATE_person);
			connection.createStatement().execute(CREATE_contract);
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
			String insert_realtyFactor = "INSERT INTO [realtyFactor] ([name], [multiplier]) VALUES (?,?)";
			PreparedStatement pstmt0 = connection.prepareStatement(insert_realtyFactor);
			pstmt0.setString(1, "Квартира"); pstmt0.setDouble(2, 1.7); pstmt0.executeUpdate();
			pstmt0.setString(1, "Дом");      pstmt0.setDouble(2, 1.5); pstmt0.executeUpdate();
			pstmt0.setString(1, "Комната");  pstmt0.setDouble(2, 1.3); pstmt0.executeUpdate();
			
			String insert_oldFactor = "INSERT INTO [oldFactor] ([name], [multiplier]) VALUES (?,?)";
			PreparedStatement pstmt1 = connection.prepareStatement(insert_oldFactor);
			pstmt1.setString(1, "Меньше 2000"); pstmt1.setDouble(2, 1.3); pstmt1.executeUpdate();
			pstmt1.setString(1, "2000-2014");   pstmt1.setDouble(2, 1.6); pstmt1.executeUpdate();
			pstmt1.setString(1, "2015");        pstmt1.setDouble(2, 2);   pstmt1.executeUpdate();
			
			String insert_squareFactor = "INSERT INTO [squareFactor] ([name], [multiplier]) VALUES (?,?)";
			PreparedStatement pstmt2 = connection.prepareStatement(insert_squareFactor);
			pstmt2.setString(1, "Менее 50 кв.м.");  pstmt2.setDouble(2, 1.2); pstmt2.executeUpdate();
			pstmt2.setString(1, "50-100 кв.м.");    pstmt2.setDouble(2, 1.5); pstmt2.executeUpdate();
			pstmt2.setString(1, "Более 100 кв.м."); pstmt2.setDouble(2, 2);   pstmt2.executeUpdate();

			String insert_person = "INSERT INTO [person] ([FIO], [birthDate]) VALUES (?,'";
			PreparedStatement pstmt3 = connection.prepareStatement(insert_person + "1920-2-29')");
			pstmt3.setString(1, "Иван Иванов");	  pstmt3.executeUpdate();
			PreparedStatement pstmt4 = connection.prepareStatement(insert_person + "1917-12-01')");
			pstmt4.setString(1, "Петр Петров");   pstmt4.executeUpdate();
			PreparedStatement pstmt5 = connection.prepareStatement(insert_person + "1905-03-08')");
			pstmt5.setString(1, "Сергей Сергеев");pstmt5.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	public static Connection createContract(int fio_id, int prize, String date1, String date2) {
		if (connection == null)
			connection = insertSimpleData();
		if (dbIsExist)
			return connection;
		String insert_contract = "INSERT INTO [contract] ([FIO_id], [prize], [crateDate], [actalDate]) "
				+ "VALUES (?,?,'" + date1 + "','" + date2 + "')";
		try {
			PreparedStatement pstmt = connection.prepareStatement(insert_contract);
			pstmt.setInt(1, fio_id);
			pstmt.setInt(2, prize);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	public static Connection createContractTableView() {
		if (connection == null) {
			insertSimpleData();
			createContract(1, 1367, "2015-2-3",  "2015-2-8");
			createContract(2, 3214, "2015-2-16", "2015-7-15");
			createContract(3, 2691, "2015-3-1",  "2015-5-31");
		}
		if (dbIsExist)
			return connection;
		try {
			PreparedStatement pstmt = connection.prepareStatement(CREATE_contractTableVIew);
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
			PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM contractTableView");
			final ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				final String 
					contractId = rs.getString("contractId"),
					crateDate = rs.getString("crateDate"),
					FIO = rs.getString("FIO"),
					prize = rs.getString("prize"),
					actalDate = rs.getString("actalDate");
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
	public static final String CREATE_contract = 
			"CREATE TABLE IF NOT EXISTS [contract] (" + "\r\n" + 
			"[contractId] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + "\r\n" + 
			"[FIO_id] INTEGER NOT NULL," + "\r\n" + 
			"[prize] INTEGER," + "\r\n" + 
			"[crateDate] DATE," + "\r\n" + 
			"[actalDate] DATE," + "\r\n" +
			"FOREIGN KEY([FIO_id]) REFERENCES [person]([id]) " + "\r\n" +
			"ON DELETE NO ACTION ON UPDATE NO ACTION" +
			");";
	public static final String CREATE_realtyFactor = 
			"CREATE TABLE IF NOT EXISTS [realtyFactor] (" + "\r\n" + 
			"[realtyFactorId] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + "\r\n" + 
			"[name] TEXT," + "\r\n" + 
			"[multiplier] REAL" + "\r\n" + 
			");";
	public static final String CREATE_oldFactor = 
			"CREATE TABLE IF NOT EXISTS [oldFactor] (" + "\r\n" + 
			"[oldFactorId] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + "\r\n" + 
			"[name] TEXT," + "\r\n" + 
			"[multiplier] REAL" + "\r\n" + 
			");";
	public static final String CREATE_squareFactor = 
			"CREATE TABLE IF NOT EXISTS [squareFactor] (" + "\r\n" + 
			"[squareFactorId] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + "\r\n" + 
			"[name] TEXT," + "\r\n" + 
			"[multiplier] REAL" + "\r\n" + 
			");";
	public static final String CREATE_person = 
			"CREATE TABLE IF NOT EXISTS [person] (" + "\r\n" + 
			"[personId] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + "\r\n" + 
			"[FIO] TEXT," + "\r\n" + 
			"[birthDate] DATE" + "\r\n" + 
			");";
	public static final String CREATE_contractTableVIew = 
			"CREATE VIEW [contractTableView] AS" + "\r\n" + 
			"SELECT" + "\r\n" +
			"[contractId], [crateDate]," + "\r\n" + 
			"person.FIO AS FIO," + "\r\n" + 
			"[prize], [actalDate]" + "\r\n" + 
			"FROM [contract]" + "\r\n" + 
			"INNER JOIN [person] ON person.personId = contract.FIO_id";
}
