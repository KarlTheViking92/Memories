package it.unical.asde2018.memory.components.persistence;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.unical.asde2018.memory.model.User;

@Repository
public class UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@PostConstruct
	public void init() {
		save(new User("a", "a"));
		save(new User("s", "s"));
	}

	public void save(User credentials) {
		Session session = sessionFactory.openSession();

		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(credentials);
			tx.commit();

		} catch (Exception e) {
			tx.rollback();
		}
		session.close();

	}

	public boolean exists(User credentials) {
		Session openSession = sessionFactory.openSession();
		Query<User> query = openSession.createQuery("from User as c where c.username=:u and c.password=:p", User.class)
				.setParameter("u", credentials.getUsername()).setParameter("p", credentials.getPassword());

		boolean result = query.uniqueResult() != null;
		openSession.close();
		return result;
	}

	public boolean yetAnUser(String username) {
		Session session = sessionFactory.openSession();

		Query<User> query = session.createQuery("from User as c where c.username=:u", User.class).setParameter("u",
				username);

		boolean result = query.uniqueResult() != null;

		return result;
	}

}
