package sg.edu.nus.iss.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.util.ArrayList;

import sg.edu.nus.iss.medipal.pojo.Medicine;
import sg.edu.nus.iss.medipal.pojo.Reminder;
import sg.edu.nus.iss.medipal.utils.DataBaseUtility;

import static sg.edu.nus.iss.medipal.manager.DataBaseManager.REMINDER_FREQUENCY;
import static sg.edu.nus.iss.medipal.manager.DataBaseManager.REMINDER_ID;
import static sg.edu.nus.iss.medipal.manager.DataBaseManager.REMINDER_INTERVAL;
import static sg.edu.nus.iss.medipal.manager.DataBaseManager.REMINDER_TABLE;
import static sg.edu.nus.iss.medipal.manager.DataBaseManager.REMINDER_TIME;

/**
 * Created by zhiguo on 19/3/17.
 */

public class ReminderDAO extends DataBaseUtility {
    private static final String WHERE_ID_EQUALS = REMINDER_ID + " =?";

    public ReminderDAO(Context context) {

        super(context);
    }

    public Reminder find(Medicine medicine) throws  SQLException
    {
        Cursor cursor;

        //Search out the reminder from SQLite

            cursor = database.query(REMINDER_TABLE,
                    new String[] {
                    REMINDER_ID,
                    REMINDER_FREQUENCY,
                    REMINDER_TIME,
                    REMINDER_INTERVAL,
                    },  WHERE_ID_EQUALS, new String[]{String.valueOf(medicine.getReminderId())},null,null,null);

        if(cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            int frequency = cursor.getInt(1);
            String startTime = cursor.getString(2);
            int interval = cursor.getInt(3);

            Reminder reminder_node = new Reminder(id,frequency,startTime,interval);

            return reminder_node;
        }else{
            return null;
        }


    }

    //Method to insert data to REMINDER table
    public long insert(Reminder reminder)throws SQLException
    {
        //return value
        long retCode = 0;
        //use the REMINDER pojo to populate the table column values
        ContentValues values =  new ContentValues();
        values.put(REMINDER_FREQUENCY, reminder.getFrequency());
        values.put(REMINDER_TIME, reminder.getStartTime());
        values.put(REMINDER_INTERVAL, reminder.getInterval());


        //Insert data into REMINDER table. If insertion is successfull then the method returns the row ID, else -1
        try {
            retCode = database.insertOrThrow(REMINDER_TABLE, null, values);
        }
        catch (SQLException sqlE)
        {
            sqlE.printStackTrace(); //unexpected error while inserting.
            retCode = -1; //set return value to error code so that caller can handle error
        }
        return retCode;
    }

    //method to delete the Reminder in database
    public long delete(Reminder reminder) throws SQLException
    {
        long retCode=0;

        //Delete the medicine from SQLite
        try {

            retCode = database.delete(REMINDER_TABLE, WHERE_ID_EQUALS, new String[]{String.valueOf(reminder.getId())});
        }catch (SQLException sqlE){
            sqlE.printStackTrace(); //unexpected error while inserting.
            retCode = -1; //set return value to error code so that caller can handle error
        }

        return retCode;
    }

    //method to update the REMINDER table
    public long update(Reminder reminder) {
        //return value
        long retCode = 0;

        //use the REMINDER pojo to populate the table column values
        ContentValues values = new ContentValues();

        values.put(REMINDER_FREQUENCY, reminder.getFrequency());
        values.put(REMINDER_TIME, reminder.getStartTime());
        values.put(REMINDER_INTERVAL, reminder.getInterval());

        //method returns number of rows affected. so if it is zero some error handling needs to be done by caller
        try {
            retCode = database.update(REMINDER_TABLE, values,
                    WHERE_ID_EQUALS,
                    new String[]{String.valueOf(reminder.getId())});
        }catch (SQLException sqlE){
            sqlE.printStackTrace(); //unexpected error while inserting.
            retCode = -1; //set return value to error code so that caller can handle error
        }
        return retCode;

    }

    //get list of REMINDERs
    public ArrayList<Reminder> getReminders() {
        ArrayList<Reminder> reminders = new ArrayList<Reminder>();

        //similiar to query "select * from REMINDERs"
        Cursor cursor = database.query(REMINDER_TABLE,
                new String[] {
                        REMINDER_ID,
                        REMINDER_FREQUENCY,
                        REMINDER_TIME,
                        REMINDER_INTERVAL,
                }, null, null, null, null, null);

        //loop through each result set to populate the appointment pojo and add to the list each time
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int frequency = cursor.getInt(1);
            String startTime = cursor.getString(2);
            int interval = cursor.getInt(3);

            Reminder reminder_node = new Reminder(id,frequency,startTime,interval);
            reminders.add(reminder_node);

        }
        return reminders;
    }
}
