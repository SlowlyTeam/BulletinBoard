package pl.slowly.team.common.packets.response;

import com.sun.istack.internal.Nullable;
import pl.slowly.team.common.data.Category;
import pl.slowly.team.common.packets.helpers.ResponseStatus;

/**
 * Created by Maxym on 2014-12-24.
 */
public class LogInResponse extends Response {
    private final Category category;

    public LogInResponse(ResponseStatus responseStatus, @Nullable Category category) {
        super(responseStatus);
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }
}
