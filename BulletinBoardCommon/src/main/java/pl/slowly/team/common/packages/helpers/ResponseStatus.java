package pl.slowly.team.common.packages.helpers;

/**
 * Response Code sent with Result.
 */
public class ResponseStatus {
    private int responseCode;
    private String friendlyMessage;

    public ResponseStatus(int responseCode, String friendlyMessage) {
        this.responseCode = responseCode;
        this.friendlyMessage = friendlyMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getFriendlyMessage() {
        return friendlyMessage;
    }
}
