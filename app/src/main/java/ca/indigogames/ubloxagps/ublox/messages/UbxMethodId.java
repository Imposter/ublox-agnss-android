package ca.indigogames.ubloxagps.ublox.messages;

import ca.indigogames.ubloxagps.compat.UByte;

public class UbxMethodId {
    public static final UByte ACK_ACK = UByte.valueOf((byte)0x01);
    public static final UByte ACK_NACK = UByte.valueOf((byte)0x00);

    public static final UByte AID_INI = UByte.valueOf((byte)0x01);
    public static final UByte AID_ALM = UByte.valueOf((byte)0x30);
    public static final UByte AID_EPH = UByte.valueOf((byte)0x31);
}
