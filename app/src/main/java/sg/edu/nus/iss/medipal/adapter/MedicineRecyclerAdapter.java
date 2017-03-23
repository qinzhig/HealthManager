package sg.edu.nus.iss.medipal.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.activity.AddEditAppointmentActivity;
import sg.edu.nus.iss.medipal.interfaces.AdapterCallbackInterface;
import sg.edu.nus.iss.medipal.manager.AppointmentManager;
import sg.edu.nus.iss.medipal.manager.PreferenceManager;
import sg.edu.nus.iss.medipal.pojo.Appointment;
import sg.edu.nus.iss.medipal.pojo.Medicine;

/**
 * Created by : Navi on 22-03-2017.
 * Description : This is the recycler adapter for showing medicines
 * Modified by :
 * Reason for modification :
 */
/*
public class MedicineRecyclerAdapter extends RecyclerView.Adapter<MedicineRecyclerAdapter.MedicineViewHolder> {
    private PreferenceManager appointmentPreference;
    private Context mContext;
    private List<Medicine> medicineList;
    private Integer fragmentPosition;

    //callback listener to communicate with the parent activity
    private AdapterCallbackInterface mCallback;


    //custom view holder to show the appointment details as card
    public class AppointmentViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView datetime;
        public TextView location;
        public TextView remainderOne;
        public TextView remainderTwo;
        public TextView description;
        public ImageView edit;
        public ImageView delete;
        public CardView cardView;

        private PopupWindow cardPopUp;

        public AppointmentViewHolder(View view, final View popUp) {
            super(view);
            //get reference to the card view elements
            cardView = (CardView)view.findViewById(R.id.card_view);
            title = (TextView) view.findViewById(R.id.appointmenttitle);
            datetime = (TextView) view.findViewById(R.id.appointmentdatetime);
            location = (TextView) view.findViewById(R.id.appointmentlocation);
            description = (TextView) view.findViewById(R.id.appointmentdescription);
            delete = (ImageView) view.findViewById(R.id.delete);
            edit = (ImageView) view.findViewById(R.id.edit);
            remainderOne = (TextView) view.findViewById(R.id.appointmentremainder_one);
            remainderTwo = (TextView) view.findViewById(R.id.appointmentremainder_two);

            if(fromHomeFragment) {
                delete.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);
            }

            //edit features are to made available for active(upcoming) appointments only
            if(fragmentPosition == UPCOMING_APPOINTMENTS) {
                //listener for editing appointments
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //passing the current information to the activity for showing in view
                        Intent appointmentEdit = new Intent(mContext, AddEditAppointmentActivity.class);
                        //flag to tell activity to show edit view
                        appointmentEdit.putExtra("isEdit",true);
                        appointmentEdit.putExtra("Id", title.getTag().toString());
                        appointmentEdit.putExtra("title", title.getText().toString());
                        String dtTime = datetime.getText().toString();
                        String dt[] = dtTime.split(" ", 2);
                        appointmentEdit.putExtra("date", dt[0]);
                        appointmentEdit.putExtra("time", dt[1]);
                        appointmentEdit.putExtra("location", location.getText().toString());
                        appointmentEdit.putExtra("remainderOne", remainderOne.getTag().toString());
                        appointmentEdit.putExtra("remainderTwo", remainderTwo.getTag().toString());
                        appointmentEdit.putExtra("desc", description.getText().toString());
                        ((Activity)mContext).startActivityForResult(appointmentEdit,102);
                    }
                });
            }
            else
            {
                edit.setVisibility(View.GONE);
                remainderOne.setVisibility(View.GONE);
                remainderTwo.setVisibility(View.GONE);
            }
            //listener for deleting the appointments
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setMessage("Are you sure you want to delete this Appointment?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    AppointmentManager appointmentManager = new AppointmentManager(mContext);
                                    //delete from db and shared preferences
                                    appointmentManager.deleteAppointment(title.getTag().toString());
                                    //refreshing the current view
                                    delete(getAdapterPosition());
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((TextView)popUp.findViewById(R.id.appointmenttitle)).setText(title.getText());
                    ((TextView)popUp.findViewById(R.id.appointmentdatetime)).setText(datetime.getText());
                    ((TextView)popUp.findViewById(R.id.appointmentlocation)).setText(location.getText());
                    ((TextView)popUp.findViewById(R.id.appointmentdescription)).setText(description.getText());
                    ((TextView) popUp.findViewById(R.id.appointmentremainder_one)).setText(remainderOne.getText());
                    ((TextView) popUp.findViewById(R.id.appointmentremainder_two)).setText(remainderTwo.getText());

                    if(fragmentPosition != UPCOMING_APPOINTMENTS) {
                        popUp.findViewById(R.id.appointmentremainder_one).setVisibility(View.GONE);
                        popUp.findViewById(R.id.appointmentremainder_two).setVisibility(View.GONE);
                        popUp.findViewById(R.id.title4).setVisibility(View.GONE);
                        popUp.findViewById(R.id.title6).setVisibility(View.GONE);
                    }

                    cardPopUp = new PopupWindow(popUp, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    cardPopUp.showAtLocation(popUp, Gravity.CENTER, 0, 0);
                }
            });
        }
    }
    //constructor for adapter
    public MedicineRecyclerAdapter(Context mContext, List<Appointment> appointmentList, Integer fragmentPosition, AdapterCallbackInterface mCallback) {
        this.mContext = mContext;
        this.appointmentList = appointmentList;
        appointmentPreference = new PreferenceManager(mContext);
        this.mCallback = mCallback;
        if(fragmentPosition == 2) {
            fromHomeFragment = true;
            fragmentPosition = 0;
        }
        this.fragmentPosition = fragmentPosition;
    }

    //called once in beginning to load the view
    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointment_card_list, parent, false);
        View popUp = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointment_card_single, parent, false);

        return new AppointmentViewHolder(itemView,popUp);
    }

    //used to populate the view elements with adapter data
    @Override
    public void onBindViewHolder(final AppointmentViewHolder holder, int position) {
        String storedStringOne,storedStringTwo, remainderOne = null,remainderTwo = null,remainderOneDesc = null, remainderTwoDesc = null,title = null;
        //get appointment data from list using current position as index
        Appointment appointment = appointmentList.get(position);

        //Appointment title and remainder details are stored in shared preferences as we do not have fields for it in db table
        storedStringOne = appointmentPreference.getAppointmentInfo(Integer.toString(appointment.getId()));
        storedStringTwo = appointmentPreference.getAppointmentInfo(Integer.toString(appointment.getId() + AppointmentManager.REMAINDER_ID_OFFSET));
        if(storedStringOne != null) {
            try {
                JSONArray jsonArray = new JSONArray(storedStringOne);
                //get appointment title and remainder info
                title = jsonArray.getString(0);
                remainderOne = jsonArray.getString(1);
                if(remainderOne.equalsIgnoreCase("No Pre-Test Remainders set."))
                    remainderOneDesc = remainderOne;
                else
                    remainderOneDesc = "Pre-Test Remainder set " + remainderOne.toLowerCase();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
            remainderOneDesc = "No Appointment Remainder set";

        if(storedStringTwo != null) {
            try {
                JSONArray jsonArray = new JSONArray(storedStringTwo);
                //get appointment title and remainder info
                title = jsonArray.getString(0);
                remainderTwo = jsonArray.getString(1);
                if (remainderTwo.equalsIgnoreCase("No Appointment Remainders set."))
                    remainderTwoDesc = remainderTwo;
                else
                    remainderTwoDesc = "Appointment Remainder set " + remainderTwo.toLowerCase();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
            remainderTwoDesc = "No Pre-Test Remainder set";

        if((storedStringOne == null) &&(storedStringTwo == null))
        {
            //foolproofing- flow will come here if user manually clears the app data
            title = "Title not Found";
        }

        //populate the view elements
            holder.title.setText(title);
            holder.title.setTag(appointment.getId());
            holder.datetime.setText(appointment.getAppointment());
            holder.location.setText(appointment.getLocation());
            holder.description.setText(appointment.getDescription());
            if(fragmentPosition == UPCOMING_APPOINTMENTS) {
                holder.remainderOne.setText(remainderOneDesc);
                holder.remainderOne.setTag(remainderOne);
                holder.remainderTwo.setText(remainderTwoDesc);
                holder.remainderTwo.setTag(remainderTwo);
            }
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    //refresh recycler view
    public void delete(int position) {
        appointmentList.remove(position);
        notifyItemRemoved(position);
        //callback to main activity if all the adapter data is deleted
        if(appointmentList.size() == 0)
        {
            mCallback.refreshView();
        }
    }
}*/
