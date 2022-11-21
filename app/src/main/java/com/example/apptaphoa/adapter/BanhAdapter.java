package com.example.apptaphoa.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptaphoa.Interfacw.ItemClickListener;
import com.example.apptaphoa.R;
import com.example.apptaphoa.activity.ChitietActivity;
import com.example.apptaphoa.model.Sanphamnew;

import java.text.DecimalFormat;
import java.util.List;

public class BanhAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Sanphamnew> array;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public BanhAdapter(Context context, List<Sanphamnew> array) {
        this.context = context;
        this.array = array;
    }


    public class LoadingViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbarload);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==VIEW_TYPE_DATA){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banh, parent, false);
            return new MyViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return  new LoadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
            MyViewHolder myViewHolder= (MyViewHolder) holder;
            Sanphamnew sanpham = array.get(position);
            myViewHolder.tensp.setText(sanpham.getTensp().trim());
            DecimalFormat decimalFormat = new DecimalFormat("###,###");
            myViewHolder.giasp.setText("Giá: "+decimalFormat.format(Double.parseDouble(sanpham.getGiasp())) +" VNĐ");
            myViewHolder.mota.setText(sanpham.getMota());
            //myViewHolder.idsp.setText(sanpham.getId() + "");
            myViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int pod, boolean isLongClick) {
                    if(!isLongClick){
                        Intent intent = new Intent(context, ChitietActivity.class);
                        intent.putExtra("chitiet", sanpham);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
            Glide.with(context).load(sanpham.getHinhanh()).into(myViewHolder.imgbanh);

        }else {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tensp, giasp, mota, idsp;
        ImageView imgbanh;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.itembanhten);
            giasp = itemView.findViewById(R.id.itembanhgia);
            mota = itemView.findViewById(R.id.itembanhmota);
            //idsp = itemView.findViewById(R.id.itembanhid);
            imgbanh = itemView.findViewById(R.id.itemimgbanh);
            itemView.setOnClickListener(this);

        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);

        }
    }
}
