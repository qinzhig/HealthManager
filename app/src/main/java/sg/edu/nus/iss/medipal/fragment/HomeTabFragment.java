package sg.edu.nus.iss.medipal.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.activity.AddEditAppointmentActivity;
import sg.edu.nus.iss.medipal.adapter.AppointmentAdapter;
import sg.edu.nus.iss.medipal.adapter.MedicineRecyclerAdapter;
import sg.edu.nus.iss.medipal.interfaces.AdapterCallbackInterface;
import sg.edu.nus.iss.medipal.manager.AppointmentManager;
import sg.edu.nus.iss.medipal.pojo.Appointment;
import sg.edu.nus.iss.medipal.pojo.Category;
import sg.edu.nus.iss.medipal.pojo.HealthBio;
import sg.edu.nus.iss.medipal.pojo.HealthManager;
import sg.edu.nus.iss.medipal.pojo.Medicine;

/**
 * Created by : Navi on 14-03-2017.
 * Description : This class is to implement tabview for Appointment
 * Modified by :
 * Reason for modification :
 */
public class HomeTabFragment extends Fragment implements AdapterCallbackInterface {
    private RecyclerView listView;
    private AppointmentAdapter appointmentAdapter;
    private MedicineRecyclerAdapter medicineAdapter;
    private List<Appointment> appointmentList;
    private AppointmentManager appointmentManager;
    private HealthManager medicineManager;
    private Context mContext;
    private View homeFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //get the tab position past from parent view(AppointmentFragment) - upcoming / past / notification content
        final Bundle args = getArguments();
        int tabPosition = -1;

        FloatingActionButton aFab;
        //using the appointment_main xml to show in the main fragment
        homeFragment = inflater.inflate(R.layout.appointment_main, container, false);
        //get reference to the current context(
        mContext = homeFragment.getContext();
        //get the reference to appointmentManager object
        appointmentManager = new AppointmentManager(mContext);

        //get reference to medicine manager
        medicineManager = new HealthManager();

        //get reference to the recyclerview
        listView = (RecyclerView) homeFragment.findViewById(R.id.appointmentrecycler_view);

        //Floating button to implement adding appointments
        aFab = (FloatingActionButton)homeFragment.findViewById(R.id.fab);
        aFab.setVisibility(View.GONE);

        if (args != null) {
            tabPosition= args.getInt("position");
        }
        //the recycler view will use linear layout to show the cards
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        listView.setLayoutManager(mLayoutManager);
        if(tabPosition==1) {
            //get the appointment details from the appointment table
            appointmentList = appointmentManager.getAppointments(0);

            if(appointmentList == null || appointmentList.isEmpty())
            {
                //show the "no appointments" message
                refreshView();
            }
            else{
                //show the list view of appointments
                populateAppointmentRecyclerView(tabPosition);
            }
        }
        else
        {
            populateMedicineRecyclerView();

        }



        return homeFragment;
    }

    private void populateMedicineRecyclerView() {
        //populate the adapter with appointments lists
        medicineAdapter = new MedicineRecyclerAdapter(mContext,medicineManager,this);
        listView.setAdapter(medicineAdapter);

    }

    private void populateAppointmentRecyclerView(int position) {

        //populate the adapter with appointments lists
        appointmentAdapter = new AppointmentAdapter(mContext, appointmentList,2,this);
        listView.setAdapter(appointmentAdapter);
    }

    @Override
    public void refreshView() {
            //if no appointments are found then the following message will be shown
            LinearLayout linearLayout = (LinearLayout) homeFragment.findViewById(R.id.appointment_layout);
            linearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            TextView txtView = (TextView) homeFragment.findViewById(R.id.placeholdertext);
            txtView.setText("No appointments found");
            txtView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);

    }


}
