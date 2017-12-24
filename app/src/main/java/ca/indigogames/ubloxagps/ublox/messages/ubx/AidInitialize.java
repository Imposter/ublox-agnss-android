package ca.indigogames.ubloxagps.ublox.messages.ubx;

import ca.indigogames.ubloxagps.compat.UInteger;
import ca.indigogames.ubloxagps.compat.UShort;
import ca.indigogames.ubloxagps.io.BinaryStream;
import ca.indigogames.ubloxagps.ublox.messages.UbxClassId;
import ca.indigogames.ubloxagps.ublox.messages.UbxMessage;
import ca.indigogames.ubloxagps.ublox.messages.UbxMethodId;

public class AidInitialize implements UbxMessage {
    public Integer ecefXOrLat; // int32
    public Integer ecefYOrLon; // int32
    public Integer ecefZOrAlt; // int32
    public UInteger posAcc; // uint32

    public Short tmCfg; // int16

    public UShort wnoOrDate; // uint16
    public UInteger towOrDate; // uint32
    public Integer towNs; // int32
    public UInteger tAccMs; // uint32
    public UInteger tAccNs; // uint32
    public Integer clkDOrFreq; // int32
    public UInteger clkDAccOrFreq; // uint32

    public Integer flags;

    @Override
    public int getClassId() {
        return UbxClassId.AID;
    }

    @Override
    public int getMethodId() {
        return UbxMethodId.AID_INI;
    }

    @Override
    public void deserialize(BinaryStream payloadStream) throws Exception {
        ecefXOrLat = payloadStream.readInt32();
        ecefYOrLon = payloadStream.readInt32();
        ecefZOrAlt = payloadStream.readInt32();
        posAcc = payloadStream.readUInt32();

        tmCfg = payloadStream.readInt16();

        wnoOrDate = payloadStream.readUInt16();
        towOrDate = payloadStream.readUInt32();
        towNs = payloadStream.readInt32();
        tAccMs = payloadStream.readUInt32();
        tAccNs = payloadStream.readUInt32();
        clkDOrFreq = payloadStream.readInt32();
        clkDAccOrFreq = payloadStream.readUInt32();

        flags = payloadStream.readInt32();
    }

    @Override
    public void serialize(BinaryStream payloadStream) throws Exception {
        payloadStream.writeInt32(ecefXOrLat);
        payloadStream.writeInt32(ecefYOrLon);
        payloadStream.writeInt32(ecefZOrAlt);
        payloadStream.writeUInt32(posAcc);

        payloadStream.writeInt16(tmCfg);

        payloadStream.writeUInt16(wnoOrDate);
        payloadStream.writeUInt32(towOrDate);
        payloadStream.writeInt32(towNs);
        payloadStream.writeUInt32(tAccMs);
        payloadStream.writeUInt32(tAccNs);
        payloadStream.writeInt32(clkDOrFreq);
        payloadStream.writeUInt32(clkDAccOrFreq);

        payloadStream.writeInt32(flags);
    }
}
