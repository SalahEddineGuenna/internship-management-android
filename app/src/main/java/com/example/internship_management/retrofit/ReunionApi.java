package com.example.internship_management.retrofit;

import com.example.internship_management.model.ReunionDTO;
import com.example.internship_management.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReunionApi {

    @GET("/reunions/")
    Call<List<ReunionDTO>> getReunionByID();

    @POST("/reunions/")
    Call<ReunionDTO> save(@Body ReunionDTO reunionDTO);

    @GET("/reunions/prof/{id}")
    Call<List<ReunionDTO>> getReunionByIProfId(@Path("id") Long id);

    @GET("/reunions/user/{id}")
    Call<List<ReunionDTO>> getReunionByIStudentId(@Path("id") Long id);
}
