package pl.slowly.team.common.packets.response;

import com.sun.istack.internal.Nullable;
import pl.slowly.team.common.packets.Packet;
import pl.slowly.team.common.data.Entity;
import pl.slowly.team.common.packets.helpers.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Response from server to clients requests.
 */
public class Response extends Packet {
    private ResponseStatus responseStatus;
    private List<? extends Entity> entities;

    public Response(ResponseStatus responseStatus, @Nullable List<? extends Entity> entities) {
        this.responseStatus = responseStatus;
        this.entities = entities;
    }

    public Response(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
        this.entities = new ArrayList<>(0);
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<? extends Entity> getEntities() {
        return entities;
    }

    public void setEntity(List<? extends Entity> entity) {
        this.entities = entity;
    }
}
