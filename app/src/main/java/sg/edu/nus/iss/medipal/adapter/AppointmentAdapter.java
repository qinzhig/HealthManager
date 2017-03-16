package sg.edu.nus.iss.medipal.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.manager.PreferenceManager;
import sg.edu.nus.iss.medipal.pojo.Appointment;

/**
 * Created by : Navi on 14-03-2017.
 * Description : This is the recycler adapter for appointment activity
 * Modified by :
 * Reason for modification :
 */

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {
    private PreferenceManager appointmentPreference;
    private Context mContext;
    private List<Appointment> appointmentList;

    public class AppointmentViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView datetime;
        public TextView location;
        public TextView description;

        public AppointmentViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.appointmenttitle);
            datetime = (TextView) view.findViewById(R.id.appointmentdatetime);
            location = (TextView) view.findViewById(R.id.appointmentlocation);
            description = (TextView) view.findViewById(R.id.appointmentdescription);
        }
    }

    public AppointmentAdapter(Context mContext, List<Appointment> appointmentList) {
        this.mContext = mContext;
        this.appointmentList = appointmentList;
        appointmentPreference = new PreferenceManager(mContext);
    }

    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointment_card, parent, false);

        return new AppointmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AppointmentViewHolder holder, int position) {
        Appointment appointment = appointmentList.get(position);
        holder.title.setText(appointmentPreference.getAppointmentInfo(Integer.toString(appointment.getId())));
        holder.datetime.setText(appointment.getAppointment());
        holder.location.setText(appointment.getLocation());
        holder.description.setText(appointment.getDescription());
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }
}