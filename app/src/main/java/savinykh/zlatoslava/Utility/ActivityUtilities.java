package savinykh.zlatoslava.Utility;

import android.app.Activity;
import android.content.Intent;

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
}
