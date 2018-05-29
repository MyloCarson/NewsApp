package com.mylocarson.newsapp.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebResourceResponse;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;

import okhttp3.HttpUrl;

/**
 * Created by user on 23/02/2018.
 */

@SuppressWarnings("ALL")
public class AdBlocker {

    // --Commented out by Inspection (29/05/2018, 17:58):private static final String AD_HOSTS_FILE = "pgl_yoyo_org.txt";
    private static final Set<String> AD_HOSTS = new HashSet<>();

// --Commented out by Inspection START (29/05/2018, 17:44):
//    public static void init(final Context context) {
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//                    loadFromAssets(context);
//                } catch (IOException e) {
//                    // noop
//                }
//                return null;
//            }
//        }.execute();
//    }
// --Commented out by Inspection STOP (29/05/2018, 17:44)

// --Commented out by Inspection START (29/05/2018, 17:55):
//    @WorkerThread
//    private static void loadFromAssets(Context context) throws IOException {
//        InputStream stream = context.getAssets().open(AD_HOSTS_FILE);
//        BufferedSource buffer = Okio.buffer(Okio.source(stream));
//        String line;
//        while ((line = buffer.readUtf8Line()) != null) {
//            AD_HOSTS.add(line);
//        }
//        buffer.close();
//        stream.close();
//    }
// --Commented out by Inspection STOP (29/05/2018, 17:55)

    public static boolean isAd(String url) {
        HttpUrl httpUrl = HttpUrl.parse(url);
        return isAdHost(httpUrl != null ? httpUrl.host() : "");
    }

    private static boolean isAdHost(String host) {
        if (TextUtils.isEmpty(host)) {
            return false;
        }
        int index = host.indexOf(".");
        return index >= 0 && (AD_HOSTS.contains(host) ||
                index + 1 < host.length() && isAdHost(host.substring(index + 1)));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static WebResourceResponse createEmptyResource() {
        return new WebResourceResponse("text/plain", "utf-8", new ByteArrayInputStream("".getBytes()));
    }
}
