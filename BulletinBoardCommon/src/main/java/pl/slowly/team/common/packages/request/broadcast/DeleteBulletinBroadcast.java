package pl.slowly.team.common.packages.request.broadcast;


import pl.slowly.team.common.packages.Packet;

public class DeleteBulletinBroadcast extends Packet {
    private int bulletinId;

    public DeleteBulletinBroadcast(int bulletinId) {
        this.bulletinId = bulletinId;
    }

    public int getBulletinId() {
        return bulletinId;
    }
}
