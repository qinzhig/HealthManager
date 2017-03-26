package sg.edu.nus.iss.medipal.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.manager.ReportManager;


/**
 * Created by Divahar on 3/19/2017.
 */

public class ReportFragment extends Fragment {

    private TableLayout tableLayout;
    private View reportFragment;
    private TableRow rowHeader;
    private static String[] healthBioArr = {"Condition", "Start Date", "Type"};
    private static String[] consumptionArr = {"Medicine Name", "Quantity", "Consumed on"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FloatingActionButton aFab;
        reportFragment = inflater.inflate(R.layout.fragment_report_view, container, false);

        // Reference to TableLayout
        tableLayout = (TableLayout) reportFragment.findViewById(R.id.reportable);

        populateHealthBioTable();


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

}
