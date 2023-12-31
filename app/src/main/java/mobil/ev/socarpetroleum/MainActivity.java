package mobil.ev.socarpetroleum;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    Context cnt=MainActivity.this;
    PersistantStorage  persistantStorage=new PersistantStorage();
    TextView tv_login,tv_sifre;
    UserInfo ui;
    private static final String TAG = "MainActivity";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        tv_login= (TextView)findViewById(R.id.et_login) ;
        tv_sifre=(TextView)findViewById(R.id.et_sifre);
        Toast.makeText(MainActivity.this,"By Qaraxanov Mobil", Toast.LENGTH_SHORT).show();
        String a =tv_login.getText().toString();
        String b = tv_sifre.getText().toString();
        boolean bol=true;
        boolean bol1=true;
        persistantStorage.init(cnt);
        bol=persistantStorage.cont("login");
        bol1=persistantStorage.cont("login1");
        String g = persistantStorage.getProperty("login1");
        //если логин1 есть
        if(bol1) {
            //если логин1 равен 0
            if (g.equals("0")) {
                //если логин есть
                if (bol) {
                    tv_login.setText(persistantStorage.getProperty("login"));
                    tv_sifre.setText(persistantStorage.getProperty("sifre"));
                }
                //если логин нет
                else {
                    persistantStorage.addProperty("login", a);
                    persistantStorage.addProperty("sifre", b);
                }


        }else{
                //если логин есть
                if(bol){
                    tv_login.setText( persistantStorage.getProperty("login"));
                    tv_sifre.setText( persistantStorage.getProperty("sifre"));
                }
                //если логин нет
                else{
                    persistantStorage.addProperty("login",a);
                    persistantStorage.addProperty("sifre",b);
                }

            }


        }
        //если логин1 нет
        else {
            persistantStorage.addProperty("login1","0");
            //если логин есть
            if(bol){
                tv_login.setText( persistantStorage.getProperty("login"));
                tv_sifre.setText( persistantStorage.getProperty("sifre"));
            }
            //если логин нет
            else{
                persistantStorage.addProperty("login",a);
                persistantStorage.addProperty("sifre",b);
            }
        }




    }

    private void askNotificationPermission() {
        // This is only necessary for API Level > 33 (TIRAMISU)

            ui.setUser_email(tv_login.getText().toString());
            ui.setUser_sifre(tv_sifre.getText().toString());
            persistantStorage.addProperty("login",ui.user_email);
            persistantStorage.addProperty("sifre",ui.user_sifre);

            Intent intent = new Intent(getApplicationContext(),Prexod.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            this.finish();


    }

    public void Start(View view){
        askNotificationPermission();

    }


    public void Qeydiyyat(View view) {
        Intent intent = new Intent(getApplicationContext(),Qeydiyyat.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

    }
}