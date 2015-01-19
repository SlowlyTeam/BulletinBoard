package pl.slowly.team.server.test;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import pl.slowly.team.server.repository.BulletinRepository;
import pl.slowly.team.server.repository.dao.DAOBulletin;

import java.util.Date;
import java.util.List;

/**
 * Created by Piotr on 2015-01-19.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CRUDTest {

    private BulletinRepository bulletinRepository;

    @Test
    public void aCreateBulletinTest()
    {
        bulletinRepository = new BulletinRepository();
        DAOBulletin bulletin = new DAOBulletin();
        bulletin.setAuthor("TestAuthor");
        bulletin.setCreationDate(new Date());
        bulletin.setBulletinTitle("TestTitle");
        bulletin.setBulletinContent("TestContent");

        bulletinRepository.saveBulletin(bulletin);
        List<DAOBulletin> bulletinFromDB = bulletinRepository.getUsersBulletins("TestAuthor");
        assert(bulletin.getAuthor().equals(bulletinFromDB.get(0).getAuthor()) && bulletin.getBulletinTitle().equals(bulletinFromDB.get(0).getBulletinTitle()) && bulletin.getBulletinContent().equals(bulletinFromDB.get(0).getBulletinContent()));
    }

    @Test
    public void bUpdateBulletinTest()
    {
        bulletinRepository = new BulletinRepository();
        List<DAOBulletin> bulletinFromDB = bulletinRepository.getUsersBulletins("TestAuthor");
        DAOBulletin bulletin = bulletinFromDB.get(0);
        bulletin.setBulletinTitle("NewTitle");
        bulletinRepository.editBulletin(bulletin, "TestAuthor");
        bulletinFromDB = bulletinRepository.getUsersBulletins("TestAuthor");
        assert (bulletinFromDB.get(0).getBulletinTitle().equals("NewTitle"));
    }

    @Test
    public void cDeleteBulletinTest()
    {
        bulletinRepository = new BulletinRepository();
        List<DAOBulletin> bulletinFromDB = bulletinRepository.getUsersBulletins("TestAuthor");
        bulletinRepository.deleteBulletin(bulletinFromDB.get(0).getBulletinID());
        bulletinFromDB = bulletinRepository.getUsersBulletins("TestAuthor");
        assert(bulletinFromDB.size()==0);
    }
}
