package pl.slowly.team.common.packets.response;

import com.sun.istack.internal.Nullable;
import pl.slowly.team.common.packets.helpers.ResponseStatus;

/**
 * Created by Maxym on 2014-12-24.
 */
public class LogInResponse extends Response {
    private final Integer categoryID;

    public LogInResponse(ResponseStatus responseStatus, @Nullable Integer category) {
        super(responseStatus);
        this.categoryID = category;
    }

    public Integer getCategory() {
        return categoryID;
    }
}
