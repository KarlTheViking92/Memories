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
public class CredentialsDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@PostConstruct
	public void init() {
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

	public boolean exists(User user) {
		Session openSession = sessionFactory.openSession();
		Query<User> query = openSession.createQuery("from User as u where u.username=:u and u.password=:p", User.class)
				.setParameter("u", user.getUsername()).setParameter("p", user.getPassword());

		boolean result = query.uniqueResult() != null;
		openSession.close();
		return result;
	}

}
