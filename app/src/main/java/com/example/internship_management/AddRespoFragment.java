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
 * Use the {@link AddRespoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRespoFragment extends Fragment {

    private TextInputEditText fname, lname, mail, phone, user, password;
    private Button confirm;

    EtablissementDTO etablissementDTO = new EtablissementDTO();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddRespoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRespoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRespoFragment newInstance(String param1, String param2) {
        AddRespoFragment fragment = new AddRespoFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_respo, container, false);

        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner1);

        fname = view.findViewById(R.id.etfirst);
        lname = view.findViewById(R.id.etlast);
        phone = view.findViewById(R.id.etphone);
        mail = view.findViewById(R.id.etMail);
        password = view.findViewById(R.id.etPassword);
        confirm = view.findViewById(R.id.bconfirm);
        user = view.findViewById(R.id.etuser);

        RetrofitService retrofitServiceetab = new RetrofitService();
        EtablissementApi etablissementApi = retrofitServiceetab
                .getRetrofit().create(EtablissementApi.class);
        etablissementApi.getAlletablissments()
                .enqueue(new Callback<List<EtablissementDTO>>() {
                    @Override
                    public void onResponse(Call<List<EtablissementDTO>> call, Response<List<EtablissementDTO>> response) {
                        if (response.isSuccessful()) {
                            List<EtablissementDTO> data = response.body();
                            ArrayList<String> names = new ArrayList<>();
                            for(EtablissementDTO item: data){
                                names.add(item.getName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, names);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    // Get the selected item information here
                                    etablissementDTO = data.get(position);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                    }
                    @Override
                    public void onFailure(Call<List<EtablissementDTO>> call, Throwable t) {
                        // handle failure
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        RetrofitService retrofitService = new RetrofitService();
        ResponsableApi responsableApi = retrofitService.getRetrofit().create(ResponsableApi.class);
        confirm.setOnClickListener(view1 -> {
            String first = String.valueOf(fname.getText());
            String last = String.valueOf(lname.getText());
            String number = String.valueOf(phone.getText());
            String email = String.valueOf(mail.getText());
            String pass = String.valueOf(password.getText());
            String username = String.valueOf(user.getText());

            ResponsableStageDTO responsable = new ResponsableStageDTO();
            responsable.setEmail(email);
            responsable.setLastName(last);
            responsable.setName(first);
            responsable.setPhoneNumber(number);
            responsable.setPassword(pass);
            responsable.setRole(Role.RESPONSABLE);
            responsable.setUsername(username);
            responsable.setEtablissementDTOS(etablissementDTO);

            responsableApi.save(responsable)
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

            /*etablissementDTO.setResponsableStageDTO(responsable);

            etablissementApi.update(etablissementDTO.getId(), etablissementDTO)
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


             */

        });

        return view;
    }
}