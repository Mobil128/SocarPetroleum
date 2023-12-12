package mobil.ev.socarpetroleum;


import static android.app.Activity.RESULT_OK;

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
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Qeydiyyat extends AppCompatActivity {
   LinearLayout linearLayout;
    private static final int GALLERYPICK = 1;

    private EditText login,password;
    FirebaseAuth mAuth;
    ImageView imageView,imageView2;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("in");
    private Uri ImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qeydiyyat);
        init();
        imageView.setOnClickListener(new View.OnClickListener() {
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
                linearLayout.setBackground(dr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //imageView2.setImageURI(ImageUri);
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



    public void init(){

       login = (EditText)findViewById(R.id.et_login0) ;
        password = (EditText)findViewById(R.id.et_sifre0) ;
        mAuth= FirebaseAuth.getInstance();
        imageView = findViewById(R.id.image_edit);
        imageView2 = findViewById(R.id.imageView7);
        linearLayout = findViewById(R.id.ll_start);
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