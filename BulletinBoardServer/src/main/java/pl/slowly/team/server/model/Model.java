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
import java.util.stream.Collectors;

public class Model implements IModel {

    private final List<Bulletin> bulletinList;
    private final List<Category> categoryList;
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
    }

    @Override
    public boolean checkCredentials(Credentials credentials) {
        DAOUser client = userRepository.getUser(credentials.getUsername());
        if (client.getPassword().equals(credentials.getPasswordHash()))
            return true;
        else
            return false;
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.getAllCategories().stream().map(this::DaoToDto).collect(Collectors.toList());
    }

    @Override
    public List<Bulletin> getUserBulletins(String username) {
        return bulletinRepository.getUsersBulletins(username)
                .stream().map(bulletin -> DaoToDto(bulletin, true)).collect(Collectors.toList());
    }

    @Override
    public Integer addBulletin(Bulletin bulletin, String username, int categoryID) {
        DAOBulletin daoBulletin = DtoToDao(bulletin);
        daoBulletin.setAuthor(username);
        daoBulletin.setCreationDate(new Date());
        daoBulletin.setCategoryID(categoryID);
        return bulletinRepository.saveBulletin(daoBulletin);
    }

    @Override
    public boolean deleteBulletin(int bulletinId, String username) {
        return bulletinRepository.deleteBulletin(bulletinId, username);
    }

    @Override
    public List<Bulletin> getBulletins(List<Integer> categoriesIds, @Nullable LocalDateTime since, String username) {
        return bulletinRepository.getBulletins(categoriesIds, since)
                .stream().map(bulletin -> DaoToDto(bulletin, bulletin.getAuthor().equals(username))).collect(Collectors.toList());
    }

    @Override
    public boolean editBulletin(Bulletin bulletin, String username) {
        DAOBulletin daoBulletin = DtoToDao(bulletin);
        daoBulletin.setAuthor(username);
        daoBulletin.setCreationDate(new Date());
        return bulletinRepository.editBulletin(daoBulletin, username);
    }

    private Bulletin DaoToDto(DAOBulletin daoBulletin, Boolean isBelongToUser) {
        return new Bulletin(daoBulletin.getBulletinID(), daoBulletin.getBulletinTitle(), daoBulletin.getBulletinContent(), isBelongToUser);
    }

    private Category DaoToDto(DAOCategory daoCategory) {
        return new Category(daoCategory.getCategoryName(), daoCategory.getCategoryID());
    }

    private DAOBulletin DtoToDao(Bulletin bulletin) {
        DAOBulletin daoBulletin = new DAOBulletin();
        daoBulletin.setBulletinContent(bulletin.getBulletinContent());
        daoBulletin.setBulletinTitle(bulletin.getBulletinTitle());
        daoBulletin.setBulletinID(bulletin.getBulletinId());
        return daoBulletin;
    }

}
