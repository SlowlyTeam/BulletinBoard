package pl.slowly.team.common.packets.request.data;

import pl.slowly.team.common.packets.request.Request;

/**
 * Delete bulletin that belongs to the client.
 */
public class DeleteBulletinRequest extends Request {

    private int bulletinId;

    public DeleteBulletinRequest(int bulletinId) {
        this.bulletinId = bulletinId;
    }

    public int getBulletinId() {
        return bulletinId;
    }
}
