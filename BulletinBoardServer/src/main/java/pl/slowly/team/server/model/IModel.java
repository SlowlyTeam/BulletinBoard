package pl.slowly.team.server.model;


import pl.slowly.team.common.packages.data.Bulletin;
import pl.slowly.team.common.packages.data.Category;
import pl.slowly.team.common.packages.helpers.Credentials;

import java.util.List;

//TODO add ids
public interface IModel {
    /** Checks credentials. Returns true if user credentials are valid. */
    public boolean checkCredentials(Credentials credentials);
    /** Return all categories existing in database. */
    public List<Category> getCategories();
    /** Returns bulletins for specified user. */
    public List<Bulletin> getUserBulletins(String username);
    /** Adds a bulletin to database for specified user. */
    boolean addBulletin(Bulletin bulletin, String username);
}
