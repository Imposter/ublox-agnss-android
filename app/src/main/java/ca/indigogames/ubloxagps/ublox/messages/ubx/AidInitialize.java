package ca.indigogames.ubloxagps.ublox.messages.ubx;

import ca.indigogames.ubloxagps.compat.UByte;
import ca.indigogames.ubloxagps.compat.UInteger;
import ca.indigogames.ubloxagps.compat.UShort;
import ca.indigogames.ubloxagps.io.BinaryStream;
import ca.indigogames.ubloxagps.ublox.messages.UbxClassId;
import ca.indigogames.ubloxagps.ublox.messages.UbxHandler;
import ca.indigogames.ubloxagps.ublox.messages.UbxMethodId;

public class AidInitialize {
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

    public static class Handler implements UbxHandler<AidInitialize> {

        @Override
        public UByte getClassId() {
            return UbxClassId.AID;
        }

        @Override
        public UByte getMethodId() {
            return UbxMethodId.AID_INI;
        }

        @Override
        public AidInitialize deserialize(BinaryStream payloadStream) throws Exception {
            AidInitialize result = new AidInitialize();

            // Read fields
            result.ecefXOrLat = payloadStream.readInt32();
            result.ecefYOrLon = payloadStream.readInt32();
            result.ecefZOrAlt = payloadStream.readInt32();
            result.posAcc = payloadStream.readUInt32();

            result.tmCfg = payloadStream.readInt16();

            result.wnoOrDate = payloadStream.readUInt16();
            result.towOrDate = payloadStream.readUInt32();
            result.towNs = payloadStream.readInt32();
            result.tAccMs = payloadStream.readUInt32();
            result.tAccNs = payloadStream.readUInt32();
            result.clkDOrFreq = payloadStream.readInt32();
            result.clkDAccOrFreq = payloadStream.readUInt32();

            result.flags = payloadStream.readInt32();

            return result;
        }

        @Override
        public void serialize(BinaryStream payloadStream, AidInitialize aidInitialize) throws Exception {
            payloadStream.writeInt32(aidInitialize.ecefXOrLat);
            payloadStream.writeInt32(aidInitialize.ecefYOrLon);
            payloadStream.writeInt32(aidInitialize.ecefZOrAlt);
            payloadStream.writeUInt32(aidInitialize.posAcc);

            payloadStream.writeInt16(aidInitialize.tmCfg);

            payloadStream.writeUInt16(aidInitialize.wnoOrDate);
            payloadStream.writeUInt32(aidInitialize.towOrDate);
            payloadStream.writeInt32(aidInitialize.towNs);
            payloadStream.writeUInt32(aidInitialize.tAccMs);
            payloadStream.writeUInt32(aidInitialize.tAccNs);
            payloadStream.writeInt32(aidInitialize.clkDOrFreq);
            payloadStream.writeUInt32(aidInitialize.clkDAccOrFreq);

            payloadStream.writeInt32(aidInitialize.flags);
        }
    }
}
