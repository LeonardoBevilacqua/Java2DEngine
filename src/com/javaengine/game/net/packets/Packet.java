package com.javaengine.game.net.packets;

import com.javaengine.game.net.GameClient;
import com.javaengine.game.net.GameServer;

/**
 * The base of the packets.
 *
 * @author leonardo
 */
public abstract class Packet {

    /**
     * The types of the packets.
     */
    public static enum PacketTypes {
        INVALID(-1),
        LOGIN(00),
        DISCONNECT(01),
        MOVE(02),
        LEVEL_UPDATE(03),
        START_DOMINGO(04);

        private int packetId;

        /**
         * Sets the packet.
         *
         * @param packetId The Id of the packet.
         */
        private PacketTypes(int packetId) {
            this.packetId = packetId;
        }

        /**
         * Gets the current packet.
         *
         * @return Returns the packet.
         */
        public int getId() {
            return packetId;
        }
    }

    public byte packetId;

    /**
     * Initizialize the packet.
     *
     * @param packetId The id of the packet.
     */
    public Packet(int packetId) {
        this.packetId = (byte) packetId;
    }

    public abstract void writeData(GameClient client);

    public abstract void writeData(GameServer server);

    /**
     * Gets the data and return the Packet type.
     *
     * @param data The array of byte of the data.
     * @return Returns the type of the packet.
     */
    public String readData(byte[] data) {
        String message = new String(data).trim();
        return message.substring(2);
    }

    public abstract byte[] getData();

    /**
     * Search for the packet. If is not found, returns the INVALID packet.
     *
     * @param packetId The id of the packet.
     * @return Returns the packet.
     */
    public static PacketTypes lookupPacket(String packetId) {
        try {
            return lookupPacket(Integer.parseInt(packetId));
        } catch (NumberFormatException e) {
            return PacketTypes.INVALID;
        }
    }

    /**
     * Search for the packet. If is not found, returns the INVALID packet.
     *
     * @param id The id of the packet.
     * @return Returns the packet.
     */
    private static PacketTypes lookupPacket(int id) {
        for (PacketTypes p : PacketTypes.values()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return PacketTypes.INVALID;
    }
}
