package mobil.ev.socarpetroleum;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class User extends AppCompatActivity {
   String polz_vez1;
    PersistantStorage  persistantStorage=new PersistantStorage();
    TextView polzAd,polzVez,polzYdm,polzAd1,polzId,polzSifre,polzTel;
    WebView webView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    boolean[] x = {false};
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        polzTel = findViewById(R.id.et_tel);
        polzAd = findViewById(R.id.et_name);
        polzVez = findViewById(R.id.et_vez);
        polzYdm = findViewById(R.id.et_ydm);
        polzId = findViewById(R.id.et_id);
        polzAd1 = findViewById(R.id.textView6);
        polzSifre = findViewById(R.id.et_sifre);

      /*  this.webView=(WebView)findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webView.setWebViewClient(webViewClient);

        webView.loadUrl("m.facebook.com/695389580579254/");*/
        if (UserInfo.getUser_vez().equals("User")) {
            polz_vez1 = "NövbəRəisi";
        }
        if (UserInfo.getUser_vez().equals("direktor")) {
            polz_vez1 = "StansiyaRəisi";
        }
        polzAd1.setText(UserInfo.getUser_name());
        polzAd.setText(UserInfo.getUser_name());
        polzVez.setText(polz_vez1);
        polzYdm.setText(UserInfo.getUser_ydm());
        polzId.setText(UserInfo.getUser_id());
        polzSifre.setText(UserInfo.getUser_sifre());
        polzTel.setText(UserInfo.getUser_tel());
    }
public void accountOut(View view){
    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
    persistantStorage.addProperty("login1","0");

    startActivity(intent);}

    public void accountSave(View view) {
        DatabaseReference myRefX=database.getReference("in").child(UserInfo.getUser_ydm());
        Map<String,String> map1=new HashMap<>();
        map1.put("ad",polzAd.getText().toString());
        map1.put("id",UserInfo.getUser_id());
        map1.put("sifre",polzSifre.getText().toString());
        map1.put("tel",polzTel.getText().toString());
        map1.put("vez",UserInfo.getUser_vez());
        String[] key = new String[1];
        myRefX.child(UserInfo.getUser_key()).setValue(map1);
        UserInfo.setUser_name(polzAd.getText().toString());
        UserInfo.setUser_tel(polzTel.getText().toString());
        UserInfo.setUser_sifre(polzSifre.getText().toString());
        persistantStorage.addProperty("login",UserInfo.getUser_ydm());
        persistantStorage.addProperty("sifre",UserInfo.getUser_sifre());
    }}

  /*  @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return  true;
        }
        return  super.onKeyDown(keyCode , event);
    }*/
