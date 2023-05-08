package com.example.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList id, fName, lName;

    CustomAdapter(Context context, ArrayList id, ArrayList fName, ArrayList lName) {
        this.context = context;
        this.id = id;
        this.fName = fName;
        this.lName = lName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id.setText(String.valueOf(id.get(position)));
        holder.fName.setText(String.valueOf(fName.get(position)));
        holder.lName.setText(String.valueOf(lName.get(position)));
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView id, fName, lName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.person_id);
            fName = itemView.findViewById(R.id.first_name);
            lName = itemView.findViewById(R.id.last_name);
        }
    }
}