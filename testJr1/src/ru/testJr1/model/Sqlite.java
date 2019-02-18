package ru.testJr1.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Sqlite {
	private static boolean dataBaseExists;
	private static Connection connection;
	
	private static Connection createConnection() {
		File f = new File("db/testJr.db");
		dataBaseExists = f.exists();
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:db/testJr.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static Connection createDataBase() {
		if (connection == null)
			connection = createConnection();
		if (dataBaseExists)
			return connection;
		String scriptPath = "src\\ru\\testJr1\\model\\data_base_creation_script.sql";
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
	public static Connection loadDataFromDataBase() {
		if (connection == null) 
			createDataBase();
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
