package pl.slowly.team.common.packets.request.data;

import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.packets.request.Request;

public class EditBulletinRequest extends Request {

    private Bulletin bulletin;

    public EditBulletinRequest(Bulletin bulletin) {
        this.bulletin = bulletin;
    }

    public Bulletin getBulletin() {
        return bulletin;
    }
}