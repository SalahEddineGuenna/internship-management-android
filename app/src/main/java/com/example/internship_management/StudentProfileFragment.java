package com.example.internship_management;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.internship_management.enums.Role;
import com.example.internship_management.model.Student;
import com.example.internship_management.retrofit.RetrofitService;
import com.example.internship_management.retrofit.StudentApi;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentProfileFragment extends Fragment {

    private TextInputEditText fname, lname, mail, phone, password, etabliss, username, niveau;
    private Button update;
    AutoCompleteTextView spinner;
    Long id;
    Student student;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StudentProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentProfileFragment newInstance(String param1, String param2) {
        StudentProfileFragment fragment = new StudentProfileFragment();
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
        View view =  inflater.inflate(R.layout.fragment_student_profile, container, false);

        loadStudent(id);

        fname = view.findViewById(R.id.etname);
        lname = view.findViewById(R.id.etlast);
        mail = view .findViewById(R.id.etMail);
        phone = view.findViewById(R.id.etphone);
        password = view .findViewById(R.id.etPassword1);
        update = view.findViewById(R.id.bupdate);
        etabliss = view.findViewById(R.id.etabl);
        niveau = view.findViewById(R.id.etniveau);
        username = view.findViewById(R.id.etuser);
        spinner = view.findViewById(R.id.spinner);



        update.setOnClickListener(view1 -> {
            updateStudent(id);
        });

        return view;
    }

    private void updateStudent(Long id) {
        RetrofitService retrofitService = new RetrofitService();
        StudentApi studentApi = retrofitService.getRetrofit().create(StudentApi.class);
        student.setEmail(mail.getText().toString());
        student.setLastName(lname.getText().toString());
        student.setName(fname.getText().toString());
        student.setPhoneNumber(phone.getText().toString());
        student.setPassword(password.getText().toString());

        studentApi.update(id, student)
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
    }

    private void loadStudent(Long id) {
        RetrofitService retrofitService = new RetrofitService();
        StudentApi studentApi = retrofitService.getRetrofit().create(StudentApi.class);
        studentApi.getById(id)
                .enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        student = response.body();
                        populateView(student);
                        }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void populateView(Student student) {
        fname.setText(student.getName());
        lname.setText(student.getLastName());
        mail.setText(student.getEmail());
        phone.setText(student.getPhoneNumber());
        password.setText(student.getPassword());
        username.setText(student.getUsername());
        niveau.setText(student.getNiveau());
        if (student.getEtablissement() != null){
            etabliss.setText(student.getEtablissement().getName());
        }

    }
}