package pl.slowly.team.common.packets.data;

public class Bulletin extends Entity {
    private String bulletinTitle;

    public Bulletin(String bulletinTitle) {
        this.bulletinTitle = bulletinTitle;
    }

    public String getBulletinTitle() {
        return bulletinTitle;
    }
}
