package pl.slowly.team.server.repository;

import com.sun.istack.internal.Nullable;
import org.hibernate.*;
import pl.slowly.team.server.repository.dao.DAOBulletin;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Piotr on 2014-12-29.
 */
public class BulletinRepository implements IBulletinRepository {

    private static SessionFactory factory;

    public BulletinRepository() {
        factory = HibernateUtil.getSessionFactory();
    }

    @Override
    public Integer saveBulletin(DAOBulletin newBulletin) {
        Integer result = new Integer(-1);
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(newBulletin);
            result = newBulletin.getBulletinID();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<DAOBulletin> getUsersBulletins(String userName) {
        List<DAOBulletin> result = null;
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM DAOBulletin WHERE author = :name");
            query.setParameter("name", userName);
            result = (List<DAOBulletin>) query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean deleteBulletin(int bulletinId) {
        boolean result = false;
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(session.get(DAOBulletin.class, bulletinId));
            tx.commit();
            result = true;
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            result = false;
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean editBulletin(DAOBulletin bulletin, String username) {
        boolean result = false;
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            bulletin.setIsValid(true);
            session.update(bulletin);
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

    @Override
    public List<DAOBulletin> getBulletins(List<Integer> categoriesIds, @Nullable Date since) {
        if (since == null) {
            List<DAOBulletin> result = null;
            Session session = factory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query = session.createQuery("FROM DAOBulletin WHERE categoryID = :category AND isValid=true ORDER BY creationDate DESC");
                query.setParameter("category", categoriesIds.get(0));
                result = (List<DAOBulletin>) query.list();
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null)
                    tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
            return result;
        } else {
            List<DAOBulletin> result = null;
            Session session = factory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query = session.createQuery("FROM DAOBulletin WHERE creationDate > :date");
                query.setTimestamp("date", since);
                result = (List<DAOBulletin>) query.list();
                tx.commit();
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

    public boolean ValidateBulletins() {
        boolean result;
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Date date = new Date(System.currentTimeMillis() - TimeUnit.MILLISECONDS.convert(14, TimeUnit.DAYS));
            Query query = session.createQuery("UPDATE DAOBulletin SET isValid=false WHERE creationDate < :date");
            query.setTimestamp("date", date);
            query.executeUpdate();

            tx.commit();
            result = true;
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            result = false;
        } finally {
            session.close();
        }
        return result;
    }
}
