package mobil.ev.socarpetroleum;


import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class Controller {



    public static final String TAG = "TAG";
    public static final String TAG1 = "TAG";
    public static final String WAURL2="https://script.google.com/macros/s/AKfycbxAJxv-ELhEzoWWB776hn_sJDEPkiKalePrwAfZfoBNzeJKJhTt_tQV5PM1i44gVCQgoQ/exec?";
    public  static final String WAURL="https://script.google.com/macros/s/AKfycbxNH1LlBboqRrGPhkwVbeMcSizhMRQ--5_Z5FldAerixKmBqGqlJ1JrwWAa3DmTv4Nh/exec?";
    // EG : https://script.google.com/macros/s/AKfycbwXXXXXXXXXXXXXXXXX/exec?
//Make Sure '?' Mark is present at the end of URL
    private static Response response;

    public static JSONObject readAllData() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(WAURL+"action=readAll")
                    .build();
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());

        } catch ( IOException | JSONException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }
    public static JSONObject WriteData(String polz_ydm,String polz_ad,String vremya,String[][] hesablanan,int cenlerin_sayi,int yanacaqin_sayi) {
        String i=polz_ydm+","+"Android"+","+polz_ad+","+vremya;

        for(int row=0;row<cenlerin_sayi;row++){
         i=i+","+hesablanan[row][2];
        }
        for(int row=0;row<yanacaqin_sayi;row++){
            i=i+","+hesablanan[row][3];
        }
        for(int row=0;row<cenlerin_sayi;row++){
            i=i+","+hesablanan[row][1];
        }
        for(int row=0;row<cenlerin_sayi;row++){
            i=i+","+hesablanan[row][0];
        }

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(WAURL2+"action=write&data="+i)
                    .build();
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());

        } catch ( IOException | JSONException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }

    public static JSONObject readAllData2() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(WAURL2+"action=readAll")
                    .build();
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());

        } catch (IOException | JSONException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }



}
