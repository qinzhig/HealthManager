package sg.edu.nus.iss.medipal.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import sg.edu.nus.iss.medipal.interfaces.AdapterCallbackInterface;
import sg.edu.nus.iss.medipal.manager.AppointmentManager;
import sg.edu.nus.iss.medipal.pojo.Appointment;

/**
 * Created by : Navi on 14-03-2017.
 * Description : This class is to implement tabview for Appointment
 * Modified by :
 * Reason for modification :
 */
public class AppointmentsTabFragment extends Fragment implements AdapterCallbackInterface {
    private RecyclerView appointmentsView;
    private AppointmentAdapter appointmentAdapter;
    private List<Appointment> appointmentList;
    private AppointmentManager appointmentManager;
    private Context mContext;
    private View appointmentFragment;

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
        String notificationContent = null;
        String notificationId = null;

        FloatingActionButton aFab;
        //using the appointment_main xml to show in the main fragment
        appointmentFragment = inflater.inflate(R.layout.appointment_main, container, false);
        //get reference to the current context(
        mContext = appointmentFragment.getContext();
        //get the reference to appointmentManager object
        appointmentManager = new AppointmentManager(mContext);
        //get reference to the recyclerview
        appointmentsView = (RecyclerView) appointmentFragment.findViewById(R.id.appointmentrecycler_view);

        //Floating button to implement adding appointments
        aFab = (FloatingActionButton)appointmentFragment.findViewById(R.id.fab);

        if (args != null) {
            tabPosition= args.getInt("position");
            notificationContent = args.getString("notification", null);
            notificationId = args.getString("Id", null);
        }

        if((notificationContent != null) && (notificationId != null)){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Appointment");
            aFab.setVisibility(View.GONE);
            Log.d("Fragment tab ","before get");
            //get the appointment details from the appointment table
            //appointmentList = appointmentManager.getAppointment(notificationId);
            appointmentList = appointmentManager.getAppointment(notificationId);
        }
        else
        {
            //get the appointment details from the appointment table
            appointmentList = appointmentManager.getAppointments(tabPosition);


            aFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent addAppointment = new Intent(mContext, AddEditAppointmentActivity.class);
                    //Same activity is used for adding and editing; using the below flag to know which logic block to execute inside it.
                    addAppointment.putExtra("isEdit",false);
                    getActivity().startActivityForResult(addAppointment,101);
                }
            });
        }


        if(appointmentList.isEmpty())
        {
            //show the "no appointments" message
            refreshView("No appointments found");
        }
        else{
            //show the list view of appointments
            populateRecyclerView(tabPosition);
        }



        return appointmentFragment;
    }

    private void populateRecyclerView(int position) {
        //the recycler view will use linear layout to show the cards
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        appointmentsView.setLayoutManager(mLayoutManager);
        //populate the adapter with appointments lists
        appointmentAdapter = new AppointmentAdapter(mContext, appointmentList,position,this);
        appointmentsView.setAdapter(appointmentAdapter);
    }

    @Override
    public void refreshView(String message) {
            //if no appointments are found then the following message will be shown
            LinearLayout linearLayout = (LinearLayout) appointmentFragment.findViewById(R.id.appointment_layout);
            linearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            TextView txtView = (TextView) appointmentFragment.findViewById(R.id.placeholdertext);
            //txtView.setText("No appointments found");
            txtView.setText(message);
            txtView.setVisibility(View.VISIBLE);
            appointmentsView.setVisibility(View.GONE);

    }


}
