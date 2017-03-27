package sg.edu.nus.iss.medipal.manager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.medipal.dao.MedicineDAO;
import sg.edu.nus.iss.medipal.dao.PersonalBioDAO;
import sg.edu.nus.iss.medipal.pojo.BloodPressure;
import sg.edu.nus.iss.medipal.pojo.Consumption;
import sg.edu.nus.iss.medipal.pojo.Pulse;
import sg.edu.nus.iss.medipal.pojo.Temperature;
import sg.edu.nus.iss.medipal.pojo.Weight;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

/**
 * Created by Divahar on 3/20/2017.
 */

public class ReportManager {

    private static HealthBioManager healthBioManager = null;
    private static StringBuilder csvStr;
    private static String[] consumptionArr = {"Medicine Name", "Quantity", "Consumed on"};
    private static List<Pulse> pulseList = null;
    private static List<Temperature> tempList = null;
    private static List<Weight> weightList = null;
    private static List<Consumption> unConsumptionList = null;
    private static List<Consumption> consumptionList = null;
    private static List<BloodPressure> bpList = null;

    public static TableRow addHeaders(String[] headerArr,
                                      Context context) {

        TableRow rowHeader = new TableRow(context);
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        for (String header : headerArr) {
            TextView tv = new TextView(context);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(14);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(header);
            rowHeader.addView(tv);
        }

        return rowHeader;
    }

    public static boolean
    addBloodPressure(Context context, TableLayout tableLayout) {

        MeasurementManager measurementManager
                = new MeasurementManager();

        boolean isBpAvail = false;

        List<Object> measurementList =
                measurementManager.getMeasurements(context, null, null);

        pulseList = new ArrayList<Pulse>();
        tempList = new ArrayList<Temperature>();
        weightList = new ArrayList<Weight>();
        bpList = new ArrayList<BloodPressure>();

        for (Object object : measurementList) {

            TableRow rowData = new TableRow(context);
            if (object instanceof BloodPressure) {
                BloodPressure pressure =
                        (BloodPressure) object;

                bpList.add(pressure);

                String[] rowArr = {String.valueOf(pressure.getSystolic()),
                        String.valueOf(pressure.getDiastolic()), MediPalUtility.
                        convertDateToString(pressure.getMeasuredOn(), "yyyy MMM dd HH:mm"), "120/80 to 140/90"};

                for (String row : rowArr) {
                    TextView tv = new TextView(context);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(14);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setText(row);
                    rowData.addView(tv);
                }
                tableLayout.addView(rowData);
                isBpAvail = true;
            }
            if (object instanceof Pulse) {
                Pulse pulse =
                        (Pulse) object;
                pulseList.add(pulse);
            }
            if (object instanceof Temperature) {
                Temperature temperature =
                        (Temperature) object;
                tempList.add(temperature);
            }
            if (object instanceof Weight) {
                Weight weight =
                        (Weight) object;
                weightList.add(weight);
            }
        }

        return isBpAvail;

    }

    public static boolean addPulse(Context context,
                                   TableLayout tableLayout) {

        boolean isPulse = false;

        for (Pulse pulse : pulseList) {

            TableRow rowData = new TableRow(context);

            String[] rowArr = {String.valueOf(pulse.getPulse()),
                    MediPalUtility.convertDateToString(pulse.getMeasuredOn(), "yyyy MMM dd HH:mm"), "60 to 100"};

            for (String row : rowArr) {
                TextView tv = new TextView(context);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(14);
                tv.setPadding(5, 5, 5, 5);
                tv.setText(row);
                rowData.addView(tv);
            }
            tableLayout.addView(rowData);
            isPulse = true;
        }
        return isPulse;
    }

    public static boolean addTemperature(Context context,
                                         TableLayout tableLayout) {

        boolean isTemp = false;

        for (Temperature temperature : tempList) {
            TableRow rowData = new TableRow(context);
            String[] rowArr = {String.valueOf(temperature.getTemperature()),
                    MediPalUtility.convertDateToString(temperature.getMeasuredOn(), "yyyy MMM dd HH:mm"), "97 to 99"};

            for (String row : rowArr) {
                TextView tv = new TextView(context);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(14);
                tv.setPadding(5, 5, 5, 5);
                tv.setText(row);
                rowData.addView(tv);
            }
            tableLayout.addView(rowData);
            isTemp = true;
        }
        return isTemp;
    }

    public static boolean addWeight(Context context,
                                    TableLayout tableLayout) {

        boolean isWeight = false;
        PersonalBioDAO personalBioDAO =
                new PersonalBioDAO(context);
        int height = personalBioDAO.retrieve().getHeight();
        float heightBmi = height * height;

        for (Weight weight : weightList) {
            TableRow rowData = new TableRow(context);

            float weightBmi = weight.getWeight();
            float bmi = weightBmi / heightBmi;

            String bmiInd = "";

            if (bmi < 18.5) {
                bmiInd = "Underweight";
            } else if (bmi >= 18.5 &&
                    bmi < 25) {
                bmiInd = "Normal";
            } else if (bmi >= 25 &&
                    bmi < 30) {
                bmiInd = "Overweight";
            } else {
                bmiInd = "Obese";
            }


            String[] rowArr = {String.valueOf(weight.getWeight()),
                    MediPalUtility.convertDateToString(weight.getMeasuredOn(), "yyyy MMM dd HH:mm"), String.valueOf(bmi/10000) + " - " + bmiInd};

            for (String row : rowArr) {
                TextView tv = new TextView(context);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(14);
                tv.setPadding(5, 5, 5, 5);
                tv.setText(row);
                rowData.addView(tv);
            }
            tableLayout.addView(rowData);
            isWeight = true;
        }
        return isWeight;
    }

    public static boolean
    addConsumptionContent(Context context, TableLayout tableLayout) {

        ConsumptionManager consumptionManager
                = new ConsumptionManager(context);

        boolean isConsump = false;

        MedicineDAO medicineDAO
                = new MedicineDAO(context);

        consumptionList =
                consumptionManager.getConsumptions();

        unConsumptionList = new ArrayList<Consumption>();

        for (Consumption consumption : consumptionList) {

            TableRow rowData = new TableRow(context);
            String medcineName = medicineDAO.
                    getMedNameQty(consumption.getMedicineId()).get(0);
            String consumedOn = consumption.getConsumedOn();
            int quantity = consumption.getQuality();

            if (quantity > 0) {
                String[] rowArr = {medcineName, String.valueOf(quantity), consumedOn};

                for (String row : rowArr) {
                    TextView tv = new TextView(context);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(14);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setText(row);
                    rowData.addView(tv);
                }
                tableLayout.addView(rowData);
                isConsump = true;
            } else {
                unConsumptionList.add(consumption);
            }
        }

        return isConsump;

    }

    public static boolean addUnconsumption(Context context,
                                           TableLayout tableLayout) {

        MedicineDAO medicineDAO
                = new MedicineDAO(context);

        boolean isUnconsump = false;

        for (Consumption consumption : unConsumptionList) {
            TableRow rowData = new TableRow(context);

            List<String> medList =
                    medicineDAO.getMedNameQty(consumption.getMedicineId());

            String[] rowArr = {medList.get(0), String.valueOf(medList.get(1)),
                    consumption.getConsumedOn()};

            for (String row : rowArr) {
                TextView tv = new TextView(context);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(14);
                tv.setPadding(5, 5, 5, 5);
                tv.setText(row);
                rowData.addView(tv);
            }

            isUnconsump = true;

            tableLayout.addView(rowData);
        }
        return isUnconsump;
    }

    public static String addBptoCsv(String[] headerArr,
                                    Context context) {


        csvStr = new StringBuilder();

        csvStr.append("Blood Pressure").append("\n");

        for (String header : headerArr) {
            csvStr.append(header);

            if (!header.equals(headerArr[headerArr.length - 1])) {
                csvStr.append(",");
            }
        }

        csvStr.append("\n");

        for (BloodPressure pressure : bpList) {

            String systolic = String.valueOf(pressure.getSystolic());
            String diastolic = String.valueOf(pressure.getDiastolic());
            String measuredOn = MediPalUtility.
                    convertDateToString(pressure.getMeasuredOn(), "yyyy MMM dd");
            String refRange = "120/80 to 140/90";


            csvStr.append(systolic).append(",");
            csvStr.append(diastolic).append(",");
            csvStr.append(measuredOn).append(",");
            csvStr.append(refRange);
        }

        csvStr.append("\n").append("\n");

        return csvStr.toString();
    }

    public static String addPulseToCsv(String[] headerArr,
                                       Context context) {


        csvStr = new StringBuilder();

        csvStr.append("Pulse").append("\n");

        for (String header : headerArr) {
            csvStr.append(header);

            if (!header.equals(headerArr[headerArr.length - 1])) {
                csvStr.append(",");
            }
        }

        csvStr.append("\n");

        for (Pulse pulse : pulseList) {

            String pulseInp = String.valueOf(pulse.getPulse());
            String measuredOn = MediPalUtility.
                    convertDateToString(pulse.getMeasuredOn(), "yyyy MMM dd");
            String refRange = "60 to 100";


            csvStr.append(pulseInp).append(",");
            csvStr.append(measuredOn).append(",");
            csvStr.append(refRange);
        }

        csvStr.append("\n").append("\n");

        return csvStr.toString();
    }

    public static String addWeightToCsv(String[] headerArr,
                                        Context context) {


        csvStr = new StringBuilder();

        csvStr.append("Weight").append("\n");

        for (String header : headerArr) {
            csvStr.append(header);

            if (!header.equals(headerArr[headerArr.length - 1])) {
                csvStr.append(",");
            }
        }

        csvStr.append("\n");

        PersonalBioDAO personalBioDAO =
                new PersonalBioDAO(context);
        int height = personalBioDAO.retrieve().getHeight();
        float heightBmi = height * height;

        for (Weight weight : weightList) {

            float weightBmi = weight.getWeight();
            float bmi = weightBmi / heightBmi;

            String bmiInd = "";

            if (bmi < 18.5) {
                bmiInd = "Underweight";
            } else if (bmi >= 18.5 &&
                    bmi < 25) {
                bmiInd = "Normal";
            } else if (bmi >= 25 &&
                    bmi < 30) {
                bmiInd = "Overweight";
            } else {
                bmiInd = "Obese";
            }

            String weightInp = String.valueOf(weight.getWeight());
            String measuredOn = MediPalUtility.
                    convertDateToString(weight.getMeasuredOn(), "yyyy MMM dd");
            String bmiCsv = String.valueOf(bmi/10000) + " - " + bmiInd;


            csvStr.append(weightInp).append(",");
            csvStr.append(measuredOn).append(",");
            csvStr.append(bmiCsv);
        }

        csvStr.append("\n").append("\n");

        return csvStr.toString();
    }

    public static String addTemperatureToCsv(String[] headerArr,
                                             Context context) {


        csvStr = new StringBuilder();

        csvStr.append("Temperature").append("\n");

        for (String header : headerArr) {
            csvStr.append(header);

            if (!header.equals(headerArr[headerArr.length - 1])) {
                csvStr.append(",");
            }
        }

        csvStr.append("\n");

        for (Temperature temperature : tempList) {

            String tempInp = String.valueOf(temperature.getTemperature());
            String measuredOn = MediPalUtility.
                    convertDateToString(temperature.getMeasuredOn(), "yyyy MMM dd");
            String refRange = "97 to 99";


            csvStr.append(tempInp).append(",");
            csvStr.append(measuredOn).append(",");
            csvStr.append(refRange);
        }

        csvStr.append("\n").append("\n");

        return csvStr.toString();
    }

    public static String addConsumptionToCsv(String[] headerArr,
                                             Context context) {


        csvStr = new StringBuilder();

        csvStr.append("Consumption").append("\n");

        for (String header : headerArr) {
            csvStr.append(header);

            if (!header.equals(headerArr[headerArr.length - 1])) {
                csvStr.append(",");
            }
        }

        csvStr.append("\n");

        MedicineDAO medicineDAO
                = new MedicineDAO(context);


        for (Consumption consumption : consumptionList) {

            String medcineName = medicineDAO.getMedNameQty(consumption.getMedicineId()).get(0);
            String consumedOn = consumption.getConsumedOn();
            int quantity = consumption.getQuality();


            csvStr.append(medcineName).append(",");
            csvStr.append(String.valueOf(quantity)).append(",");
            csvStr.append(consumedOn);
        }

        csvStr.append("\n").append("\n");

        return csvStr.toString();
    }

    public static String addUnconsumpToCsv(String[] headerArr,
                                           Context context) {


        csvStr = new StringBuilder();

        csvStr.append("Unconsumption").append("\n");

        for (String header : headerArr) {
            csvStr.append(header);

            if (!header.equals(headerArr[headerArr.length - 1])) {
                csvStr.append(",");
            }
        }

        csvStr.append("\n");

        MedicineDAO medicineDAO
                = new MedicineDAO(context);


        for (Consumption consumption : unConsumptionList) {

            List<String> medList = medicineDAO.
                    getMedNameQty(consumption.getMedicineId());
            String medName = medList.get(0);
            String missConsump = consumption.getConsumedOn();
            String missQty = medList.get(1);


            csvStr.append(medName).append(",");
            csvStr.append(missQty).append(",");
            csvStr.append(missConsump);
        }

        csvStr.append("\n").append("\n");

        return csvStr.toString();
    }
}


