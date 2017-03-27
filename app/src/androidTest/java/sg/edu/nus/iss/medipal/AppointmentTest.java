package sg.edu.nus.iss.medipal;

import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;

import sg.edu.nus.iss.medipal.pojo.Appointment;

import static junit.framework.Assert.assertEquals;

/**
 * Created by levis on 3/27/2017.
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppointmentTest extends BeforeTestSetUp  {

    @Test
    public void testInsertAppointment() throws Exception {
        Appointment appointment = new Appointment(null, "NUS-ISS", "25-03-2017 7:54 PM", "Android Presentation");
        appointmentDAO.insert(appointment);

        List<Appointment> appointmentList = appointmentDAO.getAppointments(true);
        Appointment rAppointment = appointmentList.get(appointmentList.size() - 1);

        assertEquals("NUS-ISS", rAppointment.getLocation());
        assertEquals("25-03-2017 7:54 PM", rAppointment.getAppointment());
        assertEquals("Android Presentation", rAppointment.getDescription());
    }

    @Test
    public void testRetrieveAppointment() throws Exception {
        Appointment appointment1 = new Appointment(null, "NUS-ISS", "25-3-2016 7:54 PM", "Android Presentation");
        appointmentDAO.insert(appointment1);

        Appointment appointment2 = new Appointment(null, "NUS-UTOWN", "25-3-2018 7:54 PM", "DINNER");
        appointmentDAO.insert(appointment2);

        List<Appointment> appointmentList = appointmentDAO.getAppointments(false);
        Appointment rAppointment = appointmentList.get(appointmentList.size() - 1);

        assertEquals("NUS-ISS", rAppointment.getLocation());
        assertEquals("25-3-2016 7:54 PM", rAppointment.getAppointment());
        assertEquals("Android Presentation", rAppointment.getDescription());
    }

    @Test
    public void testUpdateAppointment() throws Exception {
        Appointment appointment = new Appointment(null, "NUS-ISS", "25-03-2017 7:54 PM", "Android Presentation");
        appointmentDAO.insert(appointment);

        List<Appointment> appointmentList = appointmentDAO.getAppointments(true);
        Appointment uAppointment = appointmentList.get(appointmentList.size() - 1);
        uAppointment.setLocation("NUS-UTOWN");
        uAppointment.setAppointment("25-03-2018 7:54 PM");
        uAppointment.setDescription("SLEEP");
        appointmentDAO.update(uAppointment);

        appointmentList = appointmentDAO.getAppointments(true);
        Appointment rAppointment = appointmentList.get(appointmentList.size() - 1);

        assertEquals("NUS-UTOWN", rAppointment.getLocation());
        assertEquals("25-03-2018 7:54 PM", rAppointment.getAppointment());
        assertEquals("SLEEP", rAppointment.getDescription());
    }

    @Test
    public void testDeleteAppointment() throws Exception {
        Appointment appointment1 = new Appointment(null, "NUS-ISS", "25-3-2018 7:54 PM", "Android Presentation");
        appointmentDAO.insert(appointment1);

        Appointment appointment2 = new Appointment(null, "NUS-UTOWN", "25-3-2019 7:54 PM", "DINNER");
        appointmentDAO.insert(appointment2);

        Appointment appointment3 = new Appointment(null, "NUS-LIBRARY", "25-3-2020 7:54 PM", "SLEEP");
        appointmentDAO.insert(appointment3);

        List<Appointment> appointmentList = appointmentDAO.getAppointments(true);
        Appointment dAppointment1 = appointmentList.get(appointmentList.size() - 1);
        appointmentDAO.deleteAppointment(dAppointment1.getId().toString());

        appointmentList = appointmentDAO.getAppointments(true);
        Appointment dAppointment2 = appointmentList.get(appointmentList.size() - 2);
        appointmentDAO.deleteAppointment(dAppointment2.getId().toString());

        appointmentList = appointmentDAO.getAppointments(true);
        Appointment rAppointment = appointmentList.get(appointmentList.size() - 1);
        assertEquals("NUS-UTOWN", rAppointment.getLocation());
        assertEquals("25-3-2019 7:54 PM", rAppointment.getAppointment());
        assertEquals("DINNER", rAppointment.getDescription());
    }
}
