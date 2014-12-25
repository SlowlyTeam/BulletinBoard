package pl.slowly.team.common.packets.response;

import com.sun.istack.internal.Nullable;
import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.packets.helpers.ResponseStatus;

import java.util.List;

/**
 * Created by Maxym on 2014-12-24.
 */
public class GetBulletinsResponse extends Response {
    public GetBulletinsResponse(ResponseStatus responseStatus, @Nullable List<Bulletin> bulletins) {
        super(responseStatus, bulletins);
    }
}
