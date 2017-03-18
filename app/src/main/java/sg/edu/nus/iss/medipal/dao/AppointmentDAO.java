package sg.edu.nus.iss.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;

import sg.edu.nus.iss.medipal.manager.DataBaseManager;
import sg.edu.nus.iss.medipal.pojo.Appointment;
import sg.edu.nus.iss.medipal.utils.DataBaseUtility;

/**
 * Created by : Navi on 06-03-2017.
 * Description : This is the DAO implementation for Appointment table
 * Modified by :
 * Reason for modification :
 */

public class AppointmentDAO extends DataBaseUtility {

    private static final String WHERE_ID_EQUALS = DataBaseManager.APPMNT_ID + " =?";

    public AppointmentDAO(Context context) {
        super(context);
    }

    //method to insert into appointment table
    public long insert(Appointment appointment)throws SQLException
    {
        //return value
        long retCode = 0;
        //use the appointment pojo to populate the table column values
        ContentValues values =  new ContentValues();
        values.put(DataBaseManager.APPMNT_LOCATION, appointment.getLocation());
        values.put(DataBaseManager.APPMNT_DATETIME,appointment.getAppointment());
        values.put(DataBaseManager.APPMNT_DESCRIPTION, appointment.getDescription());

        //Insert into appointment table. If insertion is successfull then the method returns the row ID, else -1
        try {

            retCode = database.insertOrThrow(DataBaseManager.APPOINTMENT_TABLE, null, values);
        }
        catch (SQLException sqlE)
        {
            sqlE.printStackTrace(); //unexpected error while inserting.
            retCode = -1; //set return value to error code so that caller can handle error
        }

        Log.d("insert","Insert Successfull");
        return retCode;
    }

    //method to update the appointment table
    public long update(Appointment appointment) {
        //return value
        long retCode = 0;

        //use the appointment pojo to populate the table column values
        ContentValues values = new ContentValues();

        //get the values from pojo that are intended to updated into the table.
        // if the pojo getter method returns null means the corresponding column should not be updated
        if(appointment.getLocation() != null)
            values.put(DataBaseManager.APPMNT_LOCATION, appointment.getLocation());
        if(appointment.getAppointment() != null)
            values.put(DataBaseManager.APPMNT_DATETIME,appointment.getAppointment());
        if(appointment.getDescription() != null)
            values.put(DataBaseManager.APPMNT_DESCRIPTION, appointment.getDescription());

        //method returns number of rows affected. so if it is zero some error handling needs to be done by caller
        retCode = database.update(DataBaseManager.APPOINTMENT_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(appointment.getId()) });
        return retCode;

    }

    //get list of appointments
    public ArrayList<Appointment> getAppointments() {
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();

        //similiar to query "select * from appointments"
        Cursor cursor = database.query(DataBaseManager.APPOINTMENT_TABLE,
                new String[] {
                        DataBaseManager.APPMNT_ID,
                        DataBaseManager.APPMNT_LOCATION,
                        DataBaseManager.APPMNT_DATETIME,
                        DataBaseManager.APPMNT_DESCRIPTION }, null, null, null,
                null, null);

        //loop through each result set to populate the appointment pojo and add to the list each time
        while (cursor.moveToNext()) {
            Log.d("Inside getApp","Inside");
            int id = cursor.getInt(0);
            String location = cursor.getString(1);
            String datetime = cursor.getString(2);
            String desc = cursor.getString(3);

            Appointment appointment = new Appointment(id,location,datetime,desc);
            appointments.add(appointment);

        }

        return appointments;
    }

    public void deleteAppointment(String appointmentId) {

        database.delete(DataBaseManager.APPOINTMENT_TABLE, DataBaseManager.APPMNT_ID+"= ?" , new String[]{appointmentId});
    }
}
