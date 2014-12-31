package pl.slowly.team.server.repository;

import org.hibernate.*;
import pl.slowly.team.server.repository.dao.DAOUser;

/**
 * Created by Piotr on 2014-12-29.
 */
public class UserRepository {
    private static SessionFactory factory;

    public UserRepository()
    {
        factory = HibernateUtil.getSessionFactory();
    }

    public DAOUser getUser(String name)
    {
        DAOUser user = new DAOUser();
        Session session = factory.openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();
            Query query =  session.createQuery("FROM DAOUser WHERE userName = :name");
            query.setParameter("name", name);
            try {
                user = (DAOUser) query.list().get(0);
            }catch (Exception e){
                e.printStackTrace();
            }
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
        return user;
    }

    public DAOUser getUser(int id)
    {
        DAOUser user = new DAOUser();
        Session session = factory.openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();
            Query query =  session.createQuery("FROM DAOUser WHERE userID = :id");
            query.setParameter("id", id);
            try {
                user = (DAOUser) query.list().get(0);
            }catch (Exception e){
                e.printStackTrace();
            }
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
        return user;
    }

}
