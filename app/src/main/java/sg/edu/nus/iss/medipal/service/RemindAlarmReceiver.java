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

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.activity.MainActivity;
import sg.edu.nus.iss.medipal.manager.PreferenceManager;

/**
 * Created by : Navi on 15-03-2017.
 * Description : This is the broad receiver for getting the Appointment remainder from alarm manager service
 *                 after getting the intent, the class dispatches the corresponding notification
 * Modified by :
 * Reason for modification :
 */

public class RemindAlarmReceiver extends BroadcastReceiver {
    PreferenceManager remainderPreference;
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getStringExtra("ConsumptionReminderId") != null && !intent.getStringExtra("ConsumptionReminderId").isEmpty())
        {
            Log.v("MedicineReminder","---------------------->+++++++++++AlarmReminder GET!");
            Log.v("MedicineReminder","---------------------->+++++++++++Reminder Id=" + intent.getStringExtra("ConsumptionReminderId"));
            Log.v("MedicineReminder","---------------------->+++++++++++Reminder StartTime=" + intent.getStringExtra("StartTime"));

            int cReminderID = Integer.valueOf(intent.getStringExtra("ConsumptionReminderId"));

            Intent resultIntent = new Intent(context, MainActivity.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

            stackBuilder.addNextIntent(resultIntent);

            PendingIntent medicinePendingIntent = stackBuilder.getPendingIntent(cReminderID, PendingIntent.FLAG_CANCEL_CURRENT|PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

            Notification consumption_notification = builder.setContentTitle("<MedicineTime>")
                    .setContentText("Reminder for Consumpition")
                    .setTicker("Notification for Appointment")
                    .setSmallIcon(R.drawable.medicine)
                    .setAutoCancel(true)
                    .setContentIntent(medicinePendingIntent).build();

            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.notify(cReminderID, consumption_notification);



        }else {

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
