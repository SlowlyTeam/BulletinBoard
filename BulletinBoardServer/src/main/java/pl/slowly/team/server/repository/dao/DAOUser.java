package pl.slowly.team.server.repository.dao;

import javax.persistence.*;

/**
 * Created by Piotr on 2014-12-29.
 */
@Entity
@Table(name = "USER")
public class DAOUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userID;
    @Column(name = "name")
    private String userName;
    @Column(name = "password", length = 64)
    private String password;

    public DAOUser () {}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }


}
