package com.example.internship_management;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.internship_management.adapter.ReunionAdapter;
import com.example.internship_management.model.ReunionDTO;
import com.example.internship_management.retrofit.RetrofitService;
import com.example.internship_management.retrofit.ReunionApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfMeetingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfMeetingFragment extends Fragment {

    Long id;
    private RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfMeetingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfMeetingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfMeetingFragment newInstance(String param1, String param2) {
        ProfMeetingFragment fragment = new ProfMeetingFragment();
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
        View view =  inflater.inflate(R.layout.fragment_prof_meeting, container, false);

        recyclerView = view.findViewById(R.id.meetings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadEmployees(id);

        return view;
    }

    private void loadEmployees(Long id) {
        RetrofitService retrofitService = new RetrofitService();
        ReunionApi reunionApi = retrofitService.getRetrofit().create(ReunionApi.class);
        reunionApi.getReunionByIProfId(id)
                .enqueue(new Callback<List<ReunionDTO>>() {
                    @Override
                    public void onResponse(Call<List<ReunionDTO>> call, Response<List<ReunionDTO>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<ReunionDTO>> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void populateListView(List<ReunionDTO> reunionList) {
        for(int i = 0; i < reunionList.size()/2; i++)
        {
            ReunionDTO temp = reunionList.get(i);
            reunionList.set(i, reunionList.get(reunionList.size()-i-1));
            reunionList.set(reunionList.size()-i-1, temp);
        }
        ReunionAdapter reunionAdapter = new ReunionAdapter(reunionList);
        recyclerView.setAdapter(reunionAdapter);
    }
}