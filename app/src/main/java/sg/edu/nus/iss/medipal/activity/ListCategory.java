package sg.edu.nus.iss.medipal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.adapter.CategoryAdapter;

/**
 * Created by zhiguo on 25/3/17.
 */

public class ListCategory extends AppCompatActivity {

    private ListView lv_category;
    private CategoryAdapter categoryAdapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list);

        lv_category= (ListView) findViewById(R.id.lv_category);
        categoryAdapter = new CategoryAdapter(getApplicationContext());
        lv_category.setAdapter(categoryAdapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
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
        //This activity get focus again and refresh the category List

        super.onResume();

        if(categoryAdapter != null)
        {
            categoryAdapter.notifyDataSetChanged();


        }
    }
}
