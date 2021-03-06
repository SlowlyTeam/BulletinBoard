package pl.slowly.team.server.model;


import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.data.Category;
import pl.slowly.team.common.packets.helpers.Credentials;
import pl.slowly.team.server.repository.BulletinRepository;
import pl.slowly.team.server.repository.CategoryRepository;
import pl.slowly.team.server.repository.UserRepository;
import pl.slowly.team.server.repository.dao.DAOBulletin;
import pl.slowly.team.server.repository.dao.DAOCategory;
import pl.slowly.team.server.repository.dao.DAOUser;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Model implements IModel {

    private final BulletinRepository bulletinRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public Model() {
        bulletinRepository = new BulletinRepository();
        categoryRepository = new CategoryRepository();
        userRepository = new UserRepository();
    }

    @Override
    public boolean checkCredentials(Credentials credentials) {
        DAOUser client = userRepository.getUser(credentials.getUsername());
        return client != null && client.getPassword().equals(credentials.getPasswordHash());
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
    public boolean deleteBulletin(int bulletinId) {
        return bulletinRepository.deleteBulletin(bulletinId);
    }

    @Override
    public List<Bulletin> getBulletins(List<Integer> categoriesIds,  Date since, String username) {
        setUserCategory(username, categoriesIds.get(0));
        return bulletinRepository.getBulletins(categoriesIds, since)
                .stream().map(bulletin -> DaoToDto(bulletin, bulletin.getAuthor().equalsIgnoreCase(username))).collect(Collectors.toList());
    }

    @Override
    public boolean editBulletin(Bulletin bulletin, String username, int categoryID) {
        DAOBulletin daoBulletin = DtoToDao(bulletin);
        daoBulletin.setAuthor(username);
        daoBulletin.setCategoryID(categoryID);
        daoBulletin.setCreationDate(new Date());
        return bulletinRepository.editBulletin(daoBulletin, username);
    }

    @Override
    public Category getUserCategory(String username) {
        int categoryId = userRepository.getUser(username).getCategoryID();
        return DaoToDto(categoryRepository.getCategory(categoryId));
    }

    @Override
    public void setUserCategory(String username, int newCategoryID) {
        userRepository.setNewCategory(username, newCategoryID);
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
