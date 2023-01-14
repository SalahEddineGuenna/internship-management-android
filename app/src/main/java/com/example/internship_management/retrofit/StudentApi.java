package com.example.internship_management.retrofit;

import com.example.internship_management.model.Student;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.Call;

public interface StudentApi {

    @GET("/etudiants")
    Call<List<Student>> getAllStudents();
}
