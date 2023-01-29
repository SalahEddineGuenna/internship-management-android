package com.example.internship_management;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import com.google.android.material.textfield.TextInputEditText;
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

    private Button confirm;
    private Student item;
    private ProfesseurDTO professuer;
    private TextInputEditText date, time;
    private String selectedTime, selectedDate;

    Long id;
    ResponsableStageDTO responsable;
    int year, day, month, hour, minute;

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
            id = getArguments().getLong("id");
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meetings, container, false);

        final Spinner etudiants = (Spinner) view.findViewById(R.id.spinner);

        final Spinner prof = (Spinner) view.findViewById(R.id.spinner1);
        final Calendar c = Calendar.getInstance();
        loadRespo(id);


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
                        if(item.getEtablissement() != null) {
                            if (item.getEtablissement().getId() == responsable.getEtablissementDTOS().getId()) {
                                names.add(item.getName());
                            }
                        }
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

        //DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);


        //TimePicker timePicker = (TimePicker) view.findViewById(R.id.timepicker);
        //timePicker.setIs24HourView(true);


        confirm = view.findViewById(R.id.bconfirm);

        date = view.findViewById(R.id.etdate);
        time = view.findViewById(R.id.etTime);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                // on below line we are getting
                // our day, month and year.
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                selectedDate = date.getText().toString();
                                //Toast.makeText(getContext(), datetime, Toast.LENGTH_SHORT).show();

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText( selectedHour + ":" + selectedMinute);
                        selectedTime = time.getText().toString();
                        //Toast.makeText(getContext(), datetime, Toast.LENGTH_SHORT).show();
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        ReunionApi reunionApi = retrofitService.getRetrofit().create(ReunionApi.class);
        confirm.setOnClickListener(view1 -> {
            //Toast.makeText(getContext(), selectedDate + " " + selectedTime, Toast.LENGTH_SHORT).show();
            ReunionDTO reunion = new ReunionDTO();
            reunion.setDateReunion(selectedDate + " " + selectedTime);
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


    private void loadRespo(Long id){
        RetrofitService retrofitService = new RetrofitService();
        ResponsableApi responsableApi = retrofitService.getRetrofit().create(ResponsableApi.class);
        responsableApi.getById(id)
                .enqueue(new Callback<ResponsableStageDTO>() {
                    @Override
                    public void onResponse(Call<ResponsableStageDTO> call, Response<ResponsableStageDTO> response) {
                        responsable = response.body();
                    }

                    @Override
                    public void onFailure(Call<ResponsableStageDTO> call, Throwable t) {

                    }
                });
    }


}