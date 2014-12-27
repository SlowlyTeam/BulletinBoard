package pl.slowly.team.common.packets.response;

import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.data.Entity;
import pl.slowly.team.common.packets.helpers.ResponseStatus;

import java.util.ArrayList;

public class EditBulletinResponse extends Response {

    private Bulletin bulletin;

    public EditBulletinResponse(ResponseStatus responseStatus, Bulletin bulletin) {
        super(responseStatus);
        this.bulletin = bulletin;
    }

    public Bulletin getBulletin() {
        return bulletin;
    }

    public void setBulletin(Bulletin bulletin) {
        this.bulletin = bulletin;
    }
}
