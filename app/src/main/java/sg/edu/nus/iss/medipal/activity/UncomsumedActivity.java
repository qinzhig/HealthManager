package sg.edu.nus.iss.medipal.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.adapter.UnConsumptionAdapter;

public class UncomsumedActivity extends AppCompatActivity{
    ListView UnconsumptionlistView;
    UnConsumptionAdapter unConsumptionAdapter;
    //int ConsumptionQuantity = 0;
    String ConsumptionQuantity = null;
    int medicine_id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uncomsumed);

        Bundle bundleConsumptionDetil = getIntent().getExtras();
        //ConsumptionQuantity = bundleFrequent.getInt("ConsumptionQuantity");
        ConsumptionQuantity = bundleConsumptionDetil.getString("ConsumptionQuantity");
//
        Bundle bundleconsumptionDetil = getIntent().getExtras();
        medicine_id = bundleconsumptionDetil.getInt("medicine_id");

        //



        UnconsumptionlistView = (ListView)findViewById(R.id.ListUnconsumed);
        unConsumptionAdapter = new UnConsumptionAdapter(getApplicationContext(),medicine_id,ConsumptionQuantity);
        UnconsumptionlistView.setAdapter((ListAdapter) unConsumptionAdapter);

    }

   /* public String getConsumptionQuantityt() {
        return ConsumptionQuantity;
    }

    public int getMedicine_id() {
        return medicine_id;
    }*/
}

