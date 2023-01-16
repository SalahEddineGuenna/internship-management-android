package com.example.internship_management;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.internship_management.enums.Role;
import com.example.internship_management.model.EtablissementDTO;
import com.example.internship_management.model.Student;
import com.example.internship_management.retrofit.EtablissementApi;
import com.example.internship_management.retrofit.RetrofitService;
import com.example.internship_management.retrofit.StudentApi;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddStudentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddStudentFragment extends Fragment {

    private TextInputEditText fname, lname, mail, phone, niveau, password, etab;
    private Button confirm;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddStudentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddStudentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddStudentFragment newInstance(String param1, String param2) {
        AddStudentFragment fragment = new AddStudentFragment();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_student, container, false);
        fname = view.findViewById(R.id.etfirst);
        lname = view.findViewById(R.id.etlast);
        phone = view.findViewById(R.id.etphone);
        mail = view.findViewById(R.id.etMail);
        password = view.findViewById(R.id.etPassword);
        niveau = view.findViewById(R.id.etniveau);
        confirm = view.findViewById(R.id.bconfirm);
        final Spinner spinner = (Spinner) view.findViewById(R.id.etab);

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
                        }
                    }
                    @Override
                    public void onFailure(Call<List<EtablissementDTO>> call, Throwable t) {
                        // handle failure
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RetrofitService retrofitService = new RetrofitService();
        StudentApi studentApi = retrofitService.getRetrofit().create(StudentApi.class);
        confirm.setOnClickListener(view1 -> {
            String first = String.valueOf(fname.getText());
            String last = String.valueOf(lname.getText());
            String number = String.valueOf(phone.getText());
            String email = String.valueOf(mail.getText());
            String niv = String.valueOf(niveau.getText());
            String pass = String.valueOf(password.getText());

            Student student = new Student();
            student.setEmail(email);
            student.setLastName(last);
            student.setName(first);
            student.setPhoneNumber(number);
            student.setNiveau(niv);
            student.setPassword(pass);
            student.setRole(Role.ETUDIANT);

            studentApi.save(student)
                    .enqueue(new Callback<Student>() {
                        @Override
                        public void onResponse(Call<Student> call, Response<Student> response) {
                            Toast.makeText(getContext(), "Save successful!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Student> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        return view;
    }
}