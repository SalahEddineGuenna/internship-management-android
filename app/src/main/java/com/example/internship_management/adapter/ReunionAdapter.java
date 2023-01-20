package com.example.internship_management.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship_management.R;
import com.example.internship_management.model.ReunionDTO;
import com.example.internship_management.model.Student;

import java.util.List;

public class ReunionAdapter extends RecyclerView.Adapter<ReunionHolder>{

    private List<ReunionDTO> reunionList;
    private OnItemDeleteListener onItemDeleteListener;

    public ReunionAdapter(List<ReunionDTO> reunionList) {
        this.reunionList = reunionList;
    }

    @NonNull
    @Override
    public ReunionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_meeting, parent, false);
        return new ReunionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReunionHolder holder, int position) {
        ReunionDTO reunion = reunionList.get(position);
        holder.name.setText(reunion.getEtudiantDTO().getName() + " " + reunion.getEtudiantDTO().getLastName());
        holder.phone.setText(reunion.getProfesseurDTO().getName() + " " + reunion.getProfesseurDTO().getLastName());
        holder.niveau.setText(reunion.getDateReunion());


    }

    @Override
    public int getItemCount() {
        return reunionList.size();
    }
}