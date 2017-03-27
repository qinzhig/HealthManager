package sg.edu.nus.iss.medipal.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import sg.edu.nus.iss.medipal.dao.ReminderDAO;
import sg.edu.nus.iss.medipal.pojo.Reminder;

/**
 * Created by zhiguo on 19/3/17.
 */

public class AddReminder extends AsyncTask<Reminder, Void, Long> {


    Reminder reminder = null;
    private ReminderDAO reminderDAO;

    public AddReminder(Context context) {
        this.reminderDAO = new ReminderDAO(context);
    }

    @Override
    protected Long doInBackground(Reminder... params) {
        long result = reminderDAO.insert(params[0]);
        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1)
        {
            Log.v("DBSave","____________________++++++++++++++++++++Add Category successfully!");
        }

        if (reminderDAO != null)
        {
            reminderDAO.close();
        }

    }

}