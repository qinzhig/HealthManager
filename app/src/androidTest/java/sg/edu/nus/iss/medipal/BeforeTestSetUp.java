package sg.edu.nus.iss.medipal;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import sg.edu.nus.iss.medipal.dao.HealthBioDAO;
import sg.edu.nus.iss.medipal.dao.IceDAO;
import sg.edu.nus.iss.medipal.dao.MeasurementDAO;
import sg.edu.nus.iss.medipal.dao.PersonalBioDAO;
import sg.edu.nus.iss.medipal.manager.DataBaseManager;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Divahar on 3/21/2017.
 */

@RunWith(AndroidJUnit4.class)
public class BeforeTestSetUp {

    private static DataBaseManager dbManager;
    private static Context context;
    public static HealthBioDAO healthBioDAO;
    public static PersonalBioDAO personalBioDAO;
    public static IceDAO iceDAO;
    public static MeasurementDAO measurementDAO;

    @BeforeClass
    public static void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        context.deleteDatabase("medipalFT01DB");
        dbManager = new DataBaseManager(context);
        healthBioDAO = new HealthBioDAO(context);
        personalBioDAO = new PersonalBioDAO(context);
        iceDAO = new IceDAO(context);
        measurementDAO = new MeasurementDAO(context);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        healthBioDAO.close();
        personalBioDAO.close();
        iceDAO.close();
        measurementDAO.close();
    }

    @Test
    public void testSetUp() {
        assertNotNull(healthBioDAO);
        assertNotNull(personalBioDAO);
        assertNotNull(iceDAO);
        assertNotNull(measurementDAO);
    }

}
