package sg.edu.nus.iss.medipal.adapter;

import android.app.Activity;
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
import sg.edu.nus.iss.medipal.activity.EditAppointmentActivity;
import sg.edu.nus.iss.medipal.interfaces.AdapterCallbackInterface;
import sg.edu.nus.iss.medipal.manager.AppointmentManager;
import sg.edu.nus.iss.medipal.manager.PreferenceManager;
import sg.edu.nus.iss.medipal.pojo.Appointment;

/**
 * Created by : Navi on 14-03-2017.
 * Description : This is the recycler adapter for showing current appointments
 * Modified by :
 * Reason for modification :
 */

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {
    private PreferenceManager appointmentPreference;
    private Context mContext;
    private List<Appointment> appointmentList;
    private AdapterCallbackInterface mCallback;
    private Integer fragmentPosition;

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
            description = (TextView) view.findViewById(R.id.appointmentdescription);
            delete = (ImageView) view.findViewById(R.id.delete);
            if(fragmentPosition == 0) {
                edit = (ImageView) view.findViewById(R.id.edit);
                remainder = (TextView) view.findViewById(R.id.appointmentremainder);


                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent appointmentEdit = new Intent(mContext, EditAppointmentActivity.class);
                        appointmentEdit.putExtra("Id", title.getTag().toString());
                        appointmentEdit.putExtra("title", title.getText().toString());
                        String dttime = datetime.getText().toString();
                        String dt[] = dttime.split(" ", 2);
                        appointmentEdit.putExtra("date", dt[0]);
                        appointmentEdit.putExtra("time", dt[1]);
                        appointmentEdit.putExtra("location", location.getText().toString());
                        appointmentEdit.putExtra("remainder", remainder.getTag().toString());
                        appointmentEdit.putExtra("desc", description.getText().toString());
                        ((Activity) mContext).startActivityForResult(appointmentEdit, 102);
                    }
                });
            }

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setMessage("Are you sure you want to delete this Appointment?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    AppointmentManager appointmentManager = new AppointmentManager(mContext);
                                    appointmentManager.deleteAppointment(title.getTag().toString());
                                    delete(getAdapterPosition());
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });
        }
    }

    public AppointmentAdapter(Context mContext, List<Appointment> appointmentList, Integer fragmentPosition, AdapterCallbackInterface mCallback) {
        this.mContext = mContext;
        this.appointmentList = appointmentList;
        appointmentPreference = new PreferenceManager(mContext);
        this.mCallback = mCallback;
        this.fragmentPosition = fragmentPosition;
    }

    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(fragmentPosition == 0) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.appointment_card_current, parent, false);
        }
        else
        {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.appointment_card_past, parent, false);
        }
        return new AppointmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AppointmentViewHolder holder, int position) {
        String storedString, remainder = null,remainderDesc = null, title = null;

        Appointment appointment = appointmentList.get(position);

        storedString = appointmentPreference.getAppointmentInfo(Integer.toString(appointment.getId()));
        if(storedString != null) {
            try {
                JSONArray jsonArray = new JSONArray(storedString);

                title = jsonArray.getString(0);
                remainder = jsonArray.getString(1);

                if (remainder.equals("No Remainder"))
                    remainderDesc = "No remainder set";
                else
                    remainderDesc = "Remainder set " + remainder.toLowerCase();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            title = "Title not Found";
            remainderDesc = "No remainder found";
        }

            holder.title.setText(title);
            holder.title.setTag(appointment.getId());
            holder.datetime.setText(appointment.getAppointment());
            holder.location.setText(appointment.getLocation());
            holder.description.setText(appointment.getDescription());
            if(fragmentPosition == 0) {
                holder.remainder.setText(remainderDesc);
                holder.remainder.setTag(remainder);
            }
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public void delete(int position) { //removes the row
        appointmentList.remove(position);
        notifyItemRemoved(position);
        if(appointmentList.size() == 0)
        {
            mCallback.refreshView();
        }
    }
}
