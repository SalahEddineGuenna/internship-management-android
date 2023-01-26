package com.example.internship_management.adapter;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship_management.ActivityHome;
import com.example.internship_management.DashbordFragment;
import com.example.internship_management.R;
import com.example.internship_management.model.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentHolder>{

    private List<Student> studentList;
    private OnItemDeleteListener onItemDeleteListener;
    private OnButtonClickListener clickListener;

    public StudentAdapter(List<Student> studentList, OnButtonClickListener clickListener) {
        this.studentList = studentList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_list, parent, false);
        return new StudentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder holder, int position) {
        Student student = studentList.get(position);
        holder.id = student.getId();
        holder.name.setText(student.getName() + " " + student.getLastName());
        holder.phone.setText(student.getNiveau());
        holder.num.setText(student.getPhoneNumber());
        holder.etab.setVisibility(View.GONE);
        if(student.getEtablissement() != null) {
            holder.etab.setVisibility(View.VISIBLE);
            holder.etab.setText(student.getEtablissement().getName());
        }
        //holder.niveau.setText(student.getNiveau());
        if(student.getEncadrant() == null){
            holder.prof.setVisibility(View.GONE);
            holder.affecter.setVisibility(View.VISIBLE);
            holder.affecter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onButtonClick(student.getId());
                }
            });
        } else {
            holder.prof.setVisibility(View.VISIBLE);
            holder.affecter.setVisibility(View.GONE);
            //ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.view.getLayoutParams();
            //params.topToBottom = R.id.encad; // sets the left to right constraint to the id of other_view
            //holder.view.setLayoutParams(params);
            holder.prof.setText(student.getEncadrant().getName() + " " + student.getEncadrant().getLastName());
        }
    }
    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public interface OnButtonClickListener {
        void onButtonClick(Long id);
    }
}
