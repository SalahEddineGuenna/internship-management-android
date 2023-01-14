package com.example.internship_management.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship_management.R;
import com.example.internship_management.model.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentHolder>{

    private List<Student> studentList;

    public StudentAdapter(List<Student> studentList) {
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_student_item, parent, false);
        return new StudentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder holder, int position) {
        Student employee = studentList.get(position);
        holder.name.setText(employee.getName());
        holder.phone.setText(employee.getPhoneNumber());
        holder.niveau.setText(employee.getNiveau());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}