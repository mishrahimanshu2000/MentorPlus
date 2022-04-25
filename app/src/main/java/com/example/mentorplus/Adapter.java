package com.example.mentorplus;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    ArrayList<Dish> list;

    public Adapter(Context context, ArrayList<Dish> list) {
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dishName.setText(list.get(position).getDish_name());
        holder.custom.setText(list.get(position).getCustomisation().get(1));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, list.get(position).getCustomisation());
        holder.catSpinner.setAdapter(adapter);
        holder.price.setText(list.get(position).getPrice());
        holder.elegantNumberButton.setOnValueChangeListener((view, oldValue, newValue) -> {
            if(newValue<1) holder.itemView.setVisibility(View.GONE);
            setPrice(holder.getAdapterPosition(), newValue, newValue > oldValue);
        });



    }
    private void setPrice(int position, int value, boolean op){
        String price = list.get(position).getPrice().toString();
        String totalPrice = String.valueOf(value*Integer.parseInt(price)).toString();
        Intent intent = new Intent("message");
        intent.putExtra("price", list.get(position).getPrice().toString());
        intent.putExtra("operation", op);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dishName;
        TextView custom;
        Spinner catSpinner;
        TextView price;
        ElegantNumberButton elegantNumberButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.dishName);
            custom = itemView.findViewById(R.id.custom);
            catSpinner = itemView.findViewById(R.id.catSpinner);
            price = itemView.findViewById(R.id.price);
            elegantNumberButton = itemView.findViewById(R.id.eleBtn);
        }
    }
}
