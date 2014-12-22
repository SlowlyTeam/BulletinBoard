package pl.slowly.team.common.packages.response;

import com.sun.istack.internal.Nullable;
import pl.slowly.team.common.packages.Packet;
import pl.slowly.team.common.packages.data.Entity;
import pl.slowly.team.common.packages.helpers.ResponseStatus;

/**
 * Response from server to clients requests.
 */
public class Response extends Packet {
    private ResponseStatus responseStatus;
    private Entity entity;

    public Response(ResponseStatus responseStatus, @Nullable Entity entity) {
        this.responseStatus = responseStatus;
        this.entity = entity;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
