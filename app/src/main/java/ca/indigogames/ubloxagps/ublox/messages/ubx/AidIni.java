package ca.indigogames.ubloxagps.ublox.messages.ubx;

import ca.indigogames.ubloxagps.io.BinaryStream;
import ca.indigogames.ubloxagps.ublox.Message;

public class AidIni implements Message {
    public int ecefXOrLat; // int32
    public int ecefYOrLon; // int32
    public int ecefZOrAlt; // int32
    public long posAcc; // uint32

    public short tmCfg; // int16

    public int wnoOrDate; // uint16
    public long towOrDate; // uint32
    public int towNs; // int32
    public long tAccMs; // uint32
    public long tAccNs; // uint32
    public int clkDOrFreq; // int32
    public long clkDAccOrFreq; // uint32

    public Integer flags;

    @Override
    public int getClassId() {
        return ClassId.AID;
    }

    @Override
    public int getMethodId() {
        return MethodId.AID_INI;
    }

    @Override
    public void read(BinaryStream payloadStream) throws Exception {
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
    public void write(BinaryStream payloadStream) throws Exception {
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
