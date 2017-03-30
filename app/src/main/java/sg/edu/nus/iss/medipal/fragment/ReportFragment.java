package sg.edu.nus.iss.medipal.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.manager.ReportManager;


/**
 * Created by Divahar on 3/19/2017.
 * Description: This fragment is used to display the reports based on filter conditions user selects
 */

public class ReportFragment extends Fragment {

    private TableLayout tableLayout;
    private View reportFragment;
    private TableRow rowHeader;
    private String report;
    private static String[] bpArr = {"Systolic", "Diastolic", "Measured on", "Reference Range"};
    private static String[] pulseArr = {"Pulse", "Measured on", "Reference Range"};
    private static String[] tempArr = {"Temperature", "Measured on", "Reference Range"};
    private static String[] weightArr = {"Weight", "Measured on", "BMI"};
    private static String[] consumptionArr = {"Medicine", "Consumed Qty", "Consumed on"};
    private static String[] unconsumptionArr = {"Medicine", "Missed Qty", "Missed consumption Date"};
    private final static String[] REPORTTYPE = {"All Measurements", "BP measurement", "Pulse measurement", "Weight measurement", "Temperature measurement", "Consumed and Unconsumed Medicines"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Spinner reportType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FloatingActionButton aFab;
        reportFragment = inflater.inflate(R.layout.fragment_report_view, container, false);

        // Reference to TableLayout
        tableLayout = (TableLayout) reportFragment.findViewById(R.id.reportable);

        populateContent("All Measurements");

        reportType = (Spinner) reportFragment.findViewById(R.id.reporttype);

        ArrayAdapter<String> spinnerAdapterOne = new ArrayAdapter<>(reportFragment.getContext(), android.R.layout.simple_dropdown_item_1line, REPORTTYPE);
        reportType.setAdapter(spinnerAdapterOne);

        aFab = (FloatingActionButton) reportFragment.findViewById(R.id.fab);
        aFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addContentToCsv();

                File csvFile = null;
                FileOutputStream outputStream;
                try {
                    csvFile = new File(getContext().getExternalCacheDir(), "Reports.csv");
                    outputStream = new FileOutputStream(csvFile);
                    outputStream.write(addContentToCsv().getBytes());
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Uri reportFile = null;
                reportFile = Uri.fromFile(csvFile);

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Medipal - Reports");
                emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("Hi, <br><br><br> Please find the medical report attached <br><br><br> Cheers, <br> Team - Medipal FT 01"));
                emailIntent.putExtra(Intent.EXTRA_STREAM, reportFile);
                emailIntent.setType("text/html");
                startActivity(Intent.createChooser(emailIntent, "Send mail:"));
            }
        });

        reportType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                int item = reportType.getSelectedItemPosition();
                report = Arrays.asList(REPORTTYPE).get(item);
                populateContent(report);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                report = "";
            }
        });


        return reportFragment;
    }

    private void populateContent(String report) {


        tableLayout.removeAllViews();
        //Blood Pressure
        if (report.equals("BP measurement") ||
                report.equals("All Measurements")) {
            rowHeader =
                    ReportManager.addHeaders(bpArr, getContext());
            rowHeader.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
            tableLayout.addView(rowHeader);
            boolean isBpAvail =
                    ReportManager.addBloodPressure(getContext(), tableLayout);
            if (!isBpAvail) {

                TableRow rowHeader = new TableRow(getContext());
                rowHeader.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                TextView tv = new TextView(getContext());
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(14);
                tv.setText("No data available");
                rowHeader.addView(tv);

                tableLayout.addView(rowHeader);
            }
        }

        // Pulse
        if (report.equals("Pulse measurement")
                || report.equals("All Measurements")) {
            rowHeader =
                    ReportManager.addHeaders(pulseArr, getContext());
            rowHeader.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
            tableLayout.addView(rowHeader);
            boolean isPulseAvail =
                    ReportManager.addPulse(getContext(), tableLayout);

            if (!isPulseAvail) {

                TableRow rowHeader = new TableRow(getContext());
                rowHeader.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                TextView tv = new TextView(getContext());
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(14);
                tv.setText("No data available");
                rowHeader.addView(tv);

                tableLayout.addView(rowHeader);
            }
        }

        // Temperature
        if (report.equals("Temperature measurement")
                || report.equals("All Measurements")) {
            rowHeader =
                    ReportManager.addHeaders(tempArr, getContext());
            rowHeader.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
            tableLayout.addView(rowHeader);
            boolean isTempAvail =
                    ReportManager.addTemperature(getContext(), tableLayout);

            if (!isTempAvail) {

                TableRow rowHeader = new TableRow(getContext());
                rowHeader.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                TextView tv = new TextView(getContext());
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(14);
                tv.setText("No data available");
                rowHeader.addView(tv);

                tableLayout.addView(rowHeader);
            }
        }


        // Weight
        if (report.equals("Weight measurement")
                || report.equals("All Measurements")) {
            rowHeader =
                    ReportManager.addHeaders(weightArr, getContext());
            rowHeader.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
            tableLayout.addView(rowHeader);
            boolean isWeightAvail =
                    ReportManager.addWeight(getContext(), tableLayout);

            if (!isWeightAvail) {

                TableRow rowHeader = new TableRow(getContext());
                rowHeader.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                TextView tv = new TextView(getContext());
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(14);
                tv.setText("No data available");
                rowHeader.addView(tv);

                tableLayout.addView(rowHeader);
            }
        }


        if (report.equals("Consumed and Unconsumed Medicines")
                || report.equals("All Measurements")) {
            // consumption
            rowHeader =
                    ReportManager.addHeaders(consumptionArr, getContext());
            rowHeader.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
            tableLayout.addView(rowHeader);
            boolean isConsumpAvail =
                    ReportManager.addConsumptionContent(getContext(), tableLayout);

            if (!isConsumpAvail) {

                TableRow rowHeader = new TableRow(getContext());
                rowHeader.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                TextView tv = new TextView(getContext());
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(14);
                tv.setText("No data available");
                rowHeader.addView(tv);
                tableLayout.addView(rowHeader);
            }

            //UnConsumption
            rowHeader =
                    ReportManager.addHeaders(unconsumptionArr, getContext());
            rowHeader.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
            tableLayout.addView(rowHeader);
            boolean unConsump =
                    ReportManager.addUnconsumption(getContext(), tableLayout);

            if (!unConsump) {

                TableRow rowHeader = new TableRow(getContext());
                rowHeader.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                TextView tv = new TextView(getContext());
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(14);
                tv.setText("No data available");
                rowHeader.addView(tv);
                tableLayout.addView(rowHeader);
            }
        }


    }

    private String addContentToCsv() {

        String toCsvStr = "";

        if (report.equals("BP measurement") ||
                report.equals("All Measurements")) {
            String bp = ReportManager.
                    addBptoCsv(bpArr, getContext());
            toCsvStr = toCsvStr + bp;
        }

        if (report.equals("Pulse measurement")
                || report.equals("All Measurements")) {
            String pulse = ReportManager.addPulseToCsv(pulseArr, getContext());
            toCsvStr = toCsvStr + pulse;
        }

        if (report.equals("Temperature measurement")
                || report.equals("All Measurements")) {
            String temperature = ReportManager.addTemperatureToCsv(tempArr, getContext());
            toCsvStr = toCsvStr + temperature;
        }

        if (report.equals("Weight measurement")
                || report.equals("All Measurements")) {
            String weight = ReportManager.addWeightToCsv(weightArr, getContext());
            toCsvStr = toCsvStr + weight;
        }

        if (report.equals("Consumed and Unconsumed Medicines")
                || report.equals("All Measurements")) {
            String consumption = ReportManager.addConsumptionToCsv(consumptionArr, getContext());
            String unConsump = ReportManager.addUnconsumpToCsv(unconsumptionArr, getContext());

            toCsvStr = toCsvStr + consumption;
            toCsvStr = toCsvStr + unConsump;
        }
        return toCsvStr;
    }

}
