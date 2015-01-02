package pl.slowly.team.server.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.slowly.team.server.repository.dao.DAOUser;

/**
 * Created by Piotr on 2014-12-29.
 */
public class UserRepository {
    private static SessionFactory factory;

    public UserRepository() {
        factory = HibernateUtil.getSessionFactory();
    }

    public DAOUser getUser(String username) {
        DAOUser user = null;
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            user = (DAOUser) session.get(DAOUser.class, username);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }

    public boolean setNewCategory(String username, int newCategoryID) {
        Session session = factory.openSession();
        Transaction tx = null;
        boolean result = false;
        try {

            tx = session.beginTransaction();

            DAOUser daoUser = getUser(username);
            daoUser.setCategoryID(newCategoryID);
            session.update(daoUser);

            tx.commit();
            result = true;
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

}
