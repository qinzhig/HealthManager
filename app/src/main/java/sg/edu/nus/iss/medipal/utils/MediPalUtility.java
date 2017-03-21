package sg.edu.nus.iss.medipal.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Divahar on 3/17/2017.
 * Modified by Naval on 3/18/2017
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

    public static boolean isNotFutureDate(String date) {

        boolean isValid = false;

        try {
            String dateSplit[] = date.split(" ");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMMdd");
            Date varDate = dateFormat.parse(dateSplit[2] + dateSplit[1] + dateSplit[0]);
            dateFormat = new SimpleDateFormat("yyyyMMdd");
            String convertedDate = dateFormat.format(varDate);
            String currentDate = convertDateToString(Calendar.getInstance().getTime(), "yyyyMMdd");
            isValid = (Long.valueOf(currentDate) >= Long.valueOf(convertedDate));
        } catch (Exception exp) {
            exp.printStackTrace();
        }

        return isValid;
    }

    public static Long convertDateTimeToNumber(String DateTime) {
        String dateTimeSplit[] = DateTime.split(" ", 2);

        String dateSplit[] = dateTimeSplit[0].split("-");
        String timeSplit[] = dateTimeSplit[1].split(" ");
        String timeSubSplit[] = timeSplit[0].split(":");

        Integer HourAdd = 0;
        if (timeSplit[1].equalsIgnoreCase("AM") && timeSubSplit[0].equalsIgnoreCase("12")) {
            timeSubSplit[0] = "00";
        } else if (timeSplit[1].equalsIgnoreCase("PM") && !(timeSubSplit[0].equalsIgnoreCase("12"))) {
            HourAdd = 12;
        }
        Integer hour = (Integer.valueOf(timeSubSplit[0]) + HourAdd) % 24;
        String hourString;
        if (hour < 10)
            hourString = "0" + hour.toString();
        else
            hourString = hour.toString();

        String convertedDateTime = dateSplit[2] + dateSplit[1] + dateSplit[0] + hourString + timeSubSplit[1];

        return Long.valueOf(convertedDateTime);
    }

    public static String convertDateToString(Date date, String format) {
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
        Log.d("dates", currentDate + " " + convertedDate);
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
        String convertedTime = timeSubSplit[0] + timeSubSplit[1];

        if (currentDate.equals(convertedDate)) {
            Integer HourAdd = 0;
            if (timeSplit[1].equalsIgnoreCase("AM") && timeSubSplit[0].equalsIgnoreCase("12")) {
                timeSubSplit[0] = "00";
            } else if (timeSplit[1].equalsIgnoreCase("PM") && !(timeSubSplit[0].equalsIgnoreCase("12"))) {
                HourAdd = 12;
            }
            Integer hour = (Integer.valueOf(timeSubSplit[0]) + HourAdd) % 24;

            String hourString;
            if (hour < 10)
                hourString = "0" + hour.toString();
            else
                hourString = hour.toString();

            convertedTime = hourString + timeSubSplit[1];
            Log.d("CurrentTime", currentTime);
            Log.d("CoonvertTime", convertedTime);
            retVal = (Long.valueOf(currentTime) < Long.valueOf(convertedTime));
        } else if (Long.valueOf(currentDate) < Long.valueOf(convertedDate)) {
            convertedDate = convertedDate + convertedTime;
            String currentDateTime = currentDate + currentTime;
            retVal = (Long.valueOf(currentDateTime) <= Long.valueOf(convertedDate));
        } else
            retVal = false;

        return retVal;
    }

    //method to get the date and time on which a remainder needs to be set
    public static Calendar determineReminderTime(String remainderInterval, String appointmentDate) {
        //Log.d("date:",appointment);
        Integer interval = decodeRemainderInterval(remainderInterval);
        //Log.d("interval:",interval.toString());
        Calendar c = null;
        if (interval != 0) {
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            try {
                c = Calendar.getInstance();
                Date date = df.parse(appointmentDate);
                //Log.d("date", df.format(date));
                c.setTime(date);
                c.add(Calendar.MINUTE, -interval);
                //Log.d("c date", df.format(c.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return c;
    }

    //method to get the interval in minutes to be used to set a reminder
    public static Integer decodeRemainderInterval(String remainderInterval) {
        Integer retVal = 0;
        switch (remainderInterval) {
            case "No Remainder":
                retVal = 0;
                break;
            case "15 Minutes Before":
                retVal = 15;
                break;
            case "30 Minutes Before":
                retVal = 30;
                break;
            case "1 Hour Before":
                retVal = 60;
                break;
            case "4 Hours Before":
                retVal = 240;
                break;
            case "12 Hours Before":
                retVal = 720;
                break;
            case "1 Day Before":
                retVal = 1440;
                break;
            case "2 Day Before":
                retVal = 2880;
                break;
            case "4 Day Before":
                retVal = 5760;
                break;
            case "1 Week Before":
                retVal = 10080;
                break;
        }
        return retVal;
    }
}

