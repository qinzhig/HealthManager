package sg.edu.nus.iss.medipal.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import sg.edu.nus.iss.medipal.application.App;
import sg.edu.nus.iss.medipal.R;

/**
 * Created by : Qin Zhi Guo on 10-03-2017.
 * Description : Activity Class to add medicine
 * Modified by :
 * Reason for modification :
 */

public class AddMedicineActivity extends AppCompatActivity {

    private EditText et_name,et_des,et_quanity,et_dosage,et_date;
    private Spinner spinner;
    Button button_save;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
    Calendar currentDate = Calendar.getInstance();
    Calendar selectedDate = Calendar.getInstance();

    private static final String[] m_category = {"Supplement","Chronic","Incidental","Complete Course","Self Apply"};
    ArrayAdapter array_adpater;

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

        //Spinner action
        spinner= (Spinner) findViewById(R.id.spinner_category);
        array_adpater = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,m_category);
        spinner.setAdapter(array_adpater);

        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String medcine_category = m_category[arg2];
                arg0.setVisibility(View.VISIBLE);

                Toast toast = Toast.makeText(AddMedicineActivity.this,"Category Selected :"+medcine_category,Toast.LENGTH_SHORT);
                toast.show();
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



        button_save = (Button) findViewById(R.id.button_save);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                App.hm.addMedicine(0,et_name.getText().toString().trim(),et_des.getText().toString().trim(),
//                        spinner.getId(),0,false,Integer.valueOf(et_quanity.getText().toString().trim()), Integer.valueOf(et_dosage.getText().toString().trim()),
//                        et_date.getText().toString(),10,getApplicationContext());

                App.hm.addMedicine(0,"m1","m1_des",1,0,false,20,1,"14 03 2017",10,getApplicationContext());

                Toast toast = Toast.makeText(AddMedicineActivity.this,"Add Medicine Successfully!",Toast.LENGTH_SHORT);
                toast.show();


                finish();

            }
        });
    }
}
