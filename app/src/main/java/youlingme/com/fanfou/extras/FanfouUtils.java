package youlingme.com.fanfou.extras;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.ArrayList;

import youlingme.com.fanfou.model.Fanfou;
import youlingme.com.fanfou.utils.UriUtils;
import youlingme.com.fanfou.volley.Parser;
import youlingme.com.fanfou.volley.Requestor;
import youlingme.com.fanfou.volley.VolleySingleton;

/**
 * Created by wanghan on 16/11/24.
 */
public class FanfouUtils {

    public static ArrayList<Fanfou> loadFanfouDailyFeeds(RequestQueue requestQueue, String date) {
        JSONObject response = Requestor.requestJSON(VolleySingleton.getInstance().getRequestQueue(), UriUtils.getReuqestUrlHead() + date + UriUtils.getRequestUrlDailyEnd());
        ArrayList<Fanfou> fanfous = Parser.parseFanfouJSON(response);
//        ArrayList<Fanfou> fanfous = Parser.parseFanfouGSON(response);
        return fanfous;
    }


    public static ArrayList<Fanfou> loadFanfouWeeklyFeeds(RequestQueue requestQueue, String date) {
        JSONObject response = Requestor.requestJSON(requestQueue, UriUtils.getReuqestUrlHead() + date + UriUtils.getRequestUrlWeeklyEnd());
        ArrayList<Fanfou> fanfous = Parser.parseFanfouJSON(response);
        return fanfous;
    }

    public static ArrayList<Fanfou> loadFanfouFeed(String url, RequestQueue requestQueue) {
        JSONObject response = Requestor.requestJSON(requestQueue, url);
        ArrayList<Fanfou> fanfous = Parser.parseFanfouJSON(response);
        return fanfous;
    }

}
