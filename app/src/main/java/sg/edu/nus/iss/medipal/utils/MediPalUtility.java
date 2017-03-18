package sg.edu.nus.iss.medipal.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Divahar on 3/17/2017.
 */

public class MediPalUtility {

    public static String covertDateToString(Date date) {
        SimpleDateFormat dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat(
                    "dd MMM yyyy");
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return dateFormat.format(date);
    }

    public static Date covertStringToDate(String date) {
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        Date convertedDate = null;
        try {
            convertedDate = df.parse(date);
            String dateStr = df.format(convertedDate);
        } catch (ParseException parseExp) {
            parseExp.printStackTrace();
        }
        return convertedDate;
    }
}
