package com.example.internship_management.retrofit;

import com.example.internship_management.model.EtablissementDTO;
import com.example.internship_management.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface EtablissementApi {

    @GET("/etablissements/")
    Call<List<EtablissementDTO>> getAlletablissments();

    @POST("/etablissements/")
    Call<EtablissementDTO> save(@Body EtablissementDTO etablissementDTO);

    @PUT("/etablissements/")
    Call<EtablissementDTO> update(@Body EtablissementDTO etablissementDTO);
}
