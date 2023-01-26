package com.example.internship_management.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship_management.R;

public class EtabHolder extends RecyclerView.ViewHolder {

    TextView name, respo;
    Button affecter;

    public EtabHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.employeeListItem_name);
        respo = itemView.findViewById(R.id.encad);
        affecter = itemView.findViewById(R.id.affecter);
    }
}
