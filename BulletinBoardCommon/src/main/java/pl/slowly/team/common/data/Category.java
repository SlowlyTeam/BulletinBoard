package pl.slowly.team.common.data;

public class Category extends Entity {
    private final String categoryName;
    private final Integer categoryId;

    public Category(String categoryName, int categoryId) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }
}
