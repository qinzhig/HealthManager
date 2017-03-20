package sg.edu.nus.iss.medipal.manager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.medipal.asynTask.AppointmentReadAsyncTask;
import sg.edu.nus.iss.medipal.asynTask.AppointmentWriteAsyncTask;
import sg.edu.nus.iss.medipal.dao.AppointmentDAO;
import sg.edu.nus.iss.medipal.interfaces.AsyncTaskCallbacks;
import sg.edu.nus.iss.medipal.pojo.Appointment;
import sg.edu.nus.iss.medipal.service.RemindAlarmReceiver;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

;

/**
 * Created by : Navi on 15-03-2017.
 * Description : This is the manager class to for handling all operations concerning appointments
 * Modified by :
 * Reason for modification :
 */

public class AppointmentManager {
    private Appointment appointment;
    private AppointmentDAO appointmentDAO;
    private String appointmentTitle;
    private String[] remainderIntervals;
    private Context context;

    public static final Integer REMAINDER_ID_OFFSET = 100000 ;

    PreferenceManager appointmentPreference;

    //constructor 1 for setting all the parameters
    public AppointmentManager(String title, String location, String datetime, String description, String[] remainderIntervals, Context context) {
        this.context = context;
        this.remainderIntervals = new String[2];
        this.remainderIntervals[0] = remainderIntervals[0];
        this.remainderIntervals[1] = remainderIntervals[1];
        this.appointmentTitle = title;
        appointment = new Appointment(null, location, datetime, description);
        appointmentDAO = new AppointmentDAO(context);
        appointmentPreference = new PreferenceManager(context);
    }

    //constructor 2 for setting only context value. Used for delete operations
    public AppointmentManager(Context context) {
        this.context = context;
        appointmentDAO = new AppointmentDAO(context);
        appointmentPreference = new PreferenceManager(context);
    }

    //Store the appointment details in db and shared preferences, set remainders if user has given inputs
    public void addAppointment() {

        //using async task to perform db call
        new AppointmentWriteAsyncTask(context, new AsyncTaskCallbacks() {
            //using AsyncTaskCallbacks interface as a listener to execute the post tasks of creating and storing remainder
            @Override
            public void dbOperationStatus(boolean resultStatus, Long resultValue) {
                Integer resultIntValue = resultValue.intValue();
                Log.d("status,result", Boolean.toString(resultStatus) + " " + Long.toString(resultValue));

                if (resultStatus && remainderIntervals != null) {
                    int newId = resultIntValue + REMAINDER_ID_OFFSET;
                        Calendar remainderDateOne, remainderDateTwo;
                        //get the remainder date based on the remainderInterval given by user and set remainder if provided
                        if (!remainderIntervals[0].equalsIgnoreCase("No Pre-Test Remainders set.")) {
                            remainderDateOne = MediPalUtility.determineReminderTime(remainderIntervals[0], appointment.getAppointment());
                            setupAlarmForReminder(remainderDateOne, resultIntValue);
                        }
                        if (!remainderIntervals[1].equalsIgnoreCase("No Appointment Remainders set.")) {
                            remainderDateTwo = MediPalUtility.determineReminderTime(remainderIntervals[1], appointment.getAppointment());
                            setupAlarmForReminder(remainderDateTwo, newId);
                        }
                    //store the appointment Id,title and remainder in shared preferences. will be used in displaying remainders and appointments
                    storeAppointmentPreference(resultIntValue, remainderIntervals[0]);
                    storeAppointmentPreference(newId, remainderIntervals[1]);
                }
                else {
                    //uphandled exceptions. lets hope this does not happen during grading...
                    Toast.makeText(context, "something went wrong :-(", Toast.LENGTH_LONG).show();
                }
            }
        }).execute(appointment, true);
    }

    //to set remainder using AlarmManager service and registering an pending intent
    private void setupAlarmForReminder(Calendar remainderDate, Integer resultValue) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //once remainder time is reached, alarm manager will call RemainAlarmReceiver class
        Intent alertIntent = new Intent(context, RemindAlarmReceiver.class);
        alertIntent.putExtra("Id", Integer.toString(resultValue));

        PendingIntent broadcast = PendingIntent.getBroadcast(context, resultValue, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //Log.d("check",Long.toString(date.getTime()));
        Log.d("check",
                Long.toString(remainderDate.get(Calendar.DAY_OF_MONTH)) + " " + Long.toString(remainderDate.get(Calendar.MONTH)) + " " + Long.toString(remainderDate.get(Calendar.YEAR)) + " " + Long.toString(remainderDate.get(Calendar.HOUR_OF_DAY)) + " " + Long.toString(remainderDate.get(Calendar.MINUTE)));
        Log.d("doublecheck", Long.toString(new GregorianCalendar().getTimeInMillis() + (60 * 1000)));
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, remainderDate.getTimeInMillis(), broadcast);
        //alarmManager.set(AlarmManager.RTC_WAKEUP, new GregorianCalendar().getTimeInMillis() +(5*1000), broadcast);
    }

    //store the remainder details in shared preferences
    private void storeAppointmentPreference(Integer resultValue, String remainderInterval) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(appointmentTitle);
        jsonArray.put(remainderInterval);
        appointmentPreference.storeAppointmentInfo(Integer.toString(resultValue), jsonArray.toString());
    }

    public void saveAppointment(String id) {
        final int appointmentID = Integer.valueOf(id);
        appointment.setId(appointmentID);
        //using async task to perform db call
        new AppointmentWriteAsyncTask(context, new AsyncTaskCallbacks() {
            //using AsyncTaskCallbacks interface as a listener to execute the post tasks of creating and storing remainder
            @Override
            public void dbOperationStatus(boolean resultStatus, Long resultValue) {
                Integer resultIntValue = resultValue.intValue();
                Log.d("status,result", Boolean.toString(resultStatus) + " " + Long.toString(resultValue));

                if (resultStatus && remainderIntervals != null) {
                    int newId = appointmentID + REMAINDER_ID_OFFSET;
                    Calendar remainderDateOne, remainderDateTwo;
                    //get the remainder date based on the remainderInterval given by user and set remainder if provided
                    if (!remainderIntervals[0].equalsIgnoreCase("No Pre-Test Remainders set.")) {
                        remainderDateOne = MediPalUtility.determineReminderTime(remainderIntervals[0], appointment.getAppointment());
                        setupAlarmForReminder(remainderDateOne, appointmentID);
                    }
                    if (!remainderIntervals[1].equalsIgnoreCase("No Appointment Remainders set.")) {
                        remainderDateTwo = MediPalUtility.determineReminderTime(remainderIntervals[1], appointment.getAppointment());
                        setupAlarmForReminder(remainderDateTwo, newId);
                    }
                    //store the appointment Id,title and remainder in shared preferences. will be used in displaying remainders and appointments
                    storeAppointmentPreference(appointmentID, remainderIntervals[0]);
                    storeAppointmentPreference(newId, remainderIntervals[1]);

                } else {
                    //uphandled exceptions. lets hope this does not happen during grading...
                    Toast.makeText(context, "something went wrong :-(", Toast.LENGTH_LONG).show();
                }
            }
        }).execute(appointment, false);
    }

    //delete an appointment
     public boolean deleteAppointment(String appointmentId)
    {
        new AppointmentWriteAsyncTask(context, appointmentId, new AsyncTaskCallbacks() {
            @Override
            public void dbOperationStatus(boolean resultStatus, Long resultValue) {
                if(resultStatus)
                    Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(context, "something went wrong :-(", Toast.LENGTH_LONG).show();
            }
        }).execute(appointmentId);

        appointmentPreference.deleteAppointmentInfo(appointmentId);
        return true;
    }

    //get the appointments list from db
    public ArrayList<Appointment> getAppointments(int position)
    {
        Boolean deciderFlag;
        if(position == 0) //fetch upcoming appointments
            deciderFlag=true;
        else             //fetch previous appointments
            deciderFlag = false;

        //No call backs have been used here as no postprocessing in needed for this function
        ArrayList<Appointment> appointmentList = null;
        try {
            appointmentList = new AppointmentReadAsyncTask(context).execute(deciderFlag).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return appointmentList;
    }


}
