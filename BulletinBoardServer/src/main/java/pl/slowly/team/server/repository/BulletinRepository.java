package pl.slowly.team.server.repository;

import com.sun.istack.internal.Nullable;
import org.hibernate.*;
import pl.slowly.team.server.repository.dao.DAOBulletin;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by Piotr on 2014-12-29.
 */
public class BulletinRepository implements IBulletinRepository{

    private static SessionFactory factory;

    public BulletinRepository()
    {
        factory = HibernateUtil.getSessionFactory();
    }

    @Override
    public Integer saveBulletin(DAOBulletin newBulletin) {
        Integer result = new Integer(-1);
        Session session = factory.openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();
            session.save(newBulletin);
            result =  newBulletin.getBulletinID();
            tx.commit();
        }
        catch (HibernateException e)
        {
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<DAOBulletin> getUsersBulletins(String userName) {
        List<DAOBulletin> result = null;
        Session session = factory.openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM DAOBulletin WHERE author = :name");
            query.setParameter("name", userName);
            result = (List<DAOBulletin>)query.list();
            tx.commit();
        }
        catch (HibernateException e)
        {
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean deleteBulletin(int bulletinId, String username) {
        boolean result = false;
        Session session = factory.openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();
            session.delete(session.get(DAOBulletin.class, bulletinId));
            tx.commit();
            result = true;
        }
        catch (HibernateException e)
        {
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
            result = false;
        }
        finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean editBulletin(DAOBulletin bulletin, String username) {
        boolean result = false;
        Session session = factory.openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();
            session.merge(bulletin);
            tx.commit();
            result = true;
        }
        catch (HibernateException e)
        {
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<DAOBulletin> getBulletins(List<Integer> categoriesIds, @Nullable LocalDateTime since, int clientID) {
        if(since == null)
        {
            List<DAOBulletin> result = null;
            Session session = factory.openSession();
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query query = session.createQuery("FROM DAOBulletin");
                result = (List<DAOBulletin>)query.list();
                tx.commit();
            }
            catch (HibernateException e)
            {
                if (tx!=null)
                    tx.rollback();
                e.printStackTrace();
            }
            finally {
                session.close();
            }
            return result;
        }else{
            List<DAOBulletin> result = null;
            Session session = factory.openSession();
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query query = session.createQuery("FROM DAOBulletin WHERE creationDate = :date");
                query.setParameter("date", Date.from(since.atZone(ZoneId.systemDefault()).toInstant()));
                result = (List<DAOBulletin>)query.list();
                tx.commit();
            }
            catch (HibernateException e)
            {
                if (tx!=null)
                    tx.rollback();
                e.printStackTrace();
            }
            finally {
                session.close();
            }
            return result;
        }
    }
}
