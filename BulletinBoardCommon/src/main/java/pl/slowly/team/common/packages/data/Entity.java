package pl.slowly.team.common.packages.data;

import java.io.Serializable;

/**
 * Base class for all the data sent from client to server.
 */
public abstract class Entity implements Serializable {
    protected int length;
    protected byte[] data;
}
