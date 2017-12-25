package ca.indigogames.ubloxagps.ublox.messages;

import java.util.List;

public interface NmeaHandler<TArg> {
    String getPrefixId();
    String getMessageId();

    TArg deserialize(List<String> data) throws Exception;
    List<String> serialize(TArg arg) throws Exception;
}