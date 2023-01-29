package com.example.internship_management.retrofit;

import com.example.internship_management.model.EtablissementDTO;
import com.example.internship_management.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EtablissementApi {

    @GET("/etablissements/")
    Call<List<EtablissementDTO>> getAlletablissments();

    @POST("/etablissements/")
    Call<EtablissementDTO> save(@Body EtablissementDTO etablissementDTO);

    @PUT("/etablissements/{id}")
    Call<EtablissementDTO> update(@Path("id")Long id, @Body EtablissementDTO etablissementDTO);

    @DELETE("/etablissements/{id}")
    Call<Void> delete(@Path("id") Long id);

    @GET("/etablissements/{id}")
    Call<EtablissementDTO> getById(@Path("id") Long id);
}
