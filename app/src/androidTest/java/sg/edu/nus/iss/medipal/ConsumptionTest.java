package sg.edu.nus.iss.medipal;

import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;

import sg.edu.nus.iss.medipal.pojo.Consumption;

import static junit.framework.Assert.assertEquals;
import static sg.edu.nus.iss.medipal.BeforeTestSetUp.consumptionDAO;

/**
 * Created by levis on 3/27/2017.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConsumptionTest  extends BeforeTestSetUp {

    @Test
    public void testInsertConsumption() throws Exception {

        Consumption consumption = new Consumption(null, 1, 2, "25-03-2017 7:54 PM");
        consumptionDAO.insert(consumption);

        List<Consumption> consumptionList = consumptionDAO.getConsumptions();
        Consumption rConsumption = consumptionList.get(consumptionList.size() - 1);

        assertEquals(1, (int)rConsumption.getMedicineId());
        assertEquals(2, (int)rConsumption.getQuality());
        assertEquals("25-03-2017 7:54 PM", rConsumption.getConsumedOn());
    }

    @Test
    public void testRetrieveConsumption() throws Exception {

        Consumption consumption1 = new Consumption(null, 1, 2, "25-03-2015 7:54 PM");
        consumptionDAO.insert(consumption1);

        Consumption consumption2 = new Consumption(null, 3, 4, "25-03-2016 7:54 PM");
        consumptionDAO.insert(consumption2);

        Consumption consumption3 = new Consumption(null, 5, 6, "25-03-2018 7:54 PM");
        consumptionDAO.insert(consumption3);

        List<Consumption> consumptionList = consumptionDAO.getConsumptions("25-03-2016");
        Consumption rConsumption = consumptionList.get(consumptionList.size() - 1);

        assertEquals(3, (int)rConsumption.getMedicineId());
        assertEquals(4, (int)rConsumption.getQuality());
        assertEquals("25-03-2016 7:54 PM", rConsumption.getConsumedOn());
    }

    @Test
    public void testUpdateConsumption() throws Exception {

        Consumption consumption = new Consumption(null, 1, 2, "25-03-2017 7:54 PM");
        consumptionDAO.insert(consumption);

        List<Consumption> consumptionList = consumptionDAO.getConsumptions();
        Consumption uConsumption = consumptionList.get(consumptionList.size() - 1);
        uConsumption.setMedicineId(3);
        uConsumption.setQuality(4);
        uConsumption.setConsumedOn("25-03-2018 7:54 PM");
        consumptionDAO.update(uConsumption);

        consumptionList = consumptionDAO.getConsumptions();
        Consumption rConsumption = consumptionList.get(consumptionList.size() - 1);

        assertEquals(3, (int)rConsumption.getMedicineId());
        assertEquals(4, (int)rConsumption.getQuality());
        assertEquals("25-03-2018 7:54 PM", rConsumption.getConsumedOn());
    }

    @Test
    public void testDeleteConsumption() throws Exception {
        Consumption consumption1 = new Consumption(null, 1, 2, "25-03-2015 7:54 PM");
        consumptionDAO.insert(consumption1);

        Consumption consumption2 = new Consumption(null, 3, 4, "25-03-2016 7:54 PM");
        consumptionDAO.insert(consumption2);

        Consumption consumption3 = new Consumption(null, 5, 6, "25-03-2018 7:54 PM");
        consumptionDAO.insert(consumption3);

        List<Consumption> consumptionList = consumptionDAO.getConsumptions();
        Consumption dConsumption1 = consumptionList.get(consumptionList.size() - 1);
        consumptionDAO.delete(dConsumption1);

        consumptionList = consumptionDAO.getConsumptions();
        Consumption dConsumption2 = consumptionList.get(consumptionList.size() - 2);
        consumptionDAO.delete(dConsumption2);

        consumptionList = consumptionDAO.getConsumptions();
        Consumption rConsumption = consumptionList.get(consumptionList.size() - 1);

        assertEquals(3, (int)rConsumption.getMedicineId());
        assertEquals(4, (int)rConsumption.getQuality());
        assertEquals("25-03-2016 7:54 PM", rConsumption.getConsumedOn());
    }
}
