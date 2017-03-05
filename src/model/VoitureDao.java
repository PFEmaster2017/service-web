package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import entities.Voiture;


public class VoitureDao {
	SessionFactory sessionFactory = null ;
	Session session = null ;
	public VoitureDao(){
		sessionFactory = new Configuration().configure().buildSessionFactory();
	
	}
	
	public Voiture findbyCodeC(Integer numEnrg) {
		 SessionFactory sessionFactory = HibernateUtil.getSessionFactory();  
	    Session session = sessionFactory.openSession();  
	    String req= "from Voiture c where c.numEnrg=:numEnrg";
	    Query query = session.createQuery(req);
	    query.setParameter("numEnrg", numEnrg);
	  
	    List<Voiture> clt = query.list();  
	      
	    session.close();  
	   if(clt==null || clt.size()==0)   
		return null;
	   else
	   	return clt.get(0);	// TODO Auto-generated method stub

	}
	public static List<Voiture> findAll() {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		List<Voiture> voit=session.createQuery("From Voiture").list();
		session.getTransaction().commit();
		session.close();
		return voit;

	}
	public List<Voiture> findbyDescription(String descriptionVoiture) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		   String req= "from Voiture c where c.descriptionVoiture=:descriptionVoiture";
		   Query query = session.createQuery(req);
		    query.setParameter("descriptionVoiture", descriptionVoiture);
		  
		   
		List<Voiture> voit=query.list();
		session.getTransaction().commit();
		session.close();
		return voit;
	}

	
	public void delete(Voiture c) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(c);
		session.getTransaction().commit();
		session.close();
	}
	
	public Voiture save(Voiture c) {
		Session session = sessionFactory.openSession();  
	    session.beginTransaction();  
	    session.save(c);  
	    session.getTransaction().commit();  
	    session.close(); 
	    return  c ;
	   
	}
	
	

	public  Voiture update(Voiture c) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();	
		session.update(c);
		session.getTransaction().commit();
		session.close();
		return c;
	}

	
	//origin
	public List<Voiture> getAllProduct(){
		
		List<Voiture> voit = new ArrayList<Voiture>() ;
		try {
			session=sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery("from Voiture") ;
			voit= query.list();
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			session.close();
		}
		return voit ;
	}
}
