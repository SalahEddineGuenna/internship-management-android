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
import com.example.internship_management.model.ResponsableStageDTO;
import com.example.internship_management.model.ReunionDTO;
import com.example.internship_management.model.Student;
import com.example.internship_management.retrofit.ResponsableApi;
import com.example.internship_management.retrofit.RetrofitService;
import com.example.internship_management.retrofit.ReunionApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RespoMeetingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RespoMeetingsFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton add;

    private Long id;

    Bundle bundle = new Bundle();

    private ResponsableStageDTO responsable;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RespoMeetingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RespoMeetingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RespoMeetingsFragment newInstance(String param1, String param2) {
        RespoMeetingsFragment fragment = new RespoMeetingsFragment();
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
        View view =  inflater.inflate(R.layout.fragment_respo_meetings, container, false);
        recyclerView = view.findViewById(R.id.meetings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        add = view.findViewById(R.id.meeting_fab);

        loadRespo(id);

        loadMeetings();

        add.setOnClickListener(View -> {
            bundle.putLong("id", id);
            Fragment fragment = new MeetingsFragment();
            fragment.setArguments(bundle);
            loadFragment(fragment);

        });
        return view;
    }

    private void loadMeetings() {
        RetrofitService retrofitService = new RetrofitService();
        ReunionApi reunionApi = retrofitService.getRetrofit().create(ReunionApi.class);
        reunionApi.getReunionByID()
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

    private void loadRespo(Long id){
        RetrofitService retrofitService = new RetrofitService();
        ResponsableApi responsableApi = retrofitService.getRetrofit().create(ResponsableApi.class);
        responsableApi.getById(id)
                .enqueue(new Callback<ResponsableStageDTO>() {
                    @Override
                    public void onResponse(Call<ResponsableStageDTO> call, Response<ResponsableStageDTO> response) {
                        responsable = response.body();
                    }

                    @Override
                    public void onFailure(Call<ResponsableStageDTO> call, Throwable t) {

                    }
                });
    }

    private void populateListView(List<ReunionDTO> reunionList) {
        List<ReunionDTO> reunionDTOS = new ArrayList<>();
        for(ReunionDTO r: reunionList){
            if(r.getEtudiantDTO() != null & r.getEtudiantDTO().getEtablissement() != null && responsable != null) {
                if (r.getEtudiantDTO().getEtablissement().getId() == responsable.getEtablissementDTOS().getId()) {
                    reunionDTOS.add(r);
                }
            }
        }
        for(int i = 0; i < reunionDTOS.size()/2; i++)
        {
            ReunionDTO temp = reunionDTOS.get(i);
            reunionDTOS.set(i, reunionDTOS.get(reunionDTOS.size()-i-1));
            reunionDTOS.set(reunionDTOS.size()-i-1, temp);
        }
        ReunionAdapter reunionAdapter = new ReunionAdapter(reunionDTOS);
        recyclerView.setAdapter(reunionAdapter);
    }

    void loadFragment(Fragment fragment) {
        //to attach fragment
        getFragmentManager().beginTransaction().replace(R.id.frag, fragment).commit();
    }
}