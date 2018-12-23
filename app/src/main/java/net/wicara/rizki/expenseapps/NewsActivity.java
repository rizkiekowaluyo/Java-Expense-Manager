package net.wicara.rizki.expenseapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {



    Button readmore;
    private RecyclerView myrecyclerview;
    private List<News> lstNews = new ArrayList<>();
    private NewsAdapter adapter;
    DatabaseReference ref;
    public NewsActivity(){

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        loadAll();
        loadData();
    }


    private void loadData(){
        ref = FirebaseDatabase.getInstance().getReference().child("news");
        readmore = (Button) findViewById(R.id.readmore);
        myrecyclerview = (RecyclerView) findViewById(R.id.newsrecycle_id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstNews.clear();
                for (DataSnapshot single:dataSnapshot.getChildren()){
                    lstNews.add(single.getValue(News.class));
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(NewsActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadAll(){
        myrecyclerview = (RecyclerView) findViewById(R.id.newsrecycle_id);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsAdapter(lstNews,this);
        myrecyclerview.setAdapter(adapter);

        adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                News news = lstNews.get(position);
                Intent intent = new Intent(NewsActivity.this, ViewNewsActivity.class);
                intent.putExtra("news",news);
                startActivity(intent);
            }
        });
    }


//    @Override
//    public void onItemClick(int position) {
//        News news = lstNews.get(position);
//        Intent intent = new Intent(this, ViewNewsActivity.class);
//        intent.putExtra("news",news);
//        startActivity(intent);
//    }
}
