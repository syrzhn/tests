package ru.testJr1.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.testJr1.model.entities.Contracts;
import ru.testJr1.model.entities.ContractsFullView;
import ru.testJr1.model.entities.ContractsTableView;
import ru.testJr1.utils.HibernateUtil;

public class DataLists {
	public static List<Contracts> contactList;
    public static List<ContractsTableView> contractsTableViewList;
    public static List<ContractsFullView> contractsTableFullList;
	@SuppressWarnings("unchecked")
	public static void loadDataFromDataBase() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
		    transaction = session.beginTransaction();
		    
		    contactList = session.createQuery("from Contracts").list();
			contractsTableViewList = session.createQuery("from ContractsTableView").list();
		    contractsTableFullList = session.createQuery("from ContractsFullView").list();
		    
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
	}
}
