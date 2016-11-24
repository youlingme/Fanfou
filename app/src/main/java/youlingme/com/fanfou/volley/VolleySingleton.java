package youlingme.com.fanfou.volley;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import youlingme.com.fanfou.MyApplication;

/**
 * Created by wanghan on 16/11/24.
 */
public class VolleySingleton {

    public static VolleySingleton instance = null;
    private RequestQueue mRequestQueue;

    private VolleySingleton() {
        mRequestQueue = new Volley().newRequestQueue(MyApplication.getAppContext());
    }

    public static VolleySingleton getInstance() {
        if (instance == null) {
            synchronized (VolleySingleton.class){
                if (instance == null) {
                    instance = new VolleySingleton();
                }
            }
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
