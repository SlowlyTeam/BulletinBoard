package pl.slowly.team.common.packets.response;

import com.sun.istack.internal.Nullable;
import pl.slowly.team.common.data.Category;
import pl.slowly.team.common.packets.helpers.ResponseStatus;

import java.util.List;

/**
 * Created by Maxym on 2014-12-24.
 */
public class GetCategoriesListResponse extends Response {
    public GetCategoriesListResponse(ResponseStatus responseStatus, @Nullable List<Category> categories) {
        super(responseStatus, categories);
    }

    public List<Category> getCategories() {
        return (List<Category>) getEntities();
    }
}
