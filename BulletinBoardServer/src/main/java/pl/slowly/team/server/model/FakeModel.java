package pl.slowly.team.server.model;

import com.sun.istack.internal.Nullable;
import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.data.Category;
import pl.slowly.team.common.packets.helpers.Credentials;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FakeModel implements IModel {

    @Override
    public boolean checkCredentials(Credentials credentials) {
        return true;
    }

    @Override
    public List<Category> getCategories() {
        List<Category> l = new ArrayList<>();
        for (int i = 0; i < 20; ++i)
            l.add(new Category("kat. " + i, null));
        return l;
    }

    @Override
    public List<Bulletin> getUserBulletins(String username) {
        List<Bulletin> l = new ArrayList<>();
        for (int i = 0; i < 100; ++i) {
            l.add(new Bulletin(new Random().nextInt(), "Note #" + i, "content number #" + i));
        }
        return l;
    }

    @Override
    public boolean addBulletin(Bulletin bulletin, String username) {
        return true;
    }

    @Override
    public boolean deleteBulletin(int bulletinId, String username) {
        return true;
    }

    @Override
    public List<Bulletin> getBulletins(List<Integer> categoriesIds, @Nullable LocalDateTime since) {
        List<Bulletin> l = new ArrayList<>();
        for (int i = 0; i < 100; ++i) {
            l.add(new Bulletin(new Random().nextInt(), "Note #" + i, "content number #" + i));
        }
        return l;
    }
}
