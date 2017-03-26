package sg.edu.nus.iss.medipal.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Iterator;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.application.App;
import sg.edu.nus.iss.medipal.pojo.Category;

public class EditCategoryActivity extends AppCompatActivity {

    private EditText et_name,et_code,et_des;
    private Button button_save;
    private Switch switch_remind;
    private boolean remind_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Category category = (Category) getIntent().getSerializableExtra("category");


        et_name = (EditText) findViewById(R.id.et_name);
        et_code = (EditText) findViewById(R.id.et_code);
        et_des = (EditText) findViewById(R.id.et_des);
        switch_remind = (Switch) findViewById(R.id.switch_remind);

        et_name.setText(category.getCategory_name());
        et_code.setText(category.getCategory_code());
        et_des.setText(category.getCategory_des());
        switch_remind.setChecked(category.getRemind());

        //attach a listener to check for changes in state
        switch_remind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    switch_remind.setText(" ON ");
                    remind_status = true;
                }else{
                    switch_remind.setText(" OFF ");
                    remind_status = false;
                }

            }
        });


        button_save = (Button)findViewById(R.id.button_save);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(input_validate(et_name.getText().toString().trim(),et_code.getText().toString().trim(),et_des.getText().toString().trim())){

                    App.hm.updateCategory(category.getId(),et_name.getText().toString().trim(),et_code.getText().toString().trim(),et_des.getText().toString().trim(),remind_status,getApplicationContext());

                    Toast toast = Toast.makeText(EditCategoryActivity.this,"Add Category Successfully!",Toast.LENGTH_SHORT);
                    toast.show();

                    finish();
                }else{
                    Toast toast = Toast.makeText(EditCategoryActivity.this,"Something incorrect with your input,please have a check!",Toast.LENGTH_SHORT);
                    toast.show();
                }


            }
        });
    }

    public boolean input_validate(String name,String code,String des){

        boolean validate_status =true;

        if(name.isEmpty()){
            et_name.setError("Please input a Category Name!");
            validate_status = false;
        }else{

            validate_status = true;

            Iterator<Category> c_list = App.hm.getCategorys(getApplicationContext()).iterator();

            while(c_list.hasNext()){

                //Determine whether already exist a Category shortname with current one
                if(name.equals(c_list.next().getCategory_name())){
                    validate_status = false;
                    et_name.setError("Same Category Name exist,change name or Cancle!");
                    break;
                }
            }
        }

        if(code.isEmpty() || code.length() != 3){
            et_code.setError("Please input a 3  letter as Category Code!");
            validate_status = false;
        }

        if(des.isEmpty()){
            et_des.setError("Please input a Category Description!");
            validate_status = false;
        }


        return validate_status;

    }


}
