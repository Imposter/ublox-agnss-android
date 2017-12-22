package ca.indigogames.ubloxagps.app.task;

import android.support.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import ca.indigogames.ubloxagps.network.HttpResponse;
import ca.indigogames.ubloxagps.network.HttpTask;
import ca.indigogames.ubloxagps.task.AsyncTaskCallback;

public class DownloadAGPSTask extends HttpTask<byte[]> {
    private static final String UBLOX_ONLINE_DATA_ENDPOINT
            = "http://online-live1.services.u-blox.com/GetOnlineData.ashx";

    public static final String GNSS_GPS = "gps";
    public static final String DATA_TYPE_ALMANAC = "alm";

    public DownloadAGPSTask(String token, String gnss, String dataType,
                            @Nullable Double latitude, @Nullable Double longitude,
                            @Nullable Double altitude, @Nullable Double accuracy,
                            AsyncTaskCallback<byte[]> callback) {
        StringBuilder builder = new StringBuilder(UBLOX_ONLINE_DATA_ENDPOINT);
        builder.append("?token=").append(token);
        builder.append(";gnss=").append(gnss);
        builder.append(";datatype=").append(dataType);
        if (latitude != null) builder.append(";lat=").append(latitude);
        if (longitude != null) builder.append(";lon=").append(longitude);
        if (altitude != null) builder.append(";alt=").append(altitude);
        if (accuracy != null) builder.append(";pacc=").append(accuracy);
        builder.append(";format=aid");

        setUrl(builder.toString());
        setCallback(callback);
    }

    public DownloadAGPSTask(String token, String gnss, String dataType,
                            AsyncTaskCallback<byte[]> callback) {
        String url = UBLOX_ONLINE_DATA_ENDPOINT +
                "?token=" + token +
                ";gnss=" + gnss +
                ";datatype=" + dataType;

        setUrl(url);
        setCallback(callback);
    }

    @Override
    protected byte[] processResponse(HttpResponse response) throws Exception {
        if (response.getCode() != 200)
            throw new IllegalAccessException("Unable to get A-GPS information");

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        InputStream stream = response.getDataStream();
        int b;
        while ((b = stream.read()) != -1) {
            byteStream.write(b);
        }

        return byteStream.toByteArray();
    }
}