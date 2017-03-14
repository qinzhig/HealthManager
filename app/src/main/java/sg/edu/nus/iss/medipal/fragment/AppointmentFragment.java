package sg.edu.nus.iss.medipal.fragment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.adapter.AppointmentAdapter;
import sg.edu.nus.iss.medipal.dao.AppointmentDAO;
import sg.edu.nus.iss.medipal.pojo.Appointment;

/**
 * Created by : Navi on 06-03-2017.
 * Description : This is the main view for Appointment
 * Modified by :
 * Reason for modification :
 */

public class AppointmentFragment extends Fragment {
    private RecyclerView appointmentsView;
    private AppointmentAdapter appointmentAdapter;
    private List<Appointment> appointmentList;
    private AppointmentDAO appointmentDAO;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //using the appointment_main xml to show in the main fragment
        View appointmentFragment = inflater.inflate(R.layout.appointment_main,container,false);

        //get reference to the current context(
        mContext = appointmentFragment.getContext();

        //get reference to the recyclerview
        appointmentsView = (RecyclerView) appointmentFragment.findViewById(R.id.appointmentrecycler_view);
        //get the reference to appointmentDAO opbject
        appointmentDAO = new AppointmentDAO(mContext);

        //get the appointment details from the appointment table
        //appointmentList = appointmentDAO.getAppointments();

        appointmentList = new ArrayList<Appointment>();
        appointmentList.add(new Appointment(1,"Singapore","01-04-2017","Test Appointment 1"));
        appointmentList.add(new Appointment(2,"Singapore","02-04-2017","Test Appointment 2"));


        populateRecyclerView();

        return appointmentFragment;
    }


    private void populateRecyclerView() {
        //the recycler view will use linear layout to show the cards (later can be changed if needed)
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        appointmentsView.setLayoutManager(mLayoutManager);

        //set the adapter with the appointment list
        appointmentAdapter = new AppointmentAdapter(mContext, appointmentList);

        appointmentsView.setAdapter(appointmentAdapter);
    }
}
