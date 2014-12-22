package pl.slowly.team.common.packages.request.broadcast;

import pl.slowly.team.common.packages.Packet;
import pl.slowly.team.common.packages.data.Bulletin;

import java.util.List;

/**
 * Send new bulletin to all the members of category.
 */
public class SendNewBulletin extends Packet {
    private Bulletin bulletin;
    private List<Integer> userIds;

    public SendNewBulletin(Bulletin bulletin, List<Integer> userIds) {
        this.bulletin = bulletin;
        this.userIds = userIds;
    }
}
