package sg.edu.nus.iss.medipal.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Divahar on 3/17/2017.
 * Mpdified by Naval on 3/18/2017 - added
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

    public static Long convertDateTimeToNumber(String DateTime)
    {
        String dateTimeSplit[] =DateTime.split(" ", 2);

        String dateSplit[] = dateTimeSplit[0].split("-");
        String timeSplit[] = dateTimeSplit[1].split(" ");
        String timeSubSplit[] = timeSplit[0].split(":");

        Integer HourAdd = 0;
        if(timeSplit[1].equalsIgnoreCase("AM") && timeSubSplit[0].equalsIgnoreCase("12")) {
            timeSubSplit[0]="00";
        }
        if(timeSplit[1].equalsIgnoreCase("PM")){
            HourAdd=12;
        }
        Integer hour = (Integer.valueOf(timeSubSplit[0]) +  HourAdd) % 24 ;

        String convertedDateTime = dateSplit[2]+dateSplit[1]+dateSplit[0]+hour.toString()+timeSubSplit[1];

        return Long.valueOf(convertedDateTime);
    }

    public static String covertDateToString(Date date,String format) {
        SimpleDateFormat dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat(
                    format);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return dateFormat.format(date);
    }

}
