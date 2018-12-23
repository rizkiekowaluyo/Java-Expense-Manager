package net.wicara.rizki.expenseapps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AsistenActivity extends AppCompatActivity {

    private RecyclerView myrecyclerview;
    private List<Asisten> lstAsisten = new ArrayList<>();
    private AsistenAdapter adapter;
    DatabaseReference ref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asisten);
        loadAll();
        loadData();
    }

    private void loadData() {
        ref = FirebaseDatabase.getInstance().getReference().child("asisten");
        myrecyclerview = (RecyclerView) findViewById(R.id.asistenrecycle_id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstAsisten.clear();
                for (DataSnapshot single:dataSnapshot.getChildren()){
                    lstAsisten.add(single.getValue(Asisten.class));
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AsistenActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAll() {
        myrecyclerview = (RecyclerView) findViewById(R.id.asistenrecycle_id);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AsistenAdapter(lstAsisten,this);
        myrecyclerview.setAdapter(adapter);
    }
}
