package sg.edu.nus.iss.medipal.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import java.io.FileNotFoundException;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FloatingActionButton aFab;
        reportFragment = inflater.inflate(R.layout.fragment_report_view, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("MediPal_FT01 - Reports");

        // Reference to TableLayout
        tableLayout = (TableLayout) reportFragment.findViewById(R.id.reportable);

        populateHealthBioTable();


        aFab = (FloatingActionButton) reportFragment.findViewById(R.id.fab);
        aFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String columnString =   "\"PersonName\",\"Gender\",\"Street1\",\"postOffice\",\"Age\"";
                String dataString   =   "\"dd\"";
                String combinedString = columnString + "\n" + dataString;

                File file   = null;
                File root   = Environment.getExternalStorageDirectory();
                if (root.canWrite()){
                    File dir    =   new File (root.getAbsolutePath() + "/sample");
                    dir.mkdirs();
                    file   =   new File(dir, "Data.csv");
                    FileOutputStream out   =   null;
                    try {
                        out = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.write(combinedString.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            Uri u1  =   null;
            u1  =   Uri.fromFile(file);

                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Medipal - Reports");
                emailIntent.putExtra(Intent.EXTRA_STREAM, u1);
                emailIntent.setType("text/html");
                startActivity(Intent.createChooser(emailIntent, "Send mail:"));
            }
        });

        return reportFragment;
    }

    private void populateHealthBioTable() {
        String[] headerArr = {"Condition", "Start Date", "Condition Type"};

        TableRow rowHeader =
                ReportManager.addHeaders(headerArr, getContext());

        tableLayout.addView(rowHeader);

        tableLayout =
                ReportManager.addContent(getContext(), tableLayout);
    }

}
