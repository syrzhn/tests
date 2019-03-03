package ru.syrzhn.server;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.syrzhn.shared.entities.Adress;
import ru.syrzhn.shared.entities.Contract;
import ru.syrzhn.shared.entities.ContractFullView;
import ru.syrzhn.shared.entities.ContractTableView;
import ru.syrzhn.shared.entities.OldFactor;
import ru.syrzhn.shared.entities.Person;
import ru.syrzhn.shared.entities.RealtyFactor;
import ru.syrzhn.shared.entities.SquareFactor;

public class DataLists {
	public static List<Adress> adressList;
	public static List<Contract> contactList;
	public static List<Person> personList;
	public static List<SquareFactor> squareFactorList;
	public static List<OldFactor> oldFactorList;
	public static List<RealtyFactor> realtyFactorList;
    public static List<ContractTableView> contractTableViewList;
    public static List<ContractFullView> contractTableFullList;
	@SuppressWarnings("unchecked")
	public static void loadDataFromDataBase() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
		    transaction = session.beginTransaction();
		    
		    Sqlite.createDataBase();
		    
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
