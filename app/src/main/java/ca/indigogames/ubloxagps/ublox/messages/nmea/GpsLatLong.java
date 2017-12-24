package ca.indigogames.ubloxagps.ublox.messages.nmea;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ca.indigogames.ubloxagps.ublox.messages.NmeaMessage;
import ca.indigogames.ubloxagps.ublox.messages.NmeaMessageId;
import ca.indigogames.ubloxagps.ublox.messages.NmeaPrefixId;

// Useful stuff: http://freenmea.net/docs
public class GpsLatLong implements NmeaMessage {
    public Float latitude;
    public String vDirection;
    public Float longitude;
    public String hDirection;
    public Date utcTime; // UTC
    public String status;

    @Override
    public String getPrefixId() {
        return NmeaPrefixId.GP;
    }

    @Override
    public String getMessageId() {
        return NmeaMessageId.GLL;
    }

    @Override
    public void deserialize(List<String> data) throws Exception {
        // Get location
        latitude = !data.get(0).isEmpty() ? Float.parseFloat(data.get(0)) / 100 : 0;
        vDirection = data.get(1);
        longitude = !data.get(2).isEmpty() ? Float.parseFloat(data.get(2)) / 100 : 0;
        hDirection = data.get(3);

        // Parse time
        String time = data.get(4);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss.SS", Locale.ENGLISH);
        utcTime = dateFormat.parse(time);

        // Get status
        data.get(5);
    }

    @Override
    public List<String> serialize() throws Exception {
        throw new UnsupportedOperationException("Serialization not supported for type");
    }
}
