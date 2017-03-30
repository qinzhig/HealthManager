package sg.edu.nus.iss.medipal;

import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.Date;
import java.util.List;

import sg.edu.nus.iss.medipal.pojo.HealthBio;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Divahar on 3/21/2017.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HealthBioTest extends BeforeTestSetUp{

    private HealthBio healthBio;


    @Test
    public void test1InsertHealthBio() throws Exception {

        healthBio = new
                HealthBio("Condition1", new Date(), 'C');

        healthBioDAO.insert(healthBio);

        List<HealthBio>
                healthBioList = healthBioDAO.retrieve();

        String startDtStr = MediPalUtility.convertDateToString(healthBioList
                .get(0).getStartDate(), "dd MM yyyy");

        String currDatStr = MediPalUtility.convertDateToString(new Date(), "dd MM yyyy");

        assertEquals("Condition1", healthBioList
                .get(0).getCondition());
        assertEquals(currDatStr, startDtStr);
        assertEquals('C', healthBioList
                .get(0).getConditionType());


    }

    @Test
    public void test2RetrieveHealthBio() throws Exception {

        List<HealthBio>
                healthBioList = healthBioDAO.retrieve();

        String startDtStr = MediPalUtility.convertDateToString(healthBioList
                .get(0).getStartDate(), "dd MM yyyy");

        String currDatStr = MediPalUtility.convertDateToString(new Date(), "dd MM yyyy");

        assertEquals("Condition1", healthBioList
                .get(0).getCondition());
        assertEquals(currDatStr, startDtStr);
        assertEquals('C', healthBioList
                .get(0).getConditionType());


    }

    @Test
    public void test3UpdateHealthBio() throws Exception {

        HealthBio healthBio = new
                HealthBio(1, "Condition", new Date(), 'A');

        healthBioDAO.update(healthBio);

        List<HealthBio>
                healthBioList = healthBioDAO.retrieve();

        String startDtStr = MediPalUtility.convertDateToString(healthBioList
                .get(0).getStartDate(), "dd MM yyyy");

        String currDatStr = MediPalUtility.convertDateToString(new Date(), "dd MM yyyy");

        assertEquals("Condition", healthBioList
                .get(0).getCondition());
        assertEquals(currDatStr, startDtStr);
        assertEquals('A', healthBioList
                .get(0).getConditionType());


    }

    @Test
    public void test4DeleteHealthBio() throws Exception {

        healthBioDAO.delete("1");

        List<HealthBio>
                healthBioList = healthBioDAO.retrieve();

        assertEquals(0,
                healthBioList.size());


    }
}
