package sg.edu.nus.iss.medipal;

import android.database.SQLException;
import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

import java.util.ArrayList;

import sg.edu.nus.iss.medipal.pojo.Reminder;

import static junit.framework.Assert.assertEquals;

/**
 * Created by zhiguo on 26/3/17.
 */
@RunWith(AndroidJUnit4.class)
public class ReminderTest extends BeforeTestSetUp {

    public void testInsert() throws SQLException {

        Reminder reminder = new Reminder(1,2,"10:20",6);

        //Insert a data
        reminderDAO.insert(reminder);

        //Get the data from DB again
        ArrayList<Reminder> reminder_list = reminderDAO.getReminders();
        Reminder reminder_result= reminder_list.get(0);

        //Test whether the data has successfully insert and saved in DB
        assertEquals(1, reminder_result.getId());
        assertEquals(2, reminder_result.getFrequency());
        assertEquals("10:20", reminder_result.getStartTime());
        assertEquals(6, reminder_result.getInterval());

    }

    public void testUpdate() throws SQLException{

        Reminder reminder1 = new Reminder(2,3,"10:20",4);
        //Insert a data
        reminderDAO.insert(reminder1);
        ArrayList<Reminder> reminder_list = reminderDAO.getReminders();
        Reminder reminder2 = reminder_list.get(1);

        //Set new property for this obejct
        reminder2.setFrequency(2);
        reminder2.setInterval(3);
        reminder2.setStartTime("11:30");

        //Update the property data to DB
        reminderDAO.update(reminder2);
        ArrayList<Reminder> reminder_list1 = reminderDAO.getReminders();
        Reminder reminder3 = reminder_list1.get(1);

        //Test the update result
        assertEquals(2, reminder3.getFrequency());
        assertEquals(3, reminder3.getInterval());
        assertEquals("11:30", reminder3.getStartTime());
        assertEquals(2,reminder3.getId());
    }

    public void testGetCategorys() throws SQLException{

        Reminder reminder1 = new Reminder(3,1,"10:20",8);
        //Insert a data
        reminderDAO.insert(reminder1);

        Reminder reminder2 = new Reminder(4,4,"9:20",1);
        //Insert a data
        reminderDAO.insert(reminder2);

        ArrayList<Reminder> reminder_list = reminderDAO.getReminders();
        Reminder reminder3 = reminder_list.get(2);
        Reminder reminder4 = reminder_list.get(3);


        //Test the result
        assertEquals(1, reminder3.getFrequency());
        assertEquals(8, reminder3.getInterval());
        assertEquals("10:20", reminder3.getStartTime());
        assertEquals(3,reminder3.getId());

        assertEquals(4, reminder4.getFrequency());
        assertEquals(1, reminder4.getInterval());
        assertEquals("9:20", reminder4.getStartTime());
        assertEquals(4,reminder4.getId());

    }

    public void testFind() throws SQLException{

        Reminder reminder1 = new Reminder(5,2,"10:25",1);
        //Insert a data
        reminderDAO.insert(reminder1);


        ArrayList<Reminder> reminder_list = reminderDAO.getReminders();
        Reminder reminder2 = reminder_list.get(4);


        //Test the result
        assertEquals(2, reminder2.getFrequency());
        assertEquals(1, reminder2.getInterval());
        assertEquals("10:25", reminder2.getStartTime());
        assertEquals(4,reminder2.getId());


    }

    public void testDelete() throws SQLException{

        Reminder reminder1 = new Reminder(6,1,"10:10",1);
        //Insert a data
        reminderDAO.insert(reminder1);

        Reminder reminder2 = new Reminder(7,3,"15:30",3);
        //Insert a data
        reminderDAO.insert(reminder2);

        Reminder reminder3 = new Reminder(8,2,"12:20",4);
        //Insert a data
        reminderDAO.insert(reminder3);

        //Remove a record from the database
        reminderDAO.delete(reminder2);

        //Get the new reminder record list from database
        ArrayList<Reminder> reminder_list = reminderDAO.getReminders();
        Reminder reminder4 = reminder_list.get(reminder_list.size()-1);
        Reminder reminder5 = reminder_list.get(reminder_list.size()-2);

        //Test the result
        assertEquals(2, reminder4.getFrequency());
        assertEquals(4, reminder4.getInterval());
        assertEquals("12:20", reminder4.getStartTime());
        assertEquals(8,reminder4.getId());

        assertEquals(1, reminder5.getFrequency());
        assertEquals(1, reminder5.getInterval());
        assertEquals("10:10", reminder5.getStartTime());
        assertEquals(6,reminder5.getId());

    }

}
