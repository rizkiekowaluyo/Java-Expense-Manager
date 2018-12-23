package net.wicara.rizki.expenseapps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ViewNewsActivity extends AppCompatActivity{
    News news;
    TextView judul,keterangan,sumber,kategori;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news);
        loadAllViews();
    }

    private void loadAllViews() {
        news =(News) getIntent().getExtras().get("news");
        if(news != null) {
            judul = (TextView) findViewById(R.id.judul);
            keterangan = (TextView) findViewById(R.id.keterangan);
            sumber = (TextView) findViewById(R.id.link);
            kategori = (TextView) findViewById(R.id.kategori);

            judul.setText(news.getJudul());
            keterangan.setText(news.getKeterangan());
            sumber.setText(news.getLink());
            kategori.setText(news.getKategori());
        }
    }
}
