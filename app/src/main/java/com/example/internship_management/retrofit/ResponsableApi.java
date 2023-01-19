package com.example.internship_management.retrofit;

import com.example.internship_management.model.ResponsableStageDTO;
import com.example.internship_management.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ResponsableApi {

    @GET("/responsables/")
    Call<List<ResponsableStageDTO>> getallRsponsables();

    @GET("/responsables/user/{username}")
    Call<ResponsableStageDTO> getByUsername(@Path("username") String username);

    @POST("/responsables/")
    Call<ResponsableStageDTO> save(@Body ResponsableStageDTO responsableStageDTO);

    @PUT("/responsables/")
    Call<ResponsableStageDTO> update(@Body ResponsableStageDTO responsableStageDTO);
}
