package pl.slowly.team.common.packages.helpers;

/**
 * Created by Marek Majde on 2014-12-22.
 */
public enum ResponseStatus {

    AUTHORIZED (201, "User successfully authorized."),
    NOT_AUTHORIZED (401, "User not authorized.");

    private int responseCode;
    private String friendlyMessage;

    private ResponseStatus(int responseCode, String friendlyMessage) {
        this.responseCode = responseCode;
        this.friendlyMessage = friendlyMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getFriendlyMessage() {
        return friendlyMessage;
    }

    @Override
    public String toString() {
        return "ResponseStatus{" +
                "responseCode=" + responseCode +
                ", friendlyMessage='" + friendlyMessage + '\'' +
                '}';
    }
}
