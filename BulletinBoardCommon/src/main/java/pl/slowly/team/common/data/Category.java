package pl.slowly.team.common.data;

import com.sun.istack.internal.Nullable;

import java.util.List;

public class Category extends Entity {
    private String categoryName;
    private List<Bulletin> bulletins;

    public Category(String categoryName, @Nullable List<Bulletin> bulletins) {
        this.categoryName = categoryName;
        this.bulletins = bulletins;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
