package com.example.internship_management;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.internship_management.adapter.RespoAdapter;
import com.example.internship_management.adapter.StudentAdapter;
import com.example.internship_management.model.ResponsableStageDTO;
import com.example.internship_management.model.Student;
import com.example.internship_management.retrofit.ResponsableApi;
import com.example.internship_management.retrofit.RetrofitService;
import com.example.internship_management.retrofit.StudentApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RespoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RespoFragment extends Fragment {


    private RecyclerView recyclerView;
    private FloatingActionButton add;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RespoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment respoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RespoFragment newInstance(String param1, String param2) {
        RespoFragment fragment = new RespoFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_respo, container, false);

        recyclerView = view.findViewById(R.id.respo_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        add = view.findViewById(R.id.employeeList_fab);

        add.setOnClickListener(View -> {
            Fragment fragment = new AddRespoFragment();
            loadFragment(fragment);

        });

        return view;
    }

    private void loadEmployees() {
        RetrofitService retrofitService = new RetrofitService();
        ResponsableApi responsableApi = retrofitService.getRetrofit().create(ResponsableApi.class);
        responsableApi.getallRsponsables()
                .enqueue(new Callback<List<ResponsableStageDTO>>() {
                    @Override
                    public void onResponse(Call<List<ResponsableStageDTO>> call, Response<List<ResponsableStageDTO>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<ResponsableStageDTO>> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void populateListView(List<ResponsableStageDTO> responsableStageDTOList) {
        RespoAdapter respoAdapter = new RespoAdapter(responsableStageDTOList);
        recyclerView.setAdapter(respoAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadEmployees();
    }

    void loadFragment(Fragment fragment) {
        //to attach fragment
        getFragmentManager().beginTransaction().replace(R.id.frag, fragment).commit();
    }
}