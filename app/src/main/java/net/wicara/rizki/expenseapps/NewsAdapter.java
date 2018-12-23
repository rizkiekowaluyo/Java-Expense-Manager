package net.wicara.rizki.expenseapps;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    Context mContext;
    private List<News> lstNews;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;

    }
    public NewsAdapter(List<News> lstNews,Context mContext) {
        this.mContext = mContext;
        this.lstNews = lstNews;
    }

    @NonNull
    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.news_row,parent,false);
        return new NewsAdapter.MyViewHolder(v,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.MyViewHolder holder, int position) {
        News news = lstNews.get(position);
        holder.tv_judul.setText(news.getJudul());
        holder.tv_keterangan.setText(news.getKeterangan());
    }

    @Override
    public int getItemCount() {
        int arr = 0;

        try{
            if(lstNews.size()==0){

                arr = 0;

            }
            else{

                arr=lstNews.size();
            }



        }catch (Exception e){



        }

        return arr;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_judul;
        private TextView tv_keterangan;


        public MyViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            tv_judul = (TextView) itemView.findViewById(R.id.post_title);
            tv_keterangan = (TextView) itemView.findViewById(R.id.post_ket);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
