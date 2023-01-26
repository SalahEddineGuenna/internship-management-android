package com.example.internship_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internship_management.enums.Role;
import com.example.internship_management.model.ProfesseurDTO;
import com.example.internship_management.model.ResponsableStageDTO;
import com.example.internship_management.model.Student;
import com.example.internship_management.retrofit.ProfesseurApi;
import com.example.internship_management.retrofit.ResponsableApi;
import com.example.internship_management.retrofit.RetrofitService;
import com.example.internship_management.retrofit.StudentApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLogin extends AppCompatActivity {

    AutoCompleteTextView spinner;
    Button register, login;
    TextView username, password;
    String user, pass;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        spinner = findViewById(R.id.spinner);
        login = findViewById(R.id.bLogin);
        username = findViewById(R.id.etuser);
        password = findViewById(R.id.etPassword);



        ArrayList<String> names = new ArrayList<>();
        names.add(Role.ETUDIANT.toString());
        names.add(Role.PROFFESSEUR.toString());
        names.add(Role.RESPONSABLE.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
       /* spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle the item selected event
                String item = parent.getItemAtPosition(position).toString();
                if(item == "PROFFESSEUR"){
                    register.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle when nothing is selected
            }
        }); */

        login.setOnClickListener(view -> {
            role = spinner.getText().toString();
            user = username.getText().toString();
            pass = password.getText().toString();
            if(user.equals("admin") & pass.equals("1234")) {
                Intent intent = new Intent(ActivityLogin.this, ActivityHome.class);
                startActivity(intent);
            }else {
                Toast.makeText(this, "You are not admin", Toast.LENGTH_SHORT).show();
                switch (role) {
                    case "ETUDIANT":
                        loadStudent(user, pass);
                        break;
                    case "PROFFESSEUR":
                        loadProf(user, pass);
                        break;
                    case "RESPONSABLE":
                        loadRespo(user, pass);
                        break;

                }
            }
        });
    }

    private void loadStudent(String user, String pass) {
        RetrofitService retrofitService = new RetrofitService();
        StudentApi studentApi = retrofitService.getRetrofit().create(StudentApi.class);
        studentApi.getByUsername(user)
                .enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        if(checkStudent(pass, response.body())) {
                            Intent intent = new Intent(ActivityLogin.this, StudentHomeActivity.class);
                            intent.putExtra("id", response.body().getId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(ActivityLogin.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
                        Toast.makeText(ActivityLogin.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean checkStudent(String pass, Student student){

        if(student != null) {
            if (pass.equals(student.getPassword())) {
                return true;
            }
        }
        return false;

    }

    private void loadProf(String user, String pass) {
        RetrofitService retrofitService = new RetrofitService();
        ProfesseurApi professeurApi = retrofitService.getRetrofit().create(ProfesseurApi.class);
        professeurApi.getByUsername(user)
                .enqueue(new Callback<ProfesseurDTO>() {
                    @Override
                    public void onResponse(Call<ProfesseurDTO> call, Response<ProfesseurDTO> response) {
                        if(checkProf(pass, response.body())) {
                            Intent intent = new Intent(ActivityLogin.this, ProfHomeActivity.class);
                            intent.putExtra("id", response.body().getId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(ActivityLogin.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProfesseurDTO> call, Throwable t) {
                        Toast.makeText(ActivityLogin.this, "User does not exist!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean checkProf(String pass, ProfesseurDTO prof){

        if(prof != null) {
            if (pass.equals(prof.getPassword())) {
                return true;
            }
        } else {
            Toast.makeText(ActivityLogin.this, "User does not exist!", Toast.LENGTH_SHORT).show();
        }
        return false;

    }

    private void loadRespo(String user, String pass) {
        RetrofitService retrofitService = new RetrofitService();
        ResponsableApi responsableApi = retrofitService.getRetrofit().create(ResponsableApi.class);
        responsableApi.getByUsername(user)
                .enqueue(new Callback<ResponsableStageDTO>() {
                    @Override
                    public void onResponse(Call<ResponsableStageDTO> call, Response<ResponsableStageDTO> response) {
                        if(checkRespo(pass, response.body())) {
                            Intent intent = new Intent(ActivityLogin.this, RespoHomeActivity.class);
                            intent.putExtra("id", response.body().getId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(ActivityLogin.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsableStageDTO> call, Throwable t) {
                        Toast.makeText(ActivityLogin.this, "User does not exist!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean checkRespo(String pass, ResponsableStageDTO respo){

        if(respo != null) {
            if (pass.equals(respo.getPassword())) {
                return true;
            }
        } else {
            Toast.makeText(ActivityLogin.this, "User does not exist!", Toast.LENGTH_SHORT).show();
        }
        return false;

    }

    void loadFragment(Fragment fragment) {
        //to attach fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragment).commit();
    }
}