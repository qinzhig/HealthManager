package sg.edu.nus.iss.medipal.asynTask;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import sg.edu.nus.iss.medipal.dao.ReminderDAO;
import sg.edu.nus.iss.medipal.pojo.Reminder;

/**
 * Created by zhiguo on 22/3/17.
 */

public class ListReminder extends AsyncTask<Void, Void, ArrayList<Reminder>> {

    ArrayList<Reminder> reminders;
    private ReminderDAO reminderDAO;

    public ListReminder(Context context) {
        this.reminderDAO = new ReminderDAO(context);
    }

    @Override
    protected ArrayList<Reminder> doInBackground(Void... arg0) {
        reminders = reminderDAO.getReminders();
        return reminders;
    }

    @Override
    protected void onPostExecute(ArrayList<Reminder> reminderList) {

        reminders = reminderList;

        if (reminderList == null) { reminders = new ArrayList<Reminder>(); }

        reminderDAO.close();

    }

}

