package mobil.ev.socarpetroleum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


public class UserFragment extends Fragment {

   View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_user, container, false);
        WebView webView = (WebView) v.findViewById(R.id.webview);
        webView.loadUrl("https://script.google.com/macros/s/AKfycbxRKSJ_wWq2RMaI_JaoI0bj7ikIgcVbSprdral6RkM/dev");
        return v;

    }
}