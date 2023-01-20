package com.example.internship_management.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship_management.R;

public class ReunionHolder extends RecyclerView.ViewHolder{
    TextView name, phone, niveau;
    public ReunionHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.student);
        phone = itemView.findViewById(R.id.prof);
        niveau = itemView.findViewById(R.id.time);
    }
}
