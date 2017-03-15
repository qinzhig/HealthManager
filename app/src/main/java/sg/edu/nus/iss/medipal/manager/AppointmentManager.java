package sg.edu.nus.iss.medipal.manager;

import android.content.Context;;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sg.edu.nus.iss.medipal.dao.AppointmentDAO;
import sg.edu.nus.iss.medipal.pojo.Appointment;

/**
 * Created by : Navi on 15-03-2017.
 * Description : This is the manager class to for handling all operations concerning appointments
 * Modified by :
 * Reason for modification :
 */

public class AppointmentManager {
    private Appointment appointment;
    private AppointmentDAO appointmentDAO;
    private String remainderTitle;
    private String remainderInterval;
    private Context context;

    PreferenceManager appointmentPreference;

    public AppointmentManager(String title, String location, String datetime, String description,String remainderInterval,Context context ) {
        this.context = context;
        this.remainderInterval = remainderInterval;
        this.remainderTitle = title;
        appointment = new Appointment(null,location,datetime,description);
        appointmentDAO = new AppointmentDAO(context);
        appointmentPreference = new PreferenceManager(context);
    }

    public void addAppointment()
    {
        determineReminderTime(remainderInterval,appointment.getAppointment());
       // appointmentDAO.insert(appointment);


    }

    private void determineReminderTime(String remainderInterval, String appointment)  {
        Log.d("date:",appointment);
        Integer interval = decodeRemainderInterval(remainderInterval);
        Log.d("interval:",interval.toString());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        try {
            Date date = df.parse(appointment);
            Log.d("date", df.format(date));
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MINUTE,-interval);
            Log.d("date", df.format(c.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private Integer decodeRemainderInterval(String remainderInterval) {
        Integer retVal = 0;
        switch (remainderInterval){
            case "No Remainder":
                retVal = 0;
                break;
            case "15 Minutes Before":
                retVal = 15;
                break;
            case "30 Minutes Before":
                retVal = 30;
                break;
            case "1 Hour Before":
                retVal = 60;
                break;
            case "4 Hours Before":
                retVal = 240;
                break;
            case "12 Hours Before":
                retVal = 720;
                break;
            case "1 Day Before":
                retVal = 1440;
                break;
        }
        return retVal;
    }
}
