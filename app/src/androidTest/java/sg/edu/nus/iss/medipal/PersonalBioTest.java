package sg.edu.nus.iss.medipal;

import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.Date;

import sg.edu.nus.iss.medipal.pojo.PersonalBio;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

import static junit.framework.Assert.assertEquals;


/**
 * Created by Divahar on 3/21/2017.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonalBioTest extends BeforeTestSetUp{

    private PersonalBio personalBio;

    @Test
    public void test1InsertPersonalBio() throws Exception {

        personalBio = new
                PersonalBio("Divahar", MediPalUtility.
                convertStringToDate("17 Nov 1990", "dd MMM yyyy"), "ABC123", "Blk 715, Singapore", 120715, 171, "O+ve");

        personalBioDAO.insert(personalBio);

        personalBio = personalBioDAO.retrieve();

        String dobStr = MediPalUtility.convertDateToString(personalBio.getDob(), "dd MMM yyyy");
        String compareDobStr = MediPalUtility.convertDateToString(new Date("17 Nov 1990"), "dd MMM yyyy");

        assertEquals("Divahar",
                personalBio.getName());
        assertEquals(compareDobStr,
                dobStr);
        assertEquals("ABC123",
                personalBio.getIdNo());
        assertEquals("Blk 715, Singapore",
                personalBio.getAddress());
        assertEquals(120715,
                personalBio.getPostalCode());
        assertEquals(171,
                personalBio.getHeight());
        assertEquals("O+ve",
                personalBio.getBloodType());

    }

    @Test
    public void test2RetrievePersonalBio() throws Exception {

        personalBio = personalBioDAO.retrieve();

        String dobStr = MediPalUtility.convertDateToString(personalBio.getDob(), "dd MMM yyyy");
        String compareDobStr = MediPalUtility.convertDateToString(new Date("17 Nov 1990"), "dd MMM yyyy");

        assertEquals("Divahar",
                personalBio.getName());
        assertEquals(compareDobStr,
                dobStr);
        assertEquals("ABC123",
                personalBio.getIdNo());
        assertEquals("Blk 715, Singapore",
                personalBio.getAddress());
        assertEquals(120715,
                personalBio.getPostalCode());
        assertEquals(171,
                personalBio.getHeight());
        assertEquals("O+ve",
                personalBio.getBloodType());

    }

    @Test
    public void test3UpdatePersonalBio() throws Exception {

        personalBio = new PersonalBio(1, "Divahar S", MediPalUtility.convertStringToDate("17 Nov 1991", "dd MMM yyyy"), "ABC1234",
                "Blk 715, Clementi West St 2, Singapore", 130715, 173, "O-ve");

        personalBioDAO.update(personalBio);

        personalBio = personalBioDAO.retrieve();

        String dobStr = MediPalUtility.convertDateToString(personalBio.getDob(), "dd MMM yyyy");
        String compareDobStr = MediPalUtility.convertDateToString(new Date("17 Nov 1991"), "dd MMM yyyy");

        assertEquals("Divahar S",
                personalBio.getName());
        assertEquals(compareDobStr,
                dobStr);
        assertEquals("ABC1234",
                personalBio.getIdNo());
        assertEquals("Blk 715, Clementi West St 2, Singapore",
                personalBio.getAddress());
        assertEquals(130715,
                personalBio.getPostalCode());
        assertEquals(173,
                personalBio.getHeight());
        assertEquals("O-ve",
                personalBio.getBloodType());

    }

}
