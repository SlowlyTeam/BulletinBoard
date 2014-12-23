package pl.slowly.team.common.packages.request.data;

import com.sun.istack.internal.Nullable;
import pl.slowly.team.common.packages.request.Request;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Get bulletins from specified categories, with specified period.
 */
public class GetBulletinsRequest extends Request {

    private List<Integer> categoriesIds;
    private LocalDateTime since;

    public GetBulletinsRequest(List<Integer> categoriesIds, @Nullable LocalDateTime since) {
        this.categoriesIds = categoriesIds;
    }

    public List<Integer> getCategoriesIds() {
        return categoriesIds;
    }

    public LocalDateTime getSince() {
        return since;
    }

}
