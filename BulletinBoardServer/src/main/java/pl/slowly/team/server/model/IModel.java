package pl.slowly.team.server.model;


import com.sun.istack.internal.Nullable;
import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.data.Category;
import pl.slowly.team.common.packets.helpers.Credentials;

import java.time.LocalDateTime;
import java.util.List;

public interface IModel {
    /** Checks credentials. Returns true if user credentials are valid. */
    public boolean checkCredentials(Credentials credentials);
    /** Return all categories existing in database. */
    public List<Category> getCategories();
    /** Returns bulletins for specified user. */
    public List<Bulletin> getUserBulletins(String username);
    /** Adds a bulletin to database for specified user. */
    public Integer addBulletin(Bulletin bulletin, String username);
    /** Deletes the bulletin from database for specified user. */
    public boolean deleteBulletin(int bulletinId, String username);
    /** Returns bulletins from specified categories, from optional date since. */
    public List<Bulletin> getBulletins(List<Integer> categoriesIds, @Nullable LocalDateTime since, int clientID);
}
