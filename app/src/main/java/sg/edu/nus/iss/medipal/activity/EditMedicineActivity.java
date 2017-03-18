package sg.edu.nus.iss.medipal.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.application.App;
import sg.edu.nus.iss.medipal.pojo.Medicine;

public class EditMedicineActivity extends AppCompatActivity {

    private EditText et_name,et_des,et_quanity,et_dosage,et_date;
    private Spinner spinner;
    Button button_update;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
    Calendar currentDate = Calendar.getInstance();
    Calendar selectedDate = Calendar.getInstance();

    private static final String[] m_category = {"Supplement","Chronic","Incidental","Complete Course","Self Apply"};
    ArrayAdapter array_adpater;

    String medcine_category;
    Medicine medicine;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Update Medicine");

        medicine = (Medicine) getIntent().getSerializableExtra("medicineInfo");

        //Get the intentExtras data
        //Bundle intentExtras = getIntent().getExtras();

        //Align the medicine data from previous selected medicine item to current view correspondingly

        et_name = (EditText) findViewById(R.id.et_name);
        et_des = (EditText) findViewById(R.id.et_des);
        et_quanity = (EditText) findViewById(R.id.et_quantity);
        et_dosage = (EditText) findViewById(R.id.et_dosage);
        et_date = (EditText) findViewById(R.id.et_date);


        Log.v("TAG","----------->EditMedicine Object medicine:  "+medicine.toString());
        Log.v("TAG","----------------------- EditMedicineActivity catId = " + Integer.toString(medicine.getCateId()));
        Log.v("TAG","----------------------- EditMedicineActivity DES = " + medicine.getMedicine_des());
        Log.v("TAG","----------------------- EditMedicineActivity Date = " + medicine.getDateIssued());
        Log.v("TAG","----------------------- EditMedicineActivityexpireFactor = " + medicine.getExpireFactor());


        //Set the default data for every elements
        et_name.setText(medicine.getMedicine_name());
        et_des.setText(medicine.getMedicine_des());
        et_quanity.setText(Integer.toString(medicine.getQuantity()));
        et_dosage.setText(Integer.toString(medicine.getDosage()));


        et_date.setText(medicine.getDateIssued());
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
                        new DatePickerDialog(EditMedicineActivity.this, onDateSetListener,
                                currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH),
                                currentDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        //Spinner action
        spinner= (Spinner) findViewById(R.id.spinner_category);
        array_adpater = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,m_category);
        spinner.setAdapter(array_adpater);
        spinner.setSelection(medicine.getCateId());

        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                medcine_category = m_category[arg2];
                arg0.setVisibility(View.VISIBLE);

                position=arg2;

                Toast toast = Toast.makeText(EditMedicineActivity.this,"Category Selected :"+medcine_category,Toast.LENGTH_SHORT);
                toast.show();
                //position= Arrays.asList(m_category).indexOf(medcine_category);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //Set the Save Button Name to Update
        button_update = (Button) findViewById(R.id.button_save);
        button_update.setText("Update");

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                App.hm.updateMedicine(medicine.getId(),et_name.getText().toString().trim(),et_des.getText().toString().trim(),
                        position,0,false,Integer.valueOf(et_quanity.getText().toString().trim()), Integer.valueOf(et_dosage.getText().toString().trim()),
                        et_date.getText().toString(),10,getApplicationContext());

//                App.hm.addMedicine(0,"m1","m1_des",1,0,false,20,1,"14 03 2017",10,getApplicationContext());

                Toast toast = Toast.makeText(EditMedicineActivity.this,"Update Medicine Successfully!",Toast.LENGTH_SHORT);
                toast.show();


                finish();

            }
        });

    }
}
