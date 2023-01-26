package com.example.internship_management.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship_management.R;
import com.example.internship_management.model.ResponsableStageDTO;
import com.example.internship_management.model.Student;

import java.util.List;

public class RespoAdapter extends RecyclerView.Adapter<RespoHolder>{

    private List<ResponsableStageDTO> responsableStageDTOList;

    public RespoAdapter(List<ResponsableStageDTO> responsableStageDTOList) {
        this.responsableStageDTOList = responsableStageDTOList;
    }

    @NonNull
    @Override
    public RespoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_respo, parent, false);
        return new RespoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RespoHolder holder, int position) {
        ResponsableStageDTO student = responsableStageDTOList.get(position);
        holder.name.setText(student.getName() + " " + student.getLastName());
        if(student.getEtablissementDTOS() != null){
            holder.etab.setText(student.getEtablissementDTOS().getName());
        }

        //holder.niveau.setText(student.getEtablissementDTOS().getName());
    }

    @Override
    public int getItemCount() {
        return responsableStageDTOList.size();
    }
}