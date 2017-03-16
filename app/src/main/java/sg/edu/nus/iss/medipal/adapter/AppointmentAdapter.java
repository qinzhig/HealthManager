package sg.edu.nus.iss.medipal.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.manager.AppointmentManager;
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
    public String appointmentId;
    public class AppointmentViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView datetime;
        public TextView location;
        public TextView remainder;
        public TextView description;
        public ImageView edit;
        public ImageView delete;


        public AppointmentViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.appointmenttitle);
            datetime = (TextView) view.findViewById(R.id.appointmentdatetime);
            location = (TextView) view.findViewById(R.id.appointmentlocation);
            remainder = (TextView) view.findViewById(R.id.appointmentremainder);
            description = (TextView) view.findViewById(R.id.appointmentdescription);
            edit = (ImageView) view.findViewById(R.id.edit);
            delete = (ImageView) view.findViewById(R.id.delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setMessage("Are you sure you want to delete this Appointment?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    AppointmentManager appointmentManager = new AppointmentManager(mContext);
                                    appointmentManager.deleteAppointment(appointmentId);
                                    delete(getAdapterPosition());
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });
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
        String  storedString,
                title = null,
                remainder = null;
        Appointment appointment = appointmentList.get(position);
        appointmentId =Integer.toString(appointment.getId());
        storedString = appointmentPreference.getAppointmentInfo(appointmentId);
        try {
            JSONArray jsonArray = new JSONArray(storedString);

            title = jsonArray.getString(0);
            remainder = jsonArray.getString(1);

            if(remainder.equals("No Remainder"))
                remainder = "No remainder set";
            else
                remainder ="Remainder set "+remainder.toLowerCase();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.title.setText(title);
        holder.datetime.setText(appointment.getAppointment());
        holder.location.setText(appointment.getLocation());
        holder.description.setText(appointment.getDescription());
        holder.remainder.setText(remainder);
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public void delete(int position) { //removes the row
        appointmentList.remove(position);
        notifyItemRemoved(position);
    }

}