package ca.indigogames.ubloxagps.ublox.messages.nmea;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ca.indigogames.ubloxagps.ublox.messages.NmeaHandler;
import ca.indigogames.ubloxagps.ublox.messages.NmeaMessageId;
import ca.indigogames.ubloxagps.ublox.messages.NmeaPrefixId;

public class GpsLatLong {
    public Float latitude;
    public String vDirection;
    public Float longitude;
    public String hDirection;
    public Date utcTime; // UTC
    public String status;

    public static class Handler implements NmeaHandler<GpsLatLong> {
        @Override
        public String getPrefixId() {
            return NmeaPrefixId.GP;
        }

        @Override
        public String getMessageId() {
            return NmeaMessageId.GLL;
        }

        @Override
        public GpsLatLong deserialize(List<String> data) throws Exception {
            GpsLatLong result = new GpsLatLong();

            // Get location
            result.latitude = !data.get(0).isEmpty() ? Float.parseFloat(data.get(0)) / 100 : 0;
            result.vDirection = data.get(1);
            result.longitude = !data.get(2).isEmpty() ? Float.parseFloat(data.get(2)) / 100 : 0;
            result.hDirection = data.get(3);

            // Parse time
            String time = data.get(4);
            SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss.SS", Locale.ENGLISH);
            result.utcTime = dateFormat.parse(time);

            // Get status
            result.status = data.get(5);

            return result;
        }

        @Override
        public List<String> serialize(GpsLatLong gpsLatLong) throws Exception {
            throw new UnsupportedOperationException("Serialization not supported for type");
        }
    }
}
