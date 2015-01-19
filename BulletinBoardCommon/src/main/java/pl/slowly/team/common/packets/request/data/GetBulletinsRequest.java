package pl.slowly.team.common.packets.request.data;

import pl.slowly.team.common.packets.request.Request;

import java.util.Date;
import java.util.List;

/**
 * Get bulletins from specified categories, with specified period.
 */
public class GetBulletinsRequest extends Request {

    private List<Integer> categoriesIds;
    private Date since;

    public GetBulletinsRequest(List<Integer> categoriesIds, Date since) {
        this.categoriesIds = categoriesIds;
        this.since = since;
    }

    public List<Integer> getCategoriesIds() {
        return categoriesIds;
    }

    public Date getSince() {
        return since;
    }

}
