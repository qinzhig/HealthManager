package sg.edu.nus.iss.medipal.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.activity.MeasurementActivity;
import sg.edu.nus.iss.medipal.adapter.MeasurementAdapter;
import sg.edu.nus.iss.medipal.manager.MeasurementManager;
import sg.edu.nus.iss.medipal.pojo.BloodPressure;
import sg.edu.nus.iss.medipal.pojo.Pulse;
import sg.edu.nus.iss.medipal.pojo.Temperature;
import sg.edu.nus.iss.medipal.pojo.Weight;

public class MeasurementFragment extends Fragment implements View.OnClickListener {
    private MeasurementManager _measurementManager;
    private List<Object> _measurementList;
    private MeasurementAdapter _measurementAdapter;
    private RecyclerView _measurementListView;
    private TextView _measurementNotification;
    EditText toDate,
            fromDate;
    TextInputLayout l_toDate, l_fromDate;
    Spinner measureType;
    View measurementFragment;
    private String measureCategory;
    private String strDate = null;
    private String endDate = null;

    private final static String[] MEASURETYPE = {"All Measurements", "Blood Pressure", "Pulse", "Weight", "Temperature"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        measurementFragment = inflater.inflate(R.layout.fragment_measurement_list, container, false);
        _measurementListView = (RecyclerView) measurementFragment.findViewById(R.id.measurementrecycler_view);
        _measurementNotification = (TextView) measurementFragment.findViewById(R.id.measurementlist_placeholder);


        toDate = (EditText) measurementFragment.findViewById(R.id.todate);
        fromDate = (EditText) measurementFragment.findViewById(R.id.fromdate);

        toDate.setOnClickListener(this);
        fromDate.setOnClickListener(this);

        l_fromDate = (TextInputLayout) measurementFragment.findViewById(R.id.edit_text_fromdate);
        l_toDate = (TextInputLayout) measurementFragment.findViewById(R.id.edit_text_todate);

        measureType = (Spinner) measurementFragment.findViewById(R.id.measuretype);

        ArrayAdapter<String> spinnerAdapterOne = new ArrayAdapter<>(measurementFragment.getContext(), android.R.layout.simple_dropdown_item_1line, MEASURETYPE);
        measureType.setAdapter(spinnerAdapterOne);

        FloatingActionButton aFab = (FloatingActionButton) measurementFragment.findViewById(R.id.measurement_fab);
        aFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent measurementIntent = new Intent(getContext(), MeasurementActivity.class);
                getActivity().startActivityForResult(measurementIntent, 5);
            }
        });

        _measurementManager = new MeasurementManager();
        _measurementList = _measurementManager.getMeasurements(getContext(), strDate, endDate);

        if (_measurementList.isEmpty()) {
            LinearLayout linearLayout = (LinearLayout) measurementFragment.findViewById(R.id.measurement_layout);
            linearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            _measurementNotification.setText("No Measurements found for the day");
            _measurementNotification.setVisibility(View.VISIBLE);
        } else {
            populateRecyclerView();
        }

        measureType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                int item = measureType.getSelectedItemPosition();
                measureCategory = Arrays.asList(MEASURETYPE).get(item);
                    dynamicGeneration(strDate, endDate);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                measureCategory = "";
            }
        });

        return measurementFragment;
    }

    public void onResume() {
        super.onResume();
    }

    void dynamicGeneration(String strtDt, String endDt) {
        _measurementList = _measurementManager.
                getMeasurements(getContext(), strtDt, endDt);

        boolean isChange = false;
        List<Object> refinedList = new ArrayList<>();

        if (null != _measurementList &&
                _measurementList.size() > 0) {

            for (Object object : _measurementList) {
                if (measureCategory.equalsIgnoreCase("Blood Pressure")) {
                    if (object instanceof BloodPressure) {
                        refinedList.add(object);
                        isChange = true;
                    }
                }
                if (measureCategory.equalsIgnoreCase("Pulse")) {
                    if (object instanceof Pulse) {
                        refinedList.add(object);
                        isChange = true;
                    }
                }

                if (measureCategory.equalsIgnoreCase("Weight")) {
                    if (object instanceof Weight) {
                        refinedList.add(object);
                        isChange = true;
                    }
                }

                if (measureCategory.equalsIgnoreCase("Temperature")) {
                    if (object instanceof Temperature) {
                        refinedList.add(object);
                        isChange = true;
                    }
                }
            }

            if (isChange) {
                _measurementList.clear();
                _measurementList.addAll(refinedList);
            }
            populateRecyclerView();
        } else {

            _measurementAdapter = new MeasurementAdapter(getContext(), _measurementList);
            _measurementListView.setAdapter(_measurementAdapter);
            LinearLayout linearLayout = (LinearLayout) measurementFragment.findViewById(R.id.measurement_layout);
            linearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            _measurementNotification.setText("No Measurements found for the day");
            _measurementNotification.setVisibility(View.VISIBLE);
        }
    }


    private void populateRecyclerView() {
        _measurementNotification.setVisibility(View.GONE);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        _measurementListView.setLayoutManager(layoutManager);

        _measurementAdapter = new MeasurementAdapter(getContext(), _measurementList);
        _measurementListView.setAdapter(_measurementAdapter);
    }

    @Override
    public void onClick(View v) {
        final Calendar calender;
        int day, month, year;
        if (v == fromDate) {
            calender = Calendar.getInstance();
            day = calender.get(Calendar.DAY_OF_MONTH);
            month = calender.get(Calendar.MONTH);
            year = calender.get(Calendar.YEAR);

            DatePickerDialog datePicker = new DatePickerDialog(measurementFragment.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String dt = dayOfMonth + "-" + (month + 1) + "-" + year;
                    fromDate.setText(dt);
                    strDate = dt;
                    SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat format2 = new SimpleDateFormat("yyyy MMM dd");
                    try {
                        Date date = format1.parse(dt);
                        strDate = format2.format(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (null != strDate
                            && null != endDate) {
                        dynamicGeneration(strDate, endDate);
                    }
                }
            }, day, month, year);
            datePicker.updateDate(year, month, day);
            datePicker.show();
        } else if (v == toDate) {
            calender = Calendar.getInstance();
            day = calender.get(Calendar.DAY_OF_MONTH);
            month = calender.get(Calendar.MONTH);
            year = calender.get(Calendar.YEAR);

            DatePickerDialog datePicker = new DatePickerDialog(measurementFragment.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String dt = dayOfMonth + "-" + (month + 1) + "-" + year;
                    toDate.setText(dt);
                    endDate = dt;
                    SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat format2 = new SimpleDateFormat("yyyy MMM dd");
                    try {
                        Date date = format1.parse(dt);
                        endDate = format2.format(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (null != strDate
                            && null != endDate) {
                        dynamicGeneration(strDate, endDate);
                    }
                }
            }, day, month, year);
            datePicker.updateDate(year, month, day);
            datePicker.show();
        }
    }
}
