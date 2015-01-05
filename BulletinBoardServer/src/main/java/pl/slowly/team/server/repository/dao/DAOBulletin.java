package pl.slowly.team.server.repository.dao;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Piotr on 2014-12-29.
 */
@Entity
@Table(name = "BULLETIN")
public class DAOBulletin {

    @Column(name = "title")
    private String bulletinTitle;
    @Column(name = "content", columnDefinition = "TEXT")
    private String bulletinContent;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bulletin_id")
    private int bulletinID;
    @Column(name = "category_id")
    private int categoryID;
    @Column(name = "author")
    private String author;
    @Column(name = "date")
    private Date creationDate;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getBulletinTitle() {
        return bulletinTitle;
    }

    public void setBulletinTitle(String bulletinTitle) {
        this.bulletinTitle = bulletinTitle;
    }

    public String getBulletinContent() {
        return bulletinContent;
    }

    public void setBulletinContent(String bulletinContent) {
        this.bulletinContent = bulletinContent;
    }

    public int getBulletinID() {
        return bulletinID;
    }

    public void setBulletinID(int bulletinID) {
        this.bulletinID = bulletinID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
