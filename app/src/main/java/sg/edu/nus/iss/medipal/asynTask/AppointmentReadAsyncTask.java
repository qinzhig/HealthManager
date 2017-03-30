package sg.edu.nus.iss.medipal.asynTask;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import sg.edu.nus.iss.medipal.dao.AppointmentDAO;
import sg.edu.nus.iss.medipal.interfaces.AsyncTaskCallbacks;
import sg.edu.nus.iss.medipal.pojo.Appointment;

/**
 * Created by : Navi on 19-03-2017.
 * Description : This is the AsyncTask class for performing appointment table inserts
 * Modified by :
 * Reason for modification :
 */

public class AppointmentReadAsyncTask extends AsyncTask<Object, Void, ArrayList<Appointment>> {
    private AppointmentDAO appointmentDAO;
    private Context mContext;
    ArrayList<Appointment> appointmentList;

    public AppointmentReadAsyncTask(Context context) {
        mContext = context;
        this.appointmentDAO = new AppointmentDAO(context);
        appointmentList = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Appointment> doInBackground(Object... args) {
        if(args.length == 1) {
            appointmentList = appointmentDAO.getAppointments((Boolean) args[0]);
        }
        else
        {
            appointmentList = appointmentDAO.getAppointment((String) args[0]);
        }
        return appointmentList;
    }

    @Override
    protected void onPostExecute(ArrayList<Appointment> appointmentList) {
        if (appointmentDAO != null)
            appointmentDAO.close();
    }
}