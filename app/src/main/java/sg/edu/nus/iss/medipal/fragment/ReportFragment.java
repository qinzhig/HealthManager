package sg.edu.nus.iss.medipal.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TimePicker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.manager.ReportManager;


/**
 * Created by Divahar on 3/19/2017.
 */

public class ReportFragment extends Fragment implements View.OnClickListener{

    private TableLayout tableLayout;
    private View reportFragment;
    private TableRow rowHeader;
    private static String[] healthBioArr = {"Condition", "Start Date", "Type"};
    private static String[] consumptionArr = {"Medicine Name", "Quantity", "Consumed on"};

    private final static String[] REPORTTYPE = {"All Measurements", "BP Measurement", "Pulse Measurement","Weight Measurement","Temperature Measurement","Consumed Medicines","Un-Consumed Medicines"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    EditText toDate,
            fromDate;
    TextInputLayout l_toDate,l_Fromdate;
    Spinner reportType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FloatingActionButton aFab;
        reportFragment = inflater.inflate(R.layout.fragment_report_view, container, false);

        // Reference to TableLayout
        tableLayout = (TableLayout) reportFragment.findViewById(R.id.reportable);

        populateHealthBioTable();

        toDate = (EditText) reportFragment.findViewById(R.id.fromdate);
        fromDate = (EditText) reportFragment.findViewById(R.id.todate);

        toDate.setOnClickListener(this);
        fromDate.setOnClickListener(this);

        l_Fromdate=(TextInputLayout) reportFragment.findViewById(R.id.edit_text_fromdate);
        l_toDate=(TextInputLayout) reportFragment.findViewById(R.id.edit_text_todate);

        reportType = (Spinner) reportFragment.findViewById(R.id.reporttype);

        ArrayAdapter<String> spinnerAdapterOne = new ArrayAdapter<>(reportFragment.getContext(),android.R.layout.simple_dropdown_item_1line,REPORTTYPE);
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
                emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("Hi, <br><br><br> Please find the medical report attached <br><br><br> Cheers, Team - Medipal FT 01"));
                emailIntent.putExtra(Intent.EXTRA_STREAM, reportFile);
                emailIntent.setType("text/html");
                startActivity(Intent.createChooser(emailIntent, "Send mail:"));
            }
        });

        return reportFragment;
    }

    private void populateHealthBioTable() {

         rowHeader =
                ReportManager.addHeaders(healthBioArr, getContext());

        tableLayout.addView(rowHeader);

        tableLayout =
                ReportManager.addHealthBioContent(getContext(), tableLayout);

         rowHeader =
                ReportManager.addHeaders(consumptionArr, getContext());

        tableLayout.addView(rowHeader);

        tableLayout =
                ReportManager.addConsumptionContent(getContext(), tableLayout);
    }

    private String addContentToCsv() {

        return ReportManager.
                addHealthBioToCsv(healthBioArr, getContext());
    }

    @Override
    public void onClick(View v) {
        final Calendar calender;
        int day,month,year;
        if (v == fromDate) {
            calender = Calendar.getInstance();
            day = calender.get(Calendar.DAY_OF_MONTH);
            month = calender.get(Calendar.MONTH);
            year = calender.get(Calendar.YEAR);

            DatePickerDialog datePicker = new DatePickerDialog(reportFragment.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    fromDate.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                }
            }, day, month, year);
            datePicker.updateDate(year, month, day);
            datePicker.show();
        }
        else if(v == toDate)
        {
            calender = Calendar.getInstance();
            day = calender.get(Calendar.DAY_OF_MONTH);
            month = calender.get(Calendar.MONTH);
            year = calender.get(Calendar.YEAR);

            DatePickerDialog datePicker = new DatePickerDialog(reportFragment.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    toDate.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                }
            }, day, month, year);
            datePicker.updateDate(year, month, day);
            datePicker.show();
        }
    }
}
