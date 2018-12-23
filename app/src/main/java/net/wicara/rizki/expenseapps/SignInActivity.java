package net.wicara.rizki.expenseapps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    TextView emailField, passwordField;
    Button loginBtn;
    TextView signBtn;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        //progress dialog
        progressDialog = new ProgressDialog(SignInActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Autentification on progress");


        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if(ni.isConnected()) {
            mAuth = FirebaseAuth.getInstance();
            if(mAuth.getCurrentUser() != null) {
                progressDialog.show();
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            loadAllViews();
            FirebaseApp.initializeApp(this);
        } else {
            Toast.makeText(SignInActivity.this, R.string.toast_no_internet, Toast.LENGTH_LONG).show();
        }
    }

    public void loadAllViews() {
        emailField = (TextView) findViewById(R.id.emailField);
        passwordField = (TextView) findViewById(R.id.passwordField);
        loginBtn = (Button) findViewById(R.id.login);
        loginBtn.setOnClickListener(this);
        signBtn = (TextView) findViewById(R.id.signUpBtn);
        signBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login) {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();
            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignInActivity.this, R.string.toast_empty_values, Toast.LENGTH_LONG).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("test", "signInWithEmail:onComplete:" + task.isSuccessful());
                                if (!task.isSuccessful()) {
                                    Log.w("test", "signInWithEmail:failed", task.getException());
                                    Toast.makeText(SignInActivity.this, R.string.toast_loginFailed, Toast.LENGTH_LONG).show();
                                } else {
                                    progressDialog.show();
                                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        } else if (v.getId() == R.id.signUpBtn) {
            Intent intent = new Intent(this,SignUpActivity.class);
            startActivity(intent);
        }
    }
}
