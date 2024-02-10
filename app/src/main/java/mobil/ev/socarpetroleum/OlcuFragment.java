package mobil.ev.socarpetroleum;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class OlcuFragment extends Fragment {
    ImageView hesabla,saxla,yenile,sexs,bos_yer;
    String date_n="";
    String hesablanan[][]=new String[20][20];
    boolean saxlanib_he_yox=false;
    TextView tv_tarix,tv_olcu_aparan,tv_ydm;
    View v;
    private ArrayList< MyDataModel > list;
    private ListView listView;
    private MyArrayAdapter adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("YDM");
    String cenlerin_sayi="1";String yanacaq_sayi="1";
    String[] yan_novu = new String[10];
    String[] yan_novu_sayi =new String[10];



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       v = inflater.inflate(R.layout.fragment_olcu, container, false);
        init();
        bos_yer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =new  AlertDialog.Builder( getContext());
                builder.setTitle("Cənlərdə qalan boş yer")
                        .setMessage("Ai92")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        });
                 AlertDialog dialog = builder.create();
                 dialog.show();

            }
        });
        hesabla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Hesabla();
            }
        });
        saxla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Saxla();
            }
        });
        yenile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Ynn();
            }
        });
        sexs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              User();
            }
        });
        Fireread();
        StorageReference httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(UserInfo.getUser_Sekil());
        setImage(sexs,httpsReference);
        return v;

    }
    public final static Bitmap Bytes2Bitmap(byte[] b) {
        if (b == null) {
            return null;
        }/*from w w w.  j a v  a2  s.  c om*/
        if (b.length != 0) {
            InputStream is = new ByteArrayInputStream(b);
            Bitmap bmp = BitmapFactory.decodeStream(is);
            return bmp;
        } else {
            return null;
        }
    }

    private void setImage(ImageView sexs, StorageReference httpsReference) {
        final long ONE_MEGABYTE = 2024 * 2024;
        httpsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                sexs.setImageBitmap(Bytes2Bitmap(bytes));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    private void init() {
        bos_yer=(ImageView) v.findViewById(R.id.bos_yer);
        tv_tarix=(TextView)v.findViewById(R.id.Tarix);
        tv_olcu_aparan=(TextView)v.findViewById(R.id.olcu_sexs);
        tv_ydm = (TextView)v.findViewById(R.id.tv_ydm) ;
        list = new ArrayList< >();
        adapter = new MyArrayAdapter(getContext(), list);
        listView = (ListView) v.findViewById(R.id.lv_tanker);
        listView.setAdapter(adapter);
        hesabla = (ImageView) v.findViewById(R.id.hesabla);
        saxla = (ImageView) v.findViewById(R.id.saxla);
        yenile = (ImageView) v.findViewById(R.id.yenile);
        sexs = (ImageView) v.findViewById(R.id.imageView);
        sexs.setImageResource(R.drawable.icon_avator);




    }
    public void Duzelt(){
        Hesabla();
        saxlanib_he_yox=true;
        Toast.makeText(getContext(),"Hesablandı", Toast.LENGTH_SHORT).show();


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
    public void Ynn() {
        Toast.makeText(getContext(),"Yeniləndı", Toast.LENGTH_SHORT).show();
        list.clear();
        Fireread();

    }
    public void Saxla(){
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

        Toast.makeText(getContext(),"Saxlandı", Toast.LENGTH_SHORT).show();


    }
    public void User() {

        Intent intent = new Intent(getContext().getApplicationContext(),User.class);



        startActivity(intent);

    }

    private void Fireread(){
        myRef = database.getReference("Data1").child(UserInfo.getUser_ydm());
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

}