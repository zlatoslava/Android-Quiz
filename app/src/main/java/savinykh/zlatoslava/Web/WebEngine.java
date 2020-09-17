package savinykh.zlatoslava.Web;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import savinykh.zlatoslava.Constants.AppConstants;
import savinykh.zlatoslava.Listeners.WebListener;

public class WebEngine {

    private WebView mWebView;
    private Activity mActivity;
    private Context mContext;

    public static final String GOOGLE_DOCS_VIEWER = "https://docs.google.com/viewerng/viewer?url=";

    private WebListener mWebListener;

    public WebEngine(WebView webView, Activity activity) {
        mWebView = webView;
        mActivity = activity;
        mContext = activity.getApplicationContext();
    }

    public void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheMaxSize(AppConstants.SITE_CASHE_SIZE);
        mWebView.getSettings().setAppCachePath(mContext.getCacheDir().getAbsolutePath());
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);

        if(!isNetworkAvailable(mContext)) {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
    }

    public void initListeners(final WebListener webListener) {
        mWebListener = webListener;

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                webListener.onProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                webListener.onPageTitle(mWebView.getTitle());
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String webUrl) {

                loadPage(webUrl);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                webListener.onStart();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webListener.onLoaded();
            }
        });
    }

    public void loadPage(String webUrl) {
        if (isNetworkAvailable(mContext)) {
            if (webUrl.startsWith("tel:") ||
                    webUrl.startsWith("sms:") ||
                    webUrl.startsWith("mms:") ||
                    webUrl.startsWith("smsto:") ||
                    webUrl.startsWith("mmsto:") ||
                    webUrl.startsWith("mailto:") ||
                    webUrl.contains("geo:")) {
                invokeNativeApp(webUrl);
            } else if(webUrl.contains("?target=blank")) {
                invokeNativeApp(webUrl.replace("?target=blank", ""));
            } else if(webUrl.endsWith(".doc") ||
                    webUrl.endsWith(".docx") ||
                    webUrl.endsWith(".xls") ||
                    webUrl.endsWith(".xlsx") ||
                    webUrl.endsWith(".pptx") ||
                    webUrl.endsWith(".pdf")) {
                mWebView.loadUrl(GOOGLE_DOCS_VIEWER + webUrl);
                mWebView.getSettings().setBuiltInZoomControls(true);
            } else {
                mWebView.loadUrl(webUrl);
            }
        } else {
            mWebListener.onNetworkError();
        }
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private void invokeNativeApp(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        mActivity.startActivity(intent);
    }
}
