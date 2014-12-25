package pl.slowly.team.common.packets.helpers;

import java.io.Serializable;

public class Credentials implements Serializable {
    private String user;
    private String password;

    public Credentials(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return user;
    }
}