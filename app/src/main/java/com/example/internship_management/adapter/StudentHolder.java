package com.example.internship_management.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship_management.R;


public class StudentHolder extends RecyclerView.ViewHolder {

    TextView name, phone, niveau, prof, num, etab;
    Button affecter;
    View view;
    Long id;

    public StudentHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.employeeListItem_name);
        phone = itemView.findViewById(R.id.employeeListItem_location);
        niveau = itemView.findViewById(R.id.employeeListItem_branch);
        prof = itemView.findViewById(R.id.encad);
        affecter = itemView.findViewById(R.id.affecter);
        num = itemView.findViewById(R.id.num);
        etab = itemView.findViewById(R.id.etab);
    }
}
