package youlingme.com.fanfou.volley;

import android.text.Html;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import youlingme.com.fanfou.extras.Keys;
import youlingme.com.fanfou.model.Fanfou;

/**
 * Created by wanghan on 16/11/24.
 */
public class Parser {

    public static ArrayList<Fanfou> parseFanfouJSON(JSONObject response) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Fanfou> fanfos = new ArrayList<>();
        if (response != null && response.length() > 0) {
            JSONArray message = response.optJSONArray("msgs");
            for (int i = 0; i < message.length(); i++) {
                String name;
                String avatar;
                String msg;
                String img;
                String time;

                JSONObject fanfou = message.optJSONObject(i);
                name = fanfou.optString(Keys.ComeoutSomeKeys.KEY_REALNAME);
                time = fanfou.optString(Keys.ComeoutSomeKeys.KEY_TIME);
                avatar = fanfou.optString(Keys.ComeoutSomeKeys.KEY_AVATAR);
                msg = fanfou.optString(Keys.ComeoutSomeKeys.KEY_MESSAGE);
                img = fanfou.optJSONObject(Keys.ComeoutSomeKeys.KEY_IMG).optString(Keys.ComeoutSomeKeys.KEY_PREVIEW);
                if (!img.equals("")) {
                    img = img.replaceFirst("m0", "n0");
                }

                Fanfou fan = new Fanfou();
                fan.setScreenName(name);
                fan.setAvatarUrl(avatar);
                fan.setStatus(Html.fromHtml(msg).toString());
                fan.setImageUrl(img);
                fan.setTimeStamp(time);

                if (!name.equals(null)) {
                    fanfos.add(fan);
                }
            }
        }
        return fanfos;
    }

    public static ArrayList<Fanfou> parseFanfouGSON(JSONObject response) {
        ArrayList<Fanfou> list = null;
        if (response != null && response.length() > 0) {
            JSONArray message = response.optJSONArray("msgs");

            String messages = message.toString();
            Gson gson = new Gson();
            list = gson.fromJson(messages, new TypeToken<ArrayList<Fanfou>>() {}.getType());
        }
        return list;
    }

}
