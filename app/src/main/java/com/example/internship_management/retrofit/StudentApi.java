package com.example.internship_management.retrofit;

import android.telephony.SignalStrengthUpdateRequest;

import com.example.internship_management.model.Student;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StudentApi {

    @GET("/etudiants/all")
    Call<List<Student>> getAllStudents();

    @GET("/etudiants/{id}")
    Call<Student> getById(@Path("id") Long id);

    @GET("/etudiants/user/{username}")
    Call<Student> getByUsername(@Path("username") String username);

    @GET("/etudiants/prof/{id}")
    Call<List<Student>> getByProf(@Path("id") Long id);

    @POST("/etudiants/add/")
    Call<Student> save(@Body Student student);

    @PUT("/etudiants/{id}")
    Call<Student> update(@Path("id") Long id, @Body Student student);

    @DELETE("/etudiants/{id}")
    Call<Void> delete(@Path("id") Long id);
}
