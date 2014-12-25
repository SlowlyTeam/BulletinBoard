package pl.slowly.team.common.packets.response;

import pl.slowly.team.common.packets.helpers.ResponseStatus;

/**
 * Created by Maxym on 2014-12-24.
 */
public class NotAuthorizedResponse extends Response {
    public NotAuthorizedResponse(ResponseStatus responseStatus) {
        super(responseStatus);
    }
}
