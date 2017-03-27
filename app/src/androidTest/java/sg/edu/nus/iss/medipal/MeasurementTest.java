package sg.edu.nus.iss.medipal;

import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.Date;
import java.util.List;

import sg.edu.nus.iss.medipal.pojo.BloodPressure;
import sg.edu.nus.iss.medipal.pojo.Pulse;
import sg.edu.nus.iss.medipal.pojo.Temperature;
import sg.edu.nus.iss.medipal.pojo.Weight;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

import static junit.framework.Assert.assertEquals;

/**
 * Created by levis on 3/26/2017.
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MeasurementTest extends BeforeTestSetUp {

    @Test
    public void testInsertMeasurement() throws Exception {
        Date measuredOn = MediPalUtility.convertStringToDate("25-03-2017 7:54 PM", "dd-MM-yyyy h:mm a");
        BloodPressure bloodPressure = new BloodPressure(measuredOn, 50, 100);
        Pulse pulse = new Pulse(measuredOn, 80);
        Temperature temperature = new Temperature(measuredOn, 100);
        Weight weight = new Weight(measuredOn, 150);

        measurementDAO.insert(bloodPressure, pulse, weight, temperature);

        List<Object> measurementList = measurementDAO.retrieve(null,null);

        for(int i = measurementList.size() - 4; i < measurementList.size(); i++) {
            Object measurement = measurementList.get(i);

            if (measurement instanceof BloodPressure) {
                BloodPressure rBloodPressure = (BloodPressure) measurement;
                assertEquals("25-03-2017 7:54 PM", MediPalUtility.convertDateToString(rBloodPressure.getMeasuredOn(), "dd-MM-yyyy h:mm a"));
                assertEquals(50, (int)rBloodPressure.getSystolic());
                assertEquals(100, (int)rBloodPressure.getDiastolic());
            } else if (measurement instanceof Pulse) {
                Pulse rPulse = (Pulse) measurement;
                assertEquals("25-03-2017 7:54 PM", MediPalUtility.convertDateToString(rPulse.getMeasuredOn(), "dd-MM-yyyy h:mm a"));
                assertEquals(80, (int)rPulse.getPulse());
            } else if (measurement instanceof Temperature) {
                Temperature rTemperature = (Temperature) measurement;
                assertEquals("25-03-2017 7:54 PM", MediPalUtility.convertDateToString(rTemperature.getMeasuredOn(), "dd-MM-yyyy h:mm a"));
                assertEquals(100, (int)rTemperature.getTemperature());
            } else if (measurement instanceof Weight) {
                Weight rWeight = (Weight) measurement;
                assertEquals("25-03-2017 7:54 PM", MediPalUtility.convertDateToString(rWeight.getMeasuredOn(), "dd-MM-yyyy h:mm a"));
                assertEquals(150, (int)rWeight.getWeight());
            }
        }
    }

    @Test
    public void testRetrieveMeasurement() throws Exception {
        Date measuredOn1 = MediPalUtility.convertStringToDate("24-03-2017 7:54 PM", "dd-MM-yyyy h:mm a");
        BloodPressure bloodPressure1 = new BloodPressure(measuredOn1, 25, 50);
        Pulse pulse1 = new Pulse(measuredOn1, 40);
        Temperature temperature1 = new Temperature(measuredOn1, 50);
        Weight weight1 = new Weight(measuredOn1, 75);

        measurementDAO.insert(bloodPressure1, pulse1, weight1, temperature1);

        Date measuredOn2 = MediPalUtility.convertStringToDate("25-03-2017 7:54 PM", "dd-MM-yyyy h:mm a");
        BloodPressure bloodPressure2 = new BloodPressure(measuredOn2, 50, 100);
        Pulse pulse2 = new Pulse(measuredOn2, 80);
        Temperature temperature2 = new Temperature(measuredOn2, 100);
        Weight weight2 = new Weight(measuredOn2, 150);

        measurementDAO.insert(bloodPressure2, pulse2, weight2, temperature2);

        List<Object> measurementList = measurementDAO.retrieve(null,null);

        for(int i = measurementList.size() - 4; i < measurementList.size(); i++) {
            Object measurement = measurementList.get(i);

            if (measurement instanceof BloodPressure) {
                BloodPressure rBloodPressure = (BloodPressure) measurement;
                assertEquals("25-03-2017 7:54 PM", MediPalUtility.convertDateToString(rBloodPressure.getMeasuredOn(), "dd-MM-yyyy h:mm a"));
                assertEquals(50, (int)rBloodPressure.getSystolic());
                assertEquals(100, (int)rBloodPressure.getDiastolic());
            } else if (measurement instanceof Pulse) {
                Pulse rPulse = (Pulse) measurement;
                assertEquals("25-03-2017 7:54 PM", MediPalUtility.convertDateToString(rPulse.getMeasuredOn(), "dd-MM-yyyy h:mm a"));
                assertEquals(80, (int)rPulse.getPulse());
            } else if (measurement instanceof Temperature) {
                Temperature rTemperature = (Temperature) measurement;
                assertEquals("25-03-2017 7:54 PM", MediPalUtility.convertDateToString(rTemperature.getMeasuredOn(), "dd-MM-yyyy h:mm a"));
                assertEquals(100, (int)rTemperature.getTemperature());
            } else if (measurement instanceof Weight) {
                Weight rWeight = (Weight) measurement;
                assertEquals("25-03-2017 7:54 PM", MediPalUtility.convertDateToString(rWeight.getMeasuredOn(), "dd-MM-yyyy h:mm a"));
                assertEquals(150, (int)rWeight.getWeight());
            }
        }
    }
}
