package mobil.ev.socarpetroleum;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Qeydiyyat extends AppCompatActivity {
    private String downloadImageUrl;
    private StorageReference ProductImageRef,ProductImageRef2 ;
    private String saveCurrentDate,saveCurrentTime;
    private String productRandomKey;
    private ProgressDialog loadingBar;

    private static final int GALLERYPICK = 1;

    private EditText login,password;
    FirebaseAuth mAuth;
    ImageView imageView2;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("in");
    private Uri ImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qeydiyyat);
        init();
        imageView2.setOnClickListener(new View.OnClickListener() {
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
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), ImageUri);
                Bitmap croppedBitmap = cropToCircle(bitmap);
                Drawable dr = new BitmapDrawable(getResources(),croppedBitmap);
                imageView2.setImageBitmap(croppedBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private Bitmap cropToCircle(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // TODO: Implement image cropping logic to circle here
                Canvas canvas = new Canvas(output);
        int color = Color.RED;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        int radius = Math.min(width, height) / 2;
        canvas.drawCircle(width / 2f, height / 2f, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return output;
    }

    public void init() {

        login = (EditText) findViewById(R.id.et_login0);
        password = (EditText) findViewById(R.id.et_sifre0);
        mAuth = FirebaseAuth.getInstance();
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        imageView2 = findViewById(R.id.imageView6);
        imageView2.setImageResource(R.drawable.icon_avator);
        loadingBar = new ProgressDialog(this);
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
            StoreProductInformation();
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
                        map1.put("image", "-");
                        map1.put("vez","User");
                        map1.put("tel","+994");
                        map1.put("id","1");
                        map1.put("sifre",password.getText().toString());
                        myRef.setValue(map1);
                        //  Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                       // startActivity(intent);

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Qeydiyyat kecmədi", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{Toast.makeText(getApplicationContext(), "Bütün xanaları doldurun", Toast.LENGTH_SHORT).show();}

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
        imageView2.setDrawingCacheEnabled(true);
        imageView2.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView2.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = filePath.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(Qeydiyyat.this, "Xata: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Qeydiyyat.this, "Şəkil uğurla saxlanıldl", Toast.LENGTH_SHORT).show();
                downloadImageUrl = filePath.getDownloadUrl().toString();
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

                            downloadImageUrl = filePath.getDownloadUrl().toString();
                            Toast.makeText(Qeydiyyat.this, "Şəkil saxlanıldı", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            // SaveProductInfoToDatabase();

                        }
                    }
                });
            }
        });
    }
}