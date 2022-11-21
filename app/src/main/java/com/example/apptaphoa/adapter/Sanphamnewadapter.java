package com.example.apptaphoa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptaphoa.R;
import com.example.apptaphoa.model.Sanphamnew;

import java.text.DecimalFormat;
import java.util.List;

public class Sanphamnewadapter extends RecyclerView.Adapter<Sanphamnewadapter.MyViewHolder> {

    Context context;
    List<Sanphamnew> array;

    public Sanphamnewadapter(Context context, List<Sanphamnew> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spmoi, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Sanphamnew sanphamnew = array.get(position);
        holder.txttensp.setText(sanphamnew.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        holder.txtgiasp.setText("Giá: "+decimalFormat.format(Double.parseDouble(sanphamnew.getGiasp())) +" VNĐ");
        Glide.with(context).load(sanphamnew.getHinhanh()).into(holder.imghinhanh);

    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txttensp, txtgiasp;
        ImageView imghinhanh;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtgiasp = itemView.findViewById(R.id.textviewgiasanpham);
            txttensp = itemView.findViewById(R.id.textviewtensanpham);
            imghinhanh = itemView.findViewById(R.id.imgsanpham);

        }
    }
}
