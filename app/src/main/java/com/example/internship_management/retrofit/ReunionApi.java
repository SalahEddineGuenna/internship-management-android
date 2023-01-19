package com.example.internship_management.retrofit;

import com.example.internship_management.model.ReunionDTO;
import com.example.internship_management.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ReunionApi {

    @GET("/reunions/")
    Call<List<ReunionDTO>> getReunionByID();

    @POST("/reunions/")
    Call<ReunionDTO> save(@Body ReunionDTO reunionDTO);

    @GET("/reunions/prof/{id}")
    Call<List<ReunionDTO>> getReunionByIProfId(Long id);
}
