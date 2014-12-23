package pl.slowly.team.common.packets.helpers;

public enum ResponseStatus {

    AUTHORIZED (201, "User successfully authorized."),
    NOT_AUTHORIZED (401, "User not authorized."),
    OK(200, "OK"),
    ERROR(400, "Error.");

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
