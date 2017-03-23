package sg.edu.nus.iss.medipal.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.activity.AddMedicineActivity;
import sg.edu.nus.iss.medipal.adapter.MedicineAdapter;

/**
 * Created by : Qin Zhi Guo on 10-03-2017.
 * Description : Activity Class to control and show the medicine info
 * Modified by :
 * Reason for modification :
 */

public class MedicineFragment extends Fragment {

    ListView lv;
    MedicineAdapter m_adapter;
    View medicineFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FloatingActionButton aFab;

        medicineFragment = inflater.inflate(R.layout.activity_medicine, container, false);
        lv= (ListView) medicineFragment.findViewById(R.id.lv_medicine);

        m_adapter = new MedicineAdapter(getContext());
        lv.setAdapter(m_adapter);

        aFab = (FloatingActionButton)medicineFragment.findViewById(R.id.fab);
        aFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddMedicineActivity.class));
            }
        });

        return medicineFragment;
    }


    @Override
    public void onResume(){
        super.onResume();
        Log.d("is this called","lets see");
        if(m_adapter != null)
        {
            m_adapter.refreshMedicines();
        }

    }

}
