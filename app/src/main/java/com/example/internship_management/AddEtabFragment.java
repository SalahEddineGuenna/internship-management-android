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

import com.example.internship_management.enums.Role;
import com.example.internship_management.model.EtablissementDTO;
import com.example.internship_management.model.ResponsableStageDTO;
import com.example.internship_management.model.Student;
import com.example.internship_management.retrofit.EtablissementApi;
import com.example.internship_management.retrofit.ResponsableApi;
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
 * Use the {@link AddEtabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEtabFragment extends Fragment {

    private TextInputEditText etabname, mail, address;

    ResponsableStageDTO responsableStageDTO;
    private Button confirm;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddEtabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddEtabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEtabFragment newInstance(String param1, String param2) {
        AddEtabFragment fragment = new AddEtabFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_etab, container, false);

        etabname = view.findViewById(R.id.etfetab);
        confirm = view.findViewById(R.id.bconfirm);
        mail = view.findViewById(R.id.etmail);
        address = view.findViewById(R.id.address);

        RetrofitService retrofitService = new RetrofitService();
        EtablissementApi etablissementApi = retrofitService.getRetrofit().create(EtablissementApi.class);
        confirm.setOnClickListener(view1 -> {

            EtablissementDTO etab = new EtablissementDTO();
            etab.setName(String.valueOf(etabname.getText()));
            etab.setMail(String.valueOf(mail.getText()));
            etab.setAdress(String.valueOf(address.getText()));

            etablissementApi.save(etab)
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
        });

        return view;
    }
}