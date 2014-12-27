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

    private final List<Bulletin> bulletinList;
    private final List<Category> categoryList;
    private final Random random;

    public FakeModel() {
        bulletinList = new ArrayList<>();
        categoryList = new ArrayList<>();
        random = new Random();
        for (int i = 0; i < 100; ++i)
            bulletinList.add(new Bulletin(random.nextInt(), "Note #" + i, "content number #" + i, random.nextBoolean()));
        for (int i = 0; i < 20; ++i)
            categoryList.add(new Category("kat. " + i, random.nextInt()));
    }

    @Override
    public boolean checkCredentials(Credentials credentials) {
        return true;
    }

    @Override
    public List<Category> getCategories() {
        return categoryList;
    }

    @Override
    public List<Bulletin> getUserBulletins(String username) {
        List<Bulletin> l = new ArrayList<>();
        for (int i = 0; i < 100; ++i) {
            l.add(new Bulletin(new Random().nextInt(), "Note #" + i, "content number #" + i, true));
        }
        return l;
    }

    @Override
    public Integer addBulletin(Bulletin bulletin, String username) {
        int randomInt = random.nextInt();
        return randomInt < 0 ? -1 : randomInt;
    }

    @Override
    public boolean deleteBulletin(int bulletinId, String username) {
        return true;
    }

    @Override
    public List<Bulletin> getBulletins(List<Integer> categoriesIds, @Nullable LocalDateTime since, int clientID) {
        return bulletinList;
    }
}
