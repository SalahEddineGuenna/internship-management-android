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

    @GET("/etudiants/")
    Call<List<Student>> getAllStudents();

    @GET("/etudiants/user/{username}")
    Call<Student> getByUsername(@Path("username") String username);

    @POST("/etudiants/")
    Call<Student> save(@Body Student student);

    @PUT("/etudiants/")
    Call<Student> update(@Body Student student);

    @DELETE("/etudiants/{id}")
    Call<Void> delete(@Path("id") Long id);
}
