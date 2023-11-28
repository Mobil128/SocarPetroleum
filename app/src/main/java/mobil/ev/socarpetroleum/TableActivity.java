package mobil.ev.socarpetroleum;

import android.content.Intent;
//import android.support.annotation.Nullable;
//import android.support.annotation.RequiresApi;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TableActivity extends AppCompatActivity {

    private MyArrayAdapterTab2 adapter_tab2;
    private MyArrayAdapter adapter;
    private ArrayList< MyDataModelTab2 > list_tab2;
    private ArrayList< MyDataModel > list;
    private ListView listView,listViewTab2;
    boolean saxlanib_he_yox=false;

    String date_n="";
    String cenlerin_sayi="1";


    TextView tv_tarix,tv_olcu_aparan,tv_ydm,tv_versname,tv_verscode;

    String hesablanan[][]=new String[20][20];
    String yanacaq_sayi="1";
    String[] yan_novu = new String[10];
    String[] yan_novu_sayi =new String[10];
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("YDM");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        init();



        tv_tarix=(TextView)findViewById(R.id.Tarix);
        tv_olcu_aparan=(TextView)findViewById(R.id.olcu_sexs);
        tv_ydm = (TextView)findViewById(R.id.tv_ydm) ;

        list = new ArrayList < > ();
        list_tab2 = new ArrayList < > ();
        //???????????????????????
        adapter_tab2 = new MyArrayAdapterTab2(this, list_tab2);
        adapter = new MyArrayAdapter(this, list);
        listView = (ListView) findViewById(R.id.lv_tanker);
        listViewTab2 = (ListView) findViewById(R.id.list2);
        //????????????????????
        listViewTab2.setAdapter( adapter_tab2);
        listView.setAdapter(adapter);








        Fireread();




    }



    public void init(){
        TabHost tabHost;
        tabHost=findViewById(R.id.tabHost1);
        tabHost.setup();
        setupTab("son ölcü",R.id.hhhy1,tabHost);
        setupTab("bütün ölcülər",R.id.tab2,tabHost);
        setupTab("info",R.id.tab3,tabHost);
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++){
            TextView tv =tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(getResources().getColor(R.color.black));
        }
       //Tab3 set VersionName and Versioncode
        tv_versname = (TextView)findViewById(R.id.tv_versname) ;
        tv_verscode = (TextView)findViewById(R.id.tv_verscode) ;
        tv_versname.setText("VersionName: "+BuildConfig.VERSION_NAME);
        tv_verscode.setText("VersionCode: "+BuildConfig.VERSION_CODE);
    }

    private void setupTab(String title, int id, TabHost tabHost) {
        TabHost.TabSpec spec = tabHost.newTabSpec(title);
        spec.setContent(id);
        spec.setIndicator(title);
        tabHost.addTab(spec);

    }

    private void Fireread(){
        myRef = database.getReference("Data").child(UserInfo.getUser_ydm());
        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dss =task.getResult();
                cenlerin_sayi=dss.child("cen_sayi").getValue().toString();
                yanacaq_sayi=dss.child("yanacaq_sayi").getValue().toString();
                for(int i=1;i<=Integer.parseInt(yanacaq_sayi);i++){
                    yan_novu[i]=dss.child(String.valueOf(i)).getValue().toString();
                    int ii=i+10;
                    yan_novu_sayi[i]=dss.child(String.valueOf(String.valueOf(ii))).getValue().toString();
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
                for(int i=dbChıldCount-1;i>0;i--){
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
        myRef = database.getReference("YDM").child(UserInfo.getUser_ydm()).child("line");
        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dss =task.getResult();
                int index=1;
                for(int say_n=1;say_n<=Integer.parseInt(yanacaq_sayi);say_n++){
                    for(int say_n1 = 1; say_n1<=Integer.parseInt(yan_novu_sayi[say_n]); say_n1++){
                        MyDataModel model = new MyDataModel();
                        int col= Integer.parseInt(cenlerin_sayi)+Integer.parseInt(yanacaq_sayi)+index;
                        int col3=Integer.parseInt(cenlerin_sayi)+say_n;
                        int col4=Integer.parseInt(cenlerin_sayi)+Integer.parseInt(yanacaq_sayi)+Integer.parseInt(cenlerin_sayi)+index;
                        model.setOn_off(dss.child(String.valueOf(col4)).getValue().toString());
                        //yes
                        model.setYanacaq_novu(yan_novu[say_n]);
                        model.setMetrostok(dss.child(String.valueOf(col)).getValue().toString());
                        model.setHecmi(dss.child(String.valueOf(index)).getValue().toString());
                        if(say_n1==Integer.parseInt(yan_novu_sayi[say_n])){
                            model.setCemi(dss.child(String.valueOf(col3)).getValue().toString());
                        }
                        list.add(model);
                        index++;}}
                adapter.notifyDataSetChanged();
                tv_tarix.setText(dss.child("tarix").getValue().toString());
                tv_ydm.setText(dss.child("ydm").getValue().toString());
                tv_olcu_aparan.setText(dss.child("ad").getValue().toString());

            }
        });


    }
    private  void Hesabla(){





        Kalibrovka kalibrovka= new Kalibrovka();
        View v;
        View v2;
        CheckBox checkBox;
        EditText et;
        int index=0;int index2= 0;
        TextView textView;
        int cem=0;
        for(int i=1;i<=(Integer.parseInt(yanacaq_sayi));i++){
            for(int i1=1;i1<=(Integer.parseInt(yan_novu_sayi[i]));i1++){


            v = listView.getChildAt(index);
            checkBox = (CheckBox)v.findViewById(R.id.checkBox);
            et = (EditText) v.findViewById(R.id.et_metrostok);
            textView =(TextView)v.findViewById(R.id.tv_hecm) ;
                hesablanan[index][0]=String.valueOf(checkBox.isChecked());
                hesablanan[index][1]=et.getText().toString();
            hesablanan[index][2]= kalibrovka.getValue(UserInfo.getUser_ydm(),index,et.getText().toString(),2,Integer.parseInt(cenlerin_sayi));
            textView.setText(hesablanan[index][2]);
            cem=cem+Integer.parseInt(hesablanan[index][2]);
            if(i1==(Integer.parseInt(yan_novu_sayi[i]))){
                    hesablanan[index2][3]=String.valueOf(cem);
                    //Log.i("cemi",String.valueOf(index2)+";"+hesablanan[index][3]);
                textView =(TextView)v.findViewById(R.id.tv_cemi) ;
                textView.setText(hesablanan[index2][3]);
                index2++;
                }
            index++;

        }
        cem=0;}

        tv_tarix.setText("Tarix");
        tv_olcu_aparan.setText(UserInfo.getUser_name());
        Calendar c= Calendar.getInstance();
        date_n=String.valueOf(c.get(Calendar.DAY_OF_MONTH))+":"+String.valueOf(c.get(Calendar.MARCH)+1)+":"+String.valueOf(c.get(Calendar.YEAR))+" "+String.valueOf(c.get(Calendar.HOUR_OF_DAY))+":"+String.valueOf(c.get(Calendar.MINUTE))+":"+String.valueOf(c.get(Calendar.SECOND));
        tv_tarix.setText(date_n);


    }
    public void User(View view) {

        Intent intent = new Intent(getApplicationContext(),User.class);



        startActivity(intent);

    }
    public void Ynn(View view) {
        Toast.makeText(TableActivity.this,"Yeniləndı", Toast.LENGTH_SHORT).show();
        list.clear();
        Fireread();

    }
    public void Duzelt(View view){
        Hesabla();
        saxlanib_he_yox=true;
        Toast.makeText(TableActivity.this,"Hesablandı", Toast.LENGTH_SHORT).show();


    }

    public void Saxla(View view){
       saxlanib_he_yox=false;
            Hesabla();
      //  new WriteData().execute();
        int index=0;int index2=0;
        DatabaseReference myRefX=database.getReference("vx");
        myRef = database.getReference("YDM").child(UserInfo.getUser_ydm()).child("line");
        Map<String,String> map1=new HashMap<>();
        Map<String,String> map=new HashMap<>();
        int versioncode = BuildConfig.VERSION_CODE;
        map1.put("tarix",date_n);
        map1.put("ad",UserInfo.getUser_name());
        map1.put("versiya",String.valueOf(versioncode));

        map.put("ydm",UserInfo.getUser_ydm());
        map.put("ad",UserInfo.getUser_name());
        map.put("tarix",date_n);
        for(int i=1;i<=(Integer.parseInt(yanacaq_sayi));i++){
            for(int i1=1;i1<=(Integer.parseInt(yan_novu_sayi[i]));i1++) {


                map.put(String.valueOf(index+1),hesablanan[index][2]);
                if(i1==(Integer.parseInt(yan_novu_sayi[i]))){

                    map.put(String.valueOf(Integer.parseInt(cenlerin_sayi)+index2+1),hesablanan[index2][3]);
                    index2++;
                }
                index++;

            }
        }index=0;
        for(int i=1;i<=(Integer.parseInt(yanacaq_sayi));i++){
            for(int i1=1;i1<=(Integer.parseInt(yan_novu_sayi[i]));i1++) {

                map.put(String.valueOf(Integer.parseInt(cenlerin_sayi)+Integer.parseInt(yanacaq_sayi)+index+1),hesablanan[index][1]);
                index++;
            }
        }index=0;
        for(int i=1;i<=(Integer.parseInt(yanacaq_sayi));i++){
            for(int i1=1;i1<=(Integer.parseInt(yan_novu_sayi[i]));i1++) {

                map.put(String.valueOf(Integer.parseInt(cenlerin_sayi)+Integer.parseInt(cenlerin_sayi)+Integer.parseInt(yanacaq_sayi)+index+1),hesablanan[index][0]);
                index++;
            }
        }
        myRef.setValue(map);
        myRef = database.getReference("YDM").child(UserInfo.getUser_ydm()).child("DB");
        myRef.push().setValue(map);
        myRefX.push().setValue(map1);

        Toast.makeText(TableActivity.this,"Saxlandı", Toast.LENGTH_SHORT).show();


    }
    public void direkSend(View view){
        saxlanib_he_yox=false;
        Hesabla();
        //  new WriteData().execute();
        int index=0;int index2=0;
        myRef = database.getReference("YDM").child(UserInfo.getUser_ydm()).child("line");
        Map<String,String> map=new HashMap<>();
        map.put("ydm",UserInfo.getUser_ydm());
        map.put("ad",UserInfo.getUser_name());
        map.put("tarix",date_n);
        for(int i=1;i<=(Integer.parseInt(yanacaq_sayi));i++){
            for(int i1=1;i1<=(Integer.parseInt(yan_novu_sayi[i]));i1++) {


                map.put(String.valueOf(index+1),hesablanan[index][2]);
                if(i1==(Integer.parseInt(yan_novu_sayi[i]))){

                    map.put(String.valueOf(Integer.parseInt(cenlerin_sayi)+index2+1),hesablanan[index2][3]);
                    index2++;
                }
                index++;

            }
        }index=0;
        for(int i=1;i<=(Integer.parseInt(yanacaq_sayi));i++){
            for(int i1=1;i1<=(Integer.parseInt(yan_novu_sayi[i]));i1++) {

                map.put(String.valueOf(Integer.parseInt(cenlerin_sayi)+Integer.parseInt(yanacaq_sayi)+index+1),hesablanan[index][1]);
                index++;
            }
        }index=0;
        for(int i=1;i<=(Integer.parseInt(yanacaq_sayi));i++){
            for(int i1=1;i1<=(Integer.parseInt(yan_novu_sayi[i]));i1++) {

                map.put(String.valueOf(Integer.parseInt(cenlerin_sayi)+Integer.parseInt(cenlerin_sayi)+Integer.parseInt(yanacaq_sayi)+index+1),hesablanan[index][0]);
                index++;
            }
        }
        myRef.setValue(map);
        myRef = database.getReference("YDM").child(UserInfo.getUser_ydm()).child("DB");
        myRef.push().setValue(map);
        myRef = database.getReference("YDM").child(UserInfo.getUser_ydm()).child("Dline");
        myRef.setValue(map);
        myRef = database.getReference("YDM").child(UserInfo.getUser_ydm()).child("DDBline");
        myRef.push().setValue(map);
        Toast.makeText(TableActivity.this,"Saxlandı və göndərildi", Toast.LENGTH_SHORT).show();

    }



}