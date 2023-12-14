package mobil.ev.socarpetroleum;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class User extends AppCompatActivity {


    private StorageReference ProductImageRef ;
    private String downloadImageUrl;
    private static final int GALLERYPICK = 1;

    PersistantStorage  persistantStorage=new PersistantStorage();
    TextView polzAd,polzVez,polzYdm,polzAd1,polzId,polzSifre,polzTel;
    ImageView UserImage;
    WebView webView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    boolean[] x = {false};
    private Uri ImageUri;
    private ProgressDialog loadingBar;
    private String saveCurrentDate,saveCurrentTime;
    private String productRandomKey;

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
        UserImage= findViewById(R.id.imageButton);
        polzAd1.setText(UserInfo.getUser_name());
        polzAd.setText(UserInfo.getUser_name());
        polzVez.setText(UserInfo.getUser_vez());
        polzYdm.setText(UserInfo.getUser_ydm());
        polzId.setText(UserInfo.getUser_id());
        polzSifre.setText(UserInfo.getUser_sifre());
        polzTel.setText(UserInfo.getUser_tel());
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        loadingBar = new ProgressDialog(this);
        UserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });
    }
    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GALLERYPICK);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERYPICK && resultCode == RESULT_OK && data != null){
            ImageUri = data.getData();
            UserImage.setImageURI(ImageUri);
        }
    }


    public void accountSave(View view) {
        StoreProductInformation();

    }

    private  void saveInformation(){
        DatabaseReference myRefX=database.getReference().child("in");
        Map<String,String> map1=new HashMap<>();
        map1.put("ad",polzAd.getText().toString());
        map1.put("id",UserInfo.getUser_id());
        map1.put("ydm",UserInfo.getUser_ydm());
        map1.put("sifre",polzSifre.getText().toString());
        map1.put("tel",polzTel.getText().toString());
        map1.put("vez",UserInfo.getUser_vez());
        map1.put("sekil", downloadImageUrl);
        String[] key = new String[1];
        myRefX.child(UserInfo.getUser_key()).setValue(map1);
        UserInfo.setUser_name(polzAd.getText().toString());
        UserInfo.setUser_tel(polzTel.getText().toString());
        UserInfo.setUser_sifre(polzSifre.getText().toString());
        persistantStorage.addProperty("login",UserInfo.getUser_email());
        persistantStorage.addProperty("sifre",UserInfo.getUser_sifre());
        loadingBar.dismiss();
    }
    
    private void StoreProductInformation() {

      
        loadingBar.setTitle("Загрузка данных");
        loadingBar.setMessage("Пожалуйста, подождите...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("ddMMyyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HHmmss");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = ProductImageRef.child("555" + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(User.this, "Ошибка: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(User.this, "Изображение успешно загружено.", Toast.LENGTH_SHORT).show();

                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(User.this, "Фото сохранено", Toast.LENGTH_SHORT).show();

                             saveInformation();

                        }
                    }
                });
            }
        });
    }




}
    

