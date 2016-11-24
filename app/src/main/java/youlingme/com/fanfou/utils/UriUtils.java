package youlingme.com.fanfou.utils;

import youlingme.com.fanfou.extras.Constants;

/**
 * Created by wanghan on 16/11/24.
 */
public class UriUtils {

    public static String getReuqestUrlHead() {
        return Constants.URL_BASE;
    }

    public static String getRequestUrlDailyEnd() {
        return Constants.URL_DAILY + Constants.URL_END;
    }

    public static String getRequestUrlWeeklyEnd() {
        return Constants.URL_WEEKLY + Constants.URL_END;
    }

}
