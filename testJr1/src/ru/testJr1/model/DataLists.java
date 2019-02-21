package ru.testJr1.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.testJr1.model.entities.Adress;
import ru.testJr1.model.entities.Contract;
import ru.testJr1.model.entities.ContractFullView;
import ru.testJr1.model.entities.ContractTableView;
import ru.testJr1.model.entities.Person;
import ru.testJr1.utils.HibernateUtil;

public class DataLists {
	public static List<Adress> adressList;
	public static List<Contract> contactList;
    public static List<ContractTableView> contractTableViewList;
    public static List<ContractFullView> contractTableFullList;
	public static List<Person> personList;
	public static List<Person> squareFactorList;
	public static List<Person> oldFactorList;
	public static List<Person> realtyFactorList;
	@SuppressWarnings("unchecked")
	public static void loadDataFromDataBase() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
		    transaction = session.beginTransaction();
		    
		    adressList = session.createQuery("from Adress").list();
		    personList = session.createQuery("from Person").list();
		    squareFactorList = session.createQuery("from SquareFactor").list();
		    oldFactorList = session.createQuery("from OldFactor").list();
		    realtyFactorList = session.createQuery("from RealtyFactor").list();
		    contactList = session.createQuery("from Contract").list();
			contractTableViewList = session.createQuery("from ContractTableView").list();
		    contractTableFullList = session.createQuery("from ContractFullView").list();
		    
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
