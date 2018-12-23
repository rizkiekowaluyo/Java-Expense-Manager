package net.wicara.rizki.expenseapps;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AsistenAdapter extends RecyclerView.Adapter<AsistenAdapter.MyViewHolder>{
    Context mContext;
    private List<Asisten> lstAsisten;

    public AsistenAdapter(List<Asisten> lstAsisten,Context mContext) {
        this.mContext = mContext;
        this.lstAsisten = lstAsisten;
    }

    @NonNull
    @Override
    public AsistenAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.asisten_row,parent,false);
        return new AsistenAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AsistenAdapter.MyViewHolder holder, int position) {
        Asisten asisten = lstAsisten.get(position);
        holder.namaasisten.setText(asisten.getNama());
        holder.emailasisten.setText(asisten.getEmail());
        holder.nohpasisten.setText(asisten.getNohp());
        holder.alamatasisten.setText(asisten.getAlamat());

    }

    @Override
    public int getItemCount() {
        int arr = 0;

        try{
            if(lstAsisten.size()==0){

                arr = 0;

            }
            else{

                arr=lstAsisten.size();
            }



        }catch (Exception e){



        }

        return arr;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView namaasisten;
        private TextView emailasisten;
        private TextView nohpasisten;
        private TextView alamatasisten;


        public MyViewHolder(View itemView) {
            super(itemView);

            namaasisten = (TextView) itemView.findViewById(R.id.nama);
            emailasisten = (TextView) itemView.findViewById(R.id.email);
            nohpasisten = (TextView) itemView.findViewById(R.id.nohp);
            alamatasisten = (TextView) itemView.findViewById(R.id.alamat);


        }
    }
}
