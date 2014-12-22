package pl.slowly.team.common.packages.request.broadcast;

import pl.slowly.team.common.packages.Packet;
import pl.slowly.team.common.packages.data.BulletinEntity;

/**
 * Send new bulletin to all the members of category.
 */
public class SendNewBulletin extends Packet {
    private BulletinEntity bulletinEntity;

    public SendNewBulletin(BulletinEntity bulletinEntity) {
        this.bulletinEntity = bulletinEntity;
    }
}
