package pl.slowly.team.common.data;

public class Bulletin extends Entity {

    private final boolean belongToUser;
    private String bulletinTitle;
    private String bulletinContent;
    private Integer bulletinId;

    public Bulletin(String bulletinTitle, String bulletinContent) {
        this.bulletinTitle = bulletinTitle;
        this.bulletinContent = bulletinContent;
        this.belongToUser = false;
        this.bulletinId = -1;
    }

    public Bulletin(Integer bulletinId, String bulletinTitle, String bulletinContent, boolean belongToUser) {
        this.bulletinId = bulletinId;
        this.bulletinTitle = bulletinTitle;
        this.bulletinContent = bulletinContent;
        this.belongToUser = belongToUser;
    }

    public int getBulletinId() {
        return bulletinId;
    }

    public void setBulletinId(int bulletinId) {
        this.bulletinId = bulletinId;
    }

    public String getBulletinTitle() {
        return bulletinTitle;
    }

    public void setBulletinTitle(String bulletinTitle) {
        this.bulletinTitle = bulletinTitle;
    }

    public String getBulletinContent() {
        return bulletinContent;
    }

    public void setBulletinContent(String bulletinContent) {
        this.bulletinContent = bulletinContent;
    }

    public boolean isBelongToUser() {
        return belongToUser;
    }
}
