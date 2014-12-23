package pl.slowly.team.server.helpers;

import pl.slowly.team.common.packets.Packet;

/**
 * Wraps packets and adds client Id.
 */
public class PacketWrapper {

    private int userID;
    private Packet packet;

    public PacketWrapper(int userID, Packet packet) {
        this.userID = userID;
        this.packet = packet;
    }

    public int getUserID() {
        return userID;
    }

    public Packet getPacket() {
        return packet;
    }
}
