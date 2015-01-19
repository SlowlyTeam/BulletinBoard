package pl.slowly.team.server.repository;


import pl.slowly.team.server.repository.dao.DAOBulletin;

import java.util.Date;
import java.util.List;

/**
 * Created by Piotr on 2014-12-29.
 */
public interface IBulletinRepository {
    public abstract Integer saveBulletin(DAOBulletin newBulletin);
    public abstract List<DAOBulletin> getUsersBulletins(String userName);
    public abstract boolean deleteBulletin(int bulletinID);
    public abstract boolean editBulletin(DAOBulletin bulletin, String username);
    public abstract List<DAOBulletin> getBulletins(List<Integer> categoriesIds,  Date since);
}
