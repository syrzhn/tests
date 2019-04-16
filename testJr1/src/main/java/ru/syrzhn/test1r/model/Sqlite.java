package ru.syrzhn.test1r.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Sqlite {
	private static boolean dataBaseExists;
	private static Connection connection;
	
	private static Connection createConnection() {
		File f = new File("db/testJr.db");
		dataBaseExists = f.exists();
		try {
			if(connection == null)			
				connection = DriverManager.getConnection("jdbc:sqlite:db/testJr.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static Connection createDataBase() {
		createConnection();
		if (dataBaseExists)
			return connection;
		String scriptPath = "src\\main\\java\\ru\\syrzhn\\test1r\\model\\data_base_creation_script.sql";
		try {
			BufferedReader in = new BufferedReader(new FileReader(scriptPath));
			String str = null;
			StringBuffer sb = new StringBuffer();
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}
			in.close();
			for (String s : sb.toString().split(";")) {
				if (s.indexOf("INSERT") == 0) {
					PreparedStatement pstmt = connection.prepareStatement(s);
					pstmt.executeUpdate();
				}
				else {
					connection.createStatement().execute(s);
				}
			}
		} catch (Exception e) {
			System.err.println("Failed to Execute \"" + scriptPath  + "\". The error is"+ e.getMessage());
		}
		return connection;
	}	
}
