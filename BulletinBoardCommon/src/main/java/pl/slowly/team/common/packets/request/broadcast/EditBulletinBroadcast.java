package pl.slowly.team.common.packets.request.broadcast;

import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.packets.Packet;

public class EditBulletinBroadcast extends Packet {
    private Bulletin bulletin;

    public EditBulletinBroadcast(Bulletin bulletin) {
        this.bulletin = bulletin;
    }

    public Bulletin getBulletin() {
        return bulletin;
    }
}
