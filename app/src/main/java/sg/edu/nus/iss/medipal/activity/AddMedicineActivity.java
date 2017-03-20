package sg.edu.nus.iss.medipal.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.application.App;

/**
 * Created by : Qin Zhi Guo on 10-03-2017.
 * Description : Activity Class to add medicine
 * Modified by :
 * Reason for modification :
 */

public class AddMedicineActivity extends AppCompatActivity {

    private EditText et_name,et_des,et_quanity,et_dosage,et_date,et_frequency,et_interval,et_stime;
    private Spinner spinner;
    Button button_save;
    ImageButton button_add_category;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
    Calendar currentDate = Calendar.getInstance();
    Calendar selectedDate = Calendar.getInstance();
    Calendar calender = Calendar.getInstance();

   // private static final String[] m_category = {"Supplement","Chronic","Incidental","Complete Course","Self Apply"};

    ArrayAdapter array_adpater;
    int position=0;
    String medcine_category;
    String[] m_list;
    private int hour,minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_name = (EditText) findViewById(R.id.et_name);
        et_des = (EditText) findViewById(R.id.et_des);
        et_quanity = (EditText) findViewById(R.id.et_quantity);
        et_dosage = (EditText) findViewById(R.id.et_dosage);

        m_list = App.hm.getCategoryNameList(getApplicationContext());
        //Spinner action
        spinner= (Spinner) findViewById(R.id.spinner_category);
        array_adpater = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,m_list);
        spinner.setAdapter(array_adpater);

        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //medcine_category = m_list[arg2];
                arg0.setVisibility(View.VISIBLE);

                position=arg2;

                Toast toast = Toast.makeText(AddMedicineActivity.this,"Category Selected :"+medcine_category,Toast.LENGTH_SHORT);
                toast.show();
                //position= Arrays.asList(m_category).indexOf(medcine_category);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //Expire date setting
        et_date = (EditText) findViewById(R.id.et_date);

        et_date.setText(dateFormatter.format(selectedDate.getTime()));
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                selectedDate = calendar;
                                et_date.setText(dateFormatter.format(calendar.getTime()));
                            }
                        };
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(AddMedicineActivity.this, onDateSetListener,
                                currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH),
                                currentDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        et_frequency = (EditText) findViewById(R.id.et_frequency);
        et_interval = (EditText) findViewById(R.id.et_interval);
        et_stime = (EditText) findViewById(R.id.et_stime);

        et_stime.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                calender.setTimeInMillis(System.currentTimeMillis());
                int mHour = calender.get(Calendar.HOUR_OF_DAY);
                int mMinute = calender.get(Calendar.MINUTE);
                new TimePickerDialog(AddMedicineActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                calender.setTimeInMillis(System.currentTimeMillis());
                                calender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calender.set(Calendar.MINUTE, minute);
                                calender.set(Calendar.SECOND, 0); // 设为 0
                                calender.set(Calendar.MILLISECOND, 0); // 设为 0

                                et_stime.setText(hourOfDay+":"+minute);
                            }
                        }, mHour, mMinute, true).show();
            }
        });

        button_save = (Button) findViewById(R.id.button_save);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                App.hm.addReminder(0,Integer.valueOf(et_frequency.getText().toString().trim()),et_stime.getText().toString(),Integer.valueOf(et_interval.getText().toString().trim()),getApplicationContext());

                App.hm.addMedicine(0,et_name.getText().toString().trim(),et_des.getText().toString().trim(),
                        position,0,false,Integer.valueOf(et_quanity.getText().toString().trim()), Integer.valueOf(et_dosage.getText().toString().trim()),
                        et_date.getText().toString(),10,getApplicationContext());

//                App.hm.addMedicine(0,"m1","m1_des",1,0,false,20,1,"14 03 2017",10,getApplicationContext());

                Toast toast = Toast.makeText(AddMedicineActivity.this,"Add Medicine Successfully!",Toast.LENGTH_SHORT);
                toast.show();


                finish();

            }
        });

        button_add_category = (ImageButton) findViewById(R.id.button_add_category);

        button_add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_add_category= new Intent(getApplicationContext(), AddCategoryActivity.class);
                startActivity(intent_add_category);

                finish();

            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();

        if(array_adpater != null)
        {
            array_adpater.notifyDataSetChanged();
            m_list = App.hm.getCategoryNameList(getApplicationContext());


        }
    }
}
