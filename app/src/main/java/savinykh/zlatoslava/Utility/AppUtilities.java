package savinykh.zlatoslava.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import savinykh.zlatoslava.R;

public class AppUtilities {
    public static long backPressed = 0;

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void tapPromptToExit(Activity activity) {
        if(backPressed + 2500 > System.currentTimeMillis()) {
            activity.finish();
        } else {
            showToast(activity.getApplicationContext(), activity.getResources().getString(R.string.tap_again));
        }
        backPressed = System.currentTimeMillis();
    }

    public static void shareApp(Activity activity) {
        try {
            final String appPackageName = activity.getPackageName();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, activity.getResources().getString(R.string.share_title)
                    + " https://play.google.com/store/apps/details?id=" + appPackageName);
            sendIntent.setType("text/plain");
            activity.startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void rateThisApp(Activity activity) {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
