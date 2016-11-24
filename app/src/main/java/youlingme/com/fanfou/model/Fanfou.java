package youlingme.com.fanfou.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wanghan on 16/11/23.
 */
public class Fanfou implements Parcelable {

    public static final Creator<Fanfou> CREATOR = new Creator<Fanfou>() {
        @Override
        public Fanfou createFromParcel(Parcel in) {
            return new Fanfou(in);
        }

        @Override
        public Fanfou[] newArray(int size) {
            return new Fanfou[size];
        }
    };

    public Fanfou(Parcel in) {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        screenName = in.readString();
        status = in.readString();
        avatarUrl = in.readString();
        imageUrl =in.readString();
        timeStamp = in.readString();
    }

    public Fanfou(String screenName, String status, String avatarUrl, String imageUrl, String timeStamp) {
        super();
        this.screenName = screenName;
        this.status = status;
        this.avatarUrl = avatarUrl;
        this.imageUrl = imageUrl;
        this.timeStamp = timeStamp;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @SerializedName("realname")
    private String screenName;
    @SerializedName("msg")
    private String status;
    private String imageUrl;
    @SerializedName("avatar")
    private String avatarUrl;
    @SerializedName("time")
    private String timeStamp;

    public Fanfou() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(screenName);
        dest.writeString(status);
        dest.writeString(avatarUrl);
        dest.writeString(imageUrl);
        dest.writeString(timeStamp);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
