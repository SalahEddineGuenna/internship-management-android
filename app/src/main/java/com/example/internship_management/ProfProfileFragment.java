package com.example.internship_management;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.internship_management.model.ProfesseurDTO;
import com.example.internship_management.model.Student;
import com.example.internship_management.retrofit.ProfesseurApi;
import com.example.internship_management.retrofit.RetrofitService;
import com.example.internship_management.retrofit.StudentApi;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfProfileFragment extends Fragment {
    private TextInputEditText fname, lname, mail, phone, password;
    private Button update;
    Long id;
    ProfesseurDTO prof;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfProfileFragment newInstance(String param1, String param2) {
        ProfProfileFragment fragment = new ProfProfileFragment();
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
        View view =  inflater.inflate(R.layout.fragment_prof_profile, container, false);

        loadProf(id);

        fname = view.findViewById(R.id.etname);
        lname = view.findViewById(R.id.etlast);
        mail = view .findViewById(R.id.etMail);
        phone = view.findViewById(R.id.etphone);
        password = view .findViewById(R.id.etPassword1);

        update = view.findViewById(R.id.bupdate);

        update.setOnClickListener(view1 -> {
            updatProf(id);
        });

        return view;
    }

    private void updatProf(Long id) {
        RetrofitService retrofitService = new RetrofitService();
        ProfesseurApi professeurApi = retrofitService.getRetrofit().create(ProfesseurApi.class);
        prof.setEmail(mail.getText().toString());
        prof.setLastName(lname.getText().toString());
        prof.setName(fname.getText().toString());
        prof.setPhoneNumber(phone.getText().toString());
        prof.setPassword(password.getText().toString());

        professeurApi.update(id, prof)
                .enqueue(new Callback<ProfesseurDTO>() {
                    @Override
                    public void onResponse(Call<ProfesseurDTO> call, Response<ProfesseurDTO> response) {
                        Toast.makeText(getContext(), "Save successful!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<ProfesseurDTO> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadProf(Long id) {

        RetrofitService retrofitService = new RetrofitService();
        ProfesseurApi profApi = retrofitService.getRetrofit().create(ProfesseurApi.class);
        profApi.getById(id)
                .enqueue(new Callback<ProfesseurDTO>() {
                    @Override
                    public void onResponse(Call<ProfesseurDTO> call, Response<ProfesseurDTO> response) {
                        prof = response.body();
                        populateView(prof);
                    }

                    @Override
                    public void onFailure(Call<ProfesseurDTO> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void populateView(ProfesseurDTO prof) {
        fname.setText(prof.getName());
        lname.setText(prof.getLastName());
        mail.setText(prof.getEmail());
        phone.setText(prof.getPhoneNumber());
        password.setText(prof.getPassword());
    }
}