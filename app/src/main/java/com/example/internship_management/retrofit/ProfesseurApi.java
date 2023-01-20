package com.example.internship_management.retrofit;

import com.example.internship_management.model.EtablissementDTO;
import com.example.internship_management.model.ProfesseurDTO;
import com.example.internship_management.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProfesseurApi {

    @GET("/professeurs/")
    Call<List<ProfesseurDTO>> getAllprofesseurs();
    @GET("/professeurs/{id}")
    Call<ProfesseurDTO> getById(@Path("id") Long id);

    @GET("/professeurs/user/{username}")
    Call<ProfesseurDTO> getByUsername(@Path("username") String username);

    @POST("/professeurs/")
    Call<ProfesseurDTO> save(@Body ProfesseurDTO professeurDTO);

    @PUT("/professeurs/{id}")
    Call<ProfesseurDTO> update(@Path("id") Long id,@Body ProfesseurDTO professeurDTO);
}
