package pl.slowly.team.common.packages.request.broadcast;

import pl.slowly.team.common.packages.Packet;
import pl.slowly.team.common.packages.data.Bulletin;


/**
 * Send new bulletin to all the members of category.
 */
public class SendNewBulletinBroadcast extends Packet {
    private Bulletin bulletin;

    public SendNewBulletinBroadcast(Bulletin bulletin) {
        this.bulletin = bulletin;
    }

    public Bulletin getBulletin() {
        return bulletin;
    }
}
