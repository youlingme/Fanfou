package youlingme.com.fanfou.volley;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by wanghan on 16/11/24.
 */
public class Requestor {

    public static JSONObject requestJSON(RequestQueue requestQueue, String url) {
        JSONObject response = null;
        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, (String)null, requestFuture, requestFuture);
        requestQueue.add(request);

        KLog.i(request.getUrl());

        try {
            response = requestFuture.get(30000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            KLog.i(e);
        } catch (ExecutionException e) {
            KLog.i(e);
        } catch (TimeoutException e) {
            KLog.i(e);
        }

        KLog.i(response);

        return response;
    }


}
