package com.example.internship_management.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship_management.R;

public class RespoHolder extends RecyclerView.ViewHolder {

    TextView name, etab, niveau;

    public RespoHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.employeeListItem_name);
        etab = itemView.findViewById(R.id.encad);
    }
}

