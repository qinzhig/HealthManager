package sg.edu.nus.iss.medipal;

import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;

import sg.edu.nus.iss.medipal.pojo.Ice;

import static junit.framework.Assert.assertEquals;

/**
 * Created by levis on 3/26/2017.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IceTest extends BeforeTestSetUp {

    @Test
    public void testInsertICE() throws Exception {
        Ice ice = new Ice("FT01", "12345678", 0, "NUS-ISS-Team", 0);
        iceDAO.insert(ice);

        List<Ice> iceList = iceDAO.retrieve();
        //assertEquals(1, iceList.size());

        Ice rIce = iceList.get(iceList.size() - 1);

        assertEquals("FT01", rIce.getName());
        assertEquals("12345678", rIce.getContactNo());
        assertEquals(0, (int)rIce.getContactType());
        assertEquals("NUS-ISS-Team", rIce.getDescription());
        assertEquals(0, (int)rIce.getPriority());
    }

    @Test
    public void testRetrieveICE() throws Exception {
        Ice ice1 = new Ice("FT01", "12345678", 0, "NUS-ISS-Team", 0);
        iceDAO.insert(ice1);

        Ice ice2 = new Ice("FT02", "87654321", 1, "NUS-ISS-Team", 1);
        iceDAO.insert(ice2);

        List<Ice> iceList = iceDAO.retrieve();
        Ice rIce = iceList.get(iceList.size() - 1);

        assertEquals("FT02", rIce.getName());
        assertEquals("87654321", rIce.getContactNo());
        assertEquals(1, (int)rIce.getContactType());
        assertEquals("NUS-ISS-Team", rIce.getDescription());
        assertEquals(1, (int)rIce.getPriority());
    }

    @Test
    public void testUpdateICE() throws Exception {
        Ice ice = new Ice("FT01", "12345678", 0, "NUS-ISS-Team", 0);
        iceDAO.insert(ice);

        List<Ice> iceList = iceDAO.retrieve();
        Ice uIce = iceList.get(iceList.size() - 1);
        uIce.setName("FT02");
        uIce.setContactNo("87654321");
        uIce.setContactType(1);
        uIce.setDescription("NUS-ISS-Team");
        uIce.setPriority(1);
        iceDAO.update(uIce);

        iceList = iceDAO.retrieve();
        Ice rIce = iceList.get(iceList.size() - 1);

        assertEquals("FT02", rIce.getName());
        assertEquals("87654321", rIce.getContactNo());
        assertEquals(1, (int)rIce.getContactType());
        assertEquals("NUS-ISS-Team", rIce.getDescription());
        assertEquals(1, (int)rIce.getPriority());
    }

    @Test
    public void testDeleteICE() throws Exception {
        List<Ice> iceList = iceDAO.retrieve();
        int initListCount = iceList.size();

        Ice ice0 = new Ice("FT01", "12345678", 0, "NUS-ISS-Team", 0);
        iceDAO.insert(ice0);
        Ice ice1 = new Ice("FT02", "23456781", 1, "NUS-ISS-Team", 1);
        iceDAO.insert(ice1);
        Ice ice2 = new Ice("FT03", "34567812", 2, "NUS-ISS-Team", 2);
        iceDAO.insert(ice2);
        Ice ice3 = new Ice("FT04", "45678123", 0, "NUS-ISS-Team", 3);
        iceDAO.insert(ice3);

        iceList = iceDAO.retrieve();
        assertEquals(initListCount + 4, iceList.size());

        Ice dIce1 = iceList.get(iceList.size() - 1);
        iceDAO.delete(dIce1.getId().toString());
        iceList = iceDAO.retrieve();
        assertEquals(initListCount + 3, iceList.size());

        Ice dIce2 = iceList.get(iceList.size() - 2);
        iceDAO.delete(dIce2.getId().toString());
        iceList = iceDAO.retrieve();
        assertEquals(initListCount + 2, iceList.size());

        iceList = iceDAO.retrieve();
        Ice rIce = iceList.get(iceList.size() - 1);

        assertEquals("FT03", rIce.getName());
        assertEquals("34567812", rIce.getContactNo());
        assertEquals(2, (int)rIce.getContactType());
        assertEquals("NUS-ISS-Team", rIce.getDescription());
        assertEquals(2, (int)rIce.getPriority());
    }
}
