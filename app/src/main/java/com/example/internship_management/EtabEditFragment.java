package com.example.internship_management;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.internship_management.model.EtablissementDTO;
import com.example.internship_management.model.Student;
import com.example.internship_management.retrofit.EtablissementApi;
import com.example.internship_management.retrofit.RetrofitService;
import com.example.internship_management.retrofit.StudentApi;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EtabEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EtabEditFragment extends Fragment {

    private TextInputEditText fname, address, mail;
    private Button update;
    Long id;
    EtablissementDTO etablissement;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EtabEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EtabEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EtabEditFragment newInstance(String param1, String param2) {
        EtabEditFragment fragment = new EtabEditFragment();
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
        View view = inflater.inflate(R.layout.fragment_etab_edit, container, false);

        loadStudent(id);

        fname = view.findViewById(R.id.etname);
        mail = view.findViewById(R.id.etmail);
        address = view.findViewById(R.id.etadd);
        update = view.findViewById(R.id.bupdate);

        update.setOnClickListener(view1 -> {
            updateStudent(id);
        });

        return view;
    }

    private void loadStudent(Long id) {
        RetrofitService retrofitService = new RetrofitService();
        EtablissementApi etablissementApi = retrofitService.getRetrofit().create(EtablissementApi.class);
        etablissementApi.getById(id)
                .enqueue(new Callback<EtablissementDTO>() {
                    @Override
                    public void onResponse(Call<EtablissementDTO> call, Response<EtablissementDTO> response) {
                        etablissement = response.body();
                        populateView(etablissement);
                    }

                    @Override
                    public void onFailure(Call<EtablissementDTO> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void populateView(EtablissementDTO etablissement) {
        fname.setText(etablissement.getName());
        mail.setText(etablissement.getMail());
        address.setText(etablissement.getAdress());

    }

    private void updateStudent(Long id) {
        RetrofitService retrofitService = new RetrofitService();
        EtablissementApi studentApi = retrofitService.getRetrofit().create(EtablissementApi.class);
        etablissement.setMail(mail.getText().toString());
        etablissement.setName(fname.getText().toString());
        etablissement.setAdress(address.getText().toString());

        studentApi.update(id, etablissement)
                .enqueue(new Callback<EtablissementDTO>() {
                    @Override
                    public void onResponse(Call<EtablissementDTO> call, Response<EtablissementDTO> response) {
                        Toast.makeText(getContext(), "Save successful!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<EtablissementDTO> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}