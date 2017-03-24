package sg.edu.nus.iss.medipal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.manager.MeasurementManager;
import sg.edu.nus.iss.medipal.pojo.Measurement;
import sg.edu.nus.iss.medipal.adapter.MeasurementAdapter;
import sg.edu.nus.iss.medipal.activity.MeasurementActivity;

import java.util.List;

public class MeasurementFragment extends Fragment {

    private RecyclerView _measurementView;
    private MeasurementManager _measurementManager;
    private List<Measurement> _measurementList;
    private MeasurementAdapter _measurementAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_measurement_list, container, false);

        _measurementManager = new MeasurementManager();
        _measurementList = _measurementManager.getMeasurements(getContext());

        if(_measurementList.isEmpty()) {
            TextView txtView = (TextView) view.findViewById(R.id.measurementlist_placeholder);
            txtView.setText("No Measurements found");
            txtView.setVisibility(View.VISIBLE);
        } else {
            _measurementView = (RecyclerView) view.findViewById(R.id.measurementrecycler_view);
            populateRecyclerView();
        }

        FloatingActionButton aFab = (FloatingActionButton)view.findViewById(R.id.measurement_fab);
        aFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent measurementIntent = new Intent(getContext(), MeasurementActivity.class);
                measurementIntent.putExtra("isEdit", false);
                startActivityForResult(measurementIntent, 5);
            }
        });

        return view;
    }

    public void onResume() {
        super.onResume();

        _measurementList = _measurementManager.getMeasurements(getContext());
        this.populateRecyclerView();
    }

    private void populateRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        _measurementView.setLayoutManager(layoutManager);

        _measurementAdapter = new MeasurementAdapter(getContext(), _measurementList);
        _measurementView.setAdapter(_measurementAdapter);
    }
}
