package pl.slowly.team.server.model;


import pl.slowly.team.common.packages.helpers.Credentials;

/**
 * Created by Emil Kleszcz on 2014-12-17.
 */
public interface IModel {
    public boolean checkCredentials(Credentials credentials);
}
