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
    int getFrequentCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uncomsumed);

        Bundle bundleFrequent = getIntent().getExtras();
        getFrequentCount = bundleFrequent.getInt("countFrequentNum");



        UnconsumptionlistView = (ListView)findViewById(R.id.ListUnconsumed);
        unConsumptionAdapter = new UnConsumptionAdapter(getApplicationContext());
        UnconsumptionlistView.setAdapter((ListAdapter) unConsumptionAdapter);

    }

    public int getFrequentCount() {
        return getFrequentCount;
    }
}

