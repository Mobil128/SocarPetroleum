package mobil.ev.socarpetroleum;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Qeydiyyat extends AppCompatActivity {
    private EditText login,password;
    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("in");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qeydiyyat);
        init();
    }
    public void init(){
       login = (EditText)findViewById(R.id.et_login0) ;
        password = (EditText)findViewById(R.id.et_sifre0) ;
        mAuth= FirebaseAuth.getInstance();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){

            Toast.makeText(this,"İsfadəçi qeydiyyatdan keçib",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"İsfadəçi qeydiyyatdan keçməyib",Toast.LENGTH_SHORT).show();
        }
    }
    public void qeydiyyatOl(View view) {
        if(!TextUtils.isEmpty(login.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())){
            mAuth.createUserWithEmailAndPassword(login.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        String uid= mAuth.getUid();
                        myRef = myRef.child(uid);
                        Map<String,String> map1=new HashMap<>();

                        map1.put("ad","-");
                        map1.put("email",login.getText().toString());
                        map1.put("ydm","664499");
                        map1.put("vez","User");
                        map1.put("tel","+994");
                        map1.put("id","7");
                        map1.put("sifre",password.getText().toString());
                        myRef.setValue(map1);
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Qeydiyyat kecmədi", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{Toast.makeText(getApplicationContext(), "Bütün xanaları doldurun", Toast.LENGTH_SHORT).show();}

    }
}