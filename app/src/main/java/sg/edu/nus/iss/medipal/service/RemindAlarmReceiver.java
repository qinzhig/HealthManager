package sg.edu.nus.iss.medipal.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Calendar;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.activity.MainActivity;
import sg.edu.nus.iss.medipal.application.App;
import sg.edu.nus.iss.medipal.manager.ConsumptionManager;
import sg.edu.nus.iss.medipal.manager.PreferenceManager;
import sg.edu.nus.iss.medipal.pojo.Reminder;

/**
 * Created by : Navi on 15-03-2017.
 * Description : This is the broad receiver for getting the Appointment remainder from alarm manager service
 *                 after getting the intent, the class dispatches the corresponding notification
 * Modified by : zhiguo on 23-03-2017
 * Reason for modification : Add the Medicine alarm for consumption reminder
 */

public class RemindAlarmReceiver extends BroadcastReceiver {
    PreferenceManager remainderPreference;
    @Override
    public void onReceive(Context context, Intent intent) {

        if( (intent.getStringExtra("ConsumptionReminderId") != null) && (!intent.getStringExtra("ConsumptionReminderId").isEmpty()))
        {
            Log.v("MedicineReminder","---------------------->+++++++++++AlarmReminder GET!");
            Log.v("MedicineReminder","---------------------->+++++++++++Reminder Id=" + intent.getStringExtra("ConsumptionReminderId"));
            Log.v("MedicineReminder","---------------------->+++++++++++Reminder StartTime=" + intent.getStringExtra("StartTime"));

            int cReminderID = Integer.valueOf(intent.getStringExtra("ConsumptionReminderId"));

            Reminder reminder = App.hm.getReminder(cReminderID/100,context);

            if ( (reminder != null) && (cReminderID%100) <= reminder.getFrequency()) {
                int medicineId= intent.getIntExtra("medicineid",0);
                String dateTime = intent.getStringExtra("StartTime");

                Calendar c = Calendar.getInstance();
                int day, month, year,hour,minute;
                day = c.get(Calendar.DAY_OF_MONTH);
                month = c.get(Calendar.MONTH);
                year = c.get(Calendar.YEAR);
                String[] stime_hour_min = dateTime.split(":");
                hour = Integer.valueOf(stime_hour_min[0]);
                minute = Integer.valueOf(stime_hour_min[1]);
                String date = day + "-" + (month + 1) + "-" + year;
                int h = hour % 12;
                String period;

                if(hour == 0)
                    hour = 12;
                if(h < 12)
                    period = "AM";
                else
                    period = "PM";

                String time = String.format("%02d:%02d %s",hour, minute, period);

                ConsumptionManager consumptionManager = new ConsumptionManager(context);
                consumptionManager.addConsumption(0, medicineId, 0, (date+" "+time));

                Intent resultIntent = new Intent(context, MainActivity.class);
                resultIntent.putExtra("medicineId",medicineId);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

                stackBuilder.addNextIntent(resultIntent);

                PendingIntent medicinePendingIntent = stackBuilder.getPendingIntent(cReminderID, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

                Notification consumption_notification = builder.setContentTitle("<MedicineTime>")
                        .setContentText("Reminder for Consumpition")
                        .setTicker("You have a medicine to consume!")
                        .setSmallIcon(R.drawable.medicine)
                        .setAutoCancel(true)
                        .setContentIntent(medicinePendingIntent).build();

                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                mNotificationManager.notify(cReminderID, consumption_notification);
            }



        }else if((intent.getStringExtra("Id") != null) && (intent.getStringExtra("notification") != null)) {

            remainderPreference = new PreferenceManager(context);
            String notificationId = intent.getStringExtra("Id");
            String Id;
            String notificationString = intent.getStringExtra("notification");

            if (notificationString.equalsIgnoreCase("Remainder for Appointment"))
                Id = Integer.toString(Integer.valueOf(notificationId) - 100000);
            else
                Id = notificationId;

            Intent notificationIntent = new Intent(context, MainActivity.class);
            notificationIntent.putExtra("Id", Id);
            notificationIntent.putExtra("notification", notificationString);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            Log.d("ReminderReceiver", "onReceive Called");

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            //stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(notificationIntent);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(Integer.valueOf(notificationId), PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

            String storedString = remainderPreference.getAppointmentInfo(notificationId);
            Log.d("Notification", notificationId);
            Log.d("Notification string", storedString);

            if (storedString != null) {
                try {
                    JSONArray jsonArray = new JSONArray(storedString);

                    Notification notification = builder.setContentTitle(jsonArray.getString(0))
                            .setContentText(notificationString)
                            .setTicker("Notification for Appointment")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent).build();

                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(Integer.valueOf(notificationId), notification);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
