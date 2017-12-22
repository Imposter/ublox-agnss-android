package ca.indigogames.ubloxagps.app.utility;

import android.content.Context;
import android.util.TypedValue;

public class Color {
    public static int getThemeColor(Context context, int color) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(color, value, true);
        return value.data;
    }
}
