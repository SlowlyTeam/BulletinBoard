package pl.slowly.team.common.packages.helpers;

import java.io.Serializable;

public class Credentials implements Serializable {
    private String user;
    private String password;

    public Credentials(String user, String password) {
        this.user = user;
        this.password = password;
    }
}
