package pl.slowly.team.common.packets.response;

import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.packets.helpers.ResponseStatus;

import java.util.List;

/**
 * Created by Maxym on 2014-12-24.
 */
public class GetBulletinsResponse extends Response {
    public GetBulletinsResponse(ResponseStatus responseStatus, List<Bulletin> bulletins) {
        super(responseStatus, bulletins);
    }

    public List<Bulletin> getBulletins() {
        return (List<Bulletin>) getEntities();
    }
}
