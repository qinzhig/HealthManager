package sg.edu.nus.iss.medipal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.activity.AddEditHealthBioActivity;
import sg.edu.nus.iss.medipal.adapter.HealthBioAdapter;
import sg.edu.nus.iss.medipal.interfaces.AdapterCallbackInterface;
import sg.edu.nus.iss.medipal.manager.HealthBioManager;
import sg.edu.nus.iss.medipal.pojo.HealthBio;

/**
 * Created by Divahar on 3/16/2017.
 */

public class HealthBioFragment extends Fragment implements AdapterCallbackInterface {

    private RecyclerView healthBioView;
    private List<HealthBio> healthBioList;
    private HealthBioAdapter healthBioAdapter;
    private HealthBioManager healthBioManager;
    private View healthBioFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FloatingActionButton aFab;
        healthBioFragment = inflater.inflate(R.layout.fragment_list_healthbio, container, false);
        healthBioManager =
                new HealthBioManager();
        healthBioList = healthBioManager.getHealthBio(getContext());
        healthBioView = (RecyclerView) healthBioFragment.findViewById(R.id.hbrecycler_view);

        if(healthBioList.isEmpty())
        {
            refreshView();
        }
        else {

            populateRecyclerView();
        }

        aFab = (FloatingActionButton)healthBioFragment.findViewById(R.id.fab);
        aFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addHealthBioIntent = new Intent(getContext(), AddEditHealthBioActivity.class);
                addHealthBioIntent.putExtra("isEdit",false);
                addHealthBioIntent.putExtra("isFirstTime", false);
                getActivity().startActivityForResult(addHealthBioIntent,1);
            }
        });

        return healthBioFragment;
    }

    private void populateRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        healthBioView.setLayoutManager(mLayoutManager);

        healthBioAdapter = new HealthBioAdapter(getContext(), healthBioList,this);
        healthBioView.setAdapter(healthBioAdapter);
    }

    @Override
    public void refreshView() {
        //if no health are found then the following message will be shown
        LinearLayout linearLayout = (LinearLayout) healthBioFragment.findViewById(R.id.hb_layout);
        linearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        TextView txtView = (TextView) healthBioFragment.findViewById(R.id.placeholdertext);
        txtView.setText("No appointments found");
        txtView.setVisibility(View.VISIBLE);
        healthBioView.setVisibility(View.GONE);
        txtView.setText("No Health Bio found");

    }

}
