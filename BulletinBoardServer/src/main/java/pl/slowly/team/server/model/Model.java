package pl.slowly.team.server.model;

import com.sun.istack.internal.Nullable;
import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.data.Category;
import pl.slowly.team.common.packets.helpers.Credentials;
import pl.slowly.team.server.repository.BulletinRepository;
import pl.slowly.team.server.repository.CategoryRepository;
import pl.slowly.team.server.repository.UserRepository;
import pl.slowly.team.server.repository.dao.DAOBulletin;
import pl.slowly.team.server.repository.dao.DAOCategory;
import pl.slowly.team.server.repository.dao.DAOUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Model implements IModel {

    private final List<Bulletin> bulletinList;
    private final List<Category> categoryList;
    private final Random random;
    private int test_i;
    private BulletinRepository bulletinRepository;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    public Model() {
        bulletinRepository = new BulletinRepository();
        categoryRepository = new CategoryRepository();
        userRepository = new UserRepository();
        bulletinList = new ArrayList<>();
        categoryList = new ArrayList<>();
        random = new Random();
        for (int i = 0; i < 100; ++i)
            bulletinList.add(new Bulletin(random.nextInt(), "Note #" + i, "content number #" + i, random.nextBoolean()));
        for (int i = 0; i < 20; ++i)
            categoryList.add(new Category("kat. " + i, random.nextInt()));
        test_i = 0;
    }

    @Override
    public boolean checkCredentials(Credentials credentials) {
        DAOUser client = userRepository.getUser(credentials.getUsername());
        if(client.getPassword().equals(credentials.getPasswordHash()))
            return true;
        else
            return false;
    }

    @Override
    public List<Category> getCategories() {
        List<Category> result = new ArrayList<Category>();
        for(DAOCategory cat : categoryRepository.getAllCategories()){
            result.add(DaoToDto(cat));
        }
        return result;
    }

    @Override
    public List<Bulletin> getUserBulletins(String username) {
        List<Bulletin> result = new ArrayList<Bulletin>();
        for(DAOBulletin bul : bulletinRepository.getUsersBulletins(username)){
            result.add(DaoToDto(bul));
        }
        return result;
    }

    @Override
    public Integer addBulletin(Bulletin bulletin, String username) {
        DAOBulletin daoBulletin = DtoToDao(bulletin);
        daoBulletin.setAuthor(username);
        daoBulletin.setCreationDate(new Date());
        bulletinRepository.saveBulletin(daoBulletin);
        return ++test_i;
    }

    @Override
    public boolean deleteBulletin(int bulletinId, String username) {
        return bulletinRepository.deleteBulletin(bulletinId, username);
    }

    @Override
    public List<Bulletin> getBulletins(List<Integer> categoriesIds, @Nullable LocalDateTime since, int clientID) {
        List<Bulletin> result = new ArrayList<Bulletin>();
        for(DAOBulletin bul : bulletinRepository.getBulletins(categoriesIds, since, clientID)){
            result.add(DaoToDto(bul));
        }
        return result;
    }

    @Override
    public boolean editBulletin(Bulletin bulletin, String username) {
        DAOBulletin daoBulletin = DtoToDao(bulletin);
        daoBulletin.setAuthor(username);
        daoBulletin.setCreationDate(new Date());
        return bulletinRepository.editBulletin(daoBulletin, username);
    }

    private Bulletin DaoToDto(DAOBulletin daoBulletin)
    {
        return new Bulletin(daoBulletin.getBulletinID(),daoBulletin.getBulletinTitle(), daoBulletin.getBulletinContent(),true);
    }

    private Category DaoToDto(DAOCategory daoCategory)
    {
        return new Category(daoCategory.getCategoryName(), daoCategory.getCategoryID());
    }

    private DAOBulletin DtoToDao(Bulletin bulletin)
    {
        DAOBulletin daoBulletin = new DAOBulletin();
        daoBulletin.setBulletinContent(bulletin.getBulletinContent());
        daoBulletin.setBulletinTitle(bulletin.getBulletinTitle());
        daoBulletin.setBulletinID(bulletin.getBulletinId());
        return daoBulletin;
    }

}
