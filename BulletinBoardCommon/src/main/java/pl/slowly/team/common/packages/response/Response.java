package pl.slowly.team.common.packages.response;

import pl.slowly.team.common.packages.Package;
import pl.slowly.team.common.packages.data.Entity;
import pl.slowly.team.common.packages.helpers.ResponseStatus;

/**
 * Response from server to clients requests.
 */
public class Response extends Package {
    private ResponseStatus responseStatus;
    private Entity entity;
}
