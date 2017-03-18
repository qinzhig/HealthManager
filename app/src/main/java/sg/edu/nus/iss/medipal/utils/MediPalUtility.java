package sg.edu.nus.iss.medipal.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        else if(timeSplit[1].equalsIgnoreCase("PM")){
            HourAdd=12;
        }
        Integer hour = (Integer.valueOf(timeSubSplit[0]) +  HourAdd) % 24 ;

        String convertedDateTime = dateSplit[2]+dateSplit[1]+dateSplit[0]+hour.toString()+timeSubSplit[1];

        return Long.valueOf(convertedDateTime);
    }

    public static String convertDateToString(Date date,String format) {
        SimpleDateFormat dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat(
                    format);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return dateFormat.format(date);
    }

    public static Boolean isValidDate(String date) {
        Boolean retVal;
        String dateSplit[] = date.split("-");
        String convertedDate = dateSplit[2] + dateSplit[1] + dateSplit[0];

        String currentDate = convertDateToString(Calendar.getInstance().getTime(), "yyyyMdd");
        Log.d("dates",currentDate+" "+convertedDate);
        retVal = (Long.valueOf(currentDate) <= Long.valueOf(convertedDate));

        return retVal;
    }

    public static Boolean isValidTime(String date, String time) {
        Boolean retVal;

        String dateSplit[] = date.split("-");
        String convertedDate = dateSplit[2] + dateSplit[1] + dateSplit[0];

        String currentDate = convertDateToString(Calendar.getInstance().getTime(), "yyyyMdd");
        String currentTime = convertDateToString(Calendar.getInstance().getTime(), "HHmm");

        String timeSplit[] = time.split(" ");
        String timeSubSplit[] = timeSplit[0].split(":");
        String convertedTime = timeSubSplit[0]+timeSubSplit[1];

        if(Long.valueOf(currentDate) == Long.valueOf(convertedDate))
        {
            Integer HourAdd = 0;
            if(timeSplit[1].equalsIgnoreCase("AM") && timeSubSplit[0].equalsIgnoreCase("12")) {
                timeSubSplit[0]="00";
            }
            else if(timeSplit[1].equalsIgnoreCase("PM")){
                HourAdd=12;
            }
            Integer hour = (Integer.valueOf(timeSubSplit[0]) +  HourAdd) % 24 ;
            currentTime = hour.toString()+timeSubSplit[1];
            retVal = (Long.valueOf(currentTime) < Long.valueOf(convertedTime));
        }
        else if(Long.valueOf(currentDate) < Long.valueOf(convertedDate)){
            convertedDate = convertedDate+convertedTime;
            String currentDateTime=currentDate+currentTime;
            retVal = (Long.valueOf(currentDateTime) <= Long.valueOf(convertedDate));
        }
        else
            retVal=false;

        return retVal;
    }

}
