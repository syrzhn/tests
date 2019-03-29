package ru.syrzhn.test1r.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.syrzhn.test1r.model.entities.Adress;
import ru.syrzhn.test1r.model.entities.Contract;
import ru.syrzhn.test1r.model.entities.ContractFullView;
import ru.syrzhn.test1r.model.entities.ContractTableView;
import ru.syrzhn.test1r.model.entities.OldFactor;
import ru.syrzhn.test1r.model.entities.Person;
import ru.syrzhn.test1r.model.entities.RealtyFactor;
import ru.syrzhn.test1r.model.entities.SquareFactor;
import ru.syrzhn.test1r.utils.HibernateUtil;

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
