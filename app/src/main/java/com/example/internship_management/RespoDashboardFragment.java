
package com.example.internship_management;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.internship_management.adapter.StudentAdapter;
import com.example.internship_management.model.ResponsableStageDTO;
import com.example.internship_management.model.Student;
import com.example.internship_management.retrofit.ResponsableApi;
import com.example.internship_management.retrofit.RetrofitService;
import com.example.internship_management.retrofit.StudentApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RespoDashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RespoDashboardFragment extends Fragment  implements StudentAdapter.OnButtonClickListener{

    private RecyclerView recyclerView;
    private FloatingActionButton add;
    Bundle bundle = new Bundle();
    Long id;
    ResponsableStageDTO respo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RespoDashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RespoDashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RespoDashboardFragment newInstance(String param1, String param2) {
        RespoDashboardFragment fragment = new RespoDashboardFragment();
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
        View view = inflater.inflate(R.layout.fragment_respo_dashboard, container, false);

        recyclerView = view.findViewById(R.id.studentList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RetrofitService retrofitService = new RetrofitService();
        ResponsableApi responsableApi = retrofitService.getRetrofit().create(ResponsableApi.class);
        responsableApi.getById(id)
                .enqueue(new Callback<ResponsableStageDTO>() {
                    @Override
                    public void onResponse(Call<ResponsableStageDTO> call, Response<ResponsableStageDTO> response) {
                        respo = response.body();
                        loadStudents();
                    }

                    @Override
                    public void onFailure(Call<ResponsableStageDTO> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        add = view.findViewById(R.id.employeeList_fab);

        add.setOnClickListener(View -> {
            bundle.putLong("id", id);
            Fragment fragment = new RespoAddStudentFragment();
            fragment.setArguments(bundle);
            loadFragment(fragment);

        });

        return view;
    }


    private void loadStudents() {
        RetrofitService retrofitService = new RetrofitService();
        StudentApi studentApi = retrofitService.getRetrofit().create(StudentApi.class);
        studentApi.getAllStudents()
                .enqueue(new Callback<List<Student>>() {
                    @Override
                    public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                        populateView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Student>> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateView(List<Student> body) {
        List<Student> students = new ArrayList<>();
        for(Student s: body){
            if(s.getEtablissement() != null) {
                if (s.getEtablissement().getId() == respo.getEtablissementDTOS().getId()) {
                    students.add(s);
                }
            }
        }
        StudentAdapter studentAdapter = new StudentAdapter(students, this);
        recyclerView.setAdapter(studentAdapter);
    }

    void loadFragment(Fragment fragment) {
        //to attach fragment
        getFragmentManager().beginTransaction().replace(R.id.frag, fragment).commit();
    }


    @Override
    public void onButtonClick(Long id) {
        Fragment fragment = null;
        bundle.putLong("id", id);
        fragment = new AffecterFragment();
        fragment.setArguments(bundle);
        if (fragment != null){
            loadFragment(fragment);
        }
    }
}