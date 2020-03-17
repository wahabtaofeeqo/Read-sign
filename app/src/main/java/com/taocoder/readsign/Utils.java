package com.taocoder.readsign;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    private static boolean isWifiCOn;
    private  static boolean isMobileCon;

    public final int REQUEST_CODE = 20;

    public static final String BASE_URL = "https://e96b3cf7.ngrok.io/dashout/";
    public static final String IMAGE_URL = "https://e96b3cf7.ngrok.io/uploads/";

    public static void showMessage(final Context context, final String msg) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void logMessage(Context context, String msg) {
        Log.i(Utils.class.getSimpleName(), msg);
    }

    public static String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(new Date());
    }

    public static String getTime() {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss a", Locale.getDefault());
        return format.format(new Date());
    }
}
