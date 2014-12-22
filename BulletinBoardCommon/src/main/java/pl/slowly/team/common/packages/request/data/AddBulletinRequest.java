package pl.slowly.team.common.packages.request.data;

import pl.slowly.team.common.packages.data.Bulletin;
import pl.slowly.team.common.packages.request.Request;

/**
 * Add new bulletin for specific client.
 */
public class AddBulletinRequest extends Request {

    private Bulletin bulletin;

    public AddBulletinRequest(Bulletin bulletin) {
        this.bulletin = bulletin;
    }

    public Bulletin getBulletin() {
        return bulletin;
    }

}
