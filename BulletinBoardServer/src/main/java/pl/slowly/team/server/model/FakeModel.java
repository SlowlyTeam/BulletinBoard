package pl.slowly.team.server.model;

import pl.slowly.team.common.packages.data.Bulletin;
import pl.slowly.team.common.packages.data.Category;
import pl.slowly.team.common.packages.helpers.Credentials;

import java.util.ArrayList;
import java.util.List;

public class FakeModel implements IModel {

    @Override
    public boolean checkCredentials(Credentials credentials) {
        return true;
    }

    @Override
    public List<Category> getCategories() {
        List<Category> l = new ArrayList<>();
        l.add(new Category("kat. 1", null));
        return l;
    }

    @Override
    public List<Bulletin> getUserBulletins(String username) {
        List<Bulletin> l = new ArrayList<>();
        l.add(new Bulletin());
        return l;
    }

    @Override
    public boolean addBulletin(Bulletin bulletin, String username) {
        return true;
    }
}
