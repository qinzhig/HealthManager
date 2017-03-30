package sg.edu.nus.iss.medipal;

import android.database.SQLException;
import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

import java.util.ArrayList;

import sg.edu.nus.iss.medipal.pojo.Medicine;

import static junit.framework.Assert.assertEquals;

/**
 * Created by zhiguo on 26/3/17.
 */

@RunWith(AndroidJUnit4.class)
public class MedicineTest extends BeforeTestSetUp {

    public void testInsert() throws SQLException {

        Medicine medicine = new Medicine(1, "medicine1", "m1",1,1,false,10,1,2,3,"10 03 2017",12);

        //Insert a data
        medicineDAO.insert(medicine);

        //Get the data from DB again
        ArrayList<Medicine> medicine_list = medicineDAO.getMedicines();
        Medicine medicine_result= medicine_list.get(medicine_list.size()-1);

        //Test whether the data has successfully insert and saved in DB
        assertEquals("medicine1", medicine_result.getMedicine_name());
        assertEquals("m1", medicine_result.getMedicine_des());
        assertEquals(1, medicine_result.getCateId());
        assertEquals(1, medicine_result.getReminderId());
        assertEquals(false, medicine_result.isReminder());
        assertEquals(10, medicine_result.getQuantity());
        assertEquals(1, medicine_result.getDosage());
        assertEquals(2, medicine_result.getConsumequantity());
        assertEquals(3, medicine_result.getThreshold());
        assertEquals("10 03 2017", medicine_result.getDateIssued());
        assertEquals(12, medicine_result.getExpireFactor());


    }

    public void testUpdate() throws SQLException{

        Medicine medicine = new Medicine(2, "medicine2", "m2",2,2,true,20,2,4,6,"10 11 2018",2);

        //Insert a data
        medicineDAO.insert(medicine);
        ArrayList<Medicine> medicine_list = medicineDAO.getMedicines();
        Medicine medicine2 = medicine_list.get(medicine_list.size()-1);

        //Set new property for this obejct
        medicine2.setMedicine_name("medicine2_1");
        medicine2.setMedicine_des("m2_1");
        medicine2.setCateId(3);
        medicine2.setReminderId(3);
        medicine2.setReminder(false);
        medicine2.setQuantity(30);
        medicine2.setDosage(3);
        medicine2.setConsumequantity(5);
        medicine2.setThreshold(8);
        medicine2.setDateIssued("24 10 2018");
        medicine2.setExpireFactor(1);

        //Update the property data to DB
        medicineDAO.update(medicine2);

        ArrayList<Medicine> medicine_list1 = medicineDAO.getMedicines();
        Medicine medicine3 = medicine_list1.get(medicine_list1.size()-1);

        //Test whether the data has successfully update and saved in DB
        assertEquals("medicine2_1", medicine3.getMedicine_name());
        assertEquals("m2_1", medicine3.getMedicine_des());
        assertEquals(3, medicine3.getCateId());
        assertEquals(3, medicine3.getReminderId());
        assertEquals(false, medicine3.isReminder());
        assertEquals(30, medicine3.getQuantity());
        assertEquals(3, medicine3.getDosage());
        assertEquals(5, medicine3.getConsumequantity());
        assertEquals(8, medicine3.getThreshold());
        assertEquals("24 10 2018", medicine3.getDateIssued());
        assertEquals(1, medicine3.getExpireFactor());
    }

    public void testGetCategorys() throws SQLException{

        Medicine medicine = new Medicine(3, "medicine3", "m3",3,100,true,3,1,1,2,"01 05 2017",2);
        //Insert a data
        medicineDAO.insert(medicine);

        Medicine medicine2 = new Medicine(4, "medicine4", "m4",4,50,false,15,3,3,4,"13 04 2017",1);
        //Insert a data
        medicineDAO.insert(medicine2);

        ArrayList<Medicine> medicine_list = medicineDAO.getMedicines();
        Medicine medicine4 = medicine_list.get(medicine_list.size()-1);
        Medicine medicine3 = medicine_list.get(medicine_list.size()-2);


        //Test the result
        assertEquals("medicine3", medicine3.getMedicine_name());
        assertEquals("m3", medicine3.getMedicine_des());
        assertEquals(3, medicine3.getCateId());
        assertEquals(100, medicine3.getReminderId());
        assertEquals(true, medicine3.isReminder());
        assertEquals(3, medicine3.getQuantity());
        assertEquals(1, medicine3.getDosage());
        assertEquals(1, medicine3.getConsumequantity());
        assertEquals(2, medicine3.getThreshold());
        assertEquals("01 05 2017", medicine3.getDateIssued());
        assertEquals(2, medicine3.getExpireFactor());

        assertEquals("medicine4", medicine4.getMedicine_name());
        assertEquals("m4", medicine4.getMedicine_des());
        assertEquals(4, medicine4.getCateId());
        assertEquals(50, medicine4.getReminderId());
        assertEquals(false, medicine4.isReminder());
        assertEquals(13, medicine4.getQuantity());
        assertEquals(3, medicine4.getDosage());
        assertEquals(3, medicine4.getConsumequantity());
        assertEquals(4, medicine4.getThreshold());
        assertEquals("13 04 2017", medicine4.getDateIssued());
        assertEquals(1, medicine4.getExpireFactor());

    }


    public void testDelete() throws SQLException{

        Medicine medicine1 = new Medicine(5, "medicine5", "m5",1,101,true,5,2,1,3,"01 04 2019",24);
        //Insert a data
        medicineDAO.insert(medicine1);

        Medicine medicine2 = new Medicine(6, "medicine6", "m6",2,102,false,6,1,1,2,"23 11 2017",7);
        //Insert a data
        medicineDAO.insert(medicine2);

        Medicine medicine3 = new Medicine(7, "medicine7", "m7",3,103,true,7,3,1,1,"11 05 2018",14);
        //Insert a data
        medicineDAO.insert(medicine3);

        //Remove a record from the database
        medicineDAO.delete(medicine2);

        //Get the new Medicine record list from database
        ArrayList<Medicine> medicine_list = medicineDAO.getMedicines();
        Medicine medicine4 = medicine_list.get(medicine_list.size()-1);
        Medicine medicine5 = medicine_list.get(medicine_list.size()-2);

        //Test the result
        //Test the result
        assertEquals("medicine7", medicine4.getMedicine_name());
        assertEquals("m7", medicine4.getMedicine_des());
        assertEquals(3, medicine4.getCateId());
        assertEquals(103, medicine4.getReminderId());
        assertEquals(true, medicine4.isReminder());
        assertEquals(7, medicine4.getQuantity());
        assertEquals(3, medicine4.getDosage());
        assertEquals(1, medicine4.getConsumequantity());
        assertEquals(1, medicine4.getThreshold());
        assertEquals("11 05 2018", medicine4.getDateIssued());
        assertEquals(14, medicine4.getExpireFactor());

        assertEquals("medicine5", medicine5.getMedicine_name());
        assertEquals("m5", medicine5.getMedicine_des());
        assertEquals(1, medicine5.getCateId());
        assertEquals(101, medicine5.getReminderId());
        assertEquals(false, medicine5.isReminder());
        assertEquals(5, medicine5.getQuantity());
        assertEquals(2, medicine5.getDosage());
        assertEquals(1, medicine5.getConsumequantity());
        assertEquals(3, medicine5.getThreshold());
        assertEquals("01 04 2019", medicine5.getDateIssued());
        assertEquals(24, medicine5.getExpireFactor());

    }

    public void testGetMedNameQty() throws SQLException{

        Medicine medicine1 = new Medicine(8, "medicine8", "m8",1,108,true,8,2,1,3,"01 04 2019",24);
        //Insert a data
        medicineDAO.insert(medicine1);

        Medicine medicine2 = new Medicine(9, "medicine9", "m9",2,109,false,9,1,1,2,"23 11 2017",7);
        //Insert a data
        medicineDAO.insert(medicine2);

        Medicine medicine3 = new Medicine(10, "medicine10", "m10",3,107,true,10,3,1,1,"11 05 2018",14);
        //Insert a data
        medicineDAO.insert(medicine3);

        ArrayList<Medicine> medicine_list = medicineDAO.getMedicines();
        Medicine medicine = medicine_list.get(medicine_list.size()-2);


        medicineDAO.getMedNameQty(medicine.getId());
        assertEquals("medicine9", medicine.getMedicine_name());



    }


}
