package sg.edu.nus.iss.medipal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;

import sg.edu.nus.iss.medipal.utils.MediPalUtility;

import static org.junit.Assert.assertEquals;

/**
 * Created by Divahar on 3/21/2017.
 */

@RunWith(JUnit4.class)
public class MediPalUtilityTest {

    @Test
    public void testConvertDateToString() {
        String dateStr = MediPalUtility.
                convertDateToString(new Date("27 Mar 2017"), "dd MMM yyyy");
        assertEquals("27 Mar 2017", dateStr);
    }

    @Test
    public void testConvertStringToDate() {
        Date date = MediPalUtility.
                convertStringToDate("27 Mar 2017", "dd MMM yyyy");
        assertEquals(new Date("27 Mar 2017"), date);
    }
}
