package net.wicara.rizki.expenseapps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class SignUpActivity extends AppCompatActivity  implements View.OnClickListener{

    CircleImageView imageView;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private StorageReference mStorage;
    TextView fullNameField, emailField, passwordField;
    Button signUpBtn, cancelBtn;
    static int PreqCode = 1;
    static int REQUESCODE = 1;
    Uri pickImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);
//        getSupportActionBar().setTitle(R.string.app_name_signUp);
        loadAllViews();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= 22){
                    checkAndRequestForPermission();
                }else{
                    openCamera();
                }
            }
        });


    }

    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(SignUpActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(SignUpActivity.this, Manifest.permission.CAMERA)){
                Toast.makeText(SignUpActivity.this,"Please accept for permission",Toast.LENGTH_SHORT).show();
            }else{
                ActivityCompat.requestPermissions(SignUpActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        PreqCode);
            }
        }else{
            openCamera();
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,REQUESCODE);
    }


    public void loadAllViews() {
        imageView = (CircleImageView) findViewById(R.id.imageprofile);
        fullNameField = (TextView) findViewById(R.id.fullNameField);
        emailField = (TextView) findViewById(R.id.emailField);
        passwordField = (TextView) findViewById(R.id.passwordField);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(this);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.signUpBtn) {
            final String fullName = fullNameField.getText().toString();
            final String email = emailField.getText().toString();
            final String password = passwordField.getText().toString();

            if(email.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
                Toast.makeText(SignUpActivity.this, R.string.toast_signup_empty_values, Toast.LENGTH_LONG).show();
            } else if (password.length() < 6) {
                Toast.makeText(SignUpActivity.this, R.string.toast_signup_empty_values, Toast.LENGTH_LONG).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("test", "createUserWithEmail:onComplete:" + task.isSuccessful());
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, R.string.toast_signup_error, Toast.LENGTH_LONG).show();
                                } else {
                                    FirebaseUser fUser = mAuth.getCurrentUser();
                                    if (fUser != null) {
                                        User user = new User();
                                        String useruid = fUser.getUid();
                                        user.setId(useruid);
                                        user.setFullName(fullName);
                                        user.setEmail(email);
                                        user.setPassword(password);
                                        mDatabase.getReference().child("users").child(useruid).setValue(user);
                                        mAuth.signOut();
                                        Toast.makeText(SignUpActivity.this, R.string.toast_signup_success, Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }
                        });

            }
        } else if (v.getId() == R.id.cancelBtn) {
            finish();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        pickImageUri = data.getData();
//        imageView.setImageURI(pickImageUri);
//        StorageReference filepath = mStorage.child("profileimage").child(pickImageUri.getLastPathSegment());
        if (resultCode == RESULT_OK){
            StorageReference file = mStorage.child("userprofile.jpg");


            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] filedata = baos.toByteArray();

            UploadTask uploadTask = file.putBytes(filedata);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });

        }else if (resultCode == RESULT_CANCELED){
            Toast.makeText(this, "cancel capture image", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "failed capture image", Toast.LENGTH_SHORT).show();
        }




//        pickImageUri = data.getData();
//        this.imageView.setImageURI(pickImageUri);
//        encodeBitmap(photo);
    }

}
