package com.example.internship_management.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship_management.R;

public class EtudiantHolder extends RecyclerView.ViewHolder {

    TextView name, phone, niveau;

    public EtudiantHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.employeeListItem_name);
        phone = itemView.findViewById(R.id.employeeListItem_location);
        niveau = itemView.findViewById(R.id.employeeListItem_branch);
    }
}