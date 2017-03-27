package sg.edu.nus.iss.medipal.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
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
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

/**
 * Created by : Qin Zhi Guo on 12-03-2017.
 * Description : Activity Class to add category
 * Modified by :
 * Reason for modification :
 */

public class EditCategoryActivity  extends AppCompatActivity{

    private EditText et_name,et_code,et_des;
    private TextInputLayout l_name,l_code,l_desc;
    private Switch switch_remind;
    private boolean remind_status;
    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        l_name = (TextInputLayout) findViewById(R.id.textView);
        l_code = (TextInputLayout) findViewById(R.id.textView2);
        l_desc = (TextInputLayout) findViewById(R.id.textView3);

        category = (Category) getIntent().getSerializableExtra("category");

        et_name = (EditText) findViewById(R.id.et_name);
        et_code = (EditText) findViewById(R.id.et_code);
        et_des = (EditText) findViewById(R.id.et_des);
        switch_remind = (Switch) findViewById(R.id.switch_remind);


        et_name.setText(category.getCategory_name());
        et_code.setText(category.getCategory_code());
        et_des.setText(category.getCategory_des());
        switch_remind.setChecked(category.getRemind());

        //set the switch to ON
        switch_remind.setChecked(true);
        //attach a listener to check for changes in state
        switch_remind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    switch_remind.setText("Turn On/Off Reminder - ON ");
                    remind_status = true;
                }else{
                    switch_remind.setText("Turn On/Off Reminder - OFF");
                    remind_status = false;
                }

            }
        });


        //listener is added to clear error when input is given
        clearErrorOnTextInput();


    }

    private void clearErrorOnTextInput() {

        et_name.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    l_name.setError(null);
            }
        });

        et_code.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    l_code.setError(null);
            }
        });

        et_des.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    l_desc.setError(null);
            }
        });
    }

    public boolean input_validate(String name,String code,String des){

        boolean validate_status =true;

        if(name.isEmpty()){
            l_name.setError("Please input a Category Name!");
            validate_status = false;
        }else{

            validate_status = true;

            Iterator<Category> c_list = App.hm.getCategorys(getApplicationContext()).iterator();

            while(c_list.hasNext()){

                //Determine whether already exist a Category shortname with current one
                if(name.equals(c_list.next().getCategory_name())){
                    validate_status = false;
                    l_name.setError("Already Exist a same Category Name!");
                    break;
                }
            }
        }

        if(code.isEmpty() || code.length() != 3){
            l_code.setError("Please input a 3  letter as Category Code!");
            validate_status = false;
        }

        if(des.isEmpty()){
            l_desc.setError("Please input a Category Description!");
            validate_status = false;
        }


        return validate_status;

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_action_items, menu);
        MenuItem menuItem;
        menuItem = menu.findItem(R.id.action_close);
        menuItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_close) {
            finish();

        }
        if (id == R.id.action_done) {

            if(input_validate(et_name.getText().toString().trim(),et_code.getText().toString().trim(),et_des.getText().toString().trim())){
                App.hm.updateCategory(category.getId(),et_name.getText().toString().trim(),et_code.getText().toString().trim(),et_des.getText().toString().trim(),remind_status,getApplicationContext());

                final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Saving...");
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast toast = Toast.makeText(EditCategoryActivity.this,"Category Updated Successfully!",Toast.LENGTH_SHORT);
                        toast.show();

                        finish();
                    }
                }, 1000);


            }

        }
        return super.onOptionsItemSelected(item);
    }

}
