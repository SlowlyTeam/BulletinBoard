package pl.slowly.team.common.packets.request.authorization;

import pl.slowly.team.common.packets.helpers.Credentials;
import pl.slowly.team.common.packets.request.Request;

/**
 * Send login and password in order to authenticate account.
 */
public class LogInRequest extends Request {

    private Credentials userCredentials;

    public LogInRequest(Credentials userCredentials) {
        this.userCredentials = userCredentials;
    }

    public Credentials getUserCredentials() {
        return userCredentials;
    }
}
