package it.unical.asde2018.memory.components.persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.unical.asde2018.memory.model.Game;
import it.unical.asde2018.memory.model.Player;

@Repository
public class GameDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void saveGame(Game game) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.persist(game);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
		}
		session.close();
	}

	public List<Game> getGamesOfAUser(Player player) {
		Session session = sessionFactory.openSession();
		Query<Game> query = session
				.createQuery("select g from Game g join g.players p where p.id = :userId", Game.class)
				.setParameter("userId", player.getId());
		List<Game> result = query.list();
		session.close();
		return result;
	}
	
	public List<Game> getAllGames() {
		Session session = sessionFactory.openSession();
		Query<Game> query = session.createQuery("from Game g", Game.class);
		List<Game> result = query.list();
		session.close();
		return result;
	}
}
