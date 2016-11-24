package youlingme.com.fanfou.utils;

import com.socks.library.KLog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by wanghan on 16/11/23.
 */
public class DateUtils {

    public static SimpleDateFormat formator = new SimpleDateFormat("yyyy-MM-dd");

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if (hour == 8) {
            hour = 9;
        }

        if (hour < 8 && hour >= 0) {
            c.add(Calendar.DATE, -1);
        }
        return formator.format(c.getTime());
    }

    public static String getCurrentMonday() {
        Calendar c = Calendar.getInstance();

        int day = c.get(Calendar.DAY_OF_WEEK);
        KLog.i("Now is day:" + day);
        int hour = c.get(Calendar.HOUR);
        KLog.i("Now is hour:" + hour);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        if (day == 1) {
            c.add(Calendar.DAY_OF_WEEK, -7);
        }
        if (day == 2) {
            if (hour < 8 && hour >= 0) {
                c.add(Calendar.DAY_OF_WEEK, -7);
            }
        }
        return formator.format(c.getTime());
    }

}
