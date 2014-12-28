package pl.slowly.team.common.packets.response;

import pl.slowly.team.common.packets.helpers.ResponseStatus;

public class DeleteBulletinResponse extends Response {
    final int bulletinId;

    public DeleteBulletinResponse(ResponseStatus responseStatus, int bulletinId) {
        super(responseStatus);
        this.bulletinId = bulletinId;
    }

    public int getBulletinId() {
        return bulletinId;
    }
}
