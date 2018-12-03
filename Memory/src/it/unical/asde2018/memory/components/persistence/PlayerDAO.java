package it.unical.asde2018.memory.components.persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.unical.asde2018.memory.model.Player;

@Repository
public class PlayerDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void savePlayer(Player player) {
		System.out.println("Player DAO " + player);
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(player);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
		}
		finally{
	        session.close();
	    }
	}
	
	public boolean exists(String username, String password) {
		Session session = sessionFactory.openSession();
		Query<Player> query = session
				.createQuery("from Player as p where p.username=:user and p.password=:pass", Player.class)
				.setParameter("user", username).setParameter("pass", password);
		boolean result = query.uniqueResult() != null;
		session.close();
		return result;
	}
	
	public boolean yetAnUser(String username) {
		Session session = sessionFactory.openSession();
		Query<Player> query = session.createQuery("from Player as p where p.username=:user", Player.class)
				.setParameter("user", username);
		boolean result = query.uniqueResult() != null;
		session.close();
		return result;
	}

	public List<Player> getAllExistentUsers() {
		Session session = sessionFactory.openSession();
		Query<Player> query = session.createQuery("FROM Player", Player.class);
		List<Player> players = query.list();
		session.close();
		return players;
	}
	
	public Long getPlayerIdDAO(String username) {
		Session session = sessionFactory.openSession();
		System.out.println("TRASU++++++++++++++++");
		Query<Player> query = session.createQuery("FROM Player as p where p.username=:user",Player.class)
				.setParameter("user", username);
		Long playerID = query.uniqueResult().getId();
		session.close();
		return playerID;
	}

}
