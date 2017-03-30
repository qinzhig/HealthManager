package sg.edu.nus.iss.medipal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.adapter.CategoryAdapter;
import sg.edu.nus.iss.medipal.application.App;
import sg.edu.nus.iss.medipal.pojo.Category;

/**
 * Created by zhiguo on 25/3/17.
 */

public class ListCategory extends AppCompatActivity {

    private RecyclerView lv_category;
    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_category= (RecyclerView) findViewById(R.id.lv_category);
        categoryList = App.hm.getCategorys(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        lv_category.setLayoutManager(mLayoutManager);
        categoryAdapter = new CategoryAdapter(getApplicationContext(),categoryList);
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
        //Refresh the category List
       if(categoryAdapter != null){
           categoryAdapter.refreshCategorys();
       }

    }
}
