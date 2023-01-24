package com.example.internship_management;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.internship_management.model.ResponsableStageDTO;
import com.example.internship_management.model.Student;
import com.example.internship_management.retrofit.ResponsableApi;
import com.example.internship_management.retrofit.RetrofitService;
import com.example.internship_management.retrofit.StudentApi;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RespoProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RespoProfileFragment extends Fragment {

    private TextInputEditText fname, lname, mail, phone, password, etabliss, username;
    private Button update;
    Long id;
    ResponsableStageDTO responsable;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RespoProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RespoProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RespoProfileFragment newInstance(String param1, String param2) {
        RespoProfileFragment fragment = new RespoProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_respo_profile, container, false);

        loadRespo(id);
        fname = view.findViewById(R.id.etname);
        lname = view.findViewById(R.id.etlast);
        mail = view .findViewById(R.id.etMail);
        phone = view.findViewById(R.id.etphone);
        password = view .findViewById(R.id.etPassword1);
        update = view.findViewById(R.id.bupdate);
        etabliss = view.findViewById(R.id.etabl);
        username = view.findViewById(R.id.etuser);

        update.setOnClickListener(view1 -> {
            updateRespo(id);
        });

        return view;
    }

    private void updateRespo(Long id) {
        RetrofitService retrofitService = new RetrofitService();
        ResponsableApi responsableApi = retrofitService.getRetrofit().create(ResponsableApi.class);
        responsable.setEmail(mail.getText().toString());
        responsable.setLastName(lname.getText().toString());
        responsable.setName(fname.getText().toString());
        responsable.setPhoneNumber(phone.getText().toString());
        responsable.setPassword(password.getText().toString());

        responsableApi.update(id, responsable)
                .enqueue(new Callback<ResponsableStageDTO>() {
                    @Override
                    public void onResponse(Call<ResponsableStageDTO> call, Response<ResponsableStageDTO> response) {
                        Toast.makeText(getContext(), "Save successful!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<ResponsableStageDTO> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadRespo(Long id) {
        RetrofitService retrofitService = new RetrofitService();
        ResponsableApi responsableApi = retrofitService.getRetrofit().create(ResponsableApi.class);
        responsableApi.getById(id)
                .enqueue(new Callback<ResponsableStageDTO>() {
                    @Override
                    public void onResponse(Call<ResponsableStageDTO> call, Response<ResponsableStageDTO> response) {
                        responsable = response.body();
                        populateView(responsable);
                    }

                    @Override
                    public void onFailure(Call<ResponsableStageDTO> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void populateView(ResponsableStageDTO responsable) {
        fname.setText(responsable.getName());
        lname.setText(responsable.getLastName());
        mail.setText(responsable.getEmail());
        phone.setText(responsable.getPhoneNumber());
        password.setText(responsable.getPassword());
        username.setText(responsable.getUsername());
        etabliss.setText(responsable.getEtablissementDTOS().getName());
    }
}