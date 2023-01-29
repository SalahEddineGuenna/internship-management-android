package com.example.internship_management;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.internship_management.adapter.EtabAdapter;
import com.example.internship_management.adapter.OnItemDeleteListener;
import com.example.internship_management.model.EtablissementDTO;
import com.example.internship_management.retrofit.EtablissementApi;
import com.example.internship_management.retrofit.RetrofitService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EtabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EtabFragment extends Fragment implements EtabAdapter.OnButtonClickListener{

    private RecyclerView recyclerView;
    private FloatingActionButton add;

    Bundle bundle = new Bundle();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EtabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment etabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EtabFragment newInstance(String param1, String param2) {
        EtabFragment fragment = new EtabFragment();
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
        View view = inflater.inflate(R.layout.fragment_etab, container, false);

        recyclerView = view.findViewById(R.id.etab_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        add = view.findViewById(R.id.employeeList_fab);

        add.setOnClickListener(View -> {
            Fragment fragment = new AddEtabFragment();
            loadFragment(fragment);

        });

        return view;
    }

    void loadFragment(Fragment fragment) {
        //to attach fragment
        getFragmentManager().beginTransaction().replace(R.id.frag, fragment).commit();
    }



    private void loadEtab() {
        RetrofitService retrofitService = new RetrofitService();
        EtablissementApi etablissementApi = retrofitService.getRetrofit().create(EtablissementApi.class);
        etablissementApi.getAlletablissments()
                .enqueue(new Callback<List<EtablissementDTO>>() {
                    @Override
                    public void onResponse(Call<List<EtablissementDTO>> call, Response<List<EtablissementDTO>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<EtablissementDTO>> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateListView(List<EtablissementDTO> etabList) {
        EtabAdapter etabAdapter = new EtabAdapter(etabList, this);
        recyclerView.setAdapter(etabAdapter);
        etabAdapter.setItemDeleteListener(new OnItemDeleteListener() {
            @Override
            public void onItemDelete(int position) {
                EtablissementDTO  item = etabList.get(position);
                Long id = item.getId();
                RetrofitService retrofitService = new RetrofitService();
                EtablissementApi studentApi = retrofitService.getRetrofit().create(EtablissementApi.class);
                studentApi.delete(id)
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()){
                                    etabList.remove(position);
                                    etabAdapter.notifyItemRemoved(position);
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadEtab();
    }

    @Override
    public void onButtonClick(Long id) {
        Fragment fragment = null;
        bundle.putLong("id", id);
        fragment = new EtabEditFragment();
        fragment.setArguments(bundle);
        if (fragment != null){
            loadFragment(fragment);
        }
    }
}