package savinykh.zlatoslava.Utility;

import android.app.Activity;
import android.content.Intent;

import savinykh.zlatoslava.Constants.AppConstants;

public class ActivityUtilities {

    private static ActivityUtilities sActivityUtilities = null;

    public static ActivityUtilities getInstance(){
        if(sActivityUtilities == null){
            sActivityUtilities = new ActivityUtilities();
        }
        return sActivityUtilities;
    }

    public void invokeNewActivity(Activity activity, Class<?> tClass, boolean shouldFinish) {
        Intent intent = new Intent(activity, tClass);
        activity.startActivity(intent);
        if(shouldFinish) {
            activity.finish();
        }
    }

    public void invokeCustomUrlActivity(Activity activity, Class<?> tClass, String pageTitle, String pageUrl, boolean shouldFinish) {
        Intent intent = new Intent(activity, tClass);
        intent.putExtra(AppConstants.BUNDLE_KEY_TITLE, pageTitle);
        intent.putExtra(AppConstants.BUNDLE_KEY_URL, pageUrl);
        activity.startActivity(intent);
        if (shouldFinish) {
            activity.finish();
        }
    }
}
