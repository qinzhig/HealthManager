package sg.edu.nus.iss.medipal;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import sg.edu.nus.iss.medipal.pojo.Ice;

import static junit.framework.Assert.assertEquals;

/**
 * Created by levis on 3/26/2017.
 */

@RunWith(AndroidJUnit4.class)
public class IceTest  extends BeforeTestSetUp {

    @Test
    public void testInsertICE() throws Exception {
        Ice ice = new Ice("FT01", "12345678", 0, "NUS-ISS-Team", 0);
        iceDAO.insert(ice);

        List<Ice> iceList = iceDAO.retrieve();
        Ice retrievedIce = iceList.get(0);

        assertEquals("FT01", retrievedIce.getName());
        assertEquals("12345678", retrievedIce.getContactNo());
        assertEquals(0, (int)retrievedIce.getContactType());
        assertEquals("NUS-ISS-Team", retrievedIce.getDescription());
        assertEquals(0, (int)retrievedIce.getPriority());
    }

    @Test
    public void testRetrieveICE() throws Exception {
        Ice firstICE = new Ice("FT01", "12345678", 0, "NUS-ISS-Team", 0);
        iceDAO.insert(firstICE);

        Ice secondICE = new Ice("FT02", "87654321", 1, "NUS-ISS-Team", 1);
        iceDAO.insert(secondICE);

        List<Ice> iceList = iceDAO.retrieve();
        Ice retrievedIce = iceList.get(1);

        assertEquals("FT02", retrievedIce.getName());
        assertEquals("87654321", retrievedIce.getContactNo());
        assertEquals(1, (int)retrievedIce.getContactType());
        assertEquals("NUS-ISS-Team", retrievedIce.getDescription());
        assertEquals(1, (int)retrievedIce.getPriority());
    }

    @Test
    public void testUpdateICE() throws Exception {
        Ice ice = new Ice("FT01", "12345678", 0, "NUS-ISS-Team", 0);
        iceDAO.insert(ice);

        List<Ice> iceList = iceDAO.retrieve();
        Ice updatedIce = iceList.get(0);
        updatedIce.setName("FT02");
        updatedIce.setContactNo("87654321");
        updatedIce.setContactType(1);
        updatedIce.setDescription("NUS-ISS-Team");
        updatedIce.setPriority(1);
        iceDAO.update(updatedIce);

        iceList = iceDAO.retrieve();
        Ice retrievedIce = iceList.get(0);

        assertEquals("FT02", retrievedIce.getName());
        assertEquals("87654321", retrievedIce.getContactNo());
        assertEquals(1, (int)retrievedIce.getContactType());
        assertEquals("NUS-ISS-Team", retrievedIce.getDescription());
        assertEquals(1, (int)retrievedIce.getPriority());
    }
}
