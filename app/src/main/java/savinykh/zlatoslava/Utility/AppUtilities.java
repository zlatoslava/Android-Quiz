package savinykh.zlatoslava.Utility;

import android.app.Activity;
import android.content.Context;
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
}
