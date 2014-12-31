package pl.slowly.team.server.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.slowly.team.server.repository.dao.DAOCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr on 2014-12-29.
 */
public class CategoryRepository implements ICategoryRepository {

    private static SessionFactory factory;

    public CategoryRepository()
    {
        System.out.println("CategoryRepository");
        factory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List<DAOCategory> getAllCategories() {
        List<DAOCategory> categories = new ArrayList<DAOCategory>();
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            categories = session.createQuery("FROM DAOCategory").list();
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return categories;
    }

    @Override
    public void saveCategory(DAOCategory newCategory) {
        System.out.println("Saving");
        Session session = factory.openSession();
        Transaction tx = null;
        try
        {
            System.out.println("Begin");
            tx = session.beginTransaction();
            session.save(newCategory);
            tx.commit();
            System.out.println("End");
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
    }

}
