package com.example.internship_management;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.internship_management.enums.Role;
import com.example.internship_management.model.ProfesseurDTO;
import com.example.internship_management.model.ResponsableStageDTO;
import com.example.internship_management.model.ReunionDTO;
import com.example.internship_management.model.Student;
import com.example.internship_management.retrofit.ProfesseurApi;
import com.example.internship_management.retrofit.ResponsableApi;
import com.example.internship_management.retrofit.RetrofitService;
import com.example.internship_management.retrofit.ReunionApi;
import com.example.internship_management.retrofit.StudentApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeetingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeetingsFragment extends Fragment {

    Button confirm;
    Student item;
    ProfesseurDTO professuer;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MeetingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeetingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeetingsFragment newInstance(String param1, String param2) {
        MeetingsFragment fragment = new MeetingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_meetings, container, false);

        final Spinner etudiants = (Spinner) view.findViewById(R.id.spinner);

        final Spinner prof = (Spinner) view.findViewById(R.id.spinner1);


        // make the network request to the server
        RetrofitService retrofitService = new RetrofitService();
        StudentApi studentApi = retrofitService.getRetrofit().create(StudentApi.class);
        studentApi.getAllStudents()
                .enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful()) {
                    List<Student> data = response.body();
                    ArrayList<String> names = new ArrayList<>();
                    for(Student item: data){
                        names.add(item.getName());
                    }
                    // create an ArrayAdapter with the data
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, names);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    etudiants.setAdapter(adapter);
                    etudiants.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            // Get the selected item information here
                            item = data.get(position);
                            //Student selectedModel = gson.fromJson(selectedItem, Student.class);
                            //Student selectedModel = gson.fromJson(selectedItem, Student.class);
                            //Toast.makeText(getContext(), selectedItem.toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                } else {
                    // handle error case
                }
            }
            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                // handle failure
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        RetrofitService retrofitServicerespo = new RetrofitService();
        ProfesseurApi professeurApi = retrofitServicerespo.getRetrofit().create(ProfesseurApi.class);
        professeurApi.getAllprofesseurs()
                .enqueue(new Callback<List<ProfesseurDTO>>() {
                    @Override
                    public void onResponse(Call<List<ProfesseurDTO>> call, Response<List<ProfesseurDTO>> response) {
                        if (response.isSuccessful()) {
                            List<ProfesseurDTO> data = response.body();
                            ArrayList<String> names = new ArrayList<>();
                            for(ProfesseurDTO item: data){
                                names.add(item.getName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, names);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            prof.setAdapter(adapter);
                            prof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    // Get the selected item information here
                                    professuer = data.get(position);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                        } else {
                            // handle error case
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProfesseurDTO>> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    });

        DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);


        TimePicker timePicker = (TimePicker) view.findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);


        confirm = view.findViewById(R.id.bconfirm);

        ReunionApi reunionApi = retrofitService.getRetrofit().create(ReunionApi.class);
        confirm.setOnClickListener(view1 -> {

            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1;
            int year = datePicker.getYear();

            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            String date = year + "-" + month + "-" + day + " " + hour + ":" + minute;

            System.out.println(date);
            ReunionDTO reunion = new ReunionDTO();
            reunion.setDateReunion(date);
            reunion.setEtudiantDTO(item);
            reunion.setProfesseurDTO(professuer);
            reunionApi.save(reunion)
                    .enqueue(new Callback<ReunionDTO>() {
                        @Override
                        public void onResponse(Call<ReunionDTO> call, Response<ReunionDTO> response) {
                            Toast.makeText(getContext(), "Save successful!", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<ReunionDTO> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });


        return view;
    }


}