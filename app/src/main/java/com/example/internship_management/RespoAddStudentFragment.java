package com.example.internship_management;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.internship_management.enums.Role;
import com.example.internship_management.model.EtablissementDTO;
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
 * Use the {@link RespoAddStudentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RespoAddStudentFragment extends Fragment {

    private TextInputEditText fname, lname, mail, phone, niveau, password, etab, username;
    private Button confirm;

    private EtablissementDTO etablissementDTO;
    Long id;
    ResponsableStageDTO respo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RespoAddStudentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RespoAddStudentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RespoAddStudentFragment newInstance(String param1, String param2) {
        RespoAddStudentFragment fragment = new RespoAddStudentFragment();
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
            loadRespo(id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_respo_add_student, container, false);


        fname = view.findViewById(R.id.etfirst);
        lname = view.findViewById(R.id.etlast);
        mail = view .findViewById(R.id.etMail);
        phone = view.findViewById(R.id.etphone);
        password = view .findViewById(R.id.etPassword);
        etab = view.findViewById(R.id.etabl);
        username = view.findViewById(R.id.etuser);
        confirm = view.findViewById(R.id.bconfirm);
        niveau = view.findViewById(R.id.etniveau);

        RetrofitService retrofitService = new RetrofitService();
        StudentApi studentApi = retrofitService.getRetrofit().create(StudentApi.class);
        confirm.setOnClickListener(view1 -> {
            String first = String.valueOf(fname.getText());
            String last = String.valueOf(lname.getText());
            String number = String.valueOf(phone.getText());
            String email = String.valueOf(mail.getText());
            String niv = String.valueOf(niveau.getText());
            String pass = String.valueOf(password.getText());
            String user = String.valueOf(username.getText());

            Student student = new Student();
            student.setEmail(email);
            student.setLastName(last);
            student.setName(first);
            student.setPhoneNumber(number);
            student.setNiveau(niv);
            student.setPassword(pass);
            student.setRole(Role.ETUDIANT);
            student.setUsername(user);
            student.setEtablissement(respo.getEtablissementDTOS());

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

    private void loadRespo(Long id) {
        RetrofitService retrofitService = new RetrofitService();
        ResponsableApi responsableApi = retrofitService.getRetrofit().create(ResponsableApi.class);
        responsableApi.getById(id)
                .enqueue(new Callback<ResponsableStageDTO>() {
                    @Override
                    public void onResponse(Call<ResponsableStageDTO> call, Response<ResponsableStageDTO> response) {
                        respo = response.body();
                        if(respo.getEtablissementDTOS() != null) {
                            etab.setText(respo.getEtablissementDTOS().getName());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsableStageDTO> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }



}