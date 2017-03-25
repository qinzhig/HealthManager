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

import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.activity.MeasurementActivity;
import sg.edu.nus.iss.medipal.adapter.MeasurementAdapter;
import sg.edu.nus.iss.medipal.manager.MeasurementManager;

public class MeasurementFragment extends Fragment {
    private MeasurementManager _measurementManager;
    private List<Object> _measurementList;
    private MeasurementAdapter _measurementAdapter;
    private RecyclerView _measurementListView;
    private TextView _measurementNotification;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_measurement_list, container, false);
        _measurementListView = (RecyclerView) view.findViewById(R.id.measurementrecycler_view);
        _measurementNotification = (TextView) view.findViewById(R.id.measurementlist_placeholder);

        FloatingActionButton aFab = (FloatingActionButton) view.findViewById(R.id.measurement_fab);
        aFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent measurementIntent = new Intent(getContext(), MeasurementActivity.class);
                startActivityForResult(measurementIntent, 5);
            }
        });

        _measurementManager = new MeasurementManager();
        _measurementList = _measurementManager.getMeasurements(getContext());

        return view;
    }

    public void onResume() {
        super.onResume();

        //_measurementList = _measurementManager.getMeasurements(getContext());

        if (_measurementList.isEmpty()) {
            _measurementNotification.setText("No Measurements found for the day");
            _measurementNotification.setVisibility(View.VISIBLE);
        } else {
            populateRecyclerView();
        }
    }

    private void populateRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        _measurementListView.setLayoutManager(layoutManager);

        _measurementAdapter = new MeasurementAdapter(getContext(), _measurementList);
        _measurementListView.setAdapter(_measurementAdapter);
    }
}
