package it.unical.asde2018.memory.components.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import it.unical.asde2018.memory.model.Player;

public class PlayerDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void savePlayer(Player player) {
		Session session = sessionFactory.openSession();
		
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			session.save(player);
			transaction.commit();
			
		}catch (Exception e) {
			transaction.rollback();
		}
		session.close();
	}
	
}
