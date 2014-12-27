package pl.slowly.team.common.packets.response;

import pl.slowly.team.common.packets.helpers.ResponseStatus;

/**
 * Created by Maxym on 2014-12-24.
 */
public class AddBulletinResponse extends Response {
    private int bulletinId;

    public AddBulletinResponse(ResponseStatus responseStatus) {
        super(responseStatus);
    }

    public AddBulletinResponse(ResponseStatus responseStatus, int bulletinId) {
        super(responseStatus);
        this.bulletinId = bulletinId;
    }

    public int getBulletinId() {
        return bulletinId;
    }
}
