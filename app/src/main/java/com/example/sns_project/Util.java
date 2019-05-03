package com.example.sns_project;

import android.app.Activity;
import android.util.Patterns;
import android.widget.Toast;

public class Util {
    public Util(){/* */}

    public static void showToast(Activity activity, String msg){
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    public static boolean isStorageUrl(String url){
        return Patterns.WEB_URL.matcher(url).matches() && url.contains("https://firebasestorage.googleapis.com/v0/b/sns-project-30f3c.appspot.com/o/post");
    }

    public static String storageUrlToName(String url){
        return url.split("\\?")[0].split("%2F")[url.split("\\?")[0].split("%2F").length - 1];
    }
}
