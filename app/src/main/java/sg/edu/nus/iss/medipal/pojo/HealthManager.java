package sg.edu.nus.iss.medipal.pojo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.medipal.asynTask.AddCategory;
import sg.edu.nus.iss.medipal.asynTask.AddMedicine;
import sg.edu.nus.iss.medipal.asynTask.AddReminder;
import sg.edu.nus.iss.medipal.asynTask.DeleteMedicine;
import sg.edu.nus.iss.medipal.asynTask.ListCategory;
import sg.edu.nus.iss.medipal.asynTask.ListMedicine;
import sg.edu.nus.iss.medipal.asynTask.ListReminder;
import sg.edu.nus.iss.medipal.asynTask.UpdateCategory;
import sg.edu.nus.iss.medipal.asynTask.UpdateMedicine;
import sg.edu.nus.iss.medipal.asynTask.UpdateReminder;
import sg.edu.nus.iss.medipal.service.RemindAlarmReceiver;

/**
 * Created by zhiguo on 15/3/17.
 */

public class HealthManager {

    private ArrayList<Medicine> medicines;
    private ArrayList<Category> categorys;
    private ArrayList<Reminder> reminders;

    private AddMedicine     taskAddMedicine;
    private ListMedicine    taskListMedicine;
    private UpdateMedicine  taskUpdateMedicine;
    private DeleteMedicine  taskDeleteMedicine;

    private AddCategory     taskAddCategory;
    private ListCategory    taskListCategory;
    private UpdateCategory  taskUpdateCategory;

    private AddReminder     taskAddReminder;
    private UpdateReminder  taskUpdateReminder;
    private ListReminder    taskListReminder;


    public HealthManager(){
        this.medicines  =   new ArrayList<Medicine>();
        this.categorys  =   new ArrayList<Category>();
        this.reminders  =   new ArrayList<Reminder>();
    }

    public Medicine getMedicine(int id,Context context){

        Iterator<Medicine> i = getMedicines(context).iterator();

        while(i.hasNext()){
            Medicine m = i.next();
            if( m.getId() == id)
            {
                return m;
            }
        }

        return null;
    }

    //SQLite get medicine list
    public List<Medicine> getMedicines(Context context) {
        taskListMedicine = new ListMedicine(context);
        taskListMedicine.execute((Void)null);

        try {
            medicines = taskListMedicine.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(medicines == null){
            medicines = new ArrayList<Medicine>();
        }

        Log.v("DEBUG","-------------------------HealthManager++++++++++++++++++++++ "+medicines.toString());
        return  medicines;

    }

    //SQLite add medicine
    public Medicine addMedicine(int id, String medicine_name, String medicine_des, int cateId,
                                int reminderId, Boolean reminder, int quantity, int dosage,int cquantity,int threshold,
                                String dateIssued, int expireFactor,Context context){

        Medicine medicine = new Medicine(id, medicine_name, medicine_des,cateId, reminderId, reminder, quantity, dosage,cquantity,threshold,dateIssued, expireFactor);

        taskAddMedicine = new AddMedicine(context);
        taskAddMedicine.execute(medicine);

        return medicine;

    }

    //SQLite add medicine
    public Medicine updateMedicine(int id, String medicine_name, String medicine_des, int cateId,
                                int reminderId, Boolean reminder, int quantity, int dosage,int cquantity,int threshold,
                                String dateIssued, int expireFactor,Context context){

        Medicine medicine = new Medicine(id, medicine_name, medicine_des,cateId, reminderId, reminder, quantity, dosage,cquantity,threshold, dateIssued, expireFactor);

        taskUpdateMedicine = new UpdateMedicine(context);
        taskUpdateMedicine.execute(medicine);

        return medicine;

    }

    //SQLite delete medicine

    public void deleteMedicine(int id,Context context){

        Medicine m = getMedicine(id,context);

        if(m != null)
        {
            taskDeleteMedicine = new DeleteMedicine(context);
            taskDeleteMedicine.execute(m);
        }

    }


    public Category getCategory(int id,Context context){

        Iterator<Category> i = getCategorys(context).iterator();

        while(i.hasNext()){
            Category c = i.next();
            if( c.getId() == id)
            {
                return c;
            }
        }

        return null;
    }

    //SQLite get Category list
    public ArrayList<Category> getCategorys(Context context) {
        taskListCategory = new ListCategory(context);
        taskListCategory.execute((Void)null);

        try{
            categorys = taskListCategory.get();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }

        if(categorys == null){
            categorys = new ArrayList<Category>();
        }

        return categorys;

    }

    //SQLite add medicine
    public Category updateCategory(int id, String name, String code, String des,boolean remind,Context context){

        Category category = new Category(id, name, code,des,remind);

        taskUpdateCategory = new UpdateCategory(context);
        taskUpdateCategory.execute(category);

        return category;

    }

    public String[] getCategoryNameList(Context context){

        String c_name[]= new String[getCategorys(context).size()];
        Iterator<Category> c_list = getCategorys(context).iterator();

        for(int i =0;c_list.hasNext();i++){

            Category c = c_list.next();
            if( c.getCategory_name() != null)
            {
                c_name[i]=c.getCategory_name();

                Log.v("TAG","---------------HealthManager "+c_name[i]);
            }
        }


        return c_name;

    }

    //SQLite add medicine
    public Category addCategory(int id, String category_name,String category_code,String category_des,boolean remind,Context context){

        Category category = new Category(id, category_name,category_code,category_des,remind);

        taskAddCategory = new AddCategory(context);
        taskAddCategory.execute(category);

        return category;

    }




    //--------------------------------------Reminder-----------------------------------
    public Reminder getReminder(int id,Context context){


        Iterator<Reminder> i = getReminders(context).iterator();

        while(i.hasNext()){
            Reminder c = i.next();
            if( c.getId() == id)
            {
                return c;
            }
        }

        return null;
    }

    public ArrayList<Reminder> getReminders(Context context)
    {
        taskListReminder = new ListReminder(context);
        taskListReminder.execute((Void)null);

        try{
            reminders = taskListReminder.get();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }

        if(reminders == null){
            reminders = new ArrayList<Reminder>();
        }

        return reminders;
    }

    public Reminder updateReminder(int id, int fre, String stime, int interval,Context context){

            Reminder reminder = new Reminder(id, fre, stime,interval);

            taskUpdateReminder = new UpdateReminder(context);
            taskUpdateReminder.execute(reminder);

            return reminder;

    }


    //SQLite add reminder
    public Reminder addReminder(int id, int frequency,String stime,int interval,Context context){

        Reminder reminder = new Reminder(id, frequency,stime,interval);

        taskAddReminder = new AddReminder(context);
        taskAddReminder.execute(reminder);

        return reminder;

    }

    //Setup repeat reminder for the medicine consumpotion

    public void setMeidicineReminder(String stime, int interval, int frequency,int reminderId, Context context){




        for(int i=0; i < frequency ; i++){

            if(!stime.isEmpty()){

                //Slice the start time and get the reminder start Hour and Min
                String[] stime_hour_min = stime.split(":");

                //Setup Alarmanager
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Calendar calendar =Calendar.getInstance(Locale.getDefault());
                calendar.setTimeInMillis(System.currentTimeMillis());

                //Set a repeat reminder hour for the frequency alarm
                int reminder_hour = (Integer.valueOf(stime_hour_min[0]) + interval * i) % 24;

                calendar.set(Calendar.HOUR_OF_DAY, reminder_hour);
                calendar.set(Calendar.MINUTE, Integer.valueOf(stime_hour_min[1]));
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                Intent intent = new Intent(context, RemindAlarmReceiver.class);
                intent.setAction("MedicineReminder");
                intent.putExtra("ConsumptionReminderId", Integer.toString(reminderId));
                intent.putExtra("StartTime",reminder_hour+":"+stime_hour_min[1]);

                Log.v("Reminder","-------------------<<<<<<<<<<<<<<<< Reminder Alarm Send!  + Start Time = " + stime );

                PendingIntent pendingIntent=PendingIntent.getBroadcast(context,reminderId, intent,PendingIntent.FLAG_CANCEL_CURRENT|PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),1000*15,pendingIntent);
            }else{

            }
        }



    }

}
