package mobil.ev.socarpetroleum;

import static mobil.ev.socarpetroleum.R.*;
import static mobil.ev.socarpetroleum.R.id.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Workers extends AppCompatActivity {
    String name,id;
    private ArrayList< MyDataModelWorker > list;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("in");
    ListView listView;
    private MyArrayAdapterWorkers adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_workers);
        list = new ArrayList< >();
        adapter = new MyArrayAdapterWorkers(this, list);
        listView = (ListView) findViewById(lv_work);
        listView.setAdapter(adapter);
        Fireread();
    }

    private void Fireread() {
        myRef = database.getReference("in").child(UserInfo.getUser_ydm());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int index=0;
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    index++;
                       id=postSnapshot.child("id").getValue().toString();
                       name=postSnapshot.child("ad").getValue().toString();
                    MyDataModelWorker mw = new MyDataModelWorker();
                    mw.setId_worker(id);
                    mw.setName_worker(name);
                    list.add(mw);

                    if(snapshot.getChildrenCount()==index){
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}