package ru.syrzhn.test1r.utils;

import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
 
public class HibernateUtil {
 
	private static SessionFactory sessionFactory;  
	private static ServiceRegistry serviceRegistry;  
	  
	private static SessionFactory configureSessionFactory() throws HibernateException {  
	    Configuration configuration = new Configuration();  
	    configuration.configure();  
	    
	    Properties properties = configuration.getProperties();
	    
		serviceRegistry = new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry();          
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);  
	    
	    return sessionFactory;  
	}
 
    public static SessionFactory getSessionFactory() {
    	if (sessionFactory == null)
    		configureSessionFactory();
        return sessionFactory;
    }
}
