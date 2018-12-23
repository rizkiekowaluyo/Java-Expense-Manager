package net.wicara.rizki.expenseapps;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    CardView expense,news,about,assistant;
    TextView fullname;
    CircleImageView imageView;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private StorageReference mStorage;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadAllViews();
        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(MainActivity.this,SignInActivity.class));
                }

            }
        };
        loadProfile();
        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,ExpenseActivity.class);
                startActivity(i);
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,NewsActivity.class);
                startActivity(i);
            }
        });

        assistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,AsistenActivity.class);
                startActivity(i);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu,v,menuInfo);
        menu.setHeaderTitle("Choose action");
        getMenuInflater().inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        imageView =(CircleImageView) findViewById(R.id.imageprofile);
        switch (item.getItemId()) {
            case R.id.logout:
//                Toast.makeText(this, "ini logout", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void openDialog() {
        AboutDialog ad = new AboutDialog();
        ad.show(getSupportFragmentManager(),"About Dialog");
    }

    private void loadAllViews(){
        expense = (CardView) findViewById(R.id.expense);
        news = (CardView) findViewById(R.id.news);
        assistant = (CardView) findViewById(R.id.assistant);
        about = (CardView) findViewById(R.id.about);
        fullname = (TextView) findViewById(R.id.fullnameuser);
        imageView = (CircleImageView) findViewById(R.id.imageprofile);
        registerForContextMenu(imageView);
    }

    private void loadProfile(){
        final FirebaseUser user = mAuth.getCurrentUser();
        String url = "https://firebasestorage.googleapis.com/v0/b/expenseapps.appspot.com/o/userprofile.jpg?alt=media&token=39bfb8f3-e590-4b33-be23-8d99b81ccc7b";
        Glide.with(MainActivity.this)
                .load(url)
                .into(imageView);
        fullname.setText(user.getEmail());
    }
}
