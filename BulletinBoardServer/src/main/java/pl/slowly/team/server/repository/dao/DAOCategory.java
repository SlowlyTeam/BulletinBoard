package pl.slowly.team.server.repository.dao;

import javax.persistence.*;

/**
 * Created by Piotr on 2014-12-29.
 */
@Entity
@Table(name = "CATEGORY")
public class DAOCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int categoryID;
    @Column(name = "category_name")
    private String categoryName;

    public DAOCategory () {}

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
