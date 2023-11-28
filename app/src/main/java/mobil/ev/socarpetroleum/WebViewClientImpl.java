package mobil.ev.socarpetroleum;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewClientImpl extends WebViewClient {
    Activity activity= null;
    public WebViewClientImpl(Activity activity){
        this.activity= activity;

    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
      if(url.indexOf("https://m.facebook.com/695389580579254/")> -1) return false;

      Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse((url)));
      activity.startActivity(intent);
      return true;
    }
}
