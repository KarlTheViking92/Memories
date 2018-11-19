package it.unical.asde2018.memory.components.persistence;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.unical.asde2018.memory.model.Credentials;

@Repository
public class CredentialsDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@PostConstruct
	public void init() {
	}

	public void save(Credentials credentials) {
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

	public boolean exists(Credentials credentials) {
		Session openSession = sessionFactory.openSession();
		Query<Credentials> query = openSession
				.createQuery("from Credentials as c where c.username=:u and c.password=:p", Credentials.class)
				.setParameter("u", credentials.getUsername()).setParameter("p", credentials.getPassword());

		boolean result = query.uniqueResult() != null;
		openSession.close();
		return result;
	}

}
