package youlingme.com.fanfou;

import android.app.Application;
import android.content.Context;

import youlingme.com.fanfou.model.Date;

/**
 * Created by wanghan on 16/11/24.
 */
public class MyApplication extends Application{

    private static Date mDate;
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static void setDate(Date date) {
        MyApplication.mDate = date;
    }

    public static Date getDate() {
        return mDate;
    }

    public static Context getAppContext(){
        return mInstance.getApplicationContext();
    }
}
