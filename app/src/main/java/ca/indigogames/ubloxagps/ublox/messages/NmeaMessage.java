package ca.indigogames.ubloxagps.ublox.messages;

import java.util.List;

public interface NmeaMessage {
    String getPrefixId();
    String getMessageId();

    void deserialize(List<String> data) throws Exception;
    List<String> serialize() throws Exception;
}