package pl.slowly.team.server.model;



import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.data.Category;
import pl.slowly.team.common.packets.helpers.Credentials;

import java.util.Date;
import java.util.List;

public interface IModel {
    /** Checks credentials. Returns true if user credentials are valid. */
    public boolean checkCredentials(Credentials credentials);
    /** Return all categories existing in database. */
    public List<Category> getCategories();
    /** Returns bulletins for specified user. */
    public List<Bulletin> getUserBulletins(String username);
    /** Adds a bulletin to database for specified user. */
    public Integer addBulletin(Bulletin bulletin, String username, int categoryID);
    /** Deletes the bulletin from database */
    public boolean deleteBulletin(int bulletinId);
    /** Returns bulletins from specified categories, from optional date since. */
    public List<Bulletin> getBulletins(List<Integer> categoriesIds,  Date since, String user);
    /** Returns success or failure as boolean. */
    public boolean editBulletin(Bulletin bulletin, String username, int categoryID);
    /** Returns category assigned to User - the last one */
    public Category getUserCategory(String username);
    /** Assign new category to User */
    public void setUserCategory(String username, int newCategoryID);
}
