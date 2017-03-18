package sg.edu.nus.iss.medipal.fragment;

import android.content.Context;
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
import sg.edu.nus.iss.medipal.activity.AddAppointmentActivity;
import sg.edu.nus.iss.medipal.adapter.AppointmentAdapter;
import sg.edu.nus.iss.medipal.interfaces.AdapterCallbackInterface;
import sg.edu.nus.iss.medipal.manager.AppointmentManager;
import sg.edu.nus.iss.medipal.pojo.Appointment;

/**
 * Created by issuser on 18/3/2017.
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

        FloatingActionButton aFab;

        final Bundle args = getArguments();
        final int tabPosition = args.getInt("position");

        //using the appointment_main xml to show in the main fragment
        appointmentFragment = inflater.inflate(R.layout.appointment_main, container, false);

        //get reference to the current context(
        mContext = appointmentFragment.getContext();
        //get the reference to appointmentManager object
        appointmentManager = new AppointmentManager(mContext);
        //get the appointment details from the appointment table
        appointmentList = appointmentManager.getAppointments(tabPosition);

     /*   appointmentList = new ArrayList<Appointment>();
        appointmentList.add(new Appointment(1,"Singapore","01-04-2017","Test Appointment 1"));
        appointmentList.add(new Appointment(2,"Singapore","02-04-2017","Test Appointment 2"));*/

        //get reference to the recyclerview
        appointmentsView = (RecyclerView) appointmentFragment.findViewById(R.id.appointmentrecycler_view);

        if(appointmentList.isEmpty())
        {
            refreshView();
        }
        else {
            populateRecyclerView(tabPosition);
        }

        aFab = (FloatingActionButton)appointmentFragment.findViewById(R.id.fab);
        aFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivityForResult(new Intent(mContext, AddAppointmentActivity.class),101);

            }
        });

        return appointmentFragment;
    }

    public void test(){

    }


    private void populateRecyclerView(int position) {

        //the recycler view will use linear layout to show the cards (later can be changed if needed)
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        appointmentsView.setLayoutManager(mLayoutManager);

            appointmentAdapter = new AppointmentAdapter(mContext, appointmentList,position,this);

        appointmentsView.setAdapter(appointmentAdapter);
    }

    @Override
    public void refreshView() {
        TextView txtView = (TextView) appointmentFragment.findViewById(R.id.placeholdertext);
        txtView.setText("No appointments found");
        txtView.setVisibility(View.VISIBLE);
        appointmentsView.setVisibility(View.GONE);
    }
}
