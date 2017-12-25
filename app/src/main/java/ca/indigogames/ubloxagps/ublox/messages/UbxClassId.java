package ca.indigogames.ubloxagps.ublox.messages;

import ca.indigogames.ubloxagps.compat.UByte;

public class UbxClassId {
    public static final UByte NAV = UByte.valueOf((byte)0x01);
    public static final UByte RXM = UByte.valueOf((byte)0x02);
    public static final UByte INF = UByte.valueOf((byte)0x04);
    public static final UByte ACK = UByte.valueOf((byte)0x05);
    public static final UByte CFG = UByte.valueOf((byte)0x06);
    public static final UByte MON = UByte.valueOf((byte)0x0A);
    public static final UByte AID = UByte.valueOf((byte)0x0B);
    public static final UByte TIM = UByte.valueOf((byte)0x0D);
    public static final UByte LOG = UByte.valueOf((byte)0x21);
}
