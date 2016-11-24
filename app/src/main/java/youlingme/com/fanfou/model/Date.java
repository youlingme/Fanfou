package youlingme.com.fanfou.model;

/**
 * Created by wanghan on 16/11/23.
 */
public class Date {

    private String sDate;

    public Date(String date) {
        this.sDate = date;
    }

    public String getDate() {
        return sDate;
    }

    public void setDate(String sDate) {
        this.sDate = sDate;
    }

    @Override
    public String toString() {
        return sDate;
    }
}
