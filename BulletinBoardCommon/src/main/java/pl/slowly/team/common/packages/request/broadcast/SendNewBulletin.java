package pl.slowly.team.common.packages.request.broadcast;

import pl.slowly.team.common.packages.Package;
import pl.slowly.team.common.packages.data.BulletinEntity;

/**
 * Send new bulletin to all the members of category.
 */
public class SendNewBulletin extends Package {
    private BulletinEntity bulletinEntity;

    public SendNewBulletin(BulletinEntity bulletinEntity) {
        this.bulletinEntity = bulletinEntity;
    }
}
