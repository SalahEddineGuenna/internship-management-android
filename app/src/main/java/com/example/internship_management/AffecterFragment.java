package com.example.internship_management;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.internship_management.model.ProfesseurDTO;
import com.example.internship_management.model.Student;
import com.example.internship_management.retrofit.ProfesseurApi;
import com.example.internship_management.retrofit.RetrofitService;
import com.example.internship_management.retrofit.StudentApi;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AffecterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AffecterFragment extends Fragment {

    private Long id;
    private Student student;
    private ProfesseurDTO prof;
    private TextInputEditText stud;
    private Button confirm;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AffecterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AffecterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AffecterFragment newInstance(String param1, String param2) {
        AffecterFragment fragment = new AffecterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_affecter, container, false);
        stud = view.findViewById(R.id.etetud);
        final Spinner profes = (Spinner) view.findViewById(R.id.spinner1);
        confirm = view.findViewById(R.id.bconfirm);

        RetrofitService retrofitServicerespo = new RetrofitService();
        ProfesseurApi professeurApi = retrofitServicerespo.getRetrofit().create(ProfesseurApi.class);
        professeurApi.getAllprofesseurs()
                .enqueue(new Callback<List<ProfesseurDTO>>() {
                    @Override
                    public void onResponse(Call<List<ProfesseurDTO>> call, Response<List<ProfesseurDTO>> response) {
                        if (response.isSuccessful()) {
                            List<ProfesseurDTO> data = response.body();
                            ArrayList<String> names = new ArrayList<>();
                            for(ProfesseurDTO item: data){
                                names.add(item.getName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, names);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            profes.setAdapter(adapter);
                            profes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    // Get the selected item information here
                                    prof = data.get(position);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                        } else {
                            // handle error case
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProfesseurDTO>> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });


        confirm.setOnClickListener(view1 -> {
            updateStudent(student);
        });


        loadStudent(id);
        return view;
    }

    private void updateStudent(Student student) {
        RetrofitService retrofitService = new RetrofitService();
        StudentApi studentApi = retrofitService.getRetrofit().create(StudentApi.class);
        student.setEncadrant(prof);
        studentApi.update(id, student)
                .enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        Toast.makeText(getContext(), response.body().toString(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void loadStudent(Long id) {
        RetrofitService retrofitService = new RetrofitService();
        StudentApi studentApi = retrofitService.getRetrofit().create(StudentApi.class);
        studentApi.getById(id)
                .enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        student = response.body();
                        populateView(student);
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void populateView(Student student) {
        stud.setText(student.getName() + " " + student.getLastName());
    }
}