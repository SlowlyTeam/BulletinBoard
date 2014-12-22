package pl.slowly.team.server.helpers;

import pl.slowly.team.common.packages.Packet;

/**
 * Created by Marek Majde on 2014-12-22.
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
