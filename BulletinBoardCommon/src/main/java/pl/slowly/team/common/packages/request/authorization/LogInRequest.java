package pl.slowly.team.common.packages.request.authorization;

import pl.slowly.team.common.packages.helpers.Credentials;
import pl.slowly.team.common.packages.request.Request;

/**
 * Send login and password in order to authenticate account.
 */
public class LogInRequest extends Request {
    Credentials userCredentials;

    public LogInRequest(Credentials userCredentials) {
        this.userCredentials = userCredentials;
    }
}
