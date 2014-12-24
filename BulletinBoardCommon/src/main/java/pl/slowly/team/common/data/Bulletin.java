package pl.slowly.team.common.data;

public class Bulletin extends Entity {


    private String bulletinTitle;
    private String bulletinContent;

    public Bulletin(String bulletinTitle, String bulletinContent) {
        this.bulletinTitle = bulletinTitle;
        this.bulletinContent = bulletinContent;
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
}
