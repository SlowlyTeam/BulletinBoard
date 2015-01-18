package pl.slowly.team.server.repository;

import pl.slowly.team.server.repository.dao.DAOCategory;

import java.util.List;

/**
 * Created by Piotr on 2014-12-29.
 */
public interface ICategoryRepository {
    public abstract List<DAOCategory> getAllCategories();

    public DAOCategory getCategory(int categoryID);

    public abstract void saveCategory(DAOCategory newCategory);
}
