package com.example.internship_management.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship_management.R;
import com.example.internship_management.model.Student;

import java.util.List;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantHolder>{

    private List<Student> studentList;

    public EtudiantAdapter(List<Student> studentList) {
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public EtudiantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_etudiant, parent, false);
        return new EtudiantHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EtudiantHolder holder, int position) {
        Student student = studentList.get(position);
        holder.name.setText(student.getName());
        holder.phone.setText(student.getPhoneNumber());
        holder.niveau.setText(student.getNiveau());

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}