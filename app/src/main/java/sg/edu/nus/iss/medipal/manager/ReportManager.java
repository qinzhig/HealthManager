package sg.edu.nus.iss.medipal.manager;

import android.content.Context;
import android.graphics.Color;
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
            //tv.setGravity(Gravity.CENTER);
            tv.setTextSize(12);
            //tv.setPadding(5, 5, 5, 5);
            tv.setText(header);
            rowHeader.addView(tv);
        }

        return rowHeader;
    }

    public static TableLayout
    addBloodPressure(Context context, TableLayout tableLayout) {

        MeasurementManager measurementManager
                = new MeasurementManager();

        List<Object> measurementList =
                measurementManager.getMeasurements(context);

        pulseList = new ArrayList<Pulse>();
        tempList = new ArrayList<Temperature>();
        weightList = new ArrayList<Weight>();

        for (Object object : measurementList) {

            TableRow rowData = new TableRow(context);
            if (object instanceof BloodPressure) {
                BloodPressure pressure =
                        (BloodPressure) object;
                String[] rowArr = {String.valueOf(pressure.getSystolic()),
                        String.valueOf(pressure.getDiastolic()), MediPalUtility.
                        convertDateToString(pressure.getMeasuredOn(), "yyyy MMM dd HH:mm"), "120/80 to 140/90"};

                for (String row : rowArr) {
                    TextView tv = new TextView(context);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    //tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(12);
                    //tv.setPadding(5, 5, 5, 5);
                    tv.setText(row);
                    rowData.addView(tv);
                }
                tableLayout.addView(rowData);
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

        return tableLayout;

    }

    public static TableLayout addPulse(Context context,
                                       TableLayout tableLayout) {

        for (Pulse pulse : pulseList) {

            TableRow rowData = new TableRow(context);

            String[] rowArr = {String.valueOf(pulse.getPulse()),
                    MediPalUtility.convertDateToString(pulse.getMeasuredOn(), "yyyy MMM dd HH:mm"), "60 to 100"};

            for (String row : rowArr) {
                TextView tv = new TextView(context);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                //tv.setGravity(Gravity.CENTER);
                tv.setTextSize(12);
                //tv.setPadding(5, 5, 5, 5);
                tv.setText(row);
                rowData.addView(tv);
            }
            tableLayout.addView(rowData);
        }
        return tableLayout;
    }

    public static TableLayout addTemperature(Context context,
                                             TableLayout tableLayout) {

        for (Temperature temperature : tempList) {
            TableRow rowData = new TableRow(context);
            String[] rowArr = {String.valueOf(temperature.getTemperature()),
                    MediPalUtility.convertDateToString(temperature.getMeasuredOn(), "yyyy MMM dd HH:mm"), "97 to 99"};

            for (String row : rowArr) {
                TextView tv = new TextView(context);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                //tv.setGravity(Gravity.CENTER);
                tv.setTextSize(12);
                //tv.setPadding(5, 5, 5, 5);
                tv.setText(row);
                rowData.addView(tv);
            }
            tableLayout.addView(rowData);
        }
        return tableLayout;
    }

    public static TableLayout addWeight(Context context,
                                        TableLayout tableLayout) {

        PersonalBioDAO personalBioDAO =
                new PersonalBioDAO(context);
        float height = (float) (personalBioDAO.retrieve().getHeight() / 100);
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
                    MediPalUtility.convertDateToString(weight.getMeasuredOn(), "yyyy MMM dd HH:mm"), String.valueOf(bmi) + " - " + bmiInd};

            for (String row : rowArr) {
                TextView tv = new TextView(context);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                //tv.setGravity(Gravity.CENTER);
                tv.setTextSize(12);
                //tv.setPadding(5, 5, 5, 5);
                tv.setText(row);
                rowData.addView(tv);
            }
            tableLayout.addView(rowData);
        }
        return tableLayout;
    }

    public static TableLayout
    addConsumptionContent(Context context, TableLayout tableLayout) {

        ConsumptionManager consumptionManager
                = new ConsumptionManager(context);


        MedicineDAO medicineDAO
                = new MedicineDAO(context);

        List<Consumption> consumptionList =
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
                    //tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(12);
                    //tv.setPadding(5, 5, 5, 5);
                    tv.setText(row);
                    rowData.addView(tv);
                }
                tableLayout.addView(rowData);
            } else {
                unConsumptionList.add(consumption);
            }
        }

        return tableLayout;

    }

    public static TableLayout addUnconsumption(Context context,
                                               TableLayout tableLayout) {

        MedicineDAO medicineDAO
                = new MedicineDAO(context);

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
                //tv.setGravity(Gravity.CENTER);
                tv.setTextSize(12);
                //tv.setPadding(5, 5, 5, 5);
                tv.setText(row);
                rowData.addView(tv);
            }
            tableLayout.addView(rowData);
        }
        return tableLayout;
    }

    public static void addConsumptionToCsv(String[] headerArr,
                                           Context context) {

        for (String header : headerArr) {
            csvStr.append(header);

            if (!header.equals(headerArr[headerArr.length - 1])) {
                csvStr.append(",");
            }
        }

        csvStr.append("\n");

        ConsumptionManager consumptionManager
                = new ConsumptionManager(context);


        MedicineDAO medicineDAO
                = new MedicineDAO(context);

        List<Consumption> consumptionList =
                consumptionManager.getConsumptions();

        for (Consumption consumption : consumptionList) {

            String medcineName = medicineDAO.getMedNameQty(consumption.getMedicineId()).get(0);
            String consumedOn = consumption.getConsumedOn();
            int quantity = consumption.getQuality();


            csvStr.append(medcineName).append(",");
            csvStr.append(consumedOn).append(",");
            csvStr.append(String.valueOf(quantity));
        }

        csvStr.append("\n");
    }
}


