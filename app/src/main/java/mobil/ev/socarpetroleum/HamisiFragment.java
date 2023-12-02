package mobil.ev.socarpetroleum;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HamisiFragment extends Fragment {
    String yanacaq_sayi="1";
    String cenlerin_sayi="1";
    private MyArrayAdapterTab2 adapter_tab2;
    private ArrayList< MyDataModelTab2 > list_tab2;
    private ListView listViewTab2;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("YDM");
    DatabaseReference myRef1 = database.getReference("Data");
    View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       v = inflater.inflate(R.layout.fragment_hamisi, container, false);
        init();
        Fireread();
       RecyclerView recyclerView = v.findViewById(R.id.recyc);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> dataList = Arrays.asList("");
        RecycslerAdapter recyclerAdapter=new RecycslerAdapter(dataList);
        recyclerView.setAdapter(recyclerAdapter);

        return v;
    }

    private void Fireread() {
        myRef1 = database.getReference("Data").child(UserInfo.getUser_ydm());
        myRef1.child("yanacaq_sayi").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {

                }else {yanacaq_sayi= task.getResult().getValue().toString();

                }
            }
        });
        myRef1.child("cen_sayi").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {

                }else {
                   cenlerin_sayi= task.getResult().getValue().toString();
                }
            }
        });

        myRef = database.getReference("YDM").child(UserInfo.getUser_ydm()).child("DB");
        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                int dbChıldCount= (int) task.getResult().getChildrenCount();

                String[][] dbArray=new String[dbChıldCount][10];
                int indexRow=0;
                for(DataSnapshot ds : task.getResult().getChildren())
                {
                    dbArray[indexRow][8]=ds.child("tarix").getValue().toString();
                    dbArray[indexRow][9]=ds.child("ad").getValue().toString();
                    for(int i=1;i<=Integer.parseInt(yanacaq_sayi);i++){
                        dbArray[indexRow][i]=ds.child(String.valueOf((Integer.parseInt(cenlerin_sayi)+i))).getValue().toString();

                    }
                    indexRow++;
                }

                for(int i=dbChıldCount-1;i>(dbChıldCount-100);i--){
                    MyDataModelTab2 modelTab2 = new MyDataModelTab2();

                    modelTab2.setTarix(dbArray[i][8]);
                    modelTab2.setUser(dbArray[i][9]);
                    modelTab2.setAi92(dbArray[i][1]);
                    modelTab2.setPrem(dbArray[i][2]);
                    modelTab2.setSup(dbArray[i][3]);
                    modelTab2.setDiz(dbArray[i][4]);
                    modelTab2.setLpg(dbArray[i][5]);
                    list_tab2.add(modelTab2);
                }
                adapter_tab2.notifyDataSetChanged();


            }
        });
       


    }

    private void init() {
        list_tab2 = new ArrayList < > ();
        adapter_tab2 = new MyArrayAdapterTab2(getContext(), list_tab2);
        listViewTab2 = (ListView) v.findViewById(R.id.list2);
        //????????????????????
        listViewTab2.setAdapter( adapter_tab2);

    }
}