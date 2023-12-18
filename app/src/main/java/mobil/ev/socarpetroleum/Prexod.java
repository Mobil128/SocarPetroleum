package mobil.ev.socarpetroleum;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Prexod extends AppCompatActivity {

    UserInfo ui;


    PersistantStorage  persistantStorage=new PersistantStorage();
    boolean si=false;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prexod);
        init();
        userVerification();


    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if(cUser != null)
        {
           // Toast.makeText(this, "User not null", Toast.LENGTH_SHORT).show();
        }
        else
        {
           // Toast.makeText(this, "User null", Toast.LENGTH_SHORT).show();
        }
    }

    private void login(String user_vez) {
        if(UserInfo.getUser_ydm().equals("664499")){
            Intent intent = new Intent(getApplicationContext(),Perexod2.class);
            startActivity(intent);
        }else{

        persistantStorage.addProperty("login1","1");
        Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
        startActivity(intent);
        }
    }
     private void UserVerfication2(){
       String uid= mAuth.getUid();
         myRef = database.getReference("in").child(uid);
         myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<DataSnapshot> task) {
                 DataSnapshot postSnapshot =task.getResult();
                 ui.setUser_Sekil(postSnapshot.child("sekil").getValue().toString());
                 ui.setUser_tel(postSnapshot.child("tel").getValue().toString());
                 ui.setUser_id(postSnapshot.child("id").getValue().toString());
                 ui.setUser_name(postSnapshot.child("ad").getValue().toString());
                 ui.setUser_vez(postSnapshot.child("vez").getValue().toString());
                 ui.setUser_ydm(postSnapshot.child("ydm").getValue().toString());
                 ui.setUser_key(postSnapshot.getKey());
                 login(UserInfo.getUser_vez());

             }



         });
        /* myRef.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {

                 for(DataSnapshot postSnapshot: snapshot.getChildren()){



                     if(ui.getUser_email().equals(postSnapshot.child("email").getValue().toString())){
                         ui.setUser_tel(postSnapshot.child("tel").getValue().toString());
                         ui.setUser_id(postSnapshot.child("id").getValue().toString());
                         ui.setUser_name(postSnapshot.child("ad").getValue().toString());
                         ui.setUser_vez(postSnapshot.child("vez").getValue().toString());
                         ui.setUser_ydm(postSnapshot.child("ydm").getValue().toString());
                         ui.setUser_key(postSnapshot.getKey());
                         login(UserInfo.getUser_vez());
                         si=true;

                         break;

                     }



                 }if(!si){
                     Toast.makeText(getApplicationContext(), "e-mail və şifrə yalnışdı", Toast.LENGTH_SHORT).show();
                     persistantStorage.addProperty("login1","0");
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {
                 Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
             }
         });*/
     }

    private void userVerification() {

        mAuth.signInWithEmailAndPassword(UserInfo.getUser_email(),UserInfo.getUser_sifre()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {UserVerfication2();
                }
                else
                {

                }
            }
        });



    }



    private void init() {
        Bundle extras  = getIntent().getExtras();


        mAuth = FirebaseAuth.getInstance();

    }


}